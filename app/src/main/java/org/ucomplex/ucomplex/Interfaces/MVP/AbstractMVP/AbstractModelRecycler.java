package org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class AbstractModelRecycler extends AbstractModel implements ModelRecycler {

    protected int start = 0;
    protected int end = 0;
    protected int oldEnd = -1;

    public abstract ArrayList<IRecyclerItem> getDataFromJson(String jsonString) throws JSONException;

    @Override
    public IRecyclerItem getItem(int position) {
        return mRecyclerItems.get(position);
    }

    @Override
    public int getItemCount() {
        if (mRecyclerItems != null)
            return mRecyclerItems.size();
        return 0;
    }

    @Override
    public void onTaskComplete(int requestType, Object... o) {
        String result = null;
        if (!(o[0] instanceof VolleyError)) {
            try {
                result = (String) o[0];
                mRecyclerItems = getDataFromJson(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(mRecyclerItems.size()==0){
            mRecyclerItems.add(new IRecyclerItem() {
                @Override
                public boolean isEmpty() {
                    return true;
                }
            });
        }
        end = mRecyclerItems.size();
        mOnDataLoadedListener.dataLoaded(result != null, start, end, oldEnd);
    }

    @Override
    public void addItem(IRecyclerItem iRecyclerItem) {
        mRecyclerItems.add(iRecyclerItem);
    }

}
