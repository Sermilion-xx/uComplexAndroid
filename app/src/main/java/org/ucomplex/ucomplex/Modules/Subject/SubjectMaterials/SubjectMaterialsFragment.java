package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.app.Activity;
import android.os.Bundle;

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
 * Created by Sermilion on 21/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectMaterialsFragment extends MVPViewBaseFragment<String, List<IRecyclerItem>> {

    private boolean dataRequested;

    @Inject @Override
    public void setPresenter(MVPPresenterRecycler<String, List<IRecyclerItem>> presenter) {
        presenter.setView(this);
        super.setPresenter(presenter);
    }

    @Inject
    public void setModel(MVPModelRecycler<String, List<IRecyclerItem>> mModel) {
        this.mPresenter.setModel(mModel);
    }


    public static SubjectMaterialsFragment getInstance(Activity mContext) {
        SubjectMaterialsFragment fragment = new SubjectMaterialsFragment();
        fragment.setContext(mContext);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.mFragmentLayout = R.layout.fragment_recycler;
        this.mRecyclerViewId = R.id.recyclerView;
        this.mProgressViewId = R.id.progressBar;
        ((DaggerApplication)mContext.getApplication()).getSubjectMaterialsDiComponent().inject(this);
    }

    public void onFragmentVisible(){
        if(mPresenter.getItemCount() == 0 && !dataRequested){
            mPresenter.loadData();
            dataRequested = true;
        }
    }
}
