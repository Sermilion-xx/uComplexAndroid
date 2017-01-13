package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Domain.Users.Role;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleModel extends AbstractModelRecycler<UserInterface, List<RoleItem>> implements ModelRecycler<UserInterface, List<RoleItem>> {

    private int[] roleIcons = {
            R.drawable.role_select_1,
            R.drawable.role_select_2,
            R.drawable.role_select_3,
            R.drawable.role_select_4,
            R.drawable.role_select_5};

    public RoleModel() {
    }

    @Override
    public void loadData(MVPCallback<List<RoleItem>> mvpCallback, Bundle... bundles) {
        UserInterface user = bundles[0].getParcelable(Constants.EXTRA_KEY_USER);
        mvpCallback.onSuccess(processJson(user));
    }

    @Override
    public List<RoleItem> processJson(UserInterface user) {
        List<RoleItem> roles = new ArrayList<>();
        if (user!=null) {
            Random random = new Random();
            for (int i = 0; i < user.getRoles().size(); i++) {
                Role role = user.getRoles().get(i);
                String roleStr = "";
                if (role.getType() == 3) {
                    roleStr = mContext.getResources().getString(R.string.prepodvatel);
                } else if (role.getType() == 4) {
                    roleStr = mContext.getResources().getString(R.string.student);
                } else if (role.getType() == 0) {
                    roleStr = mContext.getResources().getString(R.string.sotrudnik);
                } else if (role.getType() == 3) {
                    roleStr = mContext.getResources().getString(R.string.prepodvatel);
                }
                int index = random.nextInt(5);
                roles.add(new RoleItem(roleIcons[index], roleStr));
            }

        }
        return roles;
    }

}
