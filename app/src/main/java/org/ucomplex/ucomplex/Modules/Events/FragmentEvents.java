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

import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseFragment;
import org.ucomplex.ucomplex.Modules.MyApplication;
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

    @Arg ArrayList<EventItem>                  mEventItems = null;
    Button                                     mButtonLoadMore;
    @Arg FragmentEvents.ListEventsAdapter      mListAdapter;
    @Arg EventsActivity                        mContext;
    MediaPlayer                                mAlert;
    @Arg UserInterface                         mUser;
    @Arg EventsActivity                        mActivity;

    public void setActivity(EventsActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void setUser(UserInterface mData) {
        this.mUser = mData;
    }

    public void setContext(EventsActivity mContext) {
        this.mContext = mContext;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(mContext, R.raw.alert);
        setupViews(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupViews(View view){
        mListAdapter = new FragmentEvents.ListEventsAdapter();
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
    }

    private class ListEventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

        @Override
        public int getItemCount() {
            return mActivity.mPresenter.getEventsCount();
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mActivity.mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            mActivity.mPresenter.bindViewHolder(holder, position);
        }
    }
}
