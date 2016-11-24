package org.ucomplex.ucomplex.Modules.Events;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 24/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ListEventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private Presenter mPresenter;

    public ListEventsAdapter(Presenter presenter){
        mPresenter = presenter;
    }

    @Override
    public int getItemCount() {
        return ((MVP_Events.PresenterInterface) mPresenter).getEventsCount();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ((MVP_Events.PresenterInterface) mPresenter).createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        ((MVP_Events.PresenterInterface) mPresenter).bindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return ((MVP_Events.PresenterInterface) mPresenter).getItemViewType(position);
    }
}
