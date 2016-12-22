package org.ucomplex.ucomplex.Modules.Login;


import android.net.Uri;

import com.google.gson.Gson;

import net.oneread.sermilionmvp.Data.DataSource;
import net.oneread.sermilionmvp.MVP.AbstractMVP.AbstractMVPModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies.UserDeserializationStrategy;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.UCJsonDeserializer;
import org.ucomplex.ucomplex.Retrofit.LoginService;
import org.ucomplex.ucomplex.Retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.ucomplex.ucomplex.Retrofit.ServiceGenerator.API_BASE_URL;

/**
 * Model layer on Model View PresenterToViewInterface Pattern
 * <p>
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class LoginModel extends AbstractMVPModel {

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";

    private UserInterface mUser;

    public UserInterface getUser() {
        return mUser;
    }

    public void setUser(UserInterface user) {
        this.mUser = user;
    }

    public LoginModel() {

    }
    //MVP library
    @Override
    public void loadData(DataSource.LoadJsonCallback loadJsonCallback) {
        String login = mUser.getLogin();
        String password = mUser.getPassword();

        LoginService loginService =
                ServiceGenerator.createService(LoginService.class, login, password, "");
        Call<String> call = loginService.login();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    mUser = getDataFromJson(response.body());
                    loadJsonCallback.onTasksLoaded(response.body());
                    if (mUser != null) {
                        mUser.setPassword(password);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadJsonCallback.onDataNotAvailable(t.getMessage());
            }
        });

    }

    private UserInterface getDataFromJson(String jsonString) throws JSONException {
        UserInterface user;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.getJSONArray(JSON_ROLES_KEY) == null) {
                return null;
            } else {
                user = getUserFromJson(jsonString);
                if (user.getPhoto() == 1) {
                    Uri bitmapUri = FacadeMedia.createFileForBitmap();
                    String uriString = user.getBitmapUriStringFromUri(bitmapUri);
                    user.setBitmapUriString(uriString);
                }
                return user;
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        Gson gson = new Gson();
        JSONObject userSession = rolesJson.getJSONObject(JSON_SESSION_KEY);
        return gson.fromJson(userSession.toString(), User.class);
    }

}
