package org.ucomplex.ucomplex.Activities.Login;


import org.ucomplex.ucomplex.Model.Users.User;

/**
 * Model layer on Model View Presenter Pattern
 *
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class LoginModel implements MVP_Login.ProvidedModelOpsFromPresenter {

    // Presenter reference
    private MVP_Login.RequiredPresenterOpsToModel mPresenter;
    private LoginData mDAO;
    // Recycler data
    User user;

    /**
     * Main constructor, called by Activity during MVP setup
     * @param presenter Presenter instance
     */
    public LoginModel(MVP_Login.RequiredPresenterOpsToModel presenter) {
        this.mPresenter = presenter;
        mDAO = new LoginData(mPresenter.getAppContext());
    }

    /**
     * Test contructor. Called only during unit testing
     * @param presenter Presenter instance
     * @param dao       DAO instance
     */
    public LoginModel(MVP_Login.RequiredPresenterOpsToModel presenter, LoginData dao) {
        this.mPresenter = presenter;
        mDAO = dao;
    }

    /**
     * Called by Presenter when View is destroyed
     * @param isChangingConfiguration   true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mDAO = null;
            user = null;
        }
    }

    /**
     * Loads all Data, getting EventItems from DB
     * @return  true with success
     */
    @Override
    public User loadData(String login, String password) {
        String jsonData = mDAO.login(login, password);
        user = mDAO.getUser(jsonData);
        return user;
    }

}
