package org.ucomplex.ucomplex.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import lombok.Cleanup;
import lombok.val;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Okio;

/**
 * Created by Sermilion on 20/09/16.
 */
public class HttpFactory {

    private static final String SCHEMA = "https://";
    public static final String BASE_URL = SCHEMA + "ucomplex.org/";
    public static final String USER_EVENTS_URL = BASE_URL + "user/events?mobile=1";
    public static final String PROFILE_IMAGE_URL = BASE_URL + "files/photos/";
    public static final String AUTHENTICATIO_URL = BASE_URL + "auth?mobile=1";
    public static final String RESTORE_PASSWORD_URL = BASE_URL + "public/password?mobile=1";
    public static final String LOAD_PROFILE_URL = BASE_URL + "files/photos/";

    public static void httpGetFile(@NonNull String url, @NonNull File destFile, String encodedAuth) {
        try {
            val okHttpClient = new OkHttpClient();
            val request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Basic " + encodedAuth)
                    .build();
            val response = okHttpClient.newCall(request).execute();
            val body = response.body();
//        val contentLength = body.contentLength()
            @Cleanup val source = body.source();
            @Cleanup val sink = Okio.buffer(Okio.sink(destFile));
            val sinkBuffer = sink.buffer();
            long totalBytesRead = 0;
            val bufferSize = 8 * 1024L;
            long bytesRead;
            while (source.read(sinkBuffer, bufferSize) != -1L) {
                bytesRead = source.read(sinkBuffer, bufferSize);
                sink.emit();
                totalBytesRead += bytesRead;
//            val progress = (totalBytesRead * 100 / contentLength) as Int
//            publishProgress(progress)
            }
            sink.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String httpGet(String url) {
        return "";
    }

    public static String encodeLoginData(String loginData) {
        byte[] authBytes;
        try {
            authBytes = loginData.getBytes("UTF-8");
            int flags = Base64.NO_WRAP | Base64.URL_SAFE;
            return Base64.encodeToString(authBytes, flags);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to perform HTTP request
     *
     * @param url         - url of request
     * @param encodedAuth - encoded authorization header
     * @param jsonBody    json body
     * @return json response from server
     */
    public static String httpPost(String url, String encodedAuth, String jsonBody) {
        String result = "";
        try {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, jsonBody);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Basic " + encodedAuth)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean httpPostFile(String url, String encodedAuth, File file, String fileName) {
        val MEDIA_TYPE = MediaType.parse("image/*");
        val client = new OkHttpClient();
        val builder = new MultipartBody.Builder();

        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", "fileName", RequestBody.create(MEDIA_TYPE, file));
        builder.setType(MediaType.parse("multipart/form-data"));
        val requestBody = builder.build();
        val request = new Request.Builder()
                .url(url)
                .post(requestBody).addHeader("Authorization", "Basic " + encodedAuth)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static void httpVolley(String url,
                                  final String encodedAuth,
                                  Context context,
                                  final OnTaskCompleteListener onTaskCompleteListener,
                                  final String requestType, final Object...returnData) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String utf8String;
                        try {
                            utf8String = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            Object data = null;
                            if(returnData.length>0){
                                data = returnData[0];
                            }
                            onTaskCompleteListener.onTaskComplete("", utf8String, data);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                onTaskCompleteListener.onTaskComplete(requestType, error, 0);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Basic " + encodedAuth);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}
