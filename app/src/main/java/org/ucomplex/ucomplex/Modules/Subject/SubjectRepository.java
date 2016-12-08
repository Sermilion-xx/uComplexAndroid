package org.ucomplex.ucomplex.Modules.Subject;

import android.os.Bundle;

import org.json.JSONException;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractRepository;

import java.util.HashMap;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectRepository extends AbstractRepository {

    private static final String SUBJECT_ID = "subjId";

    @Override
    public void loadData(Bundle bundle) {
        final String encodedAuth = FacadePreferences.getLoginDataFromPref(mContext);
        HashMap<String, String> params = new HashMap<>();
        if(bundle!=null){
            params.put(SUBJECT_ID, Integer.toString(bundle.getInt(SUBJECT_ID)));
        }
        HttpFactory.getInstance().httpVolley(HttpFactory.USER_SUBJECT_URL,
                encodedAuth,
                mContext,
                RequestType.SUBJECT,
                params);
    }
}
