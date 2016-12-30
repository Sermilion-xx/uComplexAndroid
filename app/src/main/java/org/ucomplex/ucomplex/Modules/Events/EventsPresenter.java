package org.ucomplex.ucomplex.Modules.Events;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.abstractmvp.AbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.R;

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
    public void setModel(MVPModel models) {
        super.setModel(models);

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
        if (item instanceof EventItem && !holder.allNullElements() && !item.isEmpty()) {
            EventItem event = (EventItem) item;
                String personName = event.getParams().getName();
                if (personName == null || personName.equals(Constants.STRING_EMPTY)) {
                    event.getParams().setName(getActivityContext().getResources().getString(R.string.ucomplex));
                }
                holder.eventPersonName.setText(event.getParams().getName());
                holder.eventTextView.setText(event.getEventText());
                holder.eventTime.setText(event.getTime());
                int id = event.getParams().getId();
                String name = event.getParams().getName();
                Drawable textDrawable = FacadeMedia.getTextDrawable(id, name, getActivityContext());
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

//            holder.eventDetailsLayout.setOnClickListener(view -> {
////                Intent intent = new Intent(getActivityContext(), null);
//            });
        } else {
            if (hasMoreEvents) {
                holder.loadMoreEventsButton.setVisibility(View.VISIBLE);
                holder.loadMoreEventsButton.setOnClickListener(
                        view -> getActivityContext().sendBroadcast(new Intent(Constants.EVENTS_LOAD_MORE_BROADCAST))
                );
            } else {
                if (holder.loadMoreEventsButton != null) {
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

    void loadMoreEvents(final int start) {
        try {
            ((EventsModel) mModel).loadMoreEvents(start);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

//    public void dataLoaded(boolean loaded, int... startEndOldEnd) {
//        int start = startEndOldEnd[0];
//        int end = startEndOldEnd[1];
//        int requestType = startEndOldEnd[3];
//        hasMoreEvents = loaded;
//        if (loaded) {
//            if (requestType == Constants.REQUEST_MORE_EVENTS) {
//                ((ViewRecycler) getView()).notifyItemRangeInserted(start, end);
//            } else {
//                hasMoreEvents = true;
//                ((ViewRecycler) getView()).notifyDataSetChanged();
//            }
//        }
//    }
}

