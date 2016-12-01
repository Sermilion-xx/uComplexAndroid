package org.ucomplex.ucomplex.Modules.RoleSelect;

import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModel;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
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

public class RoleModel extends AbstractModel implements ModelRecycler {

    private ArrayList<RoleItem> mRoles;

    public RoleModel() {
    }

    @Override
    public void setData(Object data) {
        mUser = (UserInterface) data;
    }

    @Override
    public UserInterface getUser() {
        return mUser;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        super.onDestroy(isChangingConfiguration);
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
