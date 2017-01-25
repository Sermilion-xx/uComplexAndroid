package org.ucomplex.ucomplex.Modules.Login;


import android.net.Uri;
import android.os.Bundle;

import com.google.gson.Gson;

import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Domain.Users.User;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;

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
public class LoginModel extends MVPAbstractModel<String, UserInterface> {

    private UserInterface mUser;
    private String tempPassword;

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";

    String getTempPassword() {
        String password = tempPassword;
        tempPassword = null;
        return password;
    }

    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return null;
    }

    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        Gson gson = new Gson();
        JSONObject userSession = rolesJson.getJSONObject(JSON_SESSION_KEY);
        return gson.fromJson(userSession.toString(), User.class);
    }

    @Override
    public void loadData(MVPCallback<UserInterface> mvpCallback, Bundle... bundles) {
        tempPassword = mUser.getPassword();
        final String encodedAuth = HttpFactory.encodeLoginData(mUser.getLogin() + Constants.AUTH_DELIMETER + mUser.getPassword());
        HttpFactory.getInstance().httpVolley(HttpFactory.AUTHENTICATIO_URL,
                encodedAuth,
                mContext,
                null,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        mUser = processJson(s);
                        mvpCallback.onSuccess(mUser);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                }
        );
    }

    @Override
    public UserInterface processJson(String s) {
        UserInterface user;
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.getJSONArray(JSON_ROLES_KEY) == null) {
                return null;
            } else {
                user = getUserFromJson(s);
                if (user.getPhoto() == 1) {
                    Uri bitmapUri = FacadeMedia.createFileForBitmap();
                    String uriString = user.getBitmapUriStringFromUri(bitmapUri);
                    user.setBitmapUriString(uriString);
                } else {
                    FacadePreferences.deleteFromPref(mContext, FacadePreferences.KEY_PREF_PROFILE_PHOTO);
                }
                setUser(user);
                return user;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUser(UserInterface user) {
        this.mUser = user;
    }

    public UserInterface getUser() {
        return mUser;
    }
}
