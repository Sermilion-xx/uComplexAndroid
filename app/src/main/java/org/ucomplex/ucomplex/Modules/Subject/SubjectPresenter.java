package org.ucomplex.ucomplex.Modules.Subject;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.Model.Users.Teacher;
import org.ucomplex.ucomplex.Modules.Materials.ProgressItem;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
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

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;

    public SubjectPresenter() {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_0;
        } else if (position == getItemCount() - 1) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public SubjectViewHolder createViewHolder(ViewGroup parent, int viewType) {
        SubjectViewHolder viewHolder;
        itemLayout = isAvailableListViewItem();
        if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
            itemLayout = viewType == 0 ? R.layout.list_item_event : R.layout.list_item_event_footer;
            switch (viewType) {
                case TYPE_0:
                    itemLayout = R.layout.list_item_subject_header;
                    break;
                case TYPE_1:
                    itemLayout = R.layout.list_item_subject_teacher;
                    break;
                case TYPE_2:
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
        SubjectItemForList subjectItem = (SubjectItemForList) ((ModelRecycler) mModel).getRecyclerItems().get(position);
        if (subjectItem != null) {
            switch (getItemViewType(position)) {
                case TYPE_0:
                    aHolder.mTitle.setText(getActivityContext().getString(subjectItem.getResourceString()));
                    break;
                case TYPE_1:
                    String url = HttpFactory.GET_PHOTO_URL + subjectItem.getCode() + Constants.IMAGE_FORMAT_JPG;
                    aHolder.mTeachersName.setText(subjectItem.getTextOne());
                    if(subjectItem.getCode()!=null){
                        Glide.with(getActivityContext())
                                .load(url)
                                .into(aHolder.mIcon);
                    }else{
                        Drawable drawable = FacadeMedia.getTextDrawable(Integer.valueOf(subjectItem.getTextTwo()), subjectItem.getTextOne(), getActivityContext());
                        aHolder.mIcon.setImageDrawable(drawable);
                    }
                    break;
                case TYPE_2:
                    String text = getActivityContext().getString(R.string.absence, subjectItem.getTextOne());
                    aHolder.mAttendance.setText(Html.fromHtml(text));
                    String average = getActivityContext().getString(R.string.average_mark, subjectItem.getTextTwo());
                    aHolder.mAverageGrade.setText(average);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return ((ModelRecycler) mModel).getRecyclerItems().size();
    }
}
