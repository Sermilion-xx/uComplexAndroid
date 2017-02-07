package org.ucomplex.ucomplex.Modules.UserProfile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class UserProfileActivity extends BaseRecyclerActivity {

    public static final String EXTRA_KEY_USER_ID = "userId";
    public static final String EXTRA_KEY_USER_NAME = "userName";
    private static final String TAG_MATERIALS_FRAGMENT = MVPBaseRecyclerFragment.class.getName();

    public static void receiveIntent(Context context, int userId, String userName) {
        Intent intent = new Intent(context, SubjectActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_KEY_USER_ID, userId);
        extras.putString(EXTRA_KEY_USER_NAME, userName);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    @Inject
    public void setPresenter(UserProfilePresenter presenter) {
        super.mPresenter = presenter;
    }

    //mvp
    @Inject
    public void setModel(UserProfileModel model) {
        super.mModel = model;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, TAG_MATERIALS_FRAGMENT, (Fragment) mFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication) getApplication()).getUserProfileDiComponent().inject(this);
        setContentViewWithNavDrawer(R.layout.activity_user_profile);
        Intent intent = getIntent();
        setupToolbar(intent.getStringExtra(EXTRA_KEY_USER_NAME));

        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putString(EXTRA_KEY_USER_ID, intent.getStringExtra(EXTRA_KEY_USER_ID));

        mFragment = setupFragment(this,
                TAG_MATERIALS_FRAGMENT,
                savedInstanceState,
                bundle,
                R.layout.fragment_recycler,
                R.id.recyclerView,
                R.id.progressBar,
                R.id.container);
        mFragment.setOnFragmentLoadedListener(views -> {
            setupMVP(UserProfileActivity.this, BaseActivity.class, bundle);
            setupDrawer();
            mFragment.getRecyclerView().setBackgroundColor(getResources().getColor(R.color.color_uc_activity_background));
        });
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mFragment.getRecyclerView();
    }
}
