package org.ucomplex.ucomplex.Modules;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerFragment;
import org.ucomplex.ucomplex.Interfaces.IFragment;

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

    public static IFragment getFragmentWithName(String name, BaseActivity activity) {

        if (name.equals(BaseRecyclerFragment.class.getName())) {
            return BaseRecyclerFragment.getInstance(activity);
        }
        return null;
    }

}
