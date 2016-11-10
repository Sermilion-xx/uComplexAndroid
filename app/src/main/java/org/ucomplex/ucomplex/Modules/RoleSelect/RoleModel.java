package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Intent;

import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.Constants;

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
    private ArrayList<RoleItem> mRoles;
    private RoleRepository mRolesRepository;
    private UserInterface mUser;

    public RoleModel(MVP_RoleSelect.RequiredPresenterOpsToModel presenter) {
        this.mPresenter = presenter;
        this.mRolesRepository = new RoleRepository(mPresenter.getAppContext());
    }

    public RoleModel() {
    }

    public void setPresenter(MVP_RoleSelect.RequiredPresenterOpsToModel mPresenter) {
        this.mPresenter = mPresenter;
    }

    public void setRolesRepository(RoleRepository mRolesRepository) {
        this.mRolesRepository = mRolesRepository;
    }

    public void setUser(UserInterface user){
        mUser = user;
    }

    public UserInterface getmUser() {
        return mUser;
    }

    public RoleModel(MVP_RoleSelect.RequiredPresenterOpsToModel presenter, RoleRepository repository, UserInterface user) {
        this.mPresenter = presenter;
        mRolesRepository = repository;
        this.mUser = user;
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
        mRoles = mRolesRepository.getAllRoleItems(mUser);
        return mRoles != null;
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
