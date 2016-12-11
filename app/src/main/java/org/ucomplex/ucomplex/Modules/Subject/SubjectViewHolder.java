package org.ucomplex.ucomplex.Modules.Subject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

class SubjectViewHolder extends RecyclerView.ViewHolder {
    TextView mTitle;

    CircleImageView mIcon;
    TextView mTeachersName;

    TextView mAttendance;
    TextView mAverageGrade;

    SubjectViewHolder(View itemView, int viewType) {
        super(itemView);
        switch (viewType) {
            case 0:
                mTitle = (TextView) itemView.findViewById(R.id.subjects_subject_header);
                break;
            case 1:
                mIcon = (CircleImageView) itemView.findViewById(R.id.subjects_subject_teacher);
                mTeachersName = (TextView) itemView.findViewById(R.id.subjects_subject_teacher_name);
                break;
            case 2:
                mAttendance = (TextView) itemView.findViewById(R.id.subjects_subject_attendance);
                mAverageGrade = (TextView) itemView.findViewById(R.id.subjects_subject_mark);
                break;
        }
    }
}
