package org.ucomplex.ucomplex.Interfaces.MVP;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 12/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
/**
 * Required View methods available to PresenterToView.
 * A passive layer, responsible to show data
 * and receive user interactions
 *      PresenterToView to View
 */
public interface ViewToPresenter {
    Context getAppContext();
    Context getActivityContext();
    void showToast(Toast toast);
    void showProgress();
    void hideProgress();
    void showAlert(AlertDialog dialog);
}
