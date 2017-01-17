package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class SubjectListViewHolder extends RecyclerView.ViewHolder {

    TextView mSubjectName;
    TextView mAssessmentType;
    RelativeLayout subjectLayout;


    public SubjectListViewHolder(View itemView) {
        super(itemView);
        subjectLayout = (RelativeLayout) itemView.findViewById(R.id.subject_layout);
        mSubjectName = (TextView) itemView.findViewById(R.id.subject_name);
        mAssessmentType = (TextView) itemView.findViewById(R.id.subject_assessment_type);
    }
}
