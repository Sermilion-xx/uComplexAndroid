package org.ucomplex.ucomplex.Modules.RoleSelect;

import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

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

public class RoleModel implements MVP_RoleSelect.ModelInterface {

    // PresenterToViewInterface reference
    private Presenter mPresenter;
    private ArrayList<RoleItem> mRoles;
    private Repository mRolesRepository;
    private UserInterface mUser;

    public RoleModel(Presenter presenter) {
        this.mPresenter = presenter;
        this.mRolesRepository = new RoleRepository(mPresenter.getAppContext());
    }

    public RoleModel() {
    }

    public void setPresenter(Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setData(Object data) {
        mUser = (UserInterface) data;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRolesRepository = repository;
    }

    public void setRolesRepository(RoleRepository mRolesRepository) {
        this.mRolesRepository = mRolesRepository;
    }

    public UserInterface getUser() {
        return mUser;
    }

    public RoleModel(Presenter presenter, RoleRepository repository, UserInterface user) {
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

    @Override @SuppressWarnings("unchecked")
    public boolean loadData() {
        mRoles = (ArrayList<RoleItem>) mRolesRepository.loadData(mUser);
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
