package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
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

public class EventsPresenter implements MVP_Events.PresenterInterface {

    private WeakReference<ViewRecylerToPresenter> mView;
    private Model mModel;
    private OnTaskCompleteListener onTaskCompleteListener;
    private boolean hasMoreEvents = true;

    private static final int TYPE_COMMON = 0;
    private static final int TYPE_FOOTER = 1;

    public EventsPresenter(ViewRecylerToPresenter view) {
        mView = new WeakReference<>(view);
    }

    public EventsPresenter() {

    }

    public void setHasMoreEvents(boolean hasMoreEvents) {
        this.hasMoreEvents = hasMoreEvents;

    }

    void setOnTaskCompleteListener(OnTaskCompleteListener onTaskCompleteListener) {
        this.onTaskCompleteListener = onTaskCompleteListener;
    }

    @Override
    public UserInterface getUser() {
        return mModel.getUser();
    }

    /**
     * Called by View every time it is destroyed.
     *
     * @param isChangingConfiguration true: is changing configuration
     *                                and will be recreated
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    @Override
    public void onConfigurationChanged(ViewToPresenter view) {

    }

    @Override
    public void setModel(Model models) {
        mModel = models;
        loadData();
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return {@link MVP_Events.ViewToPresenterInterface}
     * @throws NullPointerException when View is unavailable
     */
    private ViewRecylerToPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /**
     * Called by View during the reconstruction events
     *
     * @param view Activity instance
     */
    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>((ViewRecylerToPresenter) view);
    }


    /**
     * Create the RecyclerView holder and setup its view
     *
     * @param parent   Recycler viewgroup
     * @param viewType Holder type
     * @return Recycler ViewHolder
     */
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
            if (personName == null || personName.equals("")) {
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
                            .load(HttpFactory.LOAD_PROFILE_URL + event.getParams().getCode() + ".jpg")
                            .into(holder.eventsImageView);
                }
            }

            holder.eventDetailsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getActivityContext(), null);
                }
            });
        } else {
            if (hasMoreEvents) {
                holder.loadMoreEventsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivityContext().sendBroadcast(new Intent(Constants.EVENTS_LOAD_MORE_BROADCAST));
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == ((EventsModel) mModel).getEventsCount() - 1 ? TYPE_FOOTER : TYPE_COMMON;
    }


    /**
     * Load data from Model in a AsyncTask
     */
    public void loadData() {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    // Load data from Model
                    return mModel.loadData();
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    try {
                        getView().hideProgress();
                        if (!result) // Loading error
                            getView().showToast(makeToast("Error loading data."));
                        else // success
                            getView().notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoreEvents(final int start) {
        try {
            getView().showProgress();
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    return ((EventsModel) mModel).loadMoreEvents(start);
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    try {
                        getView().hideProgress();
                        onTaskCompleteListener.onTaskComplete(this, result);
                        getView().notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creat a Toast object with given message
     *
     * @param msg Toast message
     * @return A Toast object
     */
    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * Retrieve total Events count from Model
     *
     * @return Events size
     */
    @Override
    public int getEventsCount() {
        return ((EventsModel) mModel).getEventsCount();
    }

    /**
     * Retrieve Application Context
     *
     * @return Application context
     */
    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Retrieves Activity context
     *
     * @return Activity context
     */
    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
