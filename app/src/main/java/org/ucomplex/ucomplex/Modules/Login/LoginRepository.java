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

    private Context mContext;
    private OnTaskCompleteListener mModelTaskCompleteListener; //Model

    @Override
    public void setTaskCompleteListener(OnTaskCompleteListener mTaskCompleteListener) {
        this.mModelTaskCompleteListener = mTaskCompleteListener;
    }


    LoginRepository(Context context) {
        this.mContext = context;
    }

    public LoginRepository() {
    }

    private void loginRequest(final String login, final String password) {
        final String encodedAuth = HttpFactory.encodeLoginData(login + ":" + password);
        HttpFactory.httpVolley(HttpFactory.AUTHENTICATIO_URL,encodedAuth,mContext,mModelTaskCompleteListener,Constants.REQUEST_LOGIN, password);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void loadData(Object... params) {
        //Dummy user with just loginRequest and password
        UserInterface user = (UserInterface) params[0];
        String password = user.getPassword();
        loginRequest(user.getLogin(), password);
    }


    @Override
    public void setContext(Context context) {
        this.mContext = context;
    }

    public UserInterface loadLoggedUser() {
        return FacadePreferences.getUserDataFromPref(mContext);
    }
}
