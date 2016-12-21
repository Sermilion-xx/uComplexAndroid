package org.ucomplex.ucomplex.Modules.Login;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.oneread.sermilionmvp.Data.DataSource;
import net.oneread.sermilionmvp.MVP.AbstractMVP.AbstractMVPPresenter;
import net.oneread.sermilionmvp.MVP.BaseMVP.MVPModel;
import net.oneread.sermilionmvp.MVP.BaseMVP.MVPView;
import net.oneread.sermilionmvp.MVP.RecyclerMVP.MVPViewRecycler;

import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

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

public class LoginPresenter extends AbstractMVPPresenter {


    public void setUser(UserInterface user){
        ((LoginModel)mModel).setUser(user);
    }

    public UserInterface getUser(){
        return ((LoginModel)mModel).getUser();
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
//                            new AsyncTask<Void, Void, String>() {
//                                @Override
//                                protected String doInBackground(Void... params) {
//                                    return ((LoginModel)mModel).sendResetRequest(email);
//                                }
//
//                                @Override
//                                protected void onPostExecute(String result) {
//                                    super.onPostExecute(result);
//                                    if (result != null) {
//                                        makeToast(getActivityContext().getString(R.string.reset_password_sent));
//                                    }
//                                }
//                            }.execute();
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
        ArrayList<LoginErrorType> errors = new ArrayList<>();
        String password = ((LoginModel)mModel).getUser().getPassword();
        String login = ((LoginModel)mModel).getUser().getLogin();
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

    private UserInterface loadLoggedUser() {
        return FacadePreferences.getUserDataFromPref(getActivityContext());
    }
    //MVP library
    @Override
    public void setModel(MVPModel model) {
        mModel = model;
        UserInterface user = loadLoggedUser();
        if(user!=null){
            ((LoginModel)mModel).setUser(user);
        }
    }
    //MVP library
    @Override @SuppressWarnings("unchecked")
    public void loadData() {
        getView().showProgress();
        mModel.loadData(new DataSource.LoadJsonCallback<String, String>() {

            @Override
            public void onTasksLoaded(String s) {
                getView().hideProgress();
                ((LoginActivityView)getView()).successfulLogin(0);
            }

            @Override
            public void onDataNotAvailable(String s) {
                makeToast("Ошибка при входе").show();
                getView().hideProgress();
            }
        });
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

}
