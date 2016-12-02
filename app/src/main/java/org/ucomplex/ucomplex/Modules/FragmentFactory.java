package org.ucomplex.ucomplex.Modules;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Modules.Events.FragmentEvents;
import org.ucomplex.ucomplex.Modules.RoleSelect.FragmentRoles;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 26/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentFactory {

    public static IFragment getFragmentWithName(String name, Object... params) {

        if (name.equals(FragmentEvents.class.getName())) {
            return FragmentEvents.getInstance(params);

        } else if (name.equals(FragmentRoles.class.getName())) {
            return FragmentRoles.getInstance(params);
        }
        return null;
    }

}
