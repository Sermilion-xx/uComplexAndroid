package org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
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
    protected ArrayList<IRecyclerItem> mRecyclerItems = new ArrayList<>();
    protected Bundle mParams;


    public AbstractModel(){

    }

    public ArrayList<IRecyclerItem> getRecyclerItems() {
        return mRecyclerItems;
    }

    public abstract Object getDataFromJson(String jsonString)  throws JSONException;


    @Override
    @SuppressWarnings("unchecked")
    public void loadData() {

        mRepository.loadData(this.mParams);
    }

    @Override
    public UserInterface getUser() {
        return this.mUser;
    }

    @Override
    public void setUser(UserInterface user) {
        this.mUser = user;
    }

    @Override
    public void setBundle(Bundle data) {
        this.mParams = data;
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
            mRecyclerItems.clear();
            EventBus.getDefault().unregister(this);
        }
    }

}
