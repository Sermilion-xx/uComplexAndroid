package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;

import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.CommonDependencies.Constants;

import javax.inject.Inject;

public class EventsActivity extends BaseRecyclerActivity implements ViewToPresenterRecycler {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_main);
        ((MyApplication) getApplication()).getEventsDiComponent().inject(this);
        setupToolbar(getResourceString(R.string.events));
    }

    @Override
    public final void setupDrawer() {
        super.setupDrawer();
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
                mPresenter.loadData();
                onBackPressed();
            }
        }
    };

    private BroadcastReceiver mLoadMoreEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_LOAD_MORE_BROADCAST)) {
                ((EventsPresenter) mPresenter).loadMoreEvents(((EventsPresenter) mPresenter).getItemCount()+1);
            }
        }
    };

}
