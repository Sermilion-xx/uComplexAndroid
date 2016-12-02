package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.res.Configuration;
import android.os.Bundle;

import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.MyApplication;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class RoleSelectActivity extends BaseRecyclerActivity implements ViewToPresenterRecycler {

    @Inject
    public void setPresenter(RolePresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(RoleModel model) {
        super.mModel = model;
    }

    @Inject
    public void setRepository(RoleRepository repository) {
        super.mRepository = repository;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        ((MyApplication) getApplication()).getRoleDiComponent().inject(this);
        mFragment = setupFragment(getFragmentManager(),
                FragmentRoles.class.getName(),
                this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPresenter.onConfigurationChanged(this);
    }

}