package org.ucomplex.ucomplex.Modules.Users.UsersFragments;

import android.app.Activity;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersBlackListFragment extends MVPViewBaseFragment<String, List<IRecyclerItem>> {

    public static UsersBlackListFragment getInstance(Activity mContext) {
        UsersBlackListFragment fragment = new UsersBlackListFragment();
        fragment.setContext(mContext);
        return fragment;
    }

}
