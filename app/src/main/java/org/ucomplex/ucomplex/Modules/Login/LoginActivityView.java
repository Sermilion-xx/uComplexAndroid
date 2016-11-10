package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.EMPTY_EMAIL;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.INVALID_PASSWORD;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.PASSWORD_REQUIRED;

@EActivity(R.layout.activity_login)
public class LoginActivityView extends BaseActivity implements MVP_Login.ViewInterface {

    MVP_Login.PresenterToViewInterface mPresenter;
    @ViewById(R.id.login)
    AutoCompleteTextView mLoginView;
    @ViewById(R.id.password)
    EditText mPasswordView;
    @ViewById(R.id.login_progress)
    View mProgressView;
    @ViewById(R.id.forgot_pass_button)
    Button mForgotButton;
    @ViewById(R.id.login_sign_in_button)
    Button mLoginSignInButton;
    private LoginPresenter presenter;
    private LoginModel model;

    @Inject public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }
    @Inject public void setModel(LoginModel model) {
        this.model = model;
    }

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer(getFragmentManager(), LoginActivityView.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ((MyApplication) getApplication()).getLoginDiComponent().inject(this);
        setupMVP();
    }


    private void setupMVP() {
        if (mStateMaintainer.firstTimeIn()) {
            presenter.setView(this);
            model.setPresenter(presenter);
            presenter.setModel(model);
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(model);
            mPresenter = presenter;
        } else {
            mPresenter = mStateMaintainer.get(LoginPresenter.class.getName());
            mPresenter.setView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showAlert(AlertDialog dialog) {
        dialog.show();
    }

    @Click
    void forgot_pass_button() {
        mPresenter.showRestorePasswordDialog();
    }

    @Click
    void login_sign_in_button() {
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        ArrayList<LoginErrorType> error = mPresenter.checkCredentials(login, password);

        if (error.contains(PASSWORD_REQUIRED)) {
            mPasswordView.setError(getString(R.string.error_field_required));
        } else if (error.contains(INVALID_PASSWORD)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
        }
        if (error.contains(EMPTY_EMAIL)) {
            mLoginView.setError(getString(R.string.error_field_required));
        }
    }

    @Override
    public void successfulLogin(User user) {
        Intent intent;
        if (user.getRoles().size() > 1) {
            intent = new Intent(getActivityContext(), RoleSelectActivity.class);
        } else {
            intent = new Intent(getActivityContext(), RoleSelectActivity.class);
        }
        intent.putExtra(Constants.EXTRA_KEY_USER, user);
        getActivityContext().startActivity(intent);
    }

}
