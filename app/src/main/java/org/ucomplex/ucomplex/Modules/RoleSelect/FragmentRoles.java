package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseFragment;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 02/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FragmentRoles extends BaseFragment {

    public static IFragment getInstance(Object...params) {
        IFragment fragment =  new FragmentRoles();
        fragment.setActivity((BaseActivity) params[0]);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roles, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        setupRecyclerView(view, R.id.rolesRecyclerView);
        mActivity.setupMVP((RoleSelectActivity)mActivity, ((RoleSelectActivity)mActivity).getClass(), FacadeCommon.getSharedUserInstance(mActivity));
        return view;
    }

}
