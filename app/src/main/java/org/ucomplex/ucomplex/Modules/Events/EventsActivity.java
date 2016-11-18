package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class EventsActivity extends BaseActivity {

    private FragmentEvents mFragmentEvents;

    ProgressBar mProgressBar;



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
        super.setupToolbar(getResourceString(R.string.events));
        UserInterface user;
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_KEY_USER)) {
            user = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);

            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
            getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setActivity(this);
            super.setupMVP(mFragmentEvents, EventsActivity.class);
            super.setModelData(user);
            super.setupDrawer();
            addFragment(R.id.container, mFragmentEvents, "EventsFragment");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onResume() {
        registerReceiver(mUpdateEventsReceiver, new IntentFilter(
                Constants.REFRESH_EVENTS_BROADCAST));
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
            ((EventsPresenter)mPresenter).loadData();
            onBackPressed();
        }
    };

}
