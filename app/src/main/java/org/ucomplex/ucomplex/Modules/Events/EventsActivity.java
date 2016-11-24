package org.ucomplex.ucomplex.Modules.Events;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import javax.inject.Inject;

public class EventsActivity extends BaseActivity implements ViewRecylerToPresenter {

    private FragmentEvents         mFragmentEvents;
    private UserInterface          mUser;

    private static final String FRAGMENT_TAG ="EventsFragment";

    @Inject
    public void setPresenter(EventsPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(EventsModel model) {
        super.mModel = model;
    }

    @Inject
    public void setRepository(EventsRepository repository) {
        super.mRepository = repository;
    }


    public void setupMVP() {
        setupMVP(this, EventsActivity.class, mUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar(getResourceString(R.string.events));

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_KEY_USER)) {
            mUser = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);

            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
            getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

            ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

            FragmentManager fragmentManager = getFragmentManager();


            if(fragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                mFragmentEvents = FragmentEvents.getInstance(this);
                fragmentTransaction.add(R.id.container, mFragmentEvents, FRAGMENT_TAG);
                fragmentTransaction.commit();
            }else{
                mFragmentEvents = (FragmentEvents) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
                mFragmentEvents.setActivity(this);
            }
        }
    }

    @Override
    public void setupDrawer(){
        super.setupDrawer();
    }


    @Override
    public void setupRecyclerView(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onResume() {
        registerReceiver(mUpdateEventsReceiver, new IntentFilter(
                Constants.EVENTS_REFRESH_BROADCAST));
        registerReceiver(mLoadMoreEventsReceiver, new IntentFilter(
                Constants.EVENTS_LOAD_MORE_BROADCAST));
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mUpdateEventsReceiver);
        unregisterReceiver(mLoadMoreEventsReceiver);
        super.onPause();
    }

    private BroadcastReceiver mUpdateEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_REFRESH_BROADCAST)) {
                ((EventsPresenter) mPresenter).loadData();
                onBackPressed();
            }
        }
    };

    private BroadcastReceiver mLoadMoreEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_LOAD_MORE_BROADCAST)) {
                ((EventsPresenter) mPresenter).loadMoreEvents(((EventsPresenter) mPresenter).getEventsCount());
            }
        }
    };

    @Override
    public void notifyItemRemoved(int position) {
        mFragmentEvents.getListAdapter().notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mFragmentEvents.getListAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mFragmentEvents.getListAdapter().notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showToast(Toast toast) {

    }

    @Override
    public void showProgress() {
        mFragmentEvents.showProgress();
    }

    @Override
    public void hideProgress() {
        mFragmentEvents.hideProgress();
    }

    @Override
    public void showAlert(AlertDialog dialog) {

    }

}
