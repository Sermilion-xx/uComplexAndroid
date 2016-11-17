package org.ucomplex.ucomplex.Modules.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import org.androidannotations.annotations.EActivity;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class EventsActivity extends BaseActivity{

    private FragmentEvents mFragmentEvents;

    @Inject public void setPresenter(EventsPresenter presenter) {
        super.mPresenter = presenter;
    }
    @Inject public void setModel(EventsModel model) {
        super.mModel = model;
    }
    @Inject public void setRepository(EventsRepository repository) {
        super.mRepository = repository;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTitle(getResourceString(R.string.events));
        Intent intent = getIntent();
        if(intent.hasExtra(Constants.EXTRA_KEY_USER)) {
            UserInterface user = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);
            super.setmUser(user);
        }
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        ((MyApplication) getApplication()).getEventsDiComponent().inject(this);



        if(intent.hasExtra(Constants.EXTRA_KEY_USER)){
            int userType = getIntent().getIntExtra(Constants.EXTRA_KEY_USER, -1);
            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setActivity(this);
            super.setupMVP(mFragmentEvents, EventsActivity.class);
            super.setModelData(userType);
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


}
