package org.ucomplex.ucomplex.Modules.Login;


import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
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
public class LoginModel implements MVP_Login.ModelInterface {

    // PresenterToViewInterface reference
    private Context mContext;
    private Repository mRepository;
    private UserInterface user = new User();



    public LoginModel(Context context, LoginRepository dao) {
        this.mContext = context;
        mRepository = dao;
    }

    public LoginModel() {

    }

    public void setPresenter(Context context) {
        this.mContext = context;
        mRepository = new LoginRepository(context);
    }

    @Override
    public void setData(Object data) {
        this.user = (UserInterface)data;
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }



    /**
     * Called by PresenterToViewInterface when View is destroyed
     * @param isChangingConfiguration true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mContext = null;
            mRepository = null;
        }
    }

    /**
     * Loads all Data, getting User
     * @return true with success
     */
    @Override
    public boolean loadData() {
        user = (UserInterface) mRepository.loadData(user);
        return user.getRoles()!=null;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return HttpFactory.httpPost(HttpFactory.RESTORE_PASSWORD_URL, "", json);
    }

    @Override
    public UserInterface getUser() {
        return user;
    }
}
