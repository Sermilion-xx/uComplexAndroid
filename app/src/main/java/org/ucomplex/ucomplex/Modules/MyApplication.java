package org.ucomplex.ucomplex.Modules;

import android.app.Application;

import org.ucomplex.ucomplex.Modules.Login.LoginDagger.DaggerLoginDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.LoginDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.DaggerRoleDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.RoleDiComponent;

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
    LoginDiComponent loginDiComponent;
    RoleDiComponent roleDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        loginDiComponent = DaggerLoginDiComponent.builder().build();
        roleDiComponent  = DaggerRoleDiComponent.builder().build();
    }

    public LoginDiComponent getLoginDiComponent() {
        return loginDiComponent;
    }

    public RoleDiComponent getRoleDiComponent() {
        return roleDiComponent;
    }
}
