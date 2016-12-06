package org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP;

import org.json.JSONException;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class AbstractModel implements Model, OnTaskCompleteListener {

    protected Repository mRepository;
    protected UserInterface mUser;
    protected OnDataLoadedListener mOnDataLoadedListener;
    protected ArrayList<IRecyclerItem> mRecyclerItems;

    public abstract Object getDataFromJson(String jsonString)  throws JSONException;

    @Override
    @SuppressWarnings("unchecked")
    public void loadData() {
        mRepository.loadData();
    }

    @Override
    public UserInterface getUser() {
        return this.mUser;
    }

    @Override
    public void setData(Object data) {
        this.mUser = (UserInterface) data;
    }

    @Override
    public void setOnDataLoadedListener(OnDataLoadedListener mOnDataLoadedListener) {
        this.mOnDataLoadedListener = mOnDataLoadedListener;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
        mRepository.setTaskCompleteListener(this);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mRepository = null;
            mUser = null;
            mOnDataLoadedListener = null;
            mRecyclerItems = null;
        }
    }

}
