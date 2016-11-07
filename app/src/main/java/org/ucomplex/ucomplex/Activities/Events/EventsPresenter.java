package org.ucomplex.ucomplex.Activities.Events;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class EventsPresenter implements MVP_Events.ProvidedPresenterOpsToView, MVP_Events.RequiredPresenterOpsToModel {

    private WeakReference<MVP_Events.RequiredViewOpsFromPresenter> mView;
    private MVP_Events.ProvidedModelOpsFromPresenter mModel;

    /**
     * Presenter Constructor
     * @param view  MainActivity
     */
    public EventsPresenter(MVP_Events.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }

    /**
     * Called by View every time it is destroyed.
     * @param isChangingConfiguration   true: is changing configuration
     *                                  and will be recreated
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if ( !isChangingConfiguration ) {
            // Nulls Model when the Activity destruction is permanent
            mModel = null;
        }
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return  {@link MVP_Events.RequiredViewOpsFromPresenter}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Events.RequiredViewOpsFromPresenter getView() throws NullPointerException{
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
    public void setView(MVP_Events.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }

    /**
     * Create the RecyclerView holder and setup its view
     * @param parent    Recycler viewgroup
     * @param viewType  Holder type
     * @return          Recycler ViewHolder
     */
    @Override
    public EventsViewHolder createViewHolder(ViewGroup parent, int viewType) {
        EventsViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(R.layout.list_item_event, parent, false);
        viewHolder = new EventsViewHolder(viewTaskRow);
        return viewHolder;
    }

    /**
     * Binds ViewHolder with RecyclerView
     * @param holder    Holder to bind
     * @param position  Position on Recycler adapter
     */
    @Override
    public void bindViewHolder(final EventsViewHolder holder, int position) {
        final EventItem event = mModel.getEvent(position);
        holder.eventPersonName.setText(event.getParams().getName());
        holder.eventTextView.setText(event.getEventText());
        holder.eventsImageView.setImageBitmap(event.getEventImageBitmap());
        holder.eventTime.setText(event.getTime());
        holder.eventPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityContext(), null);
            }
        });
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param model Model instance
     */
    public void setModel(MVP_Events.ProvidedModelOpsFromPresenter model) {
        mModel = model;
        // start to load data
        loadData();
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
        return mModel.getEventsCount();
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
