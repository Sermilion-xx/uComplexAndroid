package org.ucomplex.ucomplex.Activities.Login;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;

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

public class LoginPresenter implements MVP_Login.ProvidedPresenterOpsToView, MVP_Login.RequiredPresenterOpsToModel {

    public final String TAG = LoginPresenter.class.getName();
    private WeakReference<MVP_Login.RequiredViewOpsFromPresenter> mView;
    private MVP_Login.ProvidedModelOpsFromPresenter mModel;
    private OnTaskCompleteListener mTaskCompleteListener = null;

    /**
     * Presenter Constructor
     * @param view  MainActivity
     */
    public LoginPresenter(MVP_Login.RequiredViewOpsFromPresenter view, OnTaskCompleteListener listener) {
        mView = new WeakReference<>(view);
        mTaskCompleteListener = listener;
    }

    /**
     * Called by View every time it is destroyed.
     * @param isChangingConfiguration   true: is changing configuration
     *                                  and will be recreated
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if ( !isChangingConfiguration ) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null;
        }
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return  {@link MVP_Login.RequiredViewOpsFromPresenter}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Login.RequiredViewOpsFromPresenter getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /**
     * Called by View during the reconstruction events
     * @param view  Activity instance
    */
    @Override
    public void setView(MVP_Login.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void login(String login, String password) {
        loadData(login, password);
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param model Model instance
     */
    public void setModel(MVP_Login.ProvidedModelOpsFromPresenter model) {
        mModel = model;
    }

    /**
     * Load data from Model in a AsyncTask
     */
    private void loadData(final String login, final String password) {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, User>() {
                @Override
                protected User doInBackground(Void... params) {
                    // Load data from Model
                    return mModel.loadData(login, password);
                }
                @Override
                protected void onPostExecute(User result) {
                    try {
                        getView().hideProgress();
                        if (result == null) // Loading error
                            getView().showToast(makeToast("Error loading data."));
                        else{
                            mTaskCompleteListener.onTaskComplete(this, result);
                        } // success

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creat a Toast object with given message
     * @param msg   Toast message
     * @return      A Toast object
     */
    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * Retrieve Application Context
     * @return  Application context
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
     * @return  Activity context
     */
    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
