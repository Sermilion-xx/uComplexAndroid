package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Activities.BaseActivity;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

public class LoginActivity extends BaseActivity implements OnTaskCompleteListener, View.OnClickListener, MVP_Login.RequiredViewOpsFromPresenter {

    private MVP_Login.ProvidedPresenterOpsToView mPresenter;
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mForgotButton;
    private Button mLoginSignInButton;

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
        mLoginSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mForgotButton = (Button) findViewById(R.id.forgot_pass_button);
        mLoginSignInButton.setOnClickListener(this);
    }

    /**
     * Setup Model View Presenter pattern.
     * Use a {@link StateMaintainer} to maintain the
     * Presenter and Model instances between configuration changes.
     * Could be done differently,
     * using a dependency injection for example.
     */
    private void setupMVP() {
        // Check if StateMaintainer has been created
        if (mStateMaintainer.firstTimeIn()) {
            // Create the Presenter
            LoginPresenter presenter = new LoginPresenter(this, this);
            // Create the Model
            LoginModel model = new LoginModel(presenter);
            // Set Presenter model
            presenter.setModel(model);
            // Add Presenter and Model to StateMaintainer
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(model);

            // Set the Presenter as a interface
            // To limit the communication with it
            mPresenter = presenter;

        }
        // get the Presenter from StateMaintainer
        else {
            // Get the Presenter
            mPresenter = mStateMaintainer.get(LoginPresenter.class.getName());
            // Updated the View in Presenter
            mPresenter.setView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {
        System.out.println(task);
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAlert(AlertDialog dialog) {
        dialog.show();
    }

    @Override
    public void proceedLogin() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_sign_in_button:
                mPresenter.login(mLoginView.getText().toString(), mPasswordView.getText().toString());
                break;
        }
    }
}
