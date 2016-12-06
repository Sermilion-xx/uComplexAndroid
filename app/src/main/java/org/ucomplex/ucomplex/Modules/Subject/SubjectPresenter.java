package org.ucomplex.ucomplex.Modules.Subject;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.Modules.Materials.ProgressItem;
import org.ucomplex.ucomplex.R;

import java.util.Locale;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectPresenter extends AbstractPresenterRecycler {

    private SubjectItem subjectItem;

    @Override
    public SubjectViewHolder createViewHolder(ViewGroup parent, int viewType) {
        SubjectViewHolder viewHolder;
        subjectItem = (SubjectItem) ((ModelRecycler) mModel).getItem(0);
        itemLayout = isAvailableListViewItem();
        if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
            itemLayout = viewType == 0 ? R.layout.list_item_event : R.layout.list_item_event_footer;
            switch (viewType) {
                case 0:
                    itemLayout = R.layout.list_item_subject_header;
                    break;
                case 1:
                    itemLayout = R.layout.list_item_subject_teacher;
                    break;
                case 2:
                    itemLayout = R.layout.list_item_subject_info;
                    break;
            }
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(itemLayout, parent, false);
        viewHolder = new SubjectViewHolder(viewTaskRow, viewType);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SubjectViewHolder aHolder = (SubjectViewHolder) holder;
        switch (position) {
            case 0:
                aHolder.mTitle.setText(getActivityContext().getString(R.string.lecturers));
                break;
            case 1:
                String url = HttpFactory.GET_PHOTO_URL + subjectItem.getTeachers().get(position-1).getCode();
                RequestQueue requestQueue = Volley.newRequestQueue(getActivityContext());
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        aHolder.mIcon.setImageBitmap(response);
                    }
                }, 0, 0, null, null);
                requestQueue.add(imageRequest);

                aHolder.mTeachersName.setText(subjectItem.getName());
                break;
            case 2:
                ProgressItem progress = subjectItem.getProgress();
                double absence = getAbsence(progress);
                absence = FacadeCommon.round(absence, 2);
                double mark = getMark(progress);
                mark = FacadeCommon.round(mark, 2);
                String text = getActivityContext().getString(R.string.absence, absence);
                aHolder.mAttendance.setText(Html.fromHtml(text));
                Locale current = getActivityContext().getResources().getConfiguration().locale;
                aHolder.mAverageGrade.setText(String.format(current,"%f", mark));
                break;
        }
    }

    private double getMark(ProgressItem progress) {
        double mark = 0.0;
        if (progress.getMark() != 0 && progress.getMarkCount() != 0) {
            mark = progress.getMark() / progress.getMarkCount();
        }
        return mark;
    }

    private double getAbsence(ProgressItem progress) {
        int a = progress.getAbsence();
        int b = progress.getHours();
        double absence = 100;
        if (a != 0 && b != 0) {
            absence = ((double) a / (double) b) * 100;
            if (absence == 0.0) {
                absence = 100;
            }
        }
        return absence;
    }
}
