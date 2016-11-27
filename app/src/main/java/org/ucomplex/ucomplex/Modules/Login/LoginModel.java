package org.ucomplex.ucomplex.Modules.Login;


import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

/**
 * Model layer on Model View PresenterToViewInterface Pattern
 * <p>
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class LoginModel implements MVP_Login.ModelInterface, OnTaskCompleteListener{

    private Repository mRepository;
    private UserInterface mUser = new User();
    private OnDataLoadedListener mOnDataLoadedListener;

    public LoginModel(LoginRepository dao) {
        mRepository = dao;
    }

    public LoginModel(){

    }

    void setOnDataLoadedListener(OnDataLoadedListener mOnDataLoadedListener) {
        this.mOnDataLoadedListener = mOnDataLoadedListener;
    }

    public void setPresenter(Context context) {
        mRepository = new LoginRepository(context);
        ((LoginRepository)mRepository).setTaskCompleteListener(this);
    }

    @Override
    public void setData(Object data) {
        this.mUser = (UserInterface)data;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
        ((LoginRepository)mRepository).setTaskCompleteListener(this);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mRepository = null;
        }
    }

    @Override
    public void loadData() {
        //User object with loadUser and password
        mRepository.loadData(mUser);
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return HttpFactory.httpPost(HttpFactory.RESTORE_PASSWORD_URL, "", json);
    }

    @Override
    public UserInterface getUser() {
        return mUser;
    }

    public UserInterface loadLoggedUser(){
        return ((LoginRepository)mRepository).loadLoggedUser();
    }

    @Override
    public void onTaskComplete(String requestType, Object... o) {
        mUser = (UserInterface) o[0];
        mOnDataLoadedListener.dataLoaded(mUser!=null);
    }
}
