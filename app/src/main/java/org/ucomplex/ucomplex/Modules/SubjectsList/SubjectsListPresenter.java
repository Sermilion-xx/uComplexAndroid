package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListPresenter extends AbstractPresenterRecycler {

    public SubjectsListPresenter() {
        baseOnClickListener = new RecyclerOnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivityContext(), SubjectActivity.class);
                int itemPosition = ((ViewToPresenterRecycler) getView()).getRecyclerView().indexOfChild(v);
                SubjectListItem item = (SubjectListItem) ((ModelRecycler) mModel).getItem(itemPosition);

                int userType = mModel.getUser().getType();
                if (userType == Constants.USER_TYPE_STUDENT) {
                    intent.putExtra("subjId", item.getCourseId());
                }
                getActivityContext().startActivity(intent);
            }
        };
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

    @Override
    public void dataLoaded(boolean loaded, int... startEndOldEnd) {
        super.dataLoaded(loaded, startEndOldEnd);
    }
}
