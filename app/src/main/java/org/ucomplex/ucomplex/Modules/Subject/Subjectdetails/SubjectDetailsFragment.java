package org.ucomplex.ucomplex.Modules.Subject.SubjectDetails;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.R;

import java.util.List;

import javax.inject.Inject;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 19/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectDetailsFragment extends MVPViewBaseFragment<String, List<IRecyclerItem>> {

    @Inject
    MVPModelRecycler<String, List<IRecyclerItem>> mModel;

    @Inject @Override
    public void setPresenter(MVPPresenterRecycler<String, List<IRecyclerItem>> presenter) {
        presenter.setView(this);
        super.setPresenter(presenter);
    }


    public static SubjectDetailsFragment getInstance(Activity mContext) {
        SubjectDetailsFragment fragment = new SubjectDetailsFragment();
        fragment.setContext(mContext);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.mFragmentLayout = R.layout.fragment_recycler;
        this.mRecyclerViewId = R.id.recyclerView;
        this.mProgressViewId = R.id.progressBar;
        ((DaggerApplication)mContext.getApplication()).getSubjectDetailsDiComponent().inject(this);
        this.setOnFragmentLoadedListener(views -> {
            if(mPresenter.getModel()==null){
                mPresenter.setModel(mModel, getArguments());
            }
        });
    }
}
