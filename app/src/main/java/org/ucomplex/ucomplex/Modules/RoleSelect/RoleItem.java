package org.ucomplex.ucomplex.Modules.RoleSelect;


import net.oneread.aghanim.components.utility.IRecyclerItem;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleItem extends IRecyclerItem {
    private int roleIcon;
    private String roleName;

    public RoleItem(int iconId, String name){
        this.roleIcon = iconId;
        this.roleName = name;
    }

    public RoleItem() {
    }

    public int getRoleIcon() {
        return roleIcon;
    }

    public void setRoleIcon(int roleIcon) {
        this.roleIcon = roleIcon;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
