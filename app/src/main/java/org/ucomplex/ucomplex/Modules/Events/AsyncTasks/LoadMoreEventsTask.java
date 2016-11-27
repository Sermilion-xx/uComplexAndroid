package org.ucomplex.ucomplex.Modules.Events.AsyncTasks;

import android.os.AsyncTask;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 23/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoadMoreEventsTask extends AsyncTask<Integer, Void, Boolean> {

    private Model mModel;
    private Presenter mPresenter;
    private OnTaskCompleteListener onTaskCompleteListener;

    public void setOnTaskCompleteListener(OnTaskCompleteListener onTaskCompleteListener) {
        this.onTaskCompleteListener = onTaskCompleteListener;
    }

    public LoadMoreEventsTask(Model model, Presenter presenter){
        mModel = model;
        mPresenter = presenter;
    }


    @Override
    protected Boolean doInBackground(Integer... params) {
        return ((EventsModel) mModel).loadMoreEvents(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try {
            mPresenter.getView().hideProgress();
//            onTaskCompleteListener.onTaskComplete(this, result);
            ((ViewRecylerToPresenter)mPresenter.getView()).notifyDataSetChanged();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
