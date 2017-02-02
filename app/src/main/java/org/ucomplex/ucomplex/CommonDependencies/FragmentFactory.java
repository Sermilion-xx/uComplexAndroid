package org.ucomplex.ucomplex.CommonDependencies;

import android.app.Activity;
import android.support.annotation.NonNull;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;
import net.oneread.aghanim.mvp.basemvp.MVPPresenter;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;

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

    public static IFragment getFragmentWithName(String name, Activity activity) {
        if (name.equals(SubjectMaterialsFragment.class.getName())) {
            return SubjectMaterialsFragment.getFragment(activity);
        }
        return null;
    }

    @NonNull
    public static IFragment getFragmentWithName(String name,
                                                MVPPresenter presenter,
                                                int fragmentLayout,
                                                int recyclerId) {
        if (name.equals(MVPBaseRecyclerFragment.class.getName())) {
            return MVPBaseRecyclerFragment.getInstance(
                    (MVPPresenterRecycler) presenter,
                    fragmentLayout,
                    recyclerId);
        } else {
            return MVPBaseRecyclerFragment.getInstance(
                    (MVPPresenterRecycler) presenter,
                    fragmentLayout,
                    recyclerId);
        }
    }

}
