package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Activities.BaseActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

public class LoginActivity extends BaseActivity implements View.OnClickListener, MVP_Login.RequiredViewOpsFromPresenter {

    private MVP_Login.ProvidedPresenterOpsToView mPresenter;
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private Button mForgotButton;

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer(getFragmentManager(), LoginActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        setupMVP();
    }

    /**
     * Setup the Views
     */
    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLoginView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mLoginSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mProgressView = findViewById(R.id.login_progress);
        mForgotButton = (Button) findViewById(R.id.forgot_pass_button);
        mLoginSignInButton.setOnClickListener(this);
        mForgotButton.setOnClickListener(this);
    }

    /**
     * Setup Model View Presenter pattern.
     * Use a {@link StateMaintainer} to maintain the
     * Presenter and Model instances between configuration changes.
     * Could be done differently,
     * using a dependency injection for example.
     */
    private void setupMVP() {
        if (mStateMaintainer.firstTimeIn()) {
            LoginPresenter presenter = new LoginPresenter(this);
            LoginModel model = new LoginModel(presenter);
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

    @Override
    public void proceedLogin() {
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String email = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            cancel = true;
        }else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mLoginView.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        if (!cancel) {
            mPresenter.login(mLoginView.getText().toString(), mPasswordView.getText().toString());
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_sign_in_button:
                proceedLogin();
                break;
            case R.id.forgot_pass_button:
                mPresenter.showRestorePasswordDialog();
                break;
        }
    }
}
