package org.ucomplex.ucomplex.Modules.Users.UsersDagger;

import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersFriendsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Singleton
@Component(modules = {UsersOnlineModule.class})
public interface UsersFriendsDiComponent {
    void inject(UsersFriendsFragment fragment);
}
