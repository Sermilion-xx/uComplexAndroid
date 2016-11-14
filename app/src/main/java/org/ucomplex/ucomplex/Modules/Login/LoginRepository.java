package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

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

    public LoginRepository(Context context) {
        this.mContext = context;
    }

    public LoginRepository() {
    }

    public String login(String login, String password) {
        String encodedAuth = HttpFactory.encodeLoginData(login + ":" + password);
        return HttpFactory.httpPost(HttpFactory.AUTHENTICATIO_URL, encodedAuth, "");
    }


    public UserInterface loadData(Object... params) {
        UserInterface user = (UserInterface) params[0];
        try {
            String jsonData = login(user.getLogin(), user.getPassword());
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getJSONArray("roles") == null) {
                return null;
            }
            if (jsonData != null && !jsonData.equals("")) {
                user = getUserFromJson(jsonData);
                if (user.getPhoto() == 1) {
                    File bitmapFile = FacadeMedia.getOutputMediaFile(MEDIA_TYPE_IMAGE, Environment.DIRECTORY_PICTURES, "ucomplex_profile");
                    if (bitmapFile != null) {
                        String url = HttpFactory.PROFILE_IMAGE_URL + user.getCode() + ".jpg";
                        HttpFactory.httpGetFile(url, bitmapFile, "");
                        Uri bitmapUri = Uri.fromFile(bitmapFile);
//                        Bitmap profileBitmap = FacadeMedia.getThumbnail(bitmapUri, mContext);
                        user.setBitmapUri(bitmapUri);
                    } else {
                        throw new NullPointerException("Bitmap file is null");
                    }
                } else {
                    FacadePreferences.deleteFromPref(mContext, "profilePhoto");
                }
                return user;
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }


    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        Gson gson = new Gson();
        JSONObject userSession = rolesJson.getJSONObject("session");
        return gson.fromJson(userSession.toString(), User.class);
    }

    @Override
    public void setContext(Context context) {
        this.mContext = context;
    }
}
