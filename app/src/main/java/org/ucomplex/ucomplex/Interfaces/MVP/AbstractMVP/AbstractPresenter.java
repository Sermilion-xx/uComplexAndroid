package org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.ViewToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;

import java.lang.ref.WeakReference;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class AbstractPresenter implements Presenter, OnDataLoadedListener {

    protected WeakReference<ViewToPresenter> mView;
    protected Model mModel;

    @Override
    public ViewToPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void setModel(Model models) {
        mModel = models;
        mModel.setOnDataLoadedListener(this);
        loadData();
    }

    @Override
    public void onConfigurationChanged(ViewToPresenter view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        HttpFactory.getInstance().cancel();
        mModel.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    @Override
    public UserInterface getUser() {
        return mModel.getUser();
    }

    @Override
    public void loadData() {
        getView().showProgress();
        mModel.loadData();
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
