package org.ucomplex.ucomplex.Modules.Events;



import android.widget.ImageView;

import org.json.JSONException;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;

import java.util.ArrayList;

/**
 * Model layer on Model View PresenterToViewInterface Pattern
 *
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class EventsModel implements MVP_Events.ModelInterface {

    private Presenter mPresenter;
    private Repository mRepository;
    private ArrayList<EventItem> mEventItems;
    private int userType;

    /**
     * Main constructor, called by Activity during MVP setup
     * @param presenter PresenterToViewInterface instance
     */
    public EventsModel(Presenter presenter, int userType) {
        this.mPresenter = presenter;
        this.userType = userType;
        mRepository = new EventsRepository(mPresenter.getAppContext());
    }

    public EventsModel() {

    }

    public void setPresenter(Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override @SuppressWarnings("unchecked")
    public void setData(Object data) {
        userType = (int) data;
    }

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    public void setRepository(EventsRepository mRepository) {
        this.mRepository = mRepository;
    }

    /**
     * Test contructor. Called only during unit testing
     * @param presenter PresenterToViewInterface instance
     * @param dao       DAO instance
     */
    public EventsModel(Presenter presenter, EventsRepository dao) {
        this.mPresenter = presenter;
        mRepository = dao;
    }

    /**
     * Called by PresenterToViewInterface when View is destroyed
     * @param isChangingConfiguration   true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mRepository = null;
            mEventItems = null;
        }
    }

    /**
     * Loads all Data, getting EventItems from DB
     * @return  true with success
     */
    @Override @SuppressWarnings("unchecked")
    public boolean loadData() {
        mEventItems = (ArrayList<EventItem>) mRepository.loadData(userType);
        return mEventItems != null;
    }

    @Override
    public void loadIcon(String code, ImageView imageView) {
        ((EventsRepository)mRepository).loadIcon(code, imageView);
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
