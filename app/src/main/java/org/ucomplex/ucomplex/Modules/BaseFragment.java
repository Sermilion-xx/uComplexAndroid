package org.ucomplex.ucomplex.Modules;


import android.os.Bundle;
import android.app.Fragment;


import com.hannesdorfmann.fragmentargs.FragmentArgs;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this); // read @Arg fields
    }
}
