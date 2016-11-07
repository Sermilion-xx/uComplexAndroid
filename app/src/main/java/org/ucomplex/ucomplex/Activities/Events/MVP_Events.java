package org.ucomplex.ucomplex.Activities.Events;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Holder interface that contains all interfaces
 * responsible to maintain communication between
 * Model View Presenter layers.
 * Each layer implements its respective interface:
 *      View implements RequiredViewOpsFromPresenter
 *      Presenter implements ProvidedPresenterOpsToView, RequiredPresenterOpsToModel
 *      Model implements ProvidedModelOpsFromPresenter
 *
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: uComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MVP_Events {
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     *      Presenter to View
     */
    interface RequiredViewOpsFromPresenter {
        Context getAppContext();
        Context getActivityContext();
        void showToast(Toast toast);
        void showProgress();
        void hideProgress();
        void showAlert(AlertDialog dialog);
        void notifyItemRemoved(int position);
        void notifyDataSetChanged();
        void notifyItemInserted(int layoutPosition);
        void notifyItemRangeChanged(int positionStart, int itemCount);
        void goToCourse();
    }
    /**
     * Operations offered to View to communicate with Presenter.
     * Process user interaction, sends data requests to Model, etc.
     *      View to Presenter
     */
    interface ProvidedPresenterOpsToView {
        void onDestroy(boolean isChangingConfiguration);
        void setView(RequiredViewOpsFromPresenter view);
        EventsViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(EventsViewHolder holder, int position);
        int getEventsCount();
    }
    /**
     * Required Presenter methods available to Model.
     *      Model to Presenter
     */
    interface RequiredPresenterOpsToModel {
        Context getAppContext();
        Context getActivityContext();
    }
    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     *      Presenter to Model
     */
    interface ProvidedModelOpsFromPresenter {
        void onDestroy(boolean isChangingConfiguration);
        boolean loadData();
        EventItem getEvent(int position);
        int getEventsCount();
    }

}
