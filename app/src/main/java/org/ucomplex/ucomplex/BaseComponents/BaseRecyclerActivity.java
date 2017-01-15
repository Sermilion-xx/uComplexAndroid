package org.ucomplex.ucomplex.BaseComponents;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;

import net.oneread.aghanim.components.base.MVPBaseRecyclerActivity;

//implements MVPBaseRecyclerActivity for mvp
public class BaseRecyclerActivity extends BaseActivity implements MVPBaseRecyclerActivity {

    @Override
    public void notifyItemRemoved(int position) {
        getAdapter().notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeInserted(int start, int end) {
        getAdapter().notifyItemRangeInserted(start, end);
    }

    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return mFragment.getListAdapter();
    }

    @Override
    public void notifyItemRangeRemoved(int start, int end) {
        getAdapter().notifyItemRangeRemoved(start, end);
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        getAdapter().notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        getAdapter().notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mFragment.getRecyclerView();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPresenter.onConfigurationChanged(this);
    }

    public void showProgress() {
        if (mFragment != null)
            mFragment.showProgress();
    }

    public void hideProgress() {
        if (mFragment != null)
            mFragment.hideProgress();
    }

}
