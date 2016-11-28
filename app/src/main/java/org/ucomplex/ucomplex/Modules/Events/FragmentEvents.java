package org.ucomplex.ucomplex.Modules.Events;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Modules.BaseFragment;
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
public class FragmentEvents extends BaseFragment{

    private MediaPlayer mAlert;
    private ProgressBar mProgressBar;
    private EventsActivity mActivity;
    private ListEventsAdapter mListAdapter;


    public static FragmentEvents getInstance(Object...params) {
        FragmentEvents fragment =  new FragmentEvents();
        fragment.setActivity((EventsActivity) params[0]);
        return fragment;
    }

    @Override
    public void setParams(Object ... params) {
        mActivity = (EventsActivity)params[0];
    }

    public ListEventsAdapter getListAdapter() {
        return mListAdapter;
    }

    public void setActivity(EventsActivity mActivity) {
        this.mActivity = mActivity;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(mActivity, R.raw.alert);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mActivity.setupMVP();
        setupRecyclerView(view);
        mActivity.setupDrawer();
        return view;
    }

    public void setupRecyclerView(View view) {
        mListAdapter = new ListEventsAdapter(mActivity.getPresenter());
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
    }

    public void showProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

}
