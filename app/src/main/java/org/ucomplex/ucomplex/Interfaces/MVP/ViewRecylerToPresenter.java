package org.ucomplex.ucomplex.Interfaces.MVP;

import android.view.View;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 12/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface ViewRecylerToPresenter extends ViewToPresenter {
    void notifyItemRemoved(int position);
    void notifyDataSetChanged();
    void notifyItemRangeInserted(int start, int end);
    void notifyItemInserted(int layoutPosition);
    void notifyItemRangeChanged(int positionStart, int itemCount);
}
