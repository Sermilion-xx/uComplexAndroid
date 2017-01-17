package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.R;

import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.USER_TYPE_STUDENT;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.USER_TYPE_TEACHER;


/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListPresenter extends MVPAbstractPresenterRecycler<String> {

    public SubjectsListPresenter() {
        baseOnClickListener = new RecyclerOnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivityContext(), SubjectsListActivity.class);
                int itemPosition = ((MVPViewRecycler) getView()).getRecyclerView().indexOfChild(v);
                SubjectListItem item = (SubjectListItem) ((MVPModelRecycler) mModel).getItem(itemPosition);

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
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), R.layout.list_item_subject);
        tempLayout = MVPUtility.resolveLayout(tempLayout, viewType, viewType1 -> R.layout.list_item_subject);
        viewTaskRow = inflater.inflate(tempLayout, parent, false);

        setCreator((view, i) -> new SubjectListViewHolder(view));
        SubjectListViewHolder holder =  (SubjectListViewHolder) this.creator.getViewHolder(viewTaskRow, tempLayout);
        holder.subjectLayout.setOnClickListener(setupOnClickListener(holder));
        return holder;
    }

    @NonNull
    private RecyclerOnClickListener setupOnClickListener(SubjectListViewHolder holder) {
        DaggerApplication mApplication = (DaggerApplication) getAppContext();
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            int position = holder.getAdapterPosition();
            SubjectListItem item = (SubjectListItem) ((MVPModelRecycler) mModel).getItem(position);
            if (mApplication.getSharedUser().getType() == USER_TYPE_STUDENT) {
                SubjectActivity.receiveIntent(getActivityContext(), item.getCourseId(), item.getCourseName());
            } else if (mApplication.getSharedUser().getType() == USER_TYPE_TEACHER) {
//                    Intent intent = new Intent(getBaseContext(), null);
//                    Bundle extras = new Bundle();
//                    extras.putString("gcourse", String.valueOf(mItems.get(position).getValue2()));
//                    intent.putExtras(extras);
//                    startActivity(intent);
            }
        };
        clickListener.setStrategy(strategy);
        return clickListener;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IRecyclerItem aItem = ((MVPModelRecycler) mModel).getItem(position);
        if (aItem instanceof SubjectListItem) {
            SubjectListItem item = (SubjectListItem) aItem;
            SubjectListViewHolder aHolder = (SubjectListViewHolder) holder;
            aHolder.mSubjectName.setText(item.getCourseName());
            aHolder.mAssessmentType.setText(getActivityContext().getString(item.getAssessmentType()));
            aHolder.mSubjectName.setText(item.getCourseName());
        }
    }


}
