package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.lang.ref.WeakReference;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginPresenter implements MVP_Login.PresenterToViewInterface, MVP_Login.PresenterToModel, OnTaskCompleteListener {

    public final String TAG = LoginPresenter.class.getName();
    private WeakReference<MVP_Login.ViewInterface> mView;
    private MVP_Login.ModelInterface mModel;
    private OnTaskCompleteListener mTaskCompleteListener = null;

    /**
     * Presenter Constructor
     *
     * @param view MainActivity
     */
    public LoginPresenter(MVP_Login.ViewInterface view) {
        mView = new WeakReference<>(view);
        mTaskCompleteListener = this;
    }

    /**
     * Called by View every time it is destroyed.
     *
     * @param isChangingConfiguration true: is changing configuration
     *                                and will be recreated
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if (!isChangingConfiguration) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null;
        }
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link MVP_Login.ViewInterface}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Login.ViewInterface getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /**
     * Called by View during the reconstruction events
     * @param view Activity instance
     */
    @Override
    public void setView(MVP_Login.ViewInterface view) {
        mView = new WeakReference<>(view);
    }

    public void login(final String login, final String password) {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return mModel.loadData(login, password);
                }
                @Override
                protected void onPostExecute(Boolean result) {
                    try {
                        getView().hideProgress();
                        if (result == null)
                            getView().showToast(makeToast(getActivityContext().getString(R.string.error_login)));
                        else
                            mTaskCompleteListener.onTaskComplete(this, result);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRestorePasswordDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivityContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_forgot_password, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivityContext());
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getActivityContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (HttpFactory.isNetworkConnected(getActivityContext())) {
                            final String email = editText.getText().toString();
                            new AsyncTask<Void, Void, String>() {
                                @Override
                                protected String doInBackground(Void... params) {
                                    return mModel.sendResetRequest(email);
                                }

                                @Override
                                protected void onPostExecute(String result) {
                                    super.onPostExecute(result);
                                    if (result != null) {
                                        makeToast(getActivityContext().getString(R.string.reset_password_sent));
                                    }
                                }
                            }.execute();
                        } else {
                            makeToast(getActivityContext().getString(R.string.error_check_internet));
                        }
                    }
                })
                .setNegativeButton(getActivityContext().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    private LoginErrorType runCheck(String login, String password) {
        if (TextUtils.isEmpty(password)) {
            return LoginErrorType.PASSWORD_REQUIRED;
        } else if (!isPasswordValid(password)) {
            return LoginErrorType.INVALID_PASSWORD;
        }
        if (TextUtils.isEmpty(login)) {
            return LoginErrorType.EMPTY_EMAIL;
        }
        return LoginErrorType.NO_ERROR;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    @Override
    public LoginErrorType checkCredentials(String login, String password) {
        LoginErrorType error = runCheck(login, password);
        if (error == LoginErrorType.NO_ERROR) {
            login(login, password);
        }
        return error;
    }


    /**
     * Called by Activity during MVP setup. Only called once.
     *
     * @param model Model instance
     */
    public void setModel(MVP_Login.ModelInterface model) {
        mModel = model;
    }

    /**
     * Creat a Toast object with given message
     *
     * @param msg Toast message
     * @return A Toast object
     */
    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * Retrieve Application Context
     *
     * @return Application context
     */
    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Retrieves Activity context
     *
     * @return Activity context
     */
    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }


    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {
        boolean result = (boolean) o[0];
        if (result) {
            getView().successfulLogin(mModel.getUser());
        }

    }
}
