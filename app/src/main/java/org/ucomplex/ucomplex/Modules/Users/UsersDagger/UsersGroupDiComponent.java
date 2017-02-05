package org.ucomplex.ucomplex.Modules.Users.UsersDagger;

import org.ucomplex.ucomplex.Modules.Users.UsersBlackList.UsersBlackListFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersGroup.UsersGroupFragment;

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
@Component(modules = {UsersGroupModule.class})
public interface UsersGroupDiComponent {
    void inject(UsersGroupFragment fragment);
}
