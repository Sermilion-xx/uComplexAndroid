package org.ucomplex.ucomplex.Modules.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.oneread.aghanim.components.base.MVPBaseActivity;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.EMPTY_EMAIL;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.INVALID_PASSWORD;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.PASSWORD_REQUIRED;


public class LoginActivityView extends MVPBaseActivity implements View.OnClickListener{

    AutoCompleteTextView mLoginView;
    EditText mPasswordView;
    Button mForgotButton;
    Button mLoginSignInButton;
    View mProgressView;

    @Inject public void setPresenter(LoginPresenter presenter) {
        super.mPresenter = presenter;
    }
    @Inject public void setModel(LoginModel model) {
        super.mModel = model;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getLoginDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setupViews(R.layout.activity_login);
        super.setupMVP(this, LoginActivityView.class);
    }

    public void setupViews(int layout) {
        setContentView(layout);
        this.mLoginView = ((AutoCompleteTextView) findViewById(R.id.login));
        this.mPasswordView = (EditText) findViewById(R.id.password);
        this.mProgressView = findViewById(R.id.login_progress);
        this.mForgotButton = (Button) findViewById(R.id.forgot_pass_button);
        this.mLoginSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mLoginSignInButton.setOnClickListener(this);
        mForgotButton.setOnClickListener(this);
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
        ((LoginModel)mModel).setUser(user);

        ArrayList<LoginErrorType> error = ((LoginPresenter) mPresenter).checkCredentials();

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
        Intent intent;
        if(flag == 1 || ((LoginModel)mModel).getUser().getRoles().size() == 1){
            intent = new Intent(getActivityContext(), EventsActivity.class);
        }else {
            intent = new Intent(getActivityContext(), RoleSelectActivity.class);
        }
        intent.putExtra(Constants.EXTRA_KEY_USER, (Parcelable) ((LoginModel)mModel).getUser());
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


    public void showToast(Toast toast) {
        toast.show();
    }
}
