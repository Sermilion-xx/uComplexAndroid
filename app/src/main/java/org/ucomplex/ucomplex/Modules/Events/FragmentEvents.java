package org.ucomplex.ucomplex.Modules.Events;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import org.ucomplex.ucomplex.Model.EventItem;
import org.ucomplex.ucomplex.Modules.BaseFragment;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentEvents  extends BaseFragment{

    @Arg ArrayList<EventItem>                  mEventItems = new ArrayList<>();
    Button                                     mButtonLoadMore;
    @Arg FragmentEvents.ListEventsAdapter      mListAdapter;
    MediaPlayer                                mAlert;
    EventsActivity                             mActivity;

    public void setActivity(EventsActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(mActivity.getActivityContext(), R.raw.alert);
        setupViews(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupViews(View view){
        mListAdapter = new FragmentEvents.ListEventsAdapter();
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity.getActivityContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
    }

    private class ListEventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

        @Override
        public int getItemCount() {
            return ((MVP_Events.PresenterInterface)mActivity.getPresenter()).getEventsCount();
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ((MVP_Events.PresenterInterface)mActivity.getPresenter()).createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            ((MVP_Events.PresenterInterface)mActivity.getPresenter()).bindViewHolder(holder, position);
        }
    }
}
