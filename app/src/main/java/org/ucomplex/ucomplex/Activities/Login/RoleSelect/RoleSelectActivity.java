package org.ucomplex.ucomplex.Activities.Login.RoleSelect;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ucomplex.ucomplex.Activities.BaseActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoleSelectActivity extends BaseActivity implements MVP_RoleSelect.RequiredViewOpsFromPresenter  {

    private MVP_RoleSelect.ProvidedPresenterOpsToView mPresenter;
    private ListRolesAdapter mListAdapter;
    private ProgressBar      mProgress;
    private TextView         mTitle;
    private CircleImageView  mIcon;

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( getFragmentManager(), RoleSelectActivity.class.getName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        setupViews();
        setupMVP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    private void setupViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIcon = (CircleImageView) findViewById(R.id.roleImage);
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
            RolePresenter presenter = new RolePresenter(this);
            RoleModel model = new RoleModel(presenter);
            presenter.setModel(model);
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(model);
            mPresenter = presenter;
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