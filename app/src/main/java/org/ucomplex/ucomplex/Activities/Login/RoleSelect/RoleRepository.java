package org.ucomplex.ucomplex.Activities.Login.RoleSelect;

import android.content.Context;

import org.ucomplex.ucomplex.Model.Users.Role;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.util.ArrayList;
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
public class RoleRepository {

    private Context mContext;
    private int[] roleIcons = {
            R.drawable.role_select_1,
            R.drawable.role_select_2,
            R.drawable.role_select_3,
            R.drawable.role_select_4,
            R.drawable.role_select_5};

    public RoleRepository(Context appContext) {
        this.mContext = appContext;
    }

    ArrayList<RoleItem> getAllRoleItems(User user) {
        ArrayList<RoleItem> roles = new ArrayList<>();
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
        return roles;
    }
}
