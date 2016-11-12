package org.ucomplex.ucomplex.Modules.Login;


import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Utility.HttpFactory;

/**
 * Model layer on Model View PresenterToView Pattern
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

    // PresenterToView reference
    private MVP_Login.PresenterToModelInterface mPresenter;
    private LoginRepository mRepository;
    private User user;

    /**
     * Main constructor, called by Activity during MVP setup
     *
     * @param presenter PresenterToView instance
     */
    public LoginModel(MVP_Login.PresenterToModelInterface presenter) {
        this.mPresenter = presenter;
        mRepository = new LoginRepository(mPresenter.getAppContext());
    }

    public LoginModel() {

    }

    public void setPresenter(MVP_Login.PresenterToModelInterface mPresenter) {
        this.mPresenter = mPresenter;
        mRepository = new LoginRepository(mPresenter.getAppContext());
    }

    /**
     * Test contructor. Called only during unit testing
     *
     * @param presenter PresenterToView instance
     * @param dao       DAO instance
     */
    public LoginModel(MVP_Login.PresenterToModelInterface presenter, LoginRepository dao) {
        this.mPresenter = presenter;
        mRepository = dao;
    }

    /**
     * Called by PresenterToView when View is destroyed
     *
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
     *
     * @return true with success
     */
    @Override
    public boolean loadData() {
        String jsonData = mRepository.login(user.getLogin(), user.getPassword());
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getJSONArray("roles") == null) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        user = mRepository.getUser(jsonData);
        return true;
    }

    @Override
    public String sendResetRequest(String email) {
        String json = "\"email\":\"" + email + "\"";
        return HttpFactory.httpPost(HttpFactory.RESTORE_PASSWORD_URL, "", json);
    }

    @Override
    public User getUser() {
        return user;
    }
}
