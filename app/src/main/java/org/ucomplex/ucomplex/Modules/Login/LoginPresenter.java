package org.ucomplex.ucomplex.Modules.Login;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractPresenter;
import net.oneread.aghanim.mvp.basemvp.MVPModel;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Domain.Users.LoginErrorType;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

import static org.ucomplex.ucomplex.CommonDependencies.HttpFactory.encodeLoginData;
import static org.ucomplex.ucomplex.Domain.Users.LoginErrorType.EMPTY_EMAIL;
import static org.ucomplex.ucomplex.Domain.Users.LoginErrorType.INVALID_PASSWORD;
import static org.ucomplex.ucomplex.Domain.Users.LoginErrorType.NO_ERROR;
import static org.ucomplex.ucomplex.Domain.Users.LoginErrorType.PASSWORD_REQUIRED;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginPresenter extends AbstractPresenter<String, UserInterface> {

    @Override
    public void setModel(MVPModel model, Bundle... bundles) {
        mModel = model;
        mModel.setContext(getActivityContext());
        UserInterface user = ((DaggerApplication) getAppContext()).getSharedUser();
        if (user != null) {
            ((LoginActivityView) getView()).successfulLogin(1);
        }
    }

    @Override
    public void loadData(Bundle... bundle) {
        ((LoginActivityView) getView()).showProgress();
        mModel.loadData(new MVPCallback<UserInterface>() {
            @Override
            public void onSuccess(UserInterface user) {
                user.setPassword(((LoginModel) mModel).getTempPassword());
                user.setType(user.getRoles().get(0).getType());
                ((DaggerApplication) getAppContext()).setSharedUser(user);
                String loginData = encodeLoginData(user.getLogin() + ":" + user.getPassword() + ":" + user.getRoles().get(0).getId());
                FacadePreferences.setLoginDataToPref(getActivityContext(), loginData);
                DaggerApplication application = (DaggerApplication) getAppContext();
                FacadePreferences.setUserDataToPref(getActivityContext(), user);
                FacadePreferences.savePrevLoginInfo(getActivityContext(), user.getLogin(), user.getPassword());
                application.setAuthString(loginData);
                application.setSharedUser(user);
                ((LoginActivityView) getView()).hideProgress();
                ((LoginActivityView) getView()).successfulLogin(0);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                ((LoginActivityView) getView()).showToast(makeToast(getActivityContext().getString(R.string.error_login)));
                ((LoginActivityView) getView()).hideProgress();
            }
        });
    }

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
                                    return ((LoginModel) mModel).sendResetRequest(email);
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
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private ArrayList<LoginErrorType> runCheck() {
        UserInterface user = ((LoginModel) mModel).getUser();
        ArrayList<LoginErrorType> errors = new ArrayList<>();
        String password = user.getPassword();
        String login = user.getLogin();
        if (TextUtils.isEmpty(password)) {
            errors.add(PASSWORD_REQUIRED);
        } else if (!isPasswordValid(password)) {
            errors.add(INVALID_PASSWORD);
        }
        if (TextUtils.isEmpty(login)) {
            errors.add(EMPTY_EMAIL);
        }
        if (errors.size() == 0) {
            errors.add(NO_ERROR);
        }
        return errors;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }


    public ArrayList<LoginErrorType> checkCredentials() {
        ArrayList<LoginErrorType> error = runCheck();
        if (error.get(0) == NO_ERROR) {
            loadData();
        }
        return error;
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }
}
