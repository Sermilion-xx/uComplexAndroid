package org.ucomplex.ucomplex.Interfaces.MVP;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface PresenterRecycler extends Presenter {
    RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType);
    void bindViewHolder(RecyclerView.ViewHolder holder, int position);
    int getItemViewType(int position);
    int getItemCount();
}
