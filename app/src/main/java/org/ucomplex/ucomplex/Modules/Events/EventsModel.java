package org.ucomplex.ucomplex.Modules.Events;


import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.AsyncTasks.LoadEventsTask;

import java.util.ArrayList;

/**
 * Model layer on Model View PresenterToViewInterface Pattern
 * <p>
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class EventsModel implements MVP_Events.ModelInterface {

    private Repository mRepository;
    private ArrayList<EventItem> mEventItems;
    private Context mContext;
    private UserInterface user;
    private OnTaskCompleteListener onTaskCompleteListener;
    private LoadEventsTask loadEventsTask;

    public EventsModel(Context context, UserInterface user) {
        this.mContext = context;
        this.user = user;
        mRepository = new EventsRepository(mContext);
    }

    public EventsModel() {

    }


    void setOnTaskCompleteListener(OnTaskCompleteListener onTaskCompleteListener) {
        this.onTaskCompleteListener = onTaskCompleteListener;
    }

    public OnTaskCompleteListener getOnTaskCompleteListener() {
        return onTaskCompleteListener;
    }

    public UserInterface getUser() {
        return user;
    }

    public void setPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setData(Object data) {
        if (data instanceof ArrayList)
            this.mEventItems = (ArrayList<EventItem>) data;
        else
            this.user = (UserInterface) data;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    public void setRepository(EventsRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mContext = null;
            mRepository = null;
            mEventItems = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean loadData() {
        loadEventsTask = new LoadEventsTask();

        return mEventItems != null;
    }

    @Override
    public boolean loadMoreEvents(int start) {
        ArrayList<EventItem> loadedEvents = ((EventsRepository) mRepository).loadMoreEvents(start);
        if (loadedEvents.size() != 0) {
            mEventItems.remove(mEventItems.size() - 1);
            mEventItems.addAll(loadedEvents);
            mEventItems.add(new EventItem());
        }
//        else{
//            mEventItems.remove(mEventItems.size());
//        }
        return loadedEvents.size() != 0;
    }

    @Override
    public EventItem getEvent(int position) {
        return mEventItems.get(position);
    }

    public int getEventItemPosition(EventItem EventItem) {
        for (int i = 0; i < mEventItems.size(); i++) {
            if (EventItem.getId() == mEventItems.get(i).getId())
                return i;
        }
        return -1;
    }

    @Override
    public int getEventsCount() {
        if (mEventItems != null)
            return mEventItems.size();
        return 0;
    }

}
