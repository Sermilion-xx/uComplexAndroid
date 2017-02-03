package org.ucomplex.ucomplex.CommonDependencies.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.oneread.aghanim.components.utility.MVPCallback;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 20/09/16.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
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
    private static final String BASE_URL = SCHEMA + "ucomplex.org/";
    public static final String USER_EVENTS_URL = BASE_URL + "user/events?mobile=1";
    public static final String PROFILE_IMAGE_URL = BASE_URL + "files/photos/";
    public static final String AUTHENTICATIO_URL = BASE_URL + "auth?mobile=1";
    public static final String RESTORE_PASSWORD_URL = BASE_URL + "public/password?mobile=1";
    public static final String LOAD_PROFILE_URL = BASE_URL + "files/photos/";
    public static final String USER_SUBJECTS_USER_URL = BASE_URL + "student/subjects_list?mobile=1";
    public static final String USER_SUBJECTS_TEACHER_URL = BASE_URL + "teacher/subjects_list?mobile=1";
    public static final String USER_SUBJECT_URL = BASE_URL + "student/ajax/my_subjects?mobile=1";
    public static final String GET_PHOTO_URL = BASE_URL + "files/photos/";
    public static final String CALENDAR_BELT_URL = BASE_URL + "student/ajax/calendar_belt?mobile=1";
    public static final String TEACHERS_FILES_URL = BASE_URL + "student/ajax/teacher_files?mobile=1";
    public static final String STUDENTS_FILES_URL = BASE_URL + "student/my_files?mobile=1";
    public static final String DOWNLOAD_MATERIAL_URL = SCHEMA + "storage.ucomplex.org/files/users/";
    public static final String DELETE_FILE_URL = BASE_URL + "student/my_files/delete_file?mobile=1";
    public static final String RENAME_FILE_URL = BASE_URL + "student/my_files/rename_file";
    public static final String GET_FILE_ACCESS_URL = BASE_URL + "teacher/my_files/get_access";


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
                           HashMap<String, String> params,
                           MVPCallback<String> callback) {
        if (params == null) {
            params = new HashMap<>();
        }

        final HashMap<String, String> finalParams = params;
        RequestQueue queue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                response -> {
                    String utf8String;
                    try {
                        utf8String = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        callback.onSuccess(utf8String);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            callback.onError(error);
            error.printStackTrace();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}
