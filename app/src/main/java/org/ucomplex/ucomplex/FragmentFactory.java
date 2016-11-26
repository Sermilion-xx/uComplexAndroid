package org.ucomplex.ucomplex;

import android.app.Fragment;

import org.codehaus.jackson.map.ObjectMapper;
import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Modules.Events.FragmentEvents;

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

    public static IFragment getFragmentWithName(String name, Object ... params){

        if(name.equals(FragmentEvents.class.getName())){
            return FragmentEvents.getInstance(params);
        }
        return null;
    }

}
