package org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger;

import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;

import javax.inject.Singleton;

import dagger.Component;
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
@Singleton
@Component(modules = {RoleModule.class})
public interface RoleDiComponent {
    void inject(RoleSelectActivity activity);
}
