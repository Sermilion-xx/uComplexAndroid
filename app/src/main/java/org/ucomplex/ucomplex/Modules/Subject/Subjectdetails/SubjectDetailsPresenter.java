package org.ucomplex.ucomplex.Modules.Subject.SubjectDetails;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;

import org.ucomplex.ucomplex.R;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectDetailsPresenter extends MVPAbstractPresenterRecycler<String> {

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
    private Bundle mBundle;

    public void setBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }

    public SubjectDetailsPresenter() {

    }

    @Override
    public void loadData(Bundle... bundle) {
        ((SubjectDetailsFragment) getView()).showProgress();
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                populateRecyclerView(o);
                ((SubjectDetailsFragment) getView()).hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                ((SubjectDetailsFragment) getView()).hideProgress();
            }
        }, bundle);
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
    public SubjectDetailsViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), 0);
        MVPUtility.LayoutResolveStrategy layoutResolveStrategy = viewType1 -> {
            int temp = -1;
            if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
                switch (viewType1) {
                    case TYPE_0:
                        temp = R.layout.list_item_subject_header;
                        break;
                    case TYPE_1:
                        temp = R.layout.list_item_subject_teacher;
                        break;
                    case TYPE_2:
                        temp = R.layout.list_item_subject_info;
                        break;
                }
            }
            return temp;
        };
        tempLayout = MVPUtility.resolveLayout(tempLayout, viewType, layoutResolveStrategy);
        viewRow = inflater.inflate(tempLayout, parent, false);

        setCreator((view, i) -> new SubjectDetailsViewHolder(view,viewType));
        SubjectDetailsViewHolder holder = (SubjectDetailsViewHolder) this.creator.getViewHolder(viewRow, tempLayout);

        if(viewType==TYPE_1){
            RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
            clickListener.setStrategy(view -> {
                final int position = holder.getAdapterPosition();
                if(position == getItemCount()-1){

                }
            });
            holder.mTeachersName.setOnClickListener(clickListener);
        }
        return holder;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SubjectDetailsViewHolder aHolder = (SubjectDetailsViewHolder) holder;
        SubjectDetailsItemForList subjectItem = (SubjectDetailsItemForList) ((MVPModelRecycler) mModel).getItems().get(position);
        if (subjectItem != null) {
            switch (getItemViewType(position)) {
                case TYPE_0:
                    aHolder.mTitle.setText(getActivityContext().getString(subjectItem.getResourceString()));
                    break;
                case TYPE_1:
                    String url = HttpFactory.GET_PHOTO_URL + subjectItem.getCode() + Constants.IMAGE_FORMAT_JPG;
                    aHolder.mTeachersName.setText(subjectItem.getTextOne());
                    if (subjectItem.getCode() != null) {
                        Glide.with(getActivityContext())
                                .load(url)
                                .into(aHolder.mIcon);
                    } else {
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

}
