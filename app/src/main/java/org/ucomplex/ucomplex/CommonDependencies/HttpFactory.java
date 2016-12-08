package org.ucomplex.ucomplex.CommonDependencies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.codehaus.jackson.map.util.EnumValues;
import org.greenrobot.eventbus.EventBus;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventBusFactory;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.EventType;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Interfaces.IRequestEventBusEvent;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sermilion on 20/09/16.
 */
public class HttpFactory {

    private StringRequest stringRequest;
    private static HttpFactory mInstance;

    private HttpFactory() {

    }


    public static HttpFactory getInstance() {
        if (mInstance == null) {
            mInstance = new HttpFactory();
        }
        return mInstance;
    }

    public void cancel() {
        if (stringRequest != null)
            stringRequest.cancel();
    }

    private static final String SCHEMA = "https://";
    public static final String BASE_URL = SCHEMA + "ucomplex.org/";
    public static final String USER_EVENTS_URL = BASE_URL + "user/events?mobile=1";
    public static final String PROFILE_IMAGE_URL = BASE_URL + "files/photos/";
    public static final String AUTHENTICATIO_URL = BASE_URL + "auth?mobile=1";
    public static final String RESTORE_PASSWORD_URL = BASE_URL + "public/password?mobile=1";
    public static final String LOAD_PROFILE_URL = BASE_URL + "files/photos/";
    public static final String USER_SUBJECTS_USER_URL = BASE_URL +"student/subjects_list?json";
    public static final String USER_SUBJECTS_TEACHER_URL = BASE_URL +"teacher/subjects_list?json";
    public static final String USER_SUBJECT_URL = BASE_URL +"student/ajax/my_subjects?json";
    public static final String GET_PHOTO_URL = BASE_URL +"files/photos/";


    String urlStudentString = "https://ucomplex.org/student/subjects_list?json";
    String urlTeacherString = "https://ucomplex.org/teacher/subjects_list?json";

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

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void httpVolley(String url,
                           final String encodedAuth,
                           Context context,
                           final EventType requestType, HashMap<String, String> params, final Object... returnData) {
        if (params == null) {
            params = new HashMap<>();
        }

        final HashMap<String, String> finalParams = params;
        RequestQueue queue = Volley.newRequestQueue(context);

        final IRequestEventBusEvent event = EventBusFactory.getHTTPEvent();
        event.setEventType(requestType);

        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String utf8String;
                        try {
                            utf8String = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            Object data = null;
                            if (returnData.length > 0) {
                                data = returnData[0];
                            }

                            event.setResult(utf8String);
                            event.addOptionalData(data);
                            EventBus.getDefault().post(event);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                event.setError(error);
                EventBus.getDefault().post(event);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return finalParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "Basic " + encodedAuth);
                return header;
            }
        };
        queue.add(stringRequest);
    }
}
