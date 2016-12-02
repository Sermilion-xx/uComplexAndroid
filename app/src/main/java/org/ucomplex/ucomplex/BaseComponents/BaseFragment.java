package org.ucomplex.ucomplex.BaseComponents;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.hannesdorfmann.fragmentargs.FragmentArgs;

import org.ucomplex.ucomplex.Interfaces.IFragment;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class BaseFragment extends Fragment implements IFragment{

    protected ProgressBar mProgressBar;
    protected BaseActivity mActivity;
    protected BaseListAdapter mListAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    public void setActivity(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this); // read @Arg fields
    }

    public void setupRecyclerView(View view, int layout) {
        mListAdapter = new BaseListAdapter(mActivity.getPresenter());
        mRecyclerView = (RecyclerView) view.findViewById(layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public BaseListAdapter getListAdapter() {
        return mListAdapter;
    }

    @Override
    public void setParams(Object ... params) {
        mActivity = (BaseActivity) params[0];

    }

    public void setActivity(Activity mActivity) {
        this.mActivity = (BaseActivity) mActivity;
    }

    public void showProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

}
