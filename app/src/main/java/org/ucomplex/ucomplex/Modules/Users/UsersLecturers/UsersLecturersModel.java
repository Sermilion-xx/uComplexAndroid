package org.ucomplex.ucomplex.Modules.Users.UsersLecturers;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersLecturersModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundles) {

    }

    @Override
    public List<IRecyclerItem> processJson(String s) {
        return null;
    }
}
