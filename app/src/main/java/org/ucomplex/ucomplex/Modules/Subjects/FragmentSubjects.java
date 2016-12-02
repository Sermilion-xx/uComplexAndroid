package org.ucomplex.ucomplex.Modules.Subjects;

import android.view.View;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseFragment;
import org.ucomplex.ucomplex.BaseComponents.BaseListAdapter;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentSubjects extends BaseFragment {


    public static IFragment getInstance(Object...params) {
        IFragment fragment =  new FragmentSubjects();
        fragment.setActivity((BaseActivity) params[0]);
        return fragment;
    }

    public BaseListAdapter getListAdapter() {
        return mListAdapter;
    }

    @Override
    public void setParams(Object... params) {

    }

    public void showProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

}
