package org.ucomplex.ucomplex.Utility;

import android.support.annotation.NonNull;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public static final String BASE_URL = SCHEMA+"ucomplex.org/";

    public static void httpGetFile(@NonNull String url, @NonNull File destFile) {
        try {
            val okHttpClient = new OkHttpClient();
            val request = new Request.Builder().url(url).build();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String httpGet(String url){
        return "";
    }

    private String encodeLoginData(String loginData){
        byte[] authBytes = null;
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
     * @param url - url of request
     * @param encodedAuth - encoded authorization header
     * @param jsonBody json body
     * @return json response from server
     */
    public static String httpPost(String url, String encodedAuth, String jsonBody) {
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
            response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
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

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
