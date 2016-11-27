package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.lang.ref.WeakReference;
import java.util.Random;

import static org.ucomplex.ucomplex.Utility.HttpFactory.encodeLoginData;

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

    @Override
    public UserInterface getUser() {
        return mModel.getUser();
    }


    private void loadData() {
        mModel.loadData();
    }

    @Override
    public MVP_RoleSelect.ViewToPresenterInterface getView() throws NullPointerException {
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
        Random random = new Random();
        int rand = random.nextInt(5);
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_focused}, createBitmapDrawbale(rand));
        states.addState(new int[] {android.R.attr.state_pressed, -android.R.attr.state_focused}, createBitmapDrawbale(rand+1));
        viewTaskRow.setBackground(states);
        viewHolder = new RoleViewHolder(viewTaskRow, getActivityContext());
        return viewHolder;
    }
    private BitmapDrawable createBitmapDrawbale(int position){
        if(position>4){
            position = 1;
        }
        return new BitmapDrawable(BitmapFactory.decodeResource(getActivityContext().getResources(),
                Constants.colorsUserSelect[position]));
    }

    @Override
    public void bindViewHolder(RoleViewHolder holder, final int position) {
        final RoleItem role = ((RoleModel)mModel).getRole(position);
        holder.roleName.setText(role.getRoleName());
        holder.roleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserInterface user = ((RoleModel)mModel).getmUser();
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        String login = user.getLogin();
                        String password = user.getPassword();
                        int role = user.getRoles().get(position).getId();
                        user.setType(user.getRoles().get(position).getType());
                        ((RoleModel)mModel).getmUser().setType(user.getType());
                        String encodedAuth = encodeLoginData(login + ":" + password + ":" + role);
                        FacadePreferences.setLoginDataToPref(getActivityContext(), encodedAuth);
                        FacadePreferences.setUserDataToPref(getActivityContext(), user);
                    return null;
                    }
                }.execute();
                Intent intent = new Intent(getActivityContext(), EventsActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_USER_TYPE,  user.getRoles().get(position).getType());
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
