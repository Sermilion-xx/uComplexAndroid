package org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.R;

import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.FacadeMedia.getLetter;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineModel.EXTRA_KEY_GCOURSE_START;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectTimelinePresenter extends MVPAbstractPresenterRecycler<String> {

    private String[] colors = {"#51cde7", "#fecd71", "#9ece2b", "#d18ec0"};
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Bundle mBundle;
    private boolean hasMoreItems = true;


    @Override
    public void setModel(MVPModel<String, List<IRecyclerItem>> models, Bundle... bundle) {
        this.mModel = models;
        this.mModel.setContext(this.getActivityContext());
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isEmpty() ? TYPE_FOOTER : TYPE_ITEM;
    }

    @Override
    public void loadData(Bundle... bundle) {
        ((MVPViewBaseFragment) getView()).showProgress();
        mBundle = bundle[0];
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                if (o.size() < 20) {
                    hasMoreItems = false;
                } else {
                    addEmptyElement(o);
                }
                if(getItemCount()>0){
                    ((MVPModelRecycler)mModel).remove(((MVPModelRecycler)mModel).getItemCount()-1);
                    ((SubjectTimelineFragment)getView()).notifyItemRemoved(((MVPModelRecycler)mModel).getItemCount()-1);
                    addMoreToRecyclerView(o);
                }else {
                    populateRecyclerView(o);
                }
                ((MVPViewBaseFragment) getView()).hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                ((MVPViewBaseFragment) getView()).hideProgress();
            }

            void addEmptyElement(List<IRecyclerItem> o) {
                o.add(new IRecyclerItem() {
                    @Override
                    public boolean isEmpty() {
                        return true;
                    }
                });
            }
        }, mBundle);
    }

    @Override
    public SubjectTimelineViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), 0);
        MVPUtility.LayoutResolveStrategy layoutResolveStrategy = viewType1 -> {
            int temp = -1;
            if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
                if (viewType == TYPE_ITEM) {
                    temp = R.layout.list_item_subject_timeline;
                } else {
                    temp = R.layout.list_item_footer;
                }
            }
            return temp;
        };
        tempLayout = MVPUtility.resolveLayout(tempLayout, viewType, layoutResolveStrategy);
        viewRow = inflater.inflate(tempLayout, parent, false);
        setCreator((view, i) -> new SubjectTimelineViewHolder(view, viewType));
        return (SubjectTimelineViewHolder) this.creator.getViewHolder(viewRow, tempLayout);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final SubjectTimelineViewHolder aHolder = (SubjectTimelineViewHolder) viewHolder;
        if (getItemViewType(position) == TYPE_ITEM) {
            SubjectTimelineItem item = (SubjectTimelineItem) ((MVPModelRecycler) mModel).getItems().get(position);
            aHolder.mName.setText(item.getmName());
            aHolder.mTime.setText(item.getmTime());

            String hexColor = this.colors[item.getType()];
            long thisCol = Long.decode(hexColor) + 4278190080L;
            Drawable drawable;
            Typeface typeface = Typeface.createFromAsset(getActivityContext().getAssets(), "fonts/fontawesome-webfont.ttf");
            if (item.getmMark() == 0) {
                drawable = TextDrawable.builder().beginConfig().useFont(typeface).textColor((int) thisCol).endConfig()
                        .buildRound(String.valueOf(getLetter(item.getmMark())), Color.WHITE);
            } else {
                drawable = TextDrawable.builder()
                        .buildRound(String.valueOf(getLetter(item.getmMark())), (int) thisCol);
            }
            aHolder.mMarkImage.setImageDrawable(drawable);
            aHolder.mTimeIcon.setTypeface(typeface);
            aHolder.mTimeIcon.setText("\uF017");
        } else {
            aHolder.loadMoreButton.setOnClickListener(view -> {
                mBundle.putString(EXTRA_KEY_GCOURSE_START, String.valueOf(getItemCount()+1));
                loadData(mBundle);
            });
        }
    }
}
