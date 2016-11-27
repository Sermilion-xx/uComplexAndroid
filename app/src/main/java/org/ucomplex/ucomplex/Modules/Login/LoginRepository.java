package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginRepository implements Repository {

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";

    private Context mContext;
    private OnTaskCompleteListener mModelTaskCompleteListener; //Model

    void setTaskCompleteListener(OnTaskCompleteListener mTaskCompleteListener) {
        this.mModelTaskCompleteListener = mTaskCompleteListener;
    }


    LoginRepository(Context context) {
        this.mContext = context;
    }

    public LoginRepository() {
    }

    //TODO: test
    private void loginRequest(final String login, final String password) {
        final String encodedAuth = HttpFactory.encodeLoginData(login + ":" + password);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpFactory.AUTHENTICATIO_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String utf8String = "";
                        try {
                            utf8String = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        UserInterface user = unpackUserFromJsonString(utf8String, password);
                        mModelTaskCompleteListener.onTaskComplete(Constants.REQUEST_LOGIN, user, 0);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mModelTaskCompleteListener.onTaskComplete(Constants.REQUEST_LOGIN, null, 0);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", "Basic " + encodedAuth);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    //TODO: test
    private UserInterface unpackUserFromJsonString(String jsonData, String password){
        UserInterface user;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getJSONArray(JSON_ROLES_KEY) == null) {
                return null;
            } else {
                user = getUserFromJson(jsonData);
                if (user.getPhoto() == 1) {
                    user.setBitmapUri(FacadeMedia.createFileForBitmap(user.getCode()));
                } else {
                    FacadePreferences.deleteFromPref(mContext, FacadePreferences.KEY_PREF_PROFILE_PHOTO);
                }
                user.setPassword(password);
                return user;
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    //TODO: test
    @Override
    public void loadData(Object... params) {
        //Dummy user with just loginRequest and password
        UserInterface user = (UserInterface) params[0];
        String password = user.getPassword();
        loginRequest(user.getLogin(), password);
    }


    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        Gson gson = new Gson();
        JSONObject userSession = rolesJson.getJSONObject(JSON_SESSION_KEY);
        return gson.fromJson(userSession.toString(), User.class);
    }

    @Override
    public void setContext(Context context) {
        this.mContext = context;
    }

    public UserInterface loadLoggedUser() {
        return FacadePreferences.getUserDataFromPref(mContext);
    }
}
