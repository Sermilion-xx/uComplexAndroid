package org.ucomplex.ucomplex.BaseComponents;

import android.os.AsyncTask;

import org.ucomplex.ucomplex.Interfaces.Consumer;
import org.ucomplex.ucomplex.Interfaces.Function;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 14/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class BaseAsyncTask<A, B, C> extends AsyncTask<A, B, C> {

    private Function<A, C> backgroundWork;
    private Runnable preBackgroundWork;
    private Consumer<C> postBackgroundWork;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(preBackgroundWork!=null){
            preBackgroundWork.run();
        }
    }

    @SafeVarargs
    @Override
    protected final C doInBackground(A... as) {
        return backgroundWork.apply(as);
    }

    @Override
    protected void onPostExecute(C c) {
        super.onPostExecute(c);
        if (postBackgroundWork!=null) {
            postBackgroundWork.consume(c);
        }
    }

    public void setBackgroundWork(Function<A, C> backgroundWork) {
        this.backgroundWork = backgroundWork;
    }

    public void setPreBackgroundWork(Runnable preBackgroundWork) {
        this.preBackgroundWork = preBackgroundWork;
    }

    public void setPostBackgroundWork(Consumer<C> postBackgroundWork) {
        this.postBackgroundWork = postBackgroundWork;
    }
}
