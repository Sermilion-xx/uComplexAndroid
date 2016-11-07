package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.io.File;
import java.util.ArrayList;

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

public class LoginData {

    private Context mContext;

    public LoginData(Context context){
        this.mContext = context;
    }

    public String login(String login, String password){
        String encodedAuth = HttpFactory.encodeLoginData(login + ":" + password);
        return HttpFactory.httpPost(HttpFactory.AUTHENTICATIO_URL, encodedAuth, "");
    }

    public User getUser(String jsonData){
        if (jsonData != null && !jsonData.equals("")) {
            User user = null;
            try {
                user = getUserFromJson(jsonData);
                if (user.getPhoto() == 1) {
                    File bitmapFile = FacadeMedia.getOutputMediaFile(MEDIA_TYPE_IMAGE, Environment.DIRECTORY_PICTURES, "ucomplex_profile");
                    if(bitmapFile != null){
                        String url = HttpFactory.PROFILE_IMAGE_URL+user.getCode()+".jpg";
                        HttpFactory.httpGetFile(url, bitmapFile, "");
                        Uri bitmapUri = Uri.fromFile(bitmapFile);
//                        Bitmap profileBitmap = FacadeMedia.getThumbnail(bitmapUri, mContext);
                        user.setBitmapUri(bitmapUri);
                    }else{
                        throw new NullPointerException("Bitmap file is null");
                    }
                } else {
                    FacadePreferences.deleteFromPref(mContext, "profilePhoto");
                }
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
            return user;
        }
        return null;
    }

    private User getUserFromJson(String rolesJsonStr) throws JSONException {
        ArrayList<User> userRoles = new ArrayList<>();
        JSONObject rolesJson = new JSONObject(rolesJsonStr);
        JSONArray rolesArray = rolesJson.getJSONArray("roles");
        User user = new User();

        JSONObject roles;
        for (int i = 0; i < rolesArray.length(); i++) {
            roles = rolesArray.getJSONObject(i);
            User userRole = new User();
            userRole.setId(roles.getInt("id"));
            userRole.setName(roles.getString("name"));
            userRole.setPerson(roles.getInt("person"));
            userRole.setType(roles.getInt("type"));
            userRoles.add(userRole);
        }

        JSONObject userSession = rolesJson.getJSONObject("session");
        user.setPerson(userSession.getInt("person"));
        user.setType(-1);
        if(rolesArray.length()==1){
            user.setType(rolesArray.getJSONObject(0).getInt("type"));
            user.setId(userSession.getInt("person"));
            user.setRole(rolesArray.getJSONObject(0).getInt("id"));
        }
//        for (int i = 0; i < rolesArray.length(); i++) {
//            user.setType(rolesArray.getJSONObject(i).getInt("type"));
//            user.setId(rolesArray.getJSONObject(i).getInt("id"));
//        }
        user.setPerson(userSession.getInt("person"));
        user.setPhoto(userSession.getInt("photo"));
        user.setCode(userSession.getString("code"));
        user.setName(userSession.getString("name"));
        user.setClient(userSession.getInt("client"));
        user.setEmail(userSession.getString("email"));
        user.setLogin(userSession.getString("login"));
        user.setPassword(userSession.getString("pass"));
        user.setPhone(userSession.getString("phone"));
        user.setSession(userSession.getString("session"));
        user.setRoles(userRoles);
        return user;
    }

}
