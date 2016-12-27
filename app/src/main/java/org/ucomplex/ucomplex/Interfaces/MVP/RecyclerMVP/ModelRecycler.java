package org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP;

import android.support.v7.widget.RecyclerView;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface ModelRecycler extends Model {
    IRecyclerItem getItem(int position);
    ArrayList<IRecyclerItem> getRecyclerItems();
    int getItemCount();
    void addItem(IRecyclerItem iRecyclerItem);
}
