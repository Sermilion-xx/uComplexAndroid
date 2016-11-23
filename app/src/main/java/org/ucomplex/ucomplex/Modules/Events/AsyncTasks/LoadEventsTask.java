package org.ucomplex.ucomplex.Modules.Events.AsyncTasks;

import android.os.AsyncTask;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsRepository;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 23/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoadEventsTask extends AsyncTask<Void, Void, ArrayList<EventItem>> {

    private Repository mRepository;
    private Model      mModel;
    private OnTaskCompleteListener onTaskCompleteListener;

    public LoadEventsTask(Repository repository, Model model){
        mRepository = repository;
        mModel = model;
        setOnTaskCompleteListener(((EventsModel)mModel).getOnTaskCompleteListener());

    }

    public LoadEventsTask(){
        setOnTaskCompleteListener(((EventsModel)mModel).getOnTaskCompleteListener());
    }

    public void setOnTaskCompleteListener(OnTaskCompleteListener onTaskCompleteListener) {
        this.onTaskCompleteListener = onTaskCompleteListener;
    }

    @Override @SuppressWarnings("unchecked")
    protected ArrayList<EventItem> doInBackground(Void... params) {
        ArrayList<EventItem> eventItems = new ArrayList<>();
        if(!isCancelled()){
            eventItems = (ArrayList<EventItem>) mRepository.loadData();
            eventItems.add(new EventItem());
        }
        return eventItems;
    }

    public Toast makeToast(String message) {
        return Toast.makeText(((EventsRepository)mRepository).getContext(), message, Toast.LENGTH_LONG);
    }

    @Override
    protected void onPostExecute(ArrayList<EventItem> result) {
        try {
            mModel.setData(result);
            onTaskCompleteListener.onTaskComplete(this, true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
