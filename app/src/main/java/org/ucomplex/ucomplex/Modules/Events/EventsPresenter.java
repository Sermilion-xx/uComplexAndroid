package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.lang.ref.WeakReference;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsPresenter implements MVP_Events.PresenterInterface, OnDataLoadedListener {

    private WeakReference<ViewRecylerToPresenter> mView;
    private Model mModel;
    private boolean hasMoreEvents = true;

    private static final int TYPE_COMMON = 0;
    private static final int TYPE_FOOTER = 1;

    public EventsPresenter() {

    }

    public EventsPresenter(ViewRecylerToPresenter view) {
        mView = new WeakReference<>(view);
    }

    public void setHasMoreEvents(boolean hasMoreEvents) {
        this.hasMoreEvents = hasMoreEvents;
    }

    @Override
    public UserInterface getUser() {
        return mModel.getUser();
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        HttpFactory.getInstance().cancel();
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    @Override
    public void onConfigurationChanged(ViewToPresenter view) {
        mView = new WeakReference<>((ViewRecylerToPresenter) view);
    }

    @Override
    public void setModel(Model models) {
        mModel = models;
        ((EventsModel) mModel).setOnDataLoadedListener(this);
        loadData();
    }

    @Override
    public ViewToPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>((ViewRecylerToPresenter) view);
    }

    @Override
    public EventViewHolder createViewHolder(ViewGroup parent, int viewType) {
        EventViewHolder viewHolder;
        int layout = viewType == 0 ? R.layout.list_item_event : R.layout.list_item_event_footer;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(layout, parent, false);
        viewHolder = new EventViewHolder(viewTaskRow);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(final EventViewHolder holder, int position) {
        if (position != getEventsCount() - 1) {
            final EventItem event = ((EventsModel) mModel).getEvent(position);
            String personName = event.getParams().getName();
            if (personName == null || personName.equals(Constants.STRING_EMPTY)) {
                event.getParams().setName(getActivityContext().getResources().getString(R.string.ucomplex));
            }
            holder.eventPersonName.setText(event.getParams().getName());
            holder.eventTextView.setText(event.getEventText());
            holder.eventTime.setText(event.getTime());
            int id = event.getParams().getId();
            String name = event.getParams().getName();
            Drawable textDrawable = FacadeMedia.getTextDrawable(id, name, getActivityContext());
            holder.eventsImageView.setImageDrawable(textDrawable);
            if (event.getEventImageBitmap() != null) {
                holder.eventsImageView.setImageBitmap(event.getEventImageBitmap());
            } else {
                if (event.getParams().getCode() == null) {
                    holder.eventsImageView.setImageDrawable(textDrawable);
                } else {
                    Glide.with(getActivityContext())
                            .load(HttpFactory.LOAD_PROFILE_URL + event.getParams().getCode() + Constants.IMAGE_FORMAT)
                            .into(holder.eventsImageView);
                }
            }

//            holder.eventDetailsLayout.setOnClickListener(view -> {
////                Intent intent = new Intent(getActivityContext(), null);
//            });
        } else {
            if (hasMoreEvents) {
                holder.loadMoreEventsButton.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View view) {
                                                                       getActivityContext().sendBroadcast(new Intent(Constants.EVENTS_LOAD_MORE_BROADCAST));
                                                                   }
                                                               }
                );
            } else {
                holder.loadMoreEventsButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == ((EventsModel) mModel).getEventsCount() - 1 ? TYPE_FOOTER : TYPE_COMMON;
    }

    public void loadData() {
        try {
            getView().showProgress();
            mModel.loadData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoreEvents(final int start) {
        try {
            getView().showProgress();
            ((EventsModel) mModel).loadMoreEvents(start);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getEventsCount() {
        return ((EventsModel) mModel).getEventsCount();
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void dataLoaded(boolean loaded, int start, int end) {
        getView().hideProgress();
        if (loaded) {
            ((ViewRecylerToPresenter) getView()).notifyItemRangeRemoved(start, end);
            ((ViewRecylerToPresenter) getView()).notifyItemRangeInserted(start, end);
        }else
            hasMoreEvents = false;
    }
}

