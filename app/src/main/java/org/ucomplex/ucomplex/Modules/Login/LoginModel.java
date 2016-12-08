package org.ucomplex.ucomplex.Modules.Login;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Implementations.BaseHTTPRequestEvent;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModel;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

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
public class LoginModel extends AbstractModel implements MVP_Login.ModelInterface {

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";

    public LoginModel() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void loadData() {
        //User object with loadData and password
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_KEY_USER, (Parcelable) mUser);
        mRepository.loadData(bundle);
    }

    @Override
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return null;
    }


    UserInterface loadLoggedUser() {
        return ((LoginRepository) mRepository).loadLoggedUser();
    }

    @Override
    public Object getDataFromJson(String jsonString) throws JSONException {
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


    @Subscribe
    public void onReceiveHTTTRequestCompleteEvent(BaseHTTPRequestEvent event) {
        if (event.getEventType() == RequestType.LOGIN) {
            if (!event.hasError()) {
                try {
                    String result = event.getResult();
                    mUser = (UserInterface) getDataFromJson(result);
                    if (mUser != null) {
                        mUser.setPassword((String) event.getDataAtIndex(0));
                    }
                    mOnDataLoadedListener.dataLoaded(mUser != null, 0, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                mOnDataLoadedListener.dataLoaded(false, 0, 0);
            }
        }
    }

    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        Gson gson = new Gson();
        JSONObject userSession = rolesJson.getJSONObject(JSON_SESSION_KEY);
        return gson.fromJson(userSession.toString(), User.class);
    }
}
