package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewActivityToPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.User;
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

public class LoginPresenter implements MVP_Login.PresenterInterface, OnTaskCompleteListener, OnDataLoadedListener {

    private WeakReference<ViewActivityToPresenter> mView;
    private Model mModel;
    private OnTaskCompleteListener mTaskCompleteListener = null;

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
        mView = new WeakReference<>((ViewActivityToPresenter)view);
    }

    @Override
    public ViewActivityToPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void dataLoaded(boolean loaded){
        getView().hideProgress();
        if(loaded)
            ((LoginActivityView)getView()).successfulLogin(0);
        else
            getView().showToast(makeToast(getActivityContext().getString(R.string.error_login)));
    }

    public void loadUser() {
        getView().showProgress();
        mModel.loadData();
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

    private ArrayList<LoginErrorType> runCheck() {
        ArrayList<LoginErrorType> errors = new ArrayList<>();
        String password = mModel.getUser().getPassword();
        String login = mModel.getUser().getLogin();
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
    public ArrayList<LoginErrorType> checkCredentials() {
        ArrayList<LoginErrorType> error = runCheck();
        if (error.get(0)==NO_ERROR) {
            loadUser();
        }
        return error;
    }


    @Override
    public void onConfigurationChanged(ViewToPresenter view) {
        mView = new WeakReference<>((ViewActivityToPresenter) view);
    }

    @Override
    public void setModel(Model model) {
        mModel = model;
        ((LoginModel)mModel).setOnDataLoadedListener(this);
        UserInterface user = ((LoginModel)mModel).loadLoggedUser();
        if(user!=null){
            mModel.setData(user);
            //1 - has already been logged
            onTaskComplete(null, true, 1);
        }
    }

    @Override
    public UserInterface getUser() {
        return mModel.getUser();
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void onTaskComplete(String requestType, Object... o) {
        boolean result = (boolean) o[0];
        if (result) {
            ((LoginActivityView)getView()).successfulLogin(1);
        }
    }
}
