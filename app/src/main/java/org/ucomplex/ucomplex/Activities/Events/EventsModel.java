package org.ucomplex.ucomplex.Activities.Events;



import org.json.JSONException;

import java.util.ArrayList;

/**
 * Model layer on Model View Presenter Pattern
 *
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class EventsModel implements MVP_Events.ProvidedModelOpsFromPresenter {

    // Presenter reference
    private MVP_Events.RequiredPresenterOpsToModel mPresenter;
    private EventsRepository mDAO;
    // Recycler data
    public ArrayList<EventItem> mEventItems;

    /**
     * Main constructor, called by Activity during MVP setup
     * @param presenter Presenter instance
     */
    public EventsModel(MVP_Events.RequiredPresenterOpsToModel presenter, int userType) {
        this.mPresenter = presenter;
        mDAO = new EventsRepository(mPresenter.getAppContext(), userType);
    }

    /**
     * Test contructor. Called only during unit testing
     * @param presenter Presenter instance
     * @param dao       DAO instance
     */
    public EventsModel(MVP_Events.RequiredPresenterOpsToModel presenter, EventsRepository dao) {
        this.mPresenter = presenter;
        mDAO = dao;
    }

    /**
     * Called by Presenter when View is destroyed
     * @param isChangingConfiguration   true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mDAO = null;
            mEventItems = null;
        }
    }

    /**
     * Loads all Data, getting EventItems from DB
     * @return  true with success
     */
    @Override
    public boolean loadData() {
        try {
            mEventItems = mDAO.getAllEvents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mEventItems != null;
    }

    /**
     * Get a specific EventItem from EventItems list using its array postion
     * @param position    Array position
     * @return            EventItem from list
     */
    @Override
    public EventItem getEvent(int position) {
        return mEventItems.get(position);
    }

    /**
     * Get EventItem's positon on ArrayList
     * @param EventItem  EventItem to check
     * @return      Positon on ArrayList
     */
    public int getEventItemPosition(EventItem EventItem) {
        for (int i=0; i<mEventItems.size(); i++){
            if ( EventItem.getId() == mEventItems.get(i).getId())
                return i;
        }
        return -1;
    }


    /**
     * Get ArrayList size
     * @return  ArrayList size
     */
    @Override
    public int getEventsCount() {
        if ( mEventItems != null )
            return mEventItems.size();
        return 0;
    }
}
