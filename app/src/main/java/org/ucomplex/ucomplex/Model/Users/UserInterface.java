package org.ucomplex.ucomplex.Model.Users;

import android.net.Uri;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public interface UserInterface {
    int getType();
    String getLogin();
    String getPassword();
    List<Role> getRoles();
    void setPassword(String password);
    void setLogin(String login);
    int getPhoto();
    void setPhoto(int photo);
    String getCode();
    void setCode(String code);
    void setBitmapUriString(String uri);
    String getBitmapUriStringFromUri(Uri uri);
    Uri getBitmapUriFromUriString();
    String getBitmapUriString();
    String getName();
    void setName(String name);
    int getId();
    void setId(int id);
    void setType(int type);
    int getPerson();



}
