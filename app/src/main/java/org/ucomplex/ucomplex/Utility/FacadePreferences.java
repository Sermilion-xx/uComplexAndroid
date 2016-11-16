package org.ucomplex.ucomplex.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

/**
 * Created by Sermilion on 01/11/2016.
 */

public class FacadePreferences {

    public static final String KEY_PREF_USER_ROLE = "userRole";
    public static final String KEY_PREF_LOGIN_DATA = "userRole";
    public static final String KEY_PREF_LOGGED_USER = "loggedUser";
    public static final String KEY_PREF_PROFILE_PHOTO = "profilePhoto";

    public static void setRoleToPref(Context mContext, int role) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putInt(KEY_PREF_USER_ROLE, role);
        editor.apply();
    }

    public static int getRoleFromPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        int role = pref.getInt(KEY_PREF_USER_ROLE, -2);
        return role;
    }

    public static String getLoginDataFromPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = pref.getString(KEY_PREF_LOGIN_DATA, "");
        return json;
    }

    public static void setLoginDataToPref(Context mContext, String loginData) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putString(KEY_PREF_LOGIN_DATA, loginData);
        editor.apply();
    }

    public static User getUserDataFromPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = pref.getString(KEY_PREF_LOGGED_USER, "");
        return gson.fromJson(json, User.class);
    }

    public static void setUserDataToPref(Context mContext, User user) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_PREF_LOGGED_USER, json);
        editor.apply();
    }

    public static void deleteFromPref(Context context, String typeStr) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(typeStr);
        editor.apply();
    }

}
