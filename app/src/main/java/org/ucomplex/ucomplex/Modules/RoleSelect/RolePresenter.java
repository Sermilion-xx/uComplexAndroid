package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenter;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractPresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;

import java.util.Random;

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

public class RolePresenter extends AbstractPresenterRecycler implements PresenterRecycler {

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
    public void bindViewHolder(RecyclerView.ViewHolder aHolder, final int position) {
        RoleViewHolder holder = (RoleViewHolder) aHolder;
        final RoleItem role = (RoleItem) ((ModelRecycler)mModel).getItem(position);
        holder.roleName.setText(role.getRoleName());
        holder.roleIcon.setImageResource(role.getRoleIcon());
        holder.roleIcon.setOnClickListener(view -> {
            final UserInterface user = mModel.getUser();
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    String login = user.getLogin();
                    String password = user.getPassword();
                    int role1 = user.getRoles().get(position).getId();
                    user.setType(user.getRoles().get(position).getType());
                    mModel.getUser().setType(user.getType());
                    String encodedAuth = encodeLoginData(login + ":" + password + ":" + role1);
                    FacadePreferences.setLoginDataToPref(getActivityContext(), encodedAuth);
                    FacadePreferences.setUserDataToPref(getActivityContext(), user);
                    return null;
                }
            }.execute();
            Intent intent = new Intent(getActivityContext(), EventsActivity.class);
            intent.putExtra(Constants.EXTRA_KEY_USER, (Parcelable) user);
            getActivityContext().startActivity(intent);
        });
    }
}
