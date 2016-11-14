package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;

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

class MVP_Events {

    interface ViewToPresenterInterface extends ViewRecylerToPresenter {
        Context getAppContext();
        Context getActivityContext();
        void showToast(Toast toast);
        void showProgress();
        void hideProgress();
        void showAlert(AlertDialog dialog);
        void goToCourse();
    }

    interface PresenterInterface extends Presenter {
        EventViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(EventViewHolder holder, int position);
        int getEventsCount();
    }

    interface ModelInterface extends Model {
        void loadIcon(String code, ImageView imageView);
        EventItem getEvent(int position);
        int getEventsCount();
    }
}
