package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
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
import net.oneread.aghanim.mvp.basemvp.MVPView;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

import static org.ucomplex.ucomplex.CommonDependencies.HttpFactory.encodeLoginData;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.EMPTY_EMAIL;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.INVALID_PASSWORD;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.NO_ERROR;
import static org.ucomplex.ucomplex.Model.Users.LoginErrorType.PASSWORD_REQUIRED;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginPresenter extends AbstractPresenter {

    @Override
    public void setModel(MVPModel model) {
        mModel = model;
        ((LoginModel)mModel).setContext(getActivityContext());
        UserInterface user = FacadeCommon.getSharedUserInstance(getActivityContext());
        if(user!=null){
            ((LoginActivityView)getView()).successfulLogin(1);
        }
    }

    @Override
    public void loadData(Bundle... bundle) {
        mModel.loadData(new MVPCallback() {
            @Override
            public void onSuccess(Object o) {
                mModel.processJson((String)o);
                UserInterface user = ((LoginModel)mModel).getUser();
                if(user.getRoles().size() == 1){
                    user.setType(user.getRoles().get(0).getType());
                    FacadePreferences.setUserDataToPref(getActivityContext(), user);
                    String loginData = encodeLoginData(user.getLogin()+":"+user.getPassword()+":"+user.getRoles().get(0).getId());
                    FacadePreferences.setLoginDataToPref(getActivityContext(), loginData);
                }
                ((LoginActivityView)getView()).successfulLogin(0);
            }
            @Override
            public void onError(Throwable throwable) {
                ((LoginActivityView)getView()).showToast(makeToast(getActivityContext().getString(R.string.error_login)));
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
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private ArrayList<LoginErrorType> runCheck() {
        UserInterface user = ((LoginModel)mModel).getUser();
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
        if(errors.size()==0){
            errors.add(NO_ERROR);
        }
        return errors;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }


    public ArrayList<LoginErrorType> checkCredentials() {
        ArrayList<LoginErrorType> error = runCheck();
        if (error.get(0)==NO_ERROR) {
            loadData();
        }
        return error;
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }


    public void onTaskComplete(int requestType, Object... o) {
        boolean result = (boolean) o[0];
        if (result) {
            ((LoginActivityView)getView()).successfulLogin(1);
        }
    }
}
