package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
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
     * Required View methods available to PresenterToViewInterface.
     * A passive layer, responsible to show data
     * and receive user interactions
     * PresenterToViewInterface to View
     */
    interface ViewToPresenterInterface extends ViewToPresenter {
        Context getAppContext();

        Context getActivityContext();

        void showToast(Toast toast);

        void notifyItemRemoved(int position);

        void notifyDataSetChanged();

        void notifyItemInserted(int layoutPosition);

        void notifyItemRangeChanged(int positionStart, int itemCount);
    }

    /**
     * Operations offered to View to communicate with PresenterToViewInterface.
     * Process user interaction, sends data requests to Model, etc.
     * View to PresenterToViewInterface
     */
    interface PresenterInterface extends Presenter{
        RoleViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(RoleViewHolder holder, int position);
        int getRolesCount();
        void setView(ViewToPresenter view);
    }

    /**
     * Operations offered to Model to communicate with PresenterToViewInterface
     * Handles all data business logic.
     * PresenterToViewInterface to Model
     */
    interface ModelInterface extends Model {

        void onDestroy(boolean isChangingConfiguration);

        boolean loadData();

        RoleItem getRole(int position);

        int getRolesCount();

        UserInterface getUser();
    }

}
