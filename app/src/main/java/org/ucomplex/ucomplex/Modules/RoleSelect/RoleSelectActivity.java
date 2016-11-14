package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.Login.LoginModel;
import org.ucomplex.ucomplex.Modules.Login.LoginPresenter;
import org.ucomplex.ucomplex.Modules.Login.LoginRepository;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import javax.inject.Inject;

public class RoleSelectActivity extends BaseActivity implements MVP_RoleSelect.ViewToPresenterInterface {

    private ListRolesAdapter mListAdapter;

    @Inject public void setPresenter(RolePresenter presenter) {
        super.mPresenter = presenter;
    }
    @Inject public void setModel(RoleModel model) {
        super.mModel = model;
    }
    @Inject public void setRepository(RoleRepository repository) {
        super.mRepository = repository;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((MyApplication) getApplication()).getRoleDiComponent().inject(this);
        setupRecyclerView();
        UserInterface user = getIntent().getParcelableExtra(Constants.EXTRA_KEY_USER);
        super.setModelData(user);
        super.setupMVP(this, RoleSelectActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    private void setupRecyclerView(){
        mListAdapter = new ListRolesAdapter();
        RecyclerView mList = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.setItemAnimator(new DefaultItemAnimator());
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAlert(AlertDialog dialog) {

    }

    @Override
    public void notifyItemRemoved(int position) {
        mListAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mListAdapter.notifyItemInserted(layoutPosition);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mListAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    private class ListRolesAdapter extends RecyclerView.Adapter<RoleViewHolder> {

        @Override
        public int getItemCount() {
            return ((MVP_RoleSelect.PresenterInterface)mPresenter).getRolesCount();
        }

        @Override
        public RoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ((MVP_RoleSelect.PresenterInterface)mPresenter).createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RoleViewHolder holder, int position) {
            ((MVP_RoleSelect.PresenterInterface)mPresenter).bindViewHolder(holder, position);
        }
    }

}