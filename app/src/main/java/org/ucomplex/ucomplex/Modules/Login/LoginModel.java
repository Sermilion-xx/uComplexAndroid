package org.ucomplex.ucomplex.Modules.Login;


import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

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
public class LoginModel implements MVP_Login.ModelInterface, OnTaskCompleteListener {

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";
    private Repository mRepository;
    private UserInterface mUser = new User();
    private OnDataLoadedListener mOnDataLoadedListener;

    public LoginModel(LoginRepository dao) {
        mRepository = dao;
    }

    public LoginModel() {

    }

    void setOnDataLoadedListener(OnDataLoadedListener mOnDataLoadedListener) {
        this.mOnDataLoadedListener = mOnDataLoadedListener;
    }

    public void setPresenter(Context context) {
        mRepository = new LoginRepository(context);
        ((LoginRepository) mRepository).setTaskCompleteListener(this);
    }

    @Override
    public void setData(Object data) {
        this.mUser = (UserInterface) data;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
        ((LoginRepository) mRepository).setTaskCompleteListener(this);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mRepository = null;
        }
    }

    @Override
    public void loadData() {
        //User object with loadUser and password
        mRepository.loadData(mUser);
    }

    @Override
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return HttpFactory.httpPost(HttpFactory.RESTORE_PASSWORD_URL, "", json);
    }

    @Override
    public UserInterface getUser() {
        return mUser;
    }

    public UserInterface loadLoggedUser() {
        return ((LoginRepository) mRepository).loadLoggedUser();
    }

    @Override
    public void onTaskComplete(String requestType, Object... o) {
        //o[1] - password
        mUser = unpackUserFromJsonString((String) o[0]);
        if (mUser != null) {
            mUser.setPassword((String) o[1]);
        }
        mOnDataLoadedListener.dataLoaded(mUser != null);
    }

    private UserInterface unpackUserFromJsonString(String jsonData) {
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
                    FacadePreferences.deleteFromPref(((LoginRepository) mRepository).getContext(), FacadePreferences.KEY_PREF_PROFILE_PHOTO);
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
