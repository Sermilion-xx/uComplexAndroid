package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;
import android.os.Parcelable;

import org.json.JSONException;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleModel extends AbstractModelRecycler implements ModelRecycler {

    public RoleModel() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadData() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_KEY_USER, (Parcelable) mUser);
        mRepository.loadData(bundle);
    }

    @Override
    public ArrayList<IRecyclerItem> getDataFromJson(String result) throws JSONException {
        throw new UnsupportedOperationException("RoleModel does not need to process json. Do not call this method.");
    }

}
