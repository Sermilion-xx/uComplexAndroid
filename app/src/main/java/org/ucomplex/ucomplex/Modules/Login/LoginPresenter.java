package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.*;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginPresenter implements MVP_Login.PresenterInterface, OnTaskCompleteListener {

    public final String TAG = LoginPresenter.class.getName();
    private WeakReference<MVP_Login.ViewToPresenterInterface> mView;
    private Model mModel;
    private OnTaskCompleteListener mTaskCompleteListener = null;

    /**
     * PresenterToViewInterface Constructor
     *
     * @param view MainActivity
     */
    public LoginPresenter(ViewToPresenter view) {
        mView = new WeakReference<>((MVP_Login.ViewToPresenterInterface) view);
        mTaskCompleteListener = this;
    }



    public LoginPresenter() {
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
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>((MVP_Login.ViewToPresenterInterface)view);
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link MVP_Login.ViewToPresenterInterface}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Login.ViewToPresenterInterface getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public void login() {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return mModel.loadData();
                }
                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                    try {
                        getView().hideProgress();
                        if (result == null)
                            getView().showToast(makeToast(getActivityContext().getString(R.string.error_login)));
                        else
                            mTaskCompleteListener.onTaskComplete(this, result, 0);
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
                                    return ((LoginModel)mModel).sendResetRequest(email);
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



    private ArrayList<LoginErrorType> runCheck(String login, String password) {
        ArrayList<LoginErrorType> errors = new ArrayList<>();
        if (TextUtils.isEmpty(password)) {
            errors.add(PASSWORD_REQUIRED);
        } else if (!isPasswordValid(password)) {
            errors.add(INVALID_PASSWORD);
        }
        if (TextUtils.isEmpty(login)) {
            errors.add(EMPTY_EMAIL);
        }
        if(errors.size()==0){
            errors.add(NO_ERROR);
        }
        return errors;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    @Override
    public ArrayList<LoginErrorType> checkCredentials(String login, String password) {
        ArrayList<LoginErrorType> error = runCheck(login, password);
        if (error.get(0)==NO_ERROR) {
            login();
        }
        return error;
    }


    @Override
    public void onConfigurationChanged(ViewToPresenter view) {
        mView = new WeakReference<>((MVP_Login.ViewToPresenterInterface) view);
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param model Model instance
     */
    @Override
    public void setModel(Model model) {
        mModel = model;
        UserInterface user = FacadePreferences.getUserDataFromPref(getActivityContext());
        if(user!=null){
            mModel.setData(user);
            //1 - has already been logged
            onTaskComplete(null, true, 1);
        }
    }

    /**
     * Creat a Toast object with given message
     * @param msg Toast message
     * @return A Toast object
     */
    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * Retrieve Application Context
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
            UserInterface user = ((LoginModel)mModel).getUser();
            getView().successfulLogin(user, (int)o[1]);
        }

    }
}
