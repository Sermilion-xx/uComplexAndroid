package org.ucomplex.ucomplex.Modules.Events;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseFragment;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.Modules.RoleSelect.MVP_RoleSelect;
import org.ucomplex.ucomplex.Modules.RoleSelect.RolePresenter;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import java.util.ArrayList;
import java.util.Objects;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentEvents  extends BaseFragment implements MVP_Events.RequiredViewOpsFromPresenter {

    @Arg private ArrayList<EventItem>          mEventItems = null;
    Button                                     mButtonLoadMore;
    @Arg MVP_Events.ProvidedPresenterOpsToView mPresenter;
    @Arg FragmentEvents.ListEventsAdapter      mListAdapter;
    @Arg EventsPresenter                       presenter;
    @Arg EventsModel                           mModel;
    @Arg EventsRepository                      mEventsRepository;
    @Arg EventsActivity                        mContext;
    MediaPlayer                                mAlert;
    @Arg UserInterface                         mUser;

    public void setUser(UserInterface mData) {
        this.mUser = mData;
    }

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( getFragmentManager(), RoleSelectActivity.class.getName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) mContext.getApplication()).getEventsDiComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(mContext, R.raw.alert);
        setupMVP(mUser);
        setupViews(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupViews(View view){
        mListAdapter = new ListEventsAdapter();
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupMVP(UserInterface user) {
        if (mStateMaintainer.firstTimeIn()) {
            presenter.setView(this);
            mEventsRepository.setContext(presenter.getAppContext());
            mEventsRepository.setUserType(user.getType());
            mModel.setPresenter(presenter);
            mModel.setRepository(mEventsRepository);
            presenter.setModel(mModel);
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(mModel);
            mPresenter = presenter;
        }
        else {
            mPresenter = mStateMaintainer.get(RolePresenter.class.getName());
            mPresenter.setView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(mContext.isChangingConfigurations());
    }

    @Override
    public Context getAppContext() {
        return mContext;
    }

    @Override
    public Context getActivityContext() {
        return mContext.getApplicationContext();
    }

    @Override
    public void showToast(Toast toast) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAlert(AlertDialog dialog) {

    }

    @Override
    public void notifyItemRemoved(int position) {

    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void notifyItemInserted(int layoutPosition) {

    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {

    }

    @Override
    public void goToCourse() {

    }

    private class ListEventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

        @Override
        public int getItemCount() {
            return mPresenter.getEventsCount();
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            mPresenter.bindViewHolder(holder, position);
        }
    }
}
