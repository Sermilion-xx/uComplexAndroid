package org.ucomplex.ucomplex.Modules.Users.UsersLecturers;

import android.app.Activity;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.ucomplex.ucomplex.Modules.Users.UsersGroup.UsersGroupFragment;

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

public class UsersLecturersFragment extends MVPViewBaseFragment<String, List<IRecyclerItem>> {

    public static UsersLecturersFragment getInstance(Activity mContext) {
        UsersLecturersFragment fragment = new UsersLecturersFragment();
        fragment.setContext(mContext);
        return fragment;
    }

}
