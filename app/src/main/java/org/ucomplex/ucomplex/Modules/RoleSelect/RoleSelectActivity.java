package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory.encodeLoginData;

public class RoleSelectActivity extends BaseRecyclerActivity implements MVPViewRecycler {

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
                MVPBaseRecyclerFragment.class.getName(),
                mPresenter,
                R.layout.fragment_recycler,
                R.id.recyclerView, R.id.container);
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
            int position = (((RoleViewHolder) view.getTag()).getItemPosition());
            UserInterface user = ((DaggerApplication) getAppContext()).getSharedUser();
            persistUser(user, position);
            Intent intent = new Intent(getActivityContext(), EventsActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_USER, (Parcelable) user);
            getActivityContext().startActivity(intent);
        };
        clickListener.setStrategy(strategy);
        ((MVPPresenterRecycler) mPresenter).setBaseOnClickListener(clickListener);
        ((MVPPresenterRecycler) mPresenter).setCreator((view, i) -> new RoleViewHolder(view));
        ((MVPPresenterRecycler) mPresenter).setItemLayout(R.layout.list_item_role);
    }

    private void persistUser(UserInterface user, int position) {
        String login = user.getLogin();
        String password = user.getPassword();
        int roleId = user.getRoles().get(position).getId();
        user.setType(user.getRoles().get(position).getType());
        String encodedAuth = encodeLoginData(login + ":" + password + ":" + roleId);
        FacadePreferences.setLoginDataToPref(getActivityContext(), encodedAuth);
        DaggerApplication application = (DaggerApplication) getAppContext();
        application.setSharedUser(user);
        application.setAuthString(encodedAuth);
    }


}