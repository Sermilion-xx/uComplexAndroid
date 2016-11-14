package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewRecylerToPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
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

public class RolePresenter implements MVP_RoleSelect.PresenterInterface {

    private WeakReference<MVP_RoleSelect.ViewToPresenterInterface> mView;
    private Model mModel;

    public RolePresenter(MVP_RoleSelect.ViewToPresenterInterface view) {
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

    @Override
    public void setView(ViewToPresenter view) {
        mView = new WeakReference<>((MVP_RoleSelect.ViewToPresenterInterface)view);
    }

    @Override
    public void onConfigurationChanged(ViewToPresenter view) {
        mView = new WeakReference<>((MVP_RoleSelect.ViewToPresenterInterface) view);
    }


@Override
    public void setModel(Model model) {
        mModel = model;
        loadData();
    }


    private void loadData() {
        mModel.loadData();
    }

    private MVP_RoleSelect.ViewToPresenterInterface getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
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
        final RoleItem role = ((RoleModel)mModel).getRole(position);
        holder.roleName.setText(role.getRoleName());
        holder.roleIcon.setImageBitmap(BitmapFactory.decodeResource(getActivityContext().getResources(),
                role.getRoleIcon()));
        holder.roleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivityContext(), EventsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_USER, (User) ((RoleModel)mModel).getUser());
                getActivityContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getRolesCount() {
        return ((RoleModel)mModel).getRolesCount();
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
