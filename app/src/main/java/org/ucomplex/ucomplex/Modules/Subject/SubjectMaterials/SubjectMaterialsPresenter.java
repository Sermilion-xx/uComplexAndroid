package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.R;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectMaterialsPresenter extends MVPAbstractPresenterRecycler<String> {

    static final int TYPE_FILE = 0;
    static final int TYPE_FOLDER = 1;

    @Override
    public void setModel(MVPModel<String, List<IRecyclerItem>> models, Bundle... bundle) {
        this.mModel = models;
        this.mModel.setContext(this.getActivityContext());
    }

    @Override
    public SubjectMaterialsViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), 0);
        MVPUtility.LayoutResolveStrategy layoutResolveStrategy = viewType1 -> {
            int temp = -1;
            if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
                switch (viewType1) {
                    case TYPE_FILE:
                        temp = R.layout.list_item_material_file;
                        break;
                    case TYPE_FOLDER:
                        temp = R.layout.list_item_material_folder;
                        break;
                }
            }
            return temp;
        };
        tempLayout = MVPUtility.resolveLayout(tempLayout, viewType, layoutResolveStrategy);
        viewRow = inflater.inflate(tempLayout, parent, false);
        setCreator((view, i) -> new SubjectMaterialsViewHolder(view,viewType));
        SubjectMaterialsViewHolder holder = (SubjectMaterialsViewHolder) this.creator.getViewHolder(viewRow, tempLayout);
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        final int position = holder.getAdapterPosition();
//        SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler)mModel).getItem(position);
        if(viewType==TYPE_FILE){
            clickListener.setStrategy(view -> {


            });
        }else {
            clickListener.setStrategy(view -> {

            });
        }
        holder.mClickArea.setOnClickListener(clickListener);
        holder.mMenuButton.setOnClickListener(view -> {

        });
        return holder;
    }

    @Override
    public void loadData(Bundle... bundle) {
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                populateRecyclerView(o);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, bundle);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SubjectMaterialsViewHolder holder = (SubjectMaterialsViewHolder) viewHolder;
        SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler)mModel).getItem(position);
        if(item!=null){
            holder.mFileName.setText(item.getName());
            holder.mFileTime.setText(FacadeCommon.makeDate(item.getTime()));
            switch (getItemViewType(position)) {
                case TYPE_FILE:
                    holder.mSize.setText(FacadeCommon.readableFileSize(item.getSize(), false));
                    break;
                case TYPE_FOLDER:
                    holder.mOwnersName.setText(item.getOwnersName());
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler)mModel).getItem(position);
        if(item.getType().equals("f")){
            return TYPE_FOLDER;
        }
        return TYPE_FILE;
    }
}
