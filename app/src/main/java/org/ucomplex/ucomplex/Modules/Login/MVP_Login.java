package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.PresenterToModel;
import org.ucomplex.ucomplex.Interfaces.MVP.PresenterToView;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.User;

import java.util.ArrayList;

/**
 * Holder interface that contains all interfaces
 * responsible to maintain communication between
 * Model View PresenterToView layers.
 * Each layer implements its respective interface:
 *      View implements ViewToPresenterInterface
 *      PresenterToView implements PresenterToViewInterface, PresenterToModelInterface
 *      Model implements ModelInterface
 *
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: uComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MVP_Login {
    /**
     * Required View methods available to PresenterToView.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      PresenterToView to View
     */
    interface ViewToPresenterInterface extends ViewToPresenter {
        Context getAppContext();
        Context getActivityContext();
        void showToast(Toast toast);
        void showProgress();
        void hideProgress();
        void showAlert(AlertDialog dialog);
        void successfulLogin(User user);
    }
    /**
     * Operations offered to View to communicate with PresenterToView.
     * Process user interaction, sends data requests to Model, etc.
     *      View to PresenterToView
     */
    interface PresenterToViewInterface extends PresenterToView {
        void onDestroy(boolean isChangingConfiguration);
        void showRestorePasswordDialog();
        ArrayList<LoginErrorType> checkCredentials(String login, String password);
    }
    /**
     * Required PresenterToView methods available to Model.
     *      Model to PresenterToView
     */
    interface PresenterToModelInterface extends PresenterToModel {

    }
    /**
     * Operations offered to Model to communicate with PresenterToView
     * Handles all data business logic.
     *      PresenterToView to Model
     */
    interface ModelInterface extends Model {
        boolean loadData();
        String sendResetRequest(String email);
        User getUser();
    }

}
