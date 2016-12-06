package org.ucomplex.ucomplex.Modules.Login;


import android.graphics.Bitmap;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.UriDeserializer;
import org.ucomplex.ucomplex.CommonDependencies.UriSerializer;
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
public class LoginModel extends AbstractModel implements MVP_Login.ModelInterface{

    private static final String JSON_SESSION_KEY = "session";
    private static final String JSON_ROLES_KEY = "roles";

    public LoginModel() {

    }

    @Override
    public void loadData() {
        //User object with loadData and password
        mRepository.loadData(mUser);
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

    @Override
    public void onTaskComplete(int requestType, Object... o) {
        //o[1] - password
        if(o[0] instanceof VolleyError){
            mOnDataLoadedListener.dataLoaded(false, 0, 0);
        }else {
            try {
                mUser = (UserInterface) getDataFromJson((String) o[0]);
                if (mUser != null) {
                    mUser.setPassword((String) o[1]);
                }
                mOnDataLoadedListener.dataLoaded(mUser != null, 0, 0);
            } catch (JSONException e) {
                e.printStackTrace();
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
