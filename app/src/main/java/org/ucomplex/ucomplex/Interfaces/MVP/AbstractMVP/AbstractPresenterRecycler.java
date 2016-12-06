package org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.BaseComponents.ViewHolderNoContent;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.Modules.Events.EventViewHolder;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleViewHolder;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectListViewHolder;
import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class AbstractPresenterRecycler extends AbstractPresenter implements PresenterRecycler {

    protected int start = 0;
    protected int end = 0;
    protected int oldEnd = 0;
    protected int itemLayout;

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    protected int isAvailableListViewItem(){
        if(!FacadeCommon.isNetworkConnected(getActivityContext())){
            return R.layout.list_item_no_internet;
        }else  if (((ModelRecycler) mModel).getItem(0).isEmpty()) {
            return R.layout.list_item_no_content;
        }else {
            return itemLayout;
        }
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        itemLayout = isAvailableListViewItem();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(itemLayout, parent, false);
        viewHolder = getViewHolderForItemLayout(viewTaskRow, itemLayout);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return ((ModelRecycler) mModel).getItemCount();
    }


    protected Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void dataLoaded(boolean loaded, int... startEndOldEnd) {
        super.dataLoaded(loaded, startEndOldEnd);
        if(startEndOldEnd.length==0){
            startEndOldEnd[0]=0;
            startEndOldEnd[1]=0;
            startEndOldEnd[2]=0;
        }
        start = startEndOldEnd[0];
        end = startEndOldEnd[1];
        oldEnd = startEndOldEnd.length == 3 ? startEndOldEnd[2] : -1;
        if (((ModelRecycler) mModel).getItemCount() == 0) {
            ((ModelRecycler) mModel).addItem(new IRecyclerItem() {
                @Override
                public boolean isEmpty() {
                    return true;
                }
            });
        }
        if (loaded) {
            if (start == 0) {
                if (oldEnd != -1) {
                    end = oldEnd;
                }
                ((ViewToPresenterRecycler) getView()).notifyItemRangeRemoved(start, end);
            }
            ((ViewToPresenterRecycler) getView()).notifyItemRangeInserted(start, end);
        } else {
            getView().showToast(makeToast(getActivityContext().getString(R.string.error_loading_data)));
            ((ViewToPresenterRecycler) getView()).notifyDataSetChanged();
        }
    }

    private RecyclerView.ViewHolder getViewHolderForItemLayout(View view, int itemLayout) {
        switch (itemLayout) {
            case R.layout.list_item_event:
                return new EventViewHolder(view);
            case R.layout.list_item_role:
                return new RoleViewHolder(view);
            case R.layout.list_item_subject:
                return new SubjectListViewHolder(view);
            case R.layout.list_item_no_content:
                return new ViewHolderNoContent(view);
            default:
                return null;
        }
    }

}
