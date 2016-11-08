package org.ucomplex.ucomplex.Activities.Login.RoleSelect;

import org.ucomplex.ucomplex.Model.Users.User;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleModel implements MVP_RoleSelect.ProvidedModelOpsFromPresenter {

    // Presenter reference
    private MVP_RoleSelect.RequiredPresenterOpsToModel mPresenter;
    public ArrayList<RoleItem> mRoles;
    private RoleRepository mRolesRepository;

    public RoleModel(MVP_RoleSelect.RequiredPresenterOpsToModel presenter){
        this.mPresenter = presenter;
        mRolesRepository = new RoleRepository(mPresenter.getAppContext());
    }

    public RoleModel(MVP_RoleSelect.RequiredPresenterOpsToModel presenter, RoleRepository repository){
        this.mPresenter = presenter;
        mRolesRepository = repository;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mRoles = null;
        }
    }

    @Override
    public boolean loadData() {
        mRoles = mRolesRepository.getAllRoleItems();
        return mRoles!=null;
    }

    @Override
    public RoleItem getRole(int position) {
        return mRoles.get(position);
    }

    public void setRoles(ArrayList<RoleItem> mRoles) {
        this.mRoles = mRoles;
    }

    @Override
    public int getRolesCount() {
        return mRoles.size();
    }
}
