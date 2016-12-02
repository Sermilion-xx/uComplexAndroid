package org.ucomplex.ucomplex.BaseComponents;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 24/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class BaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PresenterRecycler mPresenter;

    public BaseListAdapter(Presenter presenter){
        mPresenter = (PresenterRecycler) presenter;
    }

    @Override
    public int getItemCount() {
        return mPresenter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mPresenter.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mPresenter.bindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mPresenter.getItemViewType(position);
    }
}
