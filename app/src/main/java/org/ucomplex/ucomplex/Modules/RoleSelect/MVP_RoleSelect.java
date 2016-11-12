package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.ucomplex.ucomplex.Model.Users.UserInterface;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface MVP_RoleSelect {

    /**
     * Required View methods available to PresenterToView.
     * A passive layer, responsible to show data
     * and receive user interactions
     * PresenterToView to View
     */
    interface RequiredViewOpsFromPresenter {
        Context getAppContext();

        Context getActivityContext();

        void showToast(Toast toast);

        void notifyItemRemoved(int position);

        void notifyDataSetChanged();

        void notifyItemInserted(int layoutPosition);

        void notifyItemRangeChanged(int positionStart, int itemCount);
    }

    /**
     * Operations offered to View to communicate with PresenterToView.
     * Process user interaction, sends data requests to Model, etc.
     * View to PresenterToView
     */
    interface ProvidedPresenterOpsToView {
        void onDestroy(boolean isChangingConfiguration);

        void setView(RequiredViewOpsFromPresenter view);

        RoleViewHolder createViewHolder(ViewGroup parent, int viewType);

        void bindViewHolder(RoleViewHolder holder, int position);

        int getRolesCount();
    }

    /**
     * Required PresenterToView methods available to Model.
     * Model to PresenterToView
     */
    interface RequiredPresenterOpsToModel {
        Context getAppContext();

        Context getActivityContext();
    }

    /**
     * Operations offered to Model to communicate with PresenterToView
     * Handles all data business logic.
     * PresenterToView to Model
     */
    interface ProvidedModelOpsFromPresenter {

        void onDestroy(boolean isChangingConfiguration);

        boolean loadData();

        RoleItem getRole(int position);

        int getRolesCount();

        void setUser(UserInterface user);

        UserInterface getmUser();
    }

}
