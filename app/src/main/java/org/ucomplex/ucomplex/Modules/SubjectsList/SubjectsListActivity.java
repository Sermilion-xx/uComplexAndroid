package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.app.Fragment;
import android.os.Bundle;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.basemvp.MVPView;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.USER_TYPE_STUDENT;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.USER_TYPE_TEACHER;

public class SubjectsListActivity extends BaseRecyclerActivity implements MVPViewRecycler {

    private static final String TAG_MATERIALS_FRAGMENT = MVPBaseRecyclerFragment.class.getName();
    private DaggerApplication mApplication;

    @Inject
    public void setPresenter(SubjectsListPresenter presenter) {
        super.mPresenter = presenter;
        ((MVPPresenterRecycler) super.mPresenter).setItemLayout(R.layout.list_item_subject);
    }

    @Inject
    public void setModel(SubjectsListModel model) {
        super.mModel = model;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, TAG_MATERIALS_FRAGMENT, (Fragment) mFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApplication = ((DaggerApplication) getApplication());
        mApplication.getSubjectsListDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_subjects_list);
        setupToolbar(getString(R.string.subjects));

        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putInt(Constants.EXTRA_KEYT_USER_TYPE, application.getSharedUser().getType());

        mFragment = setupFragment(this,
                TAG_MATERIALS_FRAGMENT,
                savedInstanceState,
                bundle,
                R.layout.fragment_recycler,
                R.id.recyclerView,
                R.id.progressBar,
                R.id.container);
        mFragment.hasDivider(true);
        setupDrawer();
    }

//    @Override
//    protected IFragment setupFragment(MVPView mvpView,
//                                      String name,
//                                      Bundle savedInstanceState,
//                                      Bundle bundle,
//                                      int fragmentLayout,
//                                      int recyclerViewId,
//                                      int progressBarId,
//                                      int containerId) {
//        IFragment mFragment = setupRecyclerFragment(savedInstanceState,
//                MVPBaseRecyclerFragment.class.getName(),
//                mPresenter,
//                fragmentLayout,
//                recyclerViewId, containerId);
//        mFragment.setProgressViewId(progressBarId);
//        mFragment.hasDivider(true);
//        mFragment.setOnFragmentLoadedListener(views -> {
//            setupMVP(mvpView, BaseActivity.class, bundle);
//            setupDrawer();
//        });
//        return mFragment;
//    }

}
