package org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectTimelineViewHolder extends RecyclerView.ViewHolder{

    ImageView mMarkImage;
    TextView mName;
    TextView mTime;
    TextView mTimeIcon;

    public SubjectTimelineViewHolder(View itemView) {
        super(itemView);
        mMarkImage = (ImageView) itemView.findViewById(R.id.subject_mark_image);
        mName      = (TextView) itemView.findViewById(R.id.subject_name);
        mTime      = (TextView) itemView.findViewById(R.id.subject_time);
        mTimeIcon  = (TextView) itemView.findViewById(R.id.subject_time_icon);
    }
}
