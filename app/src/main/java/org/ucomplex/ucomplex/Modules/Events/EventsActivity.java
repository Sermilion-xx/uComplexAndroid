package org.ucomplex.ucomplex.Modules.Events;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.oneread.aghanim.components.utility.OnFragmentLoadedListener;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.PERMISSIONS_REQUEST_WRITE_STORAGE;

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
        FacadeCommon.checkStoragePermissions(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Вы не разрешили доступ к пямяти.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
