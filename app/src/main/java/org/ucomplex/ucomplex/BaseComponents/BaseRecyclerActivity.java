package org.ucomplex.ucomplex.BaseComponents;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;

public class BaseRecyclerActivity extends BaseActivity implements ViewToPresenterRecycler {

    public IFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mFragment.getListAdapter().notifyItemRangeRemoved(start,end);
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mFragment.getListAdapter().notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
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

    }

    @Override
    public void showAlert(AlertDialog dialog) {

    }

    @Override
    public void showProgress() {
        mFragment.showProgress();
    }

    @Override
    public void hideProgress() {
        mFragment.hideProgress();
    }
}
