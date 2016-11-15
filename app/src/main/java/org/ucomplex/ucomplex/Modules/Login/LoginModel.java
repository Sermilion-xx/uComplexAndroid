package org.ucomplex.ucomplex.Modules.Login;


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
    private Presenter mPresenter;
    private Repository mRepository;
    private UserInterface user = new User();

    /**
     * Main constructor, called by Activity during MVP setup
     *
     * @param presenter PresenterToViewInterface instance
     */
    public LoginModel(Presenter presenter) {
        this.mPresenter = presenter;
        mRepository = new LoginRepository(mPresenter.getAppContext());
    }

    public LoginModel() {

    }

    public void setPresenter(Presenter mPresenter) {
        this.mPresenter = mPresenter;
        mRepository = new LoginRepository(mPresenter.getAppContext());
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
     * Test contructor. Called only during unit testing
     * @param presenter PresenterToViewInterface instance
     * @param dao       DAO instance
     */
    public LoginModel(Presenter presenter, LoginRepository dao) {
        this.mPresenter = presenter;
        mRepository = dao;
    }

    /**
     * Called by PresenterToViewInterface when View is destroyed
     * @param isChangingConfiguration true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
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
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return HttpFactory.httpPost(HttpFactory.RESTORE_PASSWORD_URL, "", json);
    }

    @Override
    public UserInterface getUser() {
        return user;
    }
}
