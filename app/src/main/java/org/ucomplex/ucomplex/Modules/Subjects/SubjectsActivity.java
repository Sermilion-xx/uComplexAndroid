package org.ucomplex.ucomplex.Modules.Subjects;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;

import javax.inject.Inject;

public class SubjectsActivity extends BaseActivity implements ViewToPresenterRecycler {

    FragmentSubjects mFragment;

    //Used in Fragment
    public final void setupMVP() {
        UserInterface user = FacadeCommon.getSharedUserInstance(this);
        setupMVP(this, EventsActivity.class, user);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar(getResourceString(R.string.subjects));
        setContentViewWithNavDrawer(R.layout.activity_subjects);
        ((MyApplication) getApplication()).getSubjectsDiComponent().inject(this);
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


    @Inject
    public void setPresenter(SubjectsPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(SubjectsModel model) {
        super.mModel = model;
    }

    @Inject
    public void setRepository(SubjectsRepository repository) {
        super.mRepository = repository;
    }

}
