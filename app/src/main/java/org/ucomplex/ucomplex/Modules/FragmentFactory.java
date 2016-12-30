package org.ucomplex.ucomplex.Modules;

import net.oneread.aghanim.components.base.BaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;
import net.oneread.aghanim.mvp.basemvp.MVPPresenter;
import net.oneread.aghanim.mvp.recyclermvp.PresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 26/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentFactory {

    public static IFragment getFragmentWithName(String name,
                                                MVPPresenter presenter,
                                                int fragmentLayout,
                                                int recyclerId) {
        if (name.equals(BaseRecyclerFragment.class.getName())) {
            return BaseRecyclerFragment.getInstance((PresenterRecycler) presenter, fragmentLayout, recyclerId);
        }
        return null;
    }

}
