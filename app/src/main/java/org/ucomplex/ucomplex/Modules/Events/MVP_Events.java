package org.ucomplex.ucomplex.Modules.Events;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.PresenterRecycler;
import org.ucomplex.ucomplex.Model.EventItem;

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

    interface PresenterInterface extends PresenterRecycler {
        void loadMoreEvents(int start);
    }

    interface ModelInterface extends ModelRecycler {
        void loadMoreEvents(int start);
    }
}
