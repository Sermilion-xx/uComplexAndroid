package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import javax.inject.Inject;

@EActivity(resName="R.layout.activity_main")
public class EventsActivity extends BaseActivity implements OnTaskCompleteListener {

    private FragmentEvents mFragmentEvents;

    @ViewById(resName="R.id.progressBar")
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


    public void setupMVP(UserInterface user, FragmentEvents fragmentEvents){
        super.setupMVP(fragmentEvents, EventsActivity.class);
        super.setModelData(user);
        super.setupDrawer();
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

            ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setActivity(this);
            mFragmentEvents.setUser(user);

            ((EventsModel) super.mModel).setOnTaskCompleteListener(this);
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
    public void onTaskComplete(AsyncTask task, Object... o) {
        ((EventsPresenter) mPresenter).setHasMoreEvents((boolean) o[0]);
    }
}
