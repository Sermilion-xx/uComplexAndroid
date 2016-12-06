package org.ucomplex.ucomplex.Modules.SubjectsList;

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
    public void loadData(Object... param) {
        final String encodedAuth = FacadePreferences.getLoginDataFromPref(mContext);
        HashMap<String, String> params = new HashMap<>();
        int requestType = Constants.REQUEST_GET_SUBJECTS;
        HttpFactory.getInstance().httpVolley(HttpFactory.USER_SUBJECTS_USER_URL,
                encodedAuth,
                mContext,
                mOnTaskCompleteListener,
                requestType,
                params);
    }

}
