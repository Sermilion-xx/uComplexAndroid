package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseFragment;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class FragmentEvents  extends BaseFragment implements ViewRecylerToPresenter{

    @Arg FragmentEvents.ListEventsAdapter      mListAdapter;
    private MediaPlayer                        mAlert;
    @Arg EventsActivity                        mActivity;
    @Arg Presenter                             mPresenter;
    private ProgressBar                        mProgressBar;
    private UserInterface                      mUser;

    public void setActivity(EventsActivity mActivity) {
        this.mActivity = mActivity;
        this.mPresenter = mActivity.getPresenter();
    }

    public void setUser(UserInterface user){
        mUser = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(getAppContext(), R.raw.alert);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        setupRecyclerView(view);
        mActivity.setupMVP(mUser, this);
        return view;
    }

    @Override
    public void setupRecyclerView(View view) {
        mListAdapter = new FragmentEvents.ListEventsAdapter();
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAppContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void notifyItemRemoved(int position) {
        mListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mListAdapter.notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mListAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public Context getAppContext() {
        return mActivity;
    }

    @Override
    public Context getActivityContext() {
        return mActivity.getApplicationContext();
    }

    @Override
    public void showToast(Toast toast) {

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAlert(AlertDialog dialog) {

    }

    private class ListEventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

        @Override
        public int getItemCount() {
            return ((MVP_Events.PresenterInterface)mPresenter).getEventsCount();
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ((MVP_Events.PresenterInterface)mPresenter).createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            ((MVP_Events.PresenterInterface)mPresenter).bindViewHolder(holder, position);
        }

        @Override
        public int getItemViewType(int position) {
            return ((MVP_Events.PresenterInterface)mPresenter).getItemViewType(position);
        }
    }
}
