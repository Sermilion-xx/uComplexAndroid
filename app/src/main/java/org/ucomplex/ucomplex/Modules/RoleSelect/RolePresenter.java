package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

import java.lang.ref.WeakReference;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RolePresenter implements MVP_RoleSelect.ProvidedPresenterOpsToView, MVP_RoleSelect.RequiredPresenterOpsToModel {

    private WeakReference<MVP_RoleSelect.RequiredViewOpsFromPresenter> mView;
    private MVP_RoleSelect.ProvidedModelOpsFromPresenter mModel;

    public RolePresenter(MVP_RoleSelect.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }
    public RolePresenter() {

    }


    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mModel = null;
        }
    }

    public void setModel(MVP_RoleSelect.ProvidedModelOpsFromPresenter model) {
        mModel = model;
        // start to load data
        loadData();
    }

    private Toast makeToast(String msg) {
        return Toast.makeText(getView().getAppContext(), msg, Toast.LENGTH_SHORT);
    }

    private void loadData() {
        mModel.loadData();
    }

    private MVP_RoleSelect.RequiredViewOpsFromPresenter getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void setView(MVP_RoleSelect.RequiredViewOpsFromPresenter view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public RoleViewHolder createViewHolder(ViewGroup parent, int viewType) {
        RoleViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(R.layout.list_item_role, parent, false);
        viewHolder = new RoleViewHolder(viewTaskRow);
        return viewHolder;
    }

    @Override
    public void bindViewHolder(RoleViewHolder holder, int position) {
        final RoleItem role = mModel.getRole(position);
        holder.roleName.setText(role.getRoleName());
        holder.roleIcon.setImageBitmap(BitmapFactory.decodeResource(getActivityContext().getResources(),
                role.getRoleIcon()));
        holder.roleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityContext(), EventsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_USER, (User) mModel.getmUser());
                getActivityContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getRolesCount() {
        return mModel.getRolesCount();
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getActivityContext();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
