package org.ucomplex.ucomplex.CommonDependencies.Network.Retrifit;

import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface FileUploadService {

    @Multipart
    @POST(HttpFactory.UPLOAD_FILE_URL)
    Call<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Header("Content-Disposition") String content_disposition
    );
}
