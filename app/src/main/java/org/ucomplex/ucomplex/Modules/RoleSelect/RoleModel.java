package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Repository;
import org.ucomplex.ucomplex.Interfaces.MVP.ModelRecycler;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
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

public class RoleModel implements ModelRecycler, OnTaskCompleteListener {

    // PresenterToViewInterface reference
    private ArrayList<RoleItem> mRoles;
    private Repository mRepository;
    private UserInterface mUser;
    private OnDataLoadedListener mOnDataLoadedListener;

    public RoleModel() {
    }

    public void setOnDataLoadedListener(OnDataLoadedListener mOnDataLoadedListener) {
        this.mOnDataLoadedListener = mOnDataLoadedListener;
    }

    @Override
    public void setData(Object data) {
        mUser = (UserInterface) data;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
        mRepository.setTaskCompleteListener(this);
    }

    @Override
    public UserInterface getUser() {
        return mUser;
    }


    public UserInterface getmUser() {
        return mUser;
    }

    public RoleModel(Context context, RoleRepository repository, UserInterface user) {
        mRepository = repository;
        this.mUser = user;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mRoles = null;
        }
    }

    @Override @SuppressWarnings("unchecked")
    public void loadData() {
       mRepository.loadData(mUser);
    }

    @Override @SuppressWarnings("unchecked")
    public void onTaskComplete(int requestType, Object... o) {
        if(o.length>0){
            mRoles = (ArrayList<RoleItem>) o[0];
        }
        mOnDataLoadedListener.dataLoaded(o.length>0, 0, 0);
    }

    @Override
    public IRecyclerItem getItem(int position) {
        return mRoles.get(position);
    }

    @Override
    public int getItemCount() {
        return mRoles.size();
    }
}
