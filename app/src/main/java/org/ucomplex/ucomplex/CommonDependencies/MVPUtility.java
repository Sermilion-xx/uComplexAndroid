package org.ucomplex.ucomplex.CommonDependencies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.R;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 15/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MVPUtility {

    public MVPUtility(){

    }

    public static void addEmptyItem(List<IRecyclerItem> newItems) {
        newItems.add(new IRecyclerItem() {
            @Override
            public boolean isEmpty() {
                return true;
            }
        });
    }

    public static int isAvailableListViewItem(MVPModelRecycler model, Context context, int defaultLayout) {
        IRecyclerItem item = (IRecyclerItem) model.getItems().get(0);
        if (!FacadeCommon.isNetworkConnected(context) && item.isEmpty()) {
            return R.layout.list_item_no_internet;
        } else if (item.isEmpty()) {
            return R.layout.list_item_no_content;
        } else {
            return defaultLayout;
        }
    }

    public static View resolveLayout(int tempLayout,
                              int defaultLayout,
                              int viewType,
                              LayoutInflater inflater,
                              ViewGroup parent) {
        if (tempLayout != R.layout.list_item_no_content && tempLayout != R.layout.list_item_no_internet) {
            tempLayout = viewType == 0 ? defaultLayout : R.layout.list_item_event_footer;
        }
        return inflater.inflate(tempLayout, parent, false);
    }

}
