package org.ucomplex.ucomplex.Modules.Events;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.R;

import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsPresenter extends MVPAbstractPresenterRecycler<String> {

    private boolean hasMoreEvents = true;
    private static final int TYPE_COMMON = 0;
    private static final int TYPE_FOOTER = 1;

    @Override
    public void setModel(MVPModel<String, List<IRecyclerItem>> models, Bundle... bundle) {
        super.setModel(models, bundle);
    }

    @Override
    public EventViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewTaskRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), R.layout.list_item_event);
        tempLayout = MVPUtility.resolveLayout(tempLayout,
                viewType,
                (viewType1) -> viewType1 == 0 ? R.layout.list_item_event : R.layout.list_item_event_footer);
        viewTaskRow = inflater.inflate(tempLayout, parent, false);

        setCreator((view, i) -> new EventViewHolder(view));
        EventViewHolder holder = (EventViewHolder) this.creator.getViewHolder(viewTaskRow, tempLayout);
        if (viewType == TYPE_FOOTER) {
            setupOnClickListener(holder);
        }
        return holder;
    }

    private void setupOnClickListener(EventViewHolder holder) {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(EventsModel.EVENTS_START, getItemCount() - 1);
            DaggerApplication application = (DaggerApplication) getAppContext();
            bundle.putString(AUTH_STRING, application.getAuthString());
            loadData(bundle);
        };
        clickListener.setStrategy(strategy);
        holder.loadMoreEventsButton.setOnClickListener(clickListener);
    }

    @Override
    public void bindViewHolder(final RecyclerView.ViewHolder aHolder, int position) {
        EventViewHolder holder = (EventViewHolder) aHolder;
        final IRecyclerItem item = ((MVPModelRecycler) mModel).getItem(position);
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
            if (hasMoreEvents) {
                holder.loadMoreEventsButton.setVisibility(View.VISIBLE);
            } else {
                holder.loadMoreEventsButton.setVisibility(View.GONE);
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
        ((EventsActivity) getView()).showProgress();
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                if (o.size() < 10) {
                    hasMoreEvents = false;
                } else {
                    addEmptyItem(o);
                }
                if (!EventsModel.INITIAL_EVENTS_LOADED) {
                    EventsModel.INITIAL_EVENTS_LOADED = true;
                    hasMoreEvents = true;
                    populateRecyclerView(o);
                    ((EventsActivity) getView()).hideProgress();
                } else {
                    getItems().remove(getItems().size() - 1);
                    ((MVPViewRecycler) getView()).notifyItemRemoved(getItems().size() + 1);
                    addMoreToRecyclerView(o);
                    ((EventsActivity) getView()).hideProgress();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                ((EventsActivity) getView()).hideProgress();
            }
        }, bundle);
    }

    private void addEmptyItem(List<IRecyclerItem> o) {
        o.add(new IRecyclerItem() {
            @Override
            public boolean isEmpty() {
                return true;
            }
        });
    }

}

