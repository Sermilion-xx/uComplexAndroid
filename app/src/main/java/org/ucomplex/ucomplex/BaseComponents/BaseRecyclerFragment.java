package org.ucomplex.ucomplex.BaseComponents;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;

import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class BaseRecyclerFragment extends Fragment implements IFragment{


    protected ProgressBar mProgressBar;
    protected BaseActivity mActivity;
    protected BaseListAdapter mListAdapter;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager linearLayoutManager;
    @Arg protected boolean hasDivider;

    public static IFragment getInstance(BaseActivity activity) {
        IFragment fragment =  new BaseRecyclerFragment();
        fragment.setActivity(activity);
        return fragment;
    }

    @Override
    public void setActivity(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this); // read @Arg fields
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        Bundle bundle = getArguments();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mActivity.setupMVP(mActivity, mActivity.getClass(), bundle);
        setupRecyclerView(view, R.id.recyclerView);
        mActivity.setupDrawer();
        return view;
    }

    public void setupRecyclerView(View view, int layout) {
        mListAdapter = new BaseListAdapter(mActivity.getPresenter());
        mRecyclerView = (RecyclerView) view.findViewById(layout);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if(hasDivider){
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }

    @Override
    public void addDivider(){
        hasDivider = true;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public BaseListAdapter getListAdapter() {
        return mListAdapter;
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
