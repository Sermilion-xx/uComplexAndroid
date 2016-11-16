package org.ucomplex.ucomplex.Modules.Events;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import org.androidannotations.annotations.EActivity;
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
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        setUpToolbar("События");
        setSupportActionBar(mToolbar);
        ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EXTRA_KEY_USER_TYPE)){
            int userType = getIntent().getIntExtra(Constants.EXTRA_KEY_USER_TYPE, -1);
            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setActivity(this);
            super.setupMVP(mFragmentEvents, EventsActivity.class);
            super.setModelData(userType);
            addFragment(R.id.container, mFragmentEvents, "EventsFragment");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }


}
