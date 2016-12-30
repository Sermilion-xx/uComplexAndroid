package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;

import net.oneread.aghanim.mvp.recyclermvp.ViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class RoleSelectActivity extends BaseRecyclerActivity implements ViewRecycler {

    @Inject
    public void setPresenter(RolePresenter presenter) {
        super.mPresenter = presenter;
        ((PresenterRecycler)super.mPresenter).setItemLayout(R.layout.list_item_role);
    }

    @Inject
    public void setModel(RoleModel model) {
        super.mModel = model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        ((DaggerApplication) getApplication()).getRoleDiComponent().inject(this);
    }

}