package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ucomplex.ucomplex.Activities.Events.EventsActivity;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
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

public class LoginPresenter implements MVP_Login.ProvidedPresenterOpsToView, MVP_Login.RequiredPresenterOpsToModel, OnTaskCompleteListener {

    public final String TAG = LoginPresenter.class.getName();
    private WeakReference<MVP_Login.RequiredViewOpsFromPresenter> mView;
    private MVP_Login.ProvidedModelOpsFromPresenter mModel;
    private OnTaskCompleteListener mTaskCompleteListener = null;

    /**
     * Presenter Constructor
     *
     * @param view MainActivity
     */
    public LoginPresenter(MVP_Login.RequiredViewOpsFromPresenter view) {
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
     * @return {@link MVP_Login.RequiredViewOpsFromPresenter}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Login.RequiredViewOpsFromPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /**
     * Called by View during the reconstruction events
     *
     * @param view Activity instance
     */
    @Override
    public void setView(MVP_Login.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void login(final String login, final String password) {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, User>() {
                @Override
                protected User doInBackground(Void... params) {
                    return mModel.loadData(login, password);
                }

                @Override
                protected void onPostExecute(User result) {
                    try {
                        getView().hideProgress();
                        if (result == null)
                            getView().showToast(makeToast("Error loading data."));
                        else {
                            mTaskCompleteListener.onTaskComplete(this, result);
                        }
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
    public void restorePassword(final String login, final String password) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivityContext());
            View promptView = layoutInflater.inflate(R.layout.dialog_forgot_password, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivityContext());
            alertDialogBuilder.setView(promptView);
            final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton(getActivityContext().getString(R.string.reset_password_sent), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (HttpFactory.isNetworkConnected(getActivityContext())) {
                                final String email = editText.getText().toString();
                                new AsyncTask<Void, Void, String>() {
                                    @Override
                                    protected String doInBackground(Void... params) {
                                        return mModel.sendResetRequest(login, password, email);
                                    }
                                    @Override
                                    protected void onPostExecute(String result) {
                                        super.onPostExecute(result);
                                        if(result!=null){
                                            makeToast(getActivityContext().getString(R.string.reset_password_sent));
                                        }
                                    }
                                }.execute();
                            } else {
                                makeToast(getActivityContext().getString(R.string.check_internet));
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


    /**
     * Called by Activity during MVP setup. Only called once.
     *
     * @param model Model instance
     */
    public void setModel(MVP_Login.ProvidedModelOpsFromPresenter model) {
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
        Intent intent;
        User user = (User) o[0];
        FacadePreferences.setUserDataToPref(getAppContext(), user);
        if (user.getRoles().size() > 1) {
            intent = new Intent(getActivityContext(), RoleSelectActivity.class);
        } else {
            intent = new Intent(getActivityContext(), EventsActivity.class);
        }
        getActivityContext().startActivity(intent);
    }
}
