package org.ucomplex.ucomplex.Modules.Login;

import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.basemvp.MVPPresenter;

import org.ucomplex.ucomplex.Domain.Users.LoginErrorType;

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

    interface PresenterInterface extends MVPPresenter {
        void showRestorePasswordDialog();
        ArrayList<LoginErrorType> checkCredentials();
    }

    interface ModelInterface extends MVPModel {
        String sendResetRequest(String email);
    }

}
