package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;

import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractRepository;
import org.ucomplex.ucomplex.Model.Users.Role;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.Random;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.REQUEST_LOAD_ROLES;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class RoleRepository extends AbstractRepository {

    private int[] roleIcons = {
            R.drawable.role_select_1,
            R.drawable.role_select_2,
            R.drawable.role_select_3,
            R.drawable.role_select_4,
            R.drawable.role_select_5};


    public RoleRepository() {

    }

    @Override
    public void loadData(Bundle bundle) {
        User user = bundle.getParcelable(Constants.EXTRA_KEY_USER);
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
        mOnTaskCompleteListener.onTaskComplete(REQUEST_LOAD_ROLES, roles);
    }
}
