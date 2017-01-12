package org.ucomplex.ucomplex.Modules.Events;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.R;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsPresenter extends AbstractPresenterRecycler {

    private boolean hasMoreEvents = true;
    private static final int TYPE_COMMON = 0;
    private static final int TYPE_FOOTER = 1;

    @Override
    public void setModel(MVPModel models, Bundle... bundles) {
        super.setModel(models, bundles);
    }

    private int isAvailableListViewItem() {
        IRecyclerItem item = ((ModelRecycler) mModel).getItems().get(0);
        if (!FacadeCommon.isNetworkConnected(getActivityContext()) && item.isEmpty()) {
            return R.layout.list_item_no_internet;
        } else if (item.isEmpty()) {
            return R.layout.list_item_no_content;
        } else {
            return itemLayout;
        }
    }

    @Override
    public EventViewHolder createViewHolder(ViewGroup parent, int viewType) {
        itemLayout = isAvailableListViewItem();
        if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
            itemLayout = viewType == 0 ? itemLayout : R.layout.list_item_event_footer;
        }
        return (EventViewHolder) super.createViewHolder(parent, viewType);
    }

    @Override
    public void bindViewHolder(final RecyclerView.ViewHolder aHolder, int position) {
        EventViewHolder holder = (EventViewHolder) aHolder;
        final IRecyclerItem item = ((ModelRecycler) mModel).getItem(position);
        if (!holder.allNullElements() && item instanceof EventItem) {
            EventItem event = (EventItem) item;
            String personName = event.getParams().getName();
            if (personName == null || personName.equals(Constants.STRING_EMPTY)) {
                event.getParams().setName(getActivityContext().getResources().getString(R.string.ucomplex));
            }
            holder.eventPersonName.setText(event.getParams().getName());
            holder.eventTextView.setText(event.getEventText());
            holder.eventTime.setText(event.getTime());

            Drawable textDrawable = FacadeMedia.getTextDrawable(event.getParams().getId(),
                    event.getParams().getName(),
                    getActivityContext());
            holder.eventsImageView.setImageDrawable(textDrawable);
            if (event.getEventImageBitmap() != null) {
                holder.eventsImageView.setImageBitmap(event.getEventImageBitmap());
            } else {
                if (event.getParams().getCode() == null) {
                    holder.eventsImageView.setImageDrawable(textDrawable);
                } else {
                    Glide.with(getActivityContext())
                            .load(HttpFactory.LOAD_PROFILE_URL + event.getParams().getCode() + Constants.IMAGE_FORMAT)
                            .into(holder.eventsImageView);
                }
            }
            //TODO: implement "go to course click"
//            holder.eventDetailsLayout.setOnClickListener(view -> {
////                Intent intent = new Intent(getActivityContext(), null);
//            });
        } else {
            if (holder.loadMoreEventsButton != null) {
                if (hasMoreEvents) {
                    holder.loadMoreEventsButton.setVisibility(View.VISIBLE);
                    holder.loadMoreEventsButton.setOnClickListener(baseOnClickListener);
                } else {
                    holder.loadMoreEventsButton.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == ((EventsModel) mModel).getItemCount() - 1 ? TYPE_FOOTER : TYPE_COMMON;
    }

    @Override
    public void setItemLayout(int i) {
        this.itemLayout = i;
    }

    @Override
    public void loadData(Bundle... bundle) {
        try {
            ((EventsActivity) getView()).showProgress();
        } catch (NullPointerException ignored) {}
        DaggerApplication application = (DaggerApplication) getAppContext();
        application.getAuthString();
        mModel.loadData(new MVPCallback() {
            @Override
            public void onSuccess(Object o) {
                if (!EventsModel.INITIAL_EVENTS_LOADED) {
                    processInitialEvents((String) o);
                    ((EventsActivity) getView()).hideProgress();
                } else {
                    processMoreEvents((String) o);
                    ((EventsActivity) getView()).hideProgress();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, bundle);
    }

    private void processInitialEvents(String o) {
        int end = ((ModelRecycler) mModel).getItemCount();
        EventsModel.INITIAL_EVENTS_LOADED = true;
        List<IRecyclerItem> newItems = ((EventsModel) mModel).processJson(o);
        ((ModelRecycler) mModel).clear();
        ((ViewRecycler) getView()).notifyItemRangeRemoved(0, end);
        ((ModelRecycler) mModel).addAll(newItems);
        addEmptyElement();
        hasMoreEvents = true;
        ((ViewRecycler) getView()).notifyItemRangeChanged(0, newItems.size() + 1);
    }

    private void processMoreEvents(String o) {
        List<IRecyclerItem> newItems = ((EventsModel) mModel).processJson(o);
        int end = ((ModelRecycler) mModel).getItems().size();
        getItems().addAll(end, newItems);
        int itemCount = ((ModelRecycler) mModel).getItemCount();
        ((ViewRecycler) getView()).notifyItemRangeInserted(end, itemCount - 1);
    }

    private void addEmptyElement() {
        ((ModelRecycler) mModel).getItems().add(new IRecyclerItem() {
            @Override
            public boolean isEmpty() {
                return true;
            }
        });
    }
}

