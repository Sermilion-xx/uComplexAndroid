package org.ucomplex.ucomplex.CommonDependencies.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.oneread.aghanim.components.utility.MVPCallback;

import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.Network.Retrifit.FileUploadService;
import org.ucomplex.ucomplex.CommonDependencies.Network.Retrifit.ServiceGenerator;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel.FILE_URI;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter.EXTRA_KEY_FOLDER;

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
    public static final String LOAD_PHOTO_URL = BASE_URL + "files/photos/";
    public static final String USER_SUBJECTS_USER_URL = BASE_URL + "student/subjects_list?mobile=1";
    public static final String USER_SUBJECTS_TEACHER_URL = BASE_URL + "teacher/subjects_list?mobile=1";
    public static final String USER_SUBJECT_URL = BASE_URL + "student/ajax/my_subjects?mobile=1";
    public static final String GET_PHOTO_URL = BASE_URL + "files/photos/";
    public static final String CALENDAR_BELT_URL = BASE_URL + "student/ajax/calendar_belt?mobile=1";
    public static final String TEACHERS_FILES_URL = BASE_URL + "student/ajax/teacher_files?mobile=1";
    public static final String STUDENTS_FILES_URL = BASE_URL + "student/my_files?mobile=1";
    public static final String DOWNLOAD_MATERIAL_URL = SCHEMA + "storage.ucomplex.org/files/users/";
    public static final String DELETE_FILE_URL = BASE_URL + "student/my_files/delete_file?mobile=1";
    public static final String RENAME_FILE_URL = BASE_URL + "student/my_files/rename_file?mobile=1";
    public static final String GET_FILE_ACCESS_URL = BASE_URL + "teacher/my_files/get_access?mobile=1";
    public static final String USERS_ONLINE_URL = BASE_URL + "student/online?mobile=1";
    public static final String USERS_SEARCH_URL = BASE_URL + "user/user_search/action";
    public static final String USERS_FRIENDS_URL = BASE_URL + "user/friends?mobile=1";
    public static final String USERS_GROUP_URL = BASE_URL + "student/ajax/my_group?mobile=1";
    public static final String USERS_LECTURERS_URL = BASE_URL + "student/ajax/my_teachers?mobile=1";
    public static final String USERS_BLACKLIST_URL = BASE_URL + "user/blacklist?mobile=1";
    public static final String UPLOAD_FILE_URL = BASE_URL + "student/my_files/add_files?mobile=1";
    public static final String CREATE_FOLDER_URL = BASE_URL + "student/my_files/create_folder?mobile=1";
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
                           Map<String, String> params,
                           MVPCallback<String> callback) {
        if (params == null) {
            params = new HashMap<>();
        }

        final Map<String, String> finalParams = params;
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

    public static void uploadFile(Bundle bundle,
                                  Context context,
                                  MVPCallback<ResponseBody> mvpCallback) {
        String authString = bundle.getString(AUTH_STRING);
        Uri fileUri = bundle.getParcelable(FILE_URI);
        String folder = null;
        if(bundle.get(EXTRA_KEY_FOLDER)!=null){
            folder = bundle.getString(EXTRA_KEY_FOLDER);
        }
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class, authString);
        String filePath = FacadeMedia.getPath(context, fileUri);
        if (filePath != null) {
            File uploadFile = new File(filePath);
            RequestBody requestFile = RequestBody.create(
                            MediaType.parse(context.getContentResolver().getType(fileUri)),
                            uploadFile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", uploadFile.getName(), requestFile);

            Call<ResponseBody> call = service.upload(body, folder);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        mvpCallback.onSuccess(response.body());
                    }else {
                        try {
                            Log.e("Bad request: ", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mvpCallback.onError(new Throwable(response.errorBody().toString()));
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mvpCallback.onError(t);
                }
            });
        }
    }

}
