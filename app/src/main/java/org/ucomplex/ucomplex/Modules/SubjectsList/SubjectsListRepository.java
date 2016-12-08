package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.os.Bundle;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractRepository;

import java.util.HashMap;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListRepository extends AbstractRepository{

    @Override
    public void loadData(Bundle bundle) {
        final String encodedAuth = FacadePreferences.getLoginDataFromPref(mContext);
        HashMap<String, String> params = new HashMap<>();

        HttpFactory.getInstance().httpVolley(HttpFactory.USER_SUBJECTS_USER_URL,
                encodedAuth,
                mContext,
                RequestType.HTTP_RECEIVE,
                params);
    }

}
