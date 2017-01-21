package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.oneread.aghanim.components.utility.OnFragmentLoadedListener;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class EventsActivity extends BaseRecyclerActivity {

    public static final String ACTION_RELOAD_EVENTS = Constants.PREFIX + "refresh_events";
    private MediaPlayer mAlert;
    private Boolean updateEventsReceiverRegistered = false;

    //mvp
    @Inject
    public void setPresenter(EventsPresenter presenter) {
        super.mPresenter = presenter;
    }

    //mvp
    @Inject
    public void setModel(EventsModel model) {
        super.mModel = model;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("updateEventsReceiverRegistered", updateEventsReceiverRegistered);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getEventsDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_main);

        if (savedInstanceState != null) {
            updateEventsReceiverRegistered = savedInstanceState.getBoolean("updateEventsReceiverRegistered");
        }
        setupToolbar(getResourceString(R.string.events));
        mAlert = MediaPlayer.create(EventsActivity.this, R.raw.alert);

        //mvp
        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());

        mFragment = setupFragment(this,
                savedInstanceState,
                bundle,
                R.layout.fragment_recycler,
                R.id.recyclerView,
                R.id.progressBar,
                R.id.container);
        mFragment.setOnFragmentLoadedListener(views -> {
            setupMVP(EventsActivity.this, BaseActivity.class, bundle);
            setupDrawer();
            mFragment.getRecyclerView().setBackgroundColor(getResources().getColor(R.color.color_uc_activity_background));
        });

    }

    //mvp
    @Override
    public RecyclerView getRecyclerView() {
        return mFragment.getRecyclerView();
    }

    @Override
    public void onResume() {
        if (!updateEventsReceiverRegistered) {
            registerReceiver(mUpdateEventsReceiver, new IntentFilter(
                    Constants.EVENTS_REFRESH_BROADCAST));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mUpdateEventsReceiver);
        super.onPause();
    }

    private BroadcastReceiver mUpdateEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_REFRESH_BROADCAST)) {
                if (intent.hasExtra(ACTION_RELOAD_EVENTS)) {
                    EventsModel.INITIAL_EVENTS_LOADED = false;
                }
                DaggerApplication application = (DaggerApplication) getAppContext();
                Bundle bundle = new Bundle();
                bundle.putString(AUTH_STRING, application.getAuthString());
                mPresenter.loadData(bundle);
                onBackPressed();
            }
        }
    };

}
