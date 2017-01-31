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

import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel.EXTRA_KEY_MY_MATERIALS;

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

    public static final String DATA_REQUESTED = "dataRequested";
    protected boolean dataRequested;

    @Inject
    @Override
    public void setPresenter(MVPPresenterRecycler<String, List<IRecyclerItem>> presenter) {
        presenter.setView(this);
        mPresenter = presenter;
    }

    public void onBackPress(){
        ((SubjectMaterialsPresenter)mPresenter).pageDown();
    }

    public int getCurrentPage(){
        return ((SubjectMaterialsPresenter)mPresenter).getCurrentPage();
    }

    @Inject
    public void setModel(MVPModelRecycler<String, List<IRecyclerItem>> mModel) {
        this.mPresenter.setModel(mModel);
    }

    public static SubjectMaterialsFragment getFragment(Activity mContext) {
        SubjectMaterialsFragment fragment = new SubjectMaterialsFragment();
        fragment.setContext(mContext);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            dataRequested = savedInstanceState.getBoolean(DATA_REQUESTED);
        }
        ((DaggerApplication) mContext.getApplication()).getSubjectMaterialsDiComponent().inject(this);
        this.mFragmentLayout = R.layout.fragment_recycler;
        this.mRecyclerViewId = R.id.recyclerView;
        this.mProgressViewId = R.id.progressBar;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(DATA_REQUESTED, dataRequested);
        super.onSaveInstanceState(outState);
    }

    public void onFragmentVisible() {
        if (!dataRequested) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(EXTRA_KEY_MY_MATERIALS, false);
            mPresenter.loadData(bundle);
            dataRequested = true;
        }
    }
}
