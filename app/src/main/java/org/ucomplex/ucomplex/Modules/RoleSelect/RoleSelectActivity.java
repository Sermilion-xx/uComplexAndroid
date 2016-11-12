package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Modules.BaseActivity;
import org.ucomplex.ucomplex.Modules.Login.MVP_Login;
import org.ucomplex.ucomplex.Modules.MyApplication;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import javax.inject.Inject;

public class RoleSelectActivity extends BaseActivity implements MVP_RoleSelect.RequiredViewOpsFromPresenter  {

    MVP_RoleSelect.ProvidedPresenterOpsToView mPresenter;
    ListRolesAdapter mListAdapter;
    @Inject RolePresenter presenter;
    @Inject RoleModel mModel;
    @Inject RoleRepository mRoleRepository;


    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( getFragmentManager(), RoleSelectActivity.class.getName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((MyApplication) getApplication()).getRoleDiComponent().inject(this);
        setupRecyclerView();
        setupMVP();
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

    private void setupMVP() {
        if (mStateMaintainer.firstTimeIn()) {
            Intent intent = getIntent();
            if(intent.hasExtra(Constants.EXTRA_KEY_USER)){
                presenter.setView(this);
                mRoleRepository.setContext(presenter.getAppContext());
                mModel.setPresenter(presenter);
                User user = intent.getParcelableExtra(Constants.EXTRA_KEY_USER);
                mModel.setUser(user);
                mModel.setRolesRepository(mRoleRepository);
                presenter.setModel(mModel);
                mStateMaintainer.put(presenter);
                mStateMaintainer.put(mModel);
                mPresenter = presenter;
                mStateMaintainer.put(MVP_RoleSelect.RequiredViewOpsFromPresenter.class.getSimpleName(), mPresenter);
            }
        }
        else {
            mPresenter = mStateMaintainer.get(RolePresenter.class.getName());
            mPresenter.setView(this);
        }
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
            return mPresenter.getRolesCount();
        }

        @Override
        public RoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RoleViewHolder holder, int position) {
            mPresenter.bindViewHolder(holder, position);
        }
    }

}