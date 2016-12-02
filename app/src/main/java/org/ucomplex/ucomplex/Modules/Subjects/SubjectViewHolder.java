package org.ucomplex.ucomplex.Modules.Subjects;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
public class SubjectViewHolder extends RecyclerView.ViewHolder {

    TextView mSubjectName;
    TextView mAssessmentType;

    public SubjectViewHolder(View itemView) {
        super(itemView);
        mSubjectName = (TextView) itemView.findViewById(R.id.subject_name);
    }
}
