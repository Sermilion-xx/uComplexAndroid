package org.ucomplex.ucomplex.Modules.Events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import net.oneread.aghanim.components.base.BaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.recyclermvp.PresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class EventsActivity extends BaseRecyclerActivity {

    private MediaPlayer mAlert;
    private Boolean updateEventsReceiverRegistered = false;
    private View mProgressBar;

    //mvp
    @Inject
    public void setPresenter(EventsPresenter presenter) {
        super.mPresenter = presenter;
    }

    //mvp
    @Inject
    public void setModel(EventsModel model) {
        super.mModel = model;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("updateEventsReceiverRegistered", updateEventsReceiverRegistered);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getEventsDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        mProgressBar = findViewById(R.id.progressBar);
        //mvp
        setupMVP(this, BaseActivity.class);
        setupDrawer();
        mFragment = setupRecyclerFragment(savedInstanceState, BaseRecyclerFragment.class.getName(), mPresenter, R.layout.fragment_recycler,R.id.recyclerView);
        initPresenter();
        if(savedInstanceState!=null){
            updateEventsReceiverRegistered = savedInstanceState.getBoolean("updateEventsReceiverRegistered");
        }
        setupToolbar(getResourceString(R.string.events));
        mAlert = MediaPlayer.create(this, R.raw.alert);
    }

    //mvp
    private void initPresenter() {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener(view -> {
            int position = getRecyclerView().getChildAdapterPosition(view);
            if(position == ((PresenterRecycler)mPresenter).getItemCount()-1){
                getActivityContext().sendBroadcast(new Intent(Constants.EVENTS_LOAD_MORE_BROADCAST));
            }
            IRecyclerItem item = ((PresenterRecycler)mPresenter).getItem(position);
        });
        ((PresenterRecycler)mPresenter).setBaseOnClickListener(clickListener);
        ((PresenterRecycler)mPresenter).setCreator((view, i) -> new EventViewHolder(view));
        ((PresenterRecycler)mPresenter).setItemLayout(R.layout.list_item_event);
    }

    //mvp
    @Override
    public RecyclerView getRecyclerView() {
        return mFragment.getRecyclerView();
    }

    //mvp
    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return mFragment.getListAdapter();
    }


    @Override
    public void onResume() {
        if(!updateEventsReceiverRegistered){
            registerReceiver(mUpdateEventsReceiver, new IntentFilter(
                    Constants.EVENTS_REFRESH_BROADCAST));
        }
        registerReceiver(mLoadMoreEventsReceiver, new IntentFilter(
                Constants.EVENTS_LOAD_MORE_BROADCAST));
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mUpdateEventsReceiver);
        unregisterReceiver(mLoadMoreEventsReceiver);
        super.onPause();
    }

    private BroadcastReceiver mUpdateEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_REFRESH_BROADCAST)) {
                mPresenter.loadData();
                onBackPressed();
            }
        }
    };

    private BroadcastReceiver mLoadMoreEventsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.EVENTS_LOAD_MORE_BROADCAST)) {
                Bundle bundle = new Bundle();
                bundle.putInt(EventsModel.EVENTS_START, ((EventsPresenter) mPresenter).getItemCount()-1);
                mPresenter.loadData(bundle);
            }
        }
    };

    public void showProgress(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        mProgressBar.setVisibility(View.GONE);
    }

}
