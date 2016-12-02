package org.ucomplex.ucomplex.Modules.Events;

import android.media.MediaPlayer;
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
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class FragmentEvents extends BaseFragment{

    private MediaPlayer mAlert;

    public static IFragment getInstance(Object...params) {
        IFragment fragment =  new FragmentEvents();
        fragment.setActivity((BaseActivity) params[0]);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        mAlert = MediaPlayer.create(mActivity, R.raw.alert);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mActivity.setupMVP((EventsActivity)mActivity, ((EventsActivity)mActivity).getClass(), FacadeCommon.getSharedUserInstance(mActivity));
        setupRecyclerView(view, R.id.eventsRecyclerView);
        mActivity.setupDrawer();
        return view;
    }

}
