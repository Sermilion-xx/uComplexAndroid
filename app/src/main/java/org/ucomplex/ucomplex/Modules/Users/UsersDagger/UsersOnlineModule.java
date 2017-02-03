package org.ucomplex.ucomplex.Modules.Users.UsersDagger;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlineModel;
import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlinePresenter;

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
public class UsersOnlineModule {

    @Provides
    MVPPresenterRecycler<String, List<IRecyclerItem>> getUsersOnlinePresenter(){
        return new UsersOnlinePresenter();
    }

    @Provides
    MVPModelRecycler<String, List<IRecyclerItem>> getUsersOnlineModel(){
        return new UsersOnlineModel();
    }

}
