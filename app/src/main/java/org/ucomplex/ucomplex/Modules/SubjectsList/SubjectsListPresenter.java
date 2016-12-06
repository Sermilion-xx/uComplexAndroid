package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.support.v7.widget.RecyclerView;

import org.ucomplex.ucomplex.BaseComponents.ViewHolderNoContent;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;

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

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IRecyclerItem aItem = ((ModelRecycler)mModel).getItem(position);
        if(aItem instanceof SubjectListItem){
            SubjectListItem item = (SubjectListItem) aItem;
            SubjectListViewHolder aHolder = (SubjectListViewHolder) holder;
            aHolder.mSubjectName.setText(item.getCourseName());
            aHolder.mAssessmentType.setText(getActivityContext().getString(item.getAssessmentType()));
            aHolder.mSubjectName.setText(item.getCourseName());
        }else {
            ViewHolderNoContent holderNoContent = (ViewHolderNoContent) holder;
        }

    }

    @Override
    public void dataLoaded(boolean loaded, int... startEndOldEnd) {
        super.dataLoaded(loaded, startEndOldEnd);
    }
}
