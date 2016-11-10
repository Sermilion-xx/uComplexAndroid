package org.ucomplex.ucomplex.Modules.Login.LoginDagger;

import org.ucomplex.ucomplex.Modules.Login.LoginModel;
import org.ucomplex.ucomplex.Modules.Login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Module
public class LoginModule {
    @Provides
    @Singleton
    public LoginPresenter getLoginPresenter(){
        return new LoginPresenter();
    }

    @Provides
    @Singleton
    public LoginModel getLoginModel(){
        return new LoginModel();
    }
}
