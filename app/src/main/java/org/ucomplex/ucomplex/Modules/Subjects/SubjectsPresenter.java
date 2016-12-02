package org.ucomplex.ucomplex.Modules.Subjects;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsPresenter implements Presenter {

    @Override
    public Context getAppContext() {
        return null;
    }

    @Override
    public Context getActivityContext() {
        return null;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {

    }

    @Override
    public void setView(ViewToPresenter view) {

    }

    @Override
    public ViewToPresenter getView() {
        return null;
    }

    @Override
    public void onConfigurationChanged(ViewToPresenter view) {

    }

    @Override
    public void setModel(Model models) {

    }

    @Override
    public UserInterface getUser() {
        return null;
    }

    @Override
    public void loadData() {

    }
}
