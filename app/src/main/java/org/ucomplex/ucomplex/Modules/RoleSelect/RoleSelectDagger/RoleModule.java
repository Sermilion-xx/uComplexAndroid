package org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger;

import org.ucomplex.ucomplex.Modules.Login.LoginModel;
import org.ucomplex.ucomplex.Modules.Login.LoginPresenter;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleModel;
import org.ucomplex.ucomplex.Modules.RoleSelect.RolePresenter;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleRepository;

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
public class RoleModule {
    @Provides
    @Singleton
    public RolePresenter getRolePresenter(){
        return new RolePresenter();
    }

    @Provides
    @Singleton
    public RoleModel getRoleModel(){
        return new RoleModel();
    }

    @Provides
    @Singleton
    public RoleRepository getRoleRepository(){
        return new RoleRepository();
    }
}
