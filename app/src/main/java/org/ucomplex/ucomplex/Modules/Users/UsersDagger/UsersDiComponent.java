package org.ucomplex.ucomplex.Modules.Users.UsersDagger;

import org.ucomplex.ucomplex.Modules.Users.UsersActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Singleton
@Component(modules = {UsersModule.class})
public interface UsersDiComponent {
    void inject(UsersActivity activity);
}
