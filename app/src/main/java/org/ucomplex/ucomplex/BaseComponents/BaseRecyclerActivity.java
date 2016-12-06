package org.ucomplex.ucomplex.BaseComponents;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;

public class BaseRecyclerActivity extends BaseActivity implements ViewToPresenterRecycler {

    public IFragment mFragment;

    public IFragment getFragment() {
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = setupFragment(getFragmentManager(),
                BaseRecyclerFragment.class.getName());
    }

    @Override
    public void notifyItemRemoved(int position) {
        mFragment.getListAdapter().notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mFragment.getListAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyItemRangeInserted(int start, int end) {
        mFragment.getListAdapter().notifyItemRangeInserted(start, end);
    }

    @Override
    public void notifyItemRangeRemoved(int start, int end) {
        mFragment.getListAdapter().notifyItemRangeRemoved(start, end);
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mFragment.getListAdapter().notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mFragment.getListAdapter().notifyItemRangeChanged(positionStart, itemCount);
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
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void showAlert(AlertDialog dialog) {
        dialog.show();
    }

    @Override
    public void showProgress() {
        mFragment.showProgress();
    }

    @Override
    public void hideProgress() {
        mFragment.hideProgress();
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


}
