package org.ucomplex.ucomplex.Modules.Events.AsyncTasks;

import android.os.AsyncTask;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
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

    private Repository             mRepository;
    private Model                  mModel;
    private Presenter              mPresenter;
    private OnTaskCompleteListener mOnTaskCompleteListener;

    public LoadEventsTask(OnTaskCompleteListener onTaskCompleteListener){
        mOnTaskCompleteListener = onTaskCompleteListener;
    }

    public void setModel(Model mModel) {
        this.mModel = mModel;
    }

    public void setPresenter(Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    public void setRepository(Repository mRepository) {
        this.mRepository = mRepository;
    }

    @Override @SuppressWarnings("unchecked")
    protected ArrayList<EventItem> doInBackground(Void... params) {
        ArrayList<EventItem> eventItems = new ArrayList<>();
        if(!isCancelled()){

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
            mPresenter.getView().hideProgress();
//            mOnTaskCompleteListener.onTaskComplete(this, true);
            ((ViewRecylerToPresenter)mPresenter.getView()).notifyDataSetChanged();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
