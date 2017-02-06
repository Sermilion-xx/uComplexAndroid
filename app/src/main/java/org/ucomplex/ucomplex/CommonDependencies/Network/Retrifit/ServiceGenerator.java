package org.ucomplex.ucomplex.CommonDependencies.Network.Retrifit;

import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory.BASE_URL;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ServiceGenerator {

    private static Retrofit retrofit;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, String authString) {
        httpClient.addInterceptor(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", " Basic "+authString).build();
            return chain.proceed(request);
        });
        retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}