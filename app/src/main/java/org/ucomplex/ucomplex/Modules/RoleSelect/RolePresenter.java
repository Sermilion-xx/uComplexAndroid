package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.PresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;

import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.HttpFactory.encodeLoginData;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RolePresenter extends AbstractPresenterRecycler<List<RoleItem>>{

    public RolePresenter() {

    }

    private BitmapDrawable createBitmapDrawbale(int position) {
        if (position > 4) {
            position = 1;
        }
        return new BitmapDrawable(BitmapFactory.decodeResource(getActivityContext().getResources(),
                Constants.colorsUserSelect[position]));
    }

    @Override
    public void loadData(Bundle... bundle) {
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> iRecyclerItems) {
                populateRecyclerView(iRecyclerItems);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, bundle);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder aHolder, final int position) {
        RoleViewHolder holder = (RoleViewHolder) aHolder;
        final RoleItem role = (RoleItem) ((ModelRecycler)mModel).getItem(position);
        holder.roleName.setText(role.getRoleName());
        holder.roleIcon.setImageResource(role.getRoleIcon());
        holder.roleIcon.setOnClickListener(baseOnClickListener);
    }

}
