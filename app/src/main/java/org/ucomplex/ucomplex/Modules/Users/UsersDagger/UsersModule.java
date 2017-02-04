package org.ucomplex.ucomplex.Modules.Users.UsersDagger;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.Modules.Users.UsersBlackList.UsersBlackListModel;
import org.ucomplex.ucomplex.Modules.Users.UsersFriends.UsersFriendsModel;
import org.ucomplex.ucomplex.Modules.Users.UsersGroup.UsersGroupModel;
import org.ucomplex.ucomplex.Modules.Users.UsersLecturers.UsersLecturersModel;
import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlineModel;
import org.ucomplex.ucomplex.Modules.Users.UsersPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Module
public class UsersModule {

    @Provides
    MVPPresenterRecycler<String, List<IRecyclerItem>> getUsersPresenter() {
        return new UsersPresenter();
    }

    @Provides
    UsersOnlineModel getUsersOnlineModel() {
        return new UsersOnlineModel();
    }

    @Provides
    UsersFriendsModel getUsersFriendsModel() {
        return new UsersFriendsModel();
    }

    @Provides
    UsersGroupModel getUsersGroupModel() {
        return new UsersGroupModel();
    }

    @Provides
    UsersLecturersModel getUsersLecturersModel() {
        return new UsersLecturersModel();
    }

    @Provides
    UsersBlackListModel getUsersBlackListModel() {
        return new UsersBlackListModel();
    }

}
