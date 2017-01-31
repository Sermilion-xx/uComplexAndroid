package org.ucomplex.ucomplex.CommonDependencies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
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

    public static int resolveLayout(int tempLayout,
                              int viewType, LayoutResolveStrategy strategy) {
        if (tempLayout != R.layout.list_item_no_content && tempLayout != R.layout.list_item_no_internet) {
            tempLayout = strategy.resolve(viewType);
        }
        return tempLayout;
    }

    public static List<IRecyclerItem> initNoContent() {
        List<IRecyclerItem> emptyList = new ArrayList<>();
        emptyList.add(new IRecyclerItem() {
            @Override
            public boolean isEmpty() {
                return true;
            }
        });
        return emptyList;
    }

    public interface LayoutResolveStrategy{
        int resolve(int viewType);
    }



}
