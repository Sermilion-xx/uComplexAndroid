package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

    private FragmentEvents mFragmentEvents;
    private UserInterface mUser;

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


    //Used in Fragment
    public final void setupMVP() {
        setupMVP(this, EventsActivity.class, mUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar(getResourceString(R.string.events));
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_KEY_USER)) {
            mUser = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);
            ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

            mFragmentEvents = (FragmentEvents) setupFragment(getFragmentManager(),
                    FragmentEvents.class.getName(),
                    this);
        }
    }

    @Override
    public final void setupDrawer() {
        super.setupDrawer();
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
    public void showAlert(AlertDialog dialog) {

    }

    @Override
    public void showProgress() {
        mFragmentEvents.showProgress();
    }

    @Override
    public void hideProgress() {
        mFragmentEvents.hideProgress();
    }

}
