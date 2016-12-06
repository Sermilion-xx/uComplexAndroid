package org.ucomplex.ucomplex.Interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseListAdapter;


/**
 * ---------------------------------------------------
 * Created by Sermilion on 26/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface IFragment {

    void setActivity(BaseActivity activity);
    BaseListAdapter getListAdapter();
    void showProgress();
    void hideProgress();
    void setArguments(Bundle bundle);
    void addDivider();
    RecyclerView getRecyclerView();
}
