package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.EMPTY_EMAIL;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.INVALID_PASSWORD;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.PASSWORD_REQUIRED;
import static org.ucomplex.ucomplex.Utility.HttpFactory.encodeLoginData;


public class LoginActivityView extends BaseActivity implements ViewToPresenter, View.OnClickListener{

    AutoCompleteTextView mLoginView;
    EditText mPasswordView;
    View mProgressView;
    Button mForgotButton;
    Button mLoginSignInButton;

    @Inject public void setPresenter(LoginPresenter presenter) {
        super.mPresenter = presenter;
    }
    @Inject public void setModel(LoginModel model) {
        super.mModel = model;
    }
    @Inject public void setRepository(LoginRepository repository) {
        super.mRepository = repository;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews(R.layout.activity_login);
        ((MyApplication) getApplication()).getLoginDiComponent().inject(this);
        super.setupMVP(this, LoginActivityView.class);
    }

    public void setupViews(int layout) {
        setContentView(layout);
        this.mLoginView = ((AutoCompleteTextView) findViewById(R.id.login));
        this.mPasswordView = find(R.id.password);
        this.mProgressView = find(R.id.login_progress);
        this.mForgotButton = find(R.id.forgot_pass_button);
        this.mLoginSignInButton = find(R.id.login_sign_in_button);
        mLoginSignInButton.setOnClickListener(this);
        mForgotButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPresenter.onConfigurationChanged(this);
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

    void clickForgotButton() {
        ((MVP_Login.PresenterInterface) mPresenter).showRestorePasswordDialog();
    }

    void clickLoginButton() {
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        //set login data to User object from model through presenter
        UserInterface user = new User();
        user.setPassword(password);
        user.setLogin(login);
        ((LoginPresenter)mPresenter).setUser(user);

        ArrayList<LoginErrorType> error = ((MVP_Login.PresenterInterface) mPresenter).checkCredentials();

        if (error.contains(PASSWORD_REQUIRED)) {
            mPasswordView.setError(getString(R.string.error_field_required));
        } else if (error.contains(INVALID_PASSWORD)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
        }
        if (error.contains(EMPTY_EMAIL)) {
            mLoginView.setError(getString(R.string.error_field_required));
        }
    }

    public void successfulLogin(int flag) {
        UserInterface user = mPresenter.getUser();
        Intent intent;
        if(flag == 1 || user.getRoles().size() == 1){
            intent = new Intent(getActivityContext(), EventsActivity.class);
            if(user.getRoles().size() == 1){
                user.setType(user.getRoles().get(0).getType());
                FacadePreferences.setUserDataToPref(getActivityContext(), user);
                String loginData = encodeLoginData(user.getLogin()+":"+user.getPassword()+":"+user.getRoles().get(0).getId());
                FacadePreferences.setLoginDataToPref(getActivityContext(), loginData);
            }
        }else {
            intent = new Intent(getActivityContext(), RoleSelectActivity.class);
        }
        intent.putExtra(Constants.EXTRA_KEY_USER, (Parcelable) user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivityContext().startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login_sign_in_button:
                clickLoginButton();
                break;
            case R.id.forgot_pass_button:
                clickForgotButton();
                break;
        }
    }
}
