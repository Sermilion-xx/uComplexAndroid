package org.ucomplex.ucomplex.Modules.Subjects;

import android.view.View;
import android.widget.ProgressBar;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Modules.ListAdapter;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentSubjects implements IFragment {

    private ProgressBar mProgressBar;
    private ListAdapter mListAdapter;

    public ListAdapter getListAdapter() {
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
