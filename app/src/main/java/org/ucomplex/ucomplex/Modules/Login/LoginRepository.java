package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

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
        HttpFactory.getInstance().httpVolley(HttpFactory.AUTHENTICATIO_URL,
                encodedAuth,
                mContext,
                mModelTaskCompleteListener,
                Constants.REQUEST_LOGIN,
                null,
                password);
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
