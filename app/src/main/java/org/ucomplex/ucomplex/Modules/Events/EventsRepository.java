package org.ucomplex.ucomplex.Modules.Events;

import android.os.Bundle;

import org.json.JSONException;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractRepository;

import java.util.HashMap;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsRepository extends AbstractRepository {

    private static final String EVENTS_START = "start";

    public EventsRepository() {

    }

    @Override
    public void loadData(Bundle bundle) {
        final String encodedAuth = FacadePreferences.getLoginDataFromPref(mContext);
        try {
            HashMap<String, String> params = new HashMap<>();
            int requestType = Constants.REQUEST_EVENTS;
            int start;
            if (bundle != null) {
                start = bundle.getInt(EVENTS_START);
                params.put(EVENTS_START, Integer.toString(start));
                requestType = Constants.REQUEST_MORE_EVENTS;
            }
            loadEvents(HttpFactory.USER_EVENTS_URL, encodedAuth, requestType, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadEvents(String url, String encodedAuth, int requestType, HashMap<String, String> params) throws JSONException {
        HttpFactory.getInstance().httpVolley(url,
                encodedAuth,
                mContext,
                mOnTaskCompleteListener,
                requestType,
                params);
    }

}
