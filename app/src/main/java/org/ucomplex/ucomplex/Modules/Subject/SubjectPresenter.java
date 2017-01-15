package org.ucomplex.ucomplex.Modules.Subject;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.Modules.Events.EventViewHolder;
import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectPresenter extends MVPAbstractPresenterRecycler<String> {

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
        View viewTaskRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), itemLayout);
        viewTaskRow = MVPUtility.resolveLayout(tempLayout, itemLayout, viewType,inflater, parent);
        viewTaskRow.setOnClickListener(this.baseOnClickListener);
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
        SubjectViewHolder holder = (SubjectViewHolder) this.creator.getViewHolder(viewTaskRow, tempLayout);
        baseOnClickListener.setPosition(holder.getAdapterPosition());
        baseOnClickListener.setViewType(viewType);
        holder.mTeachersName.setOnClickListener(baseOnClickListener);
        return (SubjectViewHolder) this.creator.getViewHolder(viewTaskRow, tempLayout);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SubjectViewHolder aHolder = (SubjectViewHolder) holder;
        SubjectItemForList subjectItem = (SubjectItemForList) ((MVPModelRecycler) mModel).getItems().get(position);
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
                    aHolder.mAttendance.setText(FacadeCommon.fromHtml(text));
                    String average = getActivityContext().getString(R.string.average_mark, subjectItem.getTextTwo());
                    aHolder.mAverageGrade.setText(average);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return ((MVPModelRecycler) mModel).getItems().size();
    }
}
