package org.ucomplex.ucomplex.Activities.Login.RoleSelect;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class RoleItem {
    private int roleIcon;
    private String roleName;

    public RoleItem(int iconId, String name){
        this.roleIcon = iconId;
        this.roleName = name;
    }

}
