package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;
import android.os.Parcelable;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;

import org.json.JSONException;
import org.ucomplex.ucomplex.CommonDependencies.Constants;

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
    public void loadData(MVPCallback mvpCallback, Bundle... bundles) {

    }

    @Override
    public Object processJson(String s) {
        return null;
    }
}
