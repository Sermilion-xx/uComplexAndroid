package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import net.oneread.aghanim.components.base.BaseRecyclerFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.recyclermvp.PresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.HttpFactory.encodeLoginData;

public class RoleSelectActivity extends BaseRecyclerActivity implements ViewRecycler {

    @Inject
    public void setPresenter(RolePresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(RoleModel model) {
        super.mModel = model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        DaggerApplication application = (DaggerApplication) getAppContext();
        application.getRoleDiComponent().inject(this);

        mFragment = setupRecyclerFragment(savedInstanceState,
                BaseRecyclerFragment.class.getName(),
                mPresenter,
                R.layout.fragment_recycler,
                R.id.recyclerView);
        mFragment.setProgressViewId(R.id.progressBar);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_KEY_USER, (Parcelable) application.getSharedUser());
        mFragment.setOnFragmentLoadedListener(views -> {
            RoleSelectActivity.this.setupMVP(RoleSelectActivity.this, BaseActivity.class, bundle);
            initPresenter();
        });
    }

    private void initPresenter() {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            int position = clickListener.getPosition();
            if (position == ((PresenterRecycler) mPresenter).getItemCount() - 1) {
                UserInterface user = ((DaggerApplication) getAppContext()).getSharedUser();
                persistUser(user, position);
                Intent intent = new Intent(getActivityContext(), EventsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_USER, (Parcelable) user);
                getActivityContext().startActivity(intent);
            }
        };
        clickListener.setStrategy(strategy);
        ((PresenterRecycler) mPresenter).setBaseOnClickListener(clickListener);
        ((PresenterRecycler) mPresenter).setCreator((view, i) -> new RoleViewHolder(view));
        ((PresenterRecycler) mPresenter).setItemLayout(R.layout.list_item_role);
    }

    private void persistUser(UserInterface user, int position) {
        String login = user.getLogin();
        String password = user.getPassword();
        int role1 = user.getRoles().get(position).getId();
        user.setType(user.getRoles().get(position).getType());
        user.setType(user.getType());
        String encodedAuth = encodeLoginData(login + ":" + password + ":" + role1);
        FacadePreferences.setLoginDataToPref(getActivityContext(), encodedAuth);
        DaggerApplication application = (DaggerApplication) getAppContext();
        application.setSharedUser(user);
    }


}