package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class EventsActivity extends BaseActivity implements OnTaskCompleteListener, MVP_Events.ViewToPresenterInterface {

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
            super.setupMVP(this, EventsActivity.class);
            super.setModelData(userType);
            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setActivity(this);
            addFragment(R.id.container, mFragmentEvents, "EventsFragment");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public Context getActivityContext() {
        return getApplicationContext();
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
    public void setupRecyclerView() {

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

    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {

    }

}
