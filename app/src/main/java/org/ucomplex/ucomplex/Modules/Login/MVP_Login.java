package org.ucomplex.ucomplex.Modules.Login;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewActivityToPresenterInter;
import org.ucomplex.ucomplex.Model.Users.LoginErrorType;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

import java.util.ArrayList;

/**
 * Holder interface that contains all interfaces
 * responsible to maintain communication between
 * Model View PresenterToViewInterface layers.
 * Each layer implements its respective interface:
 *      View implements ViewToPresenterInterface
 *      PresenterToViewInterface implements PresenterToViewInterface, PresenterToModelInterface
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

    interface ViewToPresenterInterface extends ViewActivityToPresenterInter {
        void successfulLogin(UserInterface user);
    }

    interface PresenterInterface extends Presenter {
        void onDestroy(boolean isChangingConfiguration);
        void showRestorePasswordDialog();
        ArrayList<LoginErrorType> checkCredentials(String login, String password);
    }

    interface ModelInterface extends Model {
        boolean loadData();
        String sendResetRequest(String email);
        UserInterface getUser();
    }

}
