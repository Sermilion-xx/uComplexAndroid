package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.os.Bundle;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractRepository;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginRepository extends AbstractRepository {

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
                RequestType.HTTP_RECEIVE,
                null,
                password);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void loadData(Bundle bundle) {
        //Dummy user with just loginRequest and password
        UserInterface user = bundle.getParcelable(Constants.EXTRA_KEY_USER);
        String password = user.getPassword();
        loginRequest(user.getLogin(), password);
    }

    public UserInterface loadLoggedUser() {
        return FacadePreferences.getUserDataFromPref(mContext);
    }
}
