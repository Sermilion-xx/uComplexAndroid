package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.R;

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

public class EventsPresenter implements MVP_Events.PresenterInterface{

    private WeakReference<ViewRecylerToPresenter> mView;
    private Model mModel;

    /**
     * PresenterToViewInterface Constructor
     * @param view  MainActivity
     */
    public EventsPresenter(ViewRecylerToPresenter view) {
        mView = new WeakReference<>(view);
    }

    public EventsPresenter() {

    }

    /**
     * Called by View every time it is destroyed.
     * @param isChangingConfiguration   true: is changing configuration
     *                                  and will be recreated
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if ( !isChangingConfiguration ) {
            // Nulls Model when the Activity destruction is permanent
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
     * @return  {@link MVP_Events.ViewToPresenterInterface}
     * @throws NullPointerException when View is unavailable
     */
    private ViewRecylerToPresenter getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    /**
     * Called by View during the reconstruction events
     * @param view  Activity instance
     */
    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>((ViewRecylerToPresenter)view);
    }

    /**
     * Create the RecyclerView holder and setup its view
     * @param parent    Recycler viewgroup
     * @param viewType  Holder type
     * @return          Recycler ViewHolder
     */
    @Override
    public EventViewHolder createViewHolder(ViewGroup parent, int viewType) {
        EventViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(R.layout.list_item_event, parent, false);
        viewHolder = new EventViewHolder(viewTaskRow);
        return viewHolder;
    }

    /**
     * Binds ViewHolder with RecyclerView
     * @param holder    Holder to bind
     * @param position  Position on Recycler adapter
     */
    @Override
    public void bindViewHolder(final EventViewHolder holder, int position) {
        final EventItem event = ((EventsModel)mModel).getEvent(position);
        holder.eventPersonName.setText(event.getParams().getName());
        holder.eventTextView.setText(event.getEventText());
        holder.eventsImageView.setImageBitmap(event.getEventImageBitmap());
        holder.eventTime.setText(event.getTime());
        ((EventsModel)mModel).loadIcon(event.getParams().getCode(), holder.eventsImageView);
        holder.eventPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityContext(), null);
            }
        });
    }

    /**
     * Load data from Model in a AsyncTask
     */
    private void loadData() {
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

    /**
     * Creat a Toast object with given message
     * @param msg   Toast message
     * @return      A Toast object
     */
    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * Retrieve total Events count from Model
     * @return  Events size
     */
    @Override
    public int getEventsCount() {
        return ((EventsModel)mModel).getEventsCount();
    }

    /**
     * Retrieve Application Context
     * @return  Application context
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
     * @return  Activity context
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
