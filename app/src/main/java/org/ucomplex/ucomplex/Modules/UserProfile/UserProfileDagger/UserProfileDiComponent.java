package org.ucomplex.ucomplex.Modules.UserProfile.UserProfileDagger;

import org.ucomplex.ucomplex.Modules.UserProfile.UserProfileActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Singleton
@Component(modules = {UserProfileModule.class})
public interface UserProfileDiComponent {
    void inject(UserProfileActivity activity);
}