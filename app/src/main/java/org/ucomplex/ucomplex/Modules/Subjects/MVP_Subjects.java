package org.ucomplex.ucomplex.Modules.Subjects;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.Model.EventItem;


/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MVP_Subjects {

    interface ViewToPresenterInterface extends ViewToPresenterRecycler {
        Context getAppContext();
        Context getActivityContext();
        void showToast(Toast toast);
        void showProgress();
        void hideProgress();
        void showAlert(AlertDialog dialog);
        void goToSubject();
    }

    interface PresenterInterface extends Presenter {
        SubjectViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(SubjectViewHolder holder, int position);
        int getItemViewType(int position);
        int getItemCount();
        void loadData();
    }

    interface ModelInterface extends Model {
        EventItem getItem(int position);
        int getItemCount();
        void loadMoreEvents(int start);
    }
}
