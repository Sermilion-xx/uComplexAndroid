package org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP;

import android.support.v7.widget.RecyclerView;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.ViewToPresenter;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 12/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface ViewToPresenterRecycler extends ViewToPresenter {
    void notifyItemRemoved(int position);
    void notifyDataSetChanged();
    void notifyItemRangeInserted(int start, int end);
    void notifyItemRangeRemoved(int start, int end);
    void notifyItemInserted(int layoutPosition);
    void notifyItemRangeChanged(int positionStart, int itemCount);
    RecyclerView getRecyclerView();
}