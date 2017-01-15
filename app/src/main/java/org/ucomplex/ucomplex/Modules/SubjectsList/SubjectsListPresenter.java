package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.AbstractPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.BaseComponents.MVPUtility;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.R;

import java.util.List;


/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListPresenter extends AbstractPresenterRecycler<String> {

    public SubjectsListPresenter() {
        baseOnClickListener = new RecyclerOnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivityContext(), SubjectsListActivity.class);
                int itemPosition = ((ViewRecycler) getView()).getRecyclerView().indexOfChild(v);
                SubjectListItem item = (SubjectListItem) ((ModelRecycler) mModel).getItem(itemPosition);

                int userType = ((DaggerApplication) getAppContext()).getSharedUser().getType();
                if (userType == Constants.USER_TYPE_STUDENT) {
                    intent.putExtra("subjId", item.getCourseId());
                }
                getActivityContext().startActivity(intent);
            }
        };
    }

    @Override
    public void loadData(Bundle... bundle) {
        ((SubjectsListActivity) getView()).showProgress();
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                if (o != null) {
                    populateRecyclerView(o);
                }
                ((SubjectsListActivity) getView()).hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                ((SubjectsListActivity) getView()).hideProgress();
            }
        }, bundle);
    }


    @Override
    public SubjectListViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewTaskRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((ModelRecycler) mModel, getActivityContext(), itemLayout);
        viewTaskRow = MVPUtility.resolveLayout(tempLayout, itemLayout, viewType,inflater, parent);
        viewTaskRow.setOnClickListener(this.baseOnClickListener);
        return (SubjectListViewHolder) this.creator.getViewHolder(viewTaskRow, tempLayout);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IRecyclerItem aItem = ((ModelRecycler) mModel).getItem(position);
        if (aItem instanceof SubjectListItem) {
            SubjectListItem item = (SubjectListItem) aItem;
            SubjectListViewHolder aHolder = (SubjectListViewHolder) holder;
            aHolder.mSubjectName.setText(item.getCourseName());
            aHolder.mAssessmentType.setText(getActivityContext().getString(item.getAssessmentType()));
            aHolder.mSubjectName.setText(item.getCourseName());
        }
    }


}
