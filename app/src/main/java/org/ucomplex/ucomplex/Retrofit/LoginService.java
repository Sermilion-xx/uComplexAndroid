package org.ucomplex.ucomplex.Retrofit;

import org.ucomplex.ucomplex.Model.Users.User;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 20/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface LoginService {
    @POST(HTTPService.AUTHENTICATIO_URL)
    Call<String> login();
}
