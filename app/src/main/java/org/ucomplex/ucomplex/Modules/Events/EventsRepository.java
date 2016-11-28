package org.ucomplex.ucomplex.Modules.Events;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsRepository implements Repository {


    private Context mContext;
    private OnTaskCompleteListener mOnTaskCompleteListener;

    EventsRepository(Context appContext) {
        this.mContext = appContext;
    }

    public EventsRepository() {

    }

    @Override
    public void setTaskCompleteListener(OnTaskCompleteListener mOnTaskCompleteListener) {
        this.mOnTaskCompleteListener = mOnTaskCompleteListener;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void loadData(Object... param) {
        try {
            loadEvents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadEvents() throws JSONException {
        final String encodedAuth = FacadePreferences.getLoginDataFromPref(mContext);
        HttpFactory.httpVolley(HttpFactory.USER_EVENTS_URL,
                encodedAuth,
                mContext,
                mOnTaskCompleteListener,
                Constants.REQUEST_EVENTS);
    }

    ArrayList<EventItem> loadMoreEvents(int start){
        ArrayList<EventItem> loadedEvents = new ArrayList<>();
        String jsonBody = "{\"start\":\""+start+"\"}";
        String stringResult = HttpFactory.httpPost(HttpFactory.USER_EVENTS_URL, FacadePreferences.getLoginDataFromPref(mContext), jsonBody);
        if (stringResult != null) {
//            try {
////                loadedEvents = getEventsDataFromJson(stringResult);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return loadedEvents;
        }
        return null;
    }


}
