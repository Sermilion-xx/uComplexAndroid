package org.ucomplex.ucomplex.Modules.Users.UsersFragments;

import android.app.Activity;
import android.os.Bundle;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 05/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersFragmentFactory {

    public static MVPViewBaseFragment getUsersFragment(int type, Activity activity, Bundle arguments){
        MVPViewBaseFragment fragment = null;
        switch (type){
            case 0:
                fragment = UsersOnlineFragment.getInstance(activity);
            break;
            case 1:
                fragment = UsersFriendsFragment.getInstance(activity);
                break;
            case 2:
                fragment = UsersGroupFragment.getInstance(activity);
                break;
            case 3:
                fragment = UsersLecturersFragment.getInstance(activity);
                break;
            case 4:
                fragment = UsersBlackListFragment.getInstance(activity);
                break;
        }
        if(fragment!=null){
            fragment.setArguments(arguments);
        }
        return fragment;
    }
}
