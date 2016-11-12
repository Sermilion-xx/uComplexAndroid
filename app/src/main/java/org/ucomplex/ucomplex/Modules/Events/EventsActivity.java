package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import javax.inject.Inject;

public class EventsActivity extends BaseActivity implements OnTaskCompleteListener, MVP_Events.RequiredViewOpsFromPresenter {

    FragmentEvents                                mFragmentEvents;
    @Inject MVP_Events.ProvidedPresenterOpsToView mPresenter;
    @Inject EventsPresenter                       presenter;
    @Inject EventsModel                           mModel;
    @Inject EventsRepository                      mEventsRepository;

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( getFragmentManager(), EventsActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        setUpToolbar("События");
        setSupportActionBar(mToolbar);
        ((MyApplication) getApplication()).getEventsDiComponent().inject(this);

        Intent intent = getIntent();
        UserInterface user;

        if(intent.hasExtra(Constants.EXTRA_KEY_USER)){
            user = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);
            mFragmentEvents = new FragmentEvents();
            mFragmentEvents.setUser(user);
            mFragmentEvents.setContext(this);
            setupMVP(user);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        addFragment(R.id.container, mFragmentEvents, "EventsFragment");
    }



    private void setupMVP(UserInterface user) {
        if (mStateMaintainer.firstTimeIn()) {
            presenter.setView(this);
            mEventsRepository.setContext(presenter.getAppContext());
            mEventsRepository.setUserType(user.getType());
            mModel.setPresenter(presenter);
            mModel.setRepository(mEventsRepository);
            presenter.setModel(mModel);
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(mModel);
            mPresenter = presenter;
            mStateMaintainer.put(MVP_Events.RequiredViewOpsFromPresenter.class.getName(), mPresenter);
        }
        else {
            mPresenter = mStateMaintainer.get(EventsActivity.class.getName());
            mPresenter.setView(this);
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
