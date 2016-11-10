package org.ucomplex.ucomplex.Modules;

import android.app.Application;

import org.ucomplex.ucomplex.Modules.Login.LoginDagger.DaggerLoginDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.LoginDiComponent;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MyApplication extends Application{
    LoginDiComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerLoginDiComponent.builder().build();
    }

    public LoginDiComponent getComponent() {
        return component;
    }
}
