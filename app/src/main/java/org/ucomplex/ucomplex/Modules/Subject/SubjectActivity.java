package org.ucomplex.ucomplex.Modules.Subject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPStateMaintainer;
import net.oneread.aghanim.components.utility.OnFragmentLoadedListener;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.basemvp.MVPPresenter;
import net.oneread.aghanim.mvp.basemvp.MVPView;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FragmentFactory;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelinePresenter;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class SubjectActivity extends BaseRecyclerActivity {

    private static final String EXTRA_KEY_COURSE_NAME = "courseName";
    private int currentFragmentIndex = 0;
    private List<IFragment> mFragments = new ArrayList<>();
    private MVPBaseRecyclerFragment subjectDetailsFragment;
    private MVPBaseRecyclerFragment subjectMaterialsFragment;
    private MVPBaseRecyclerFragment subjectTimelineFragment;
    private Intent intent;

    @Override
    protected IFragment setupFragment(MVPView mvpView, Bundle savedInstanceState, Bundle bundle, int fragmentLayout, int recyclerViewId, int progressBarId, int containerId) {
        return super.setupFragment(mvpView, savedInstanceState, bundle, fragmentLayout, recyclerViewId, progressBarId, containerId);
    }

    public static void receiveIntent(Context context, int courseId, String courseName) {
        Intent intent = new Intent(context, SubjectActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, courseId);
        extras.putString(EXTRA_KEY_COURSE_NAME, courseName);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        ((DaggerApplication) getApplication()).getSubjectDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_subject);
        setupToolbar(intent.getStringExtra(EXTRA_KEY_COURSE_NAME));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(pageChangeListener);

        viewPagerAdapter.addFragment(subjectDetailsFragment, "Дисциплина");
        viewPagerAdapter.addFragment(subjectMaterialsFragment, "Материалы");
        mFragments.add(subjectDetailsFragment);
        mFragments.add(subjectMaterialsFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mFragment = mFragments.get(position);
            currentFragmentIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private MVPBaseRecyclerFragment createFragment(MVPPresenter presenter){
        MVPBaseRecyclerFragment fragment = (MVPBaseRecyclerFragment) FragmentFactory.getFragmentWithName(
                MVPBaseRecyclerFragment.class.getName(),
                presenter,
                R.layout.fragment_recycler,
                R.id.recyclerView);
        fragment.setProgressViewId(R.id.progressBar);
        return fragment;
    }

    @Inject
    public void setSubjectDetailsPresenter(SubjectDetailsPresenter presenter) {
        presenter.setView(this);
        subjectDetailsFragment = createFragment(presenter);
        subjectDetailsFragment.setOnFragmentLoadedListener(views -> {
            Bundle bundle = ((SubjectDetailsPresenter)subjectDetailsFragment.getPresenter()).getModelBundle();
            subjectDetailsFragment.getPresenter().loadData(bundle);
            setupDrawer();
        });
        mFragment = subjectDetailsFragment;
    }

    public void subjectModelLoaded(){
        subjectMaterialsFragment.getPresenter().loadData();
    }

    @Inject @SuppressWarnings("unchecked")
    public void setSubjectDetailsModel(SubjectDetailsModel model) {
        Bundle bundle = new Bundle();
        bundle.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, intent.getIntExtra(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, -1));
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        subjectDetailsFragment.getPresenter().setModel(model, bundle);
    }

    @Inject
    public void setSubjectMaterialPresenter(SubjectMaterialsPresenter presenter) {
        presenter.setView(this);
        subjectMaterialsFragment = createFragment(presenter);
        subjectMaterialsFragment.setOnFragmentLoadedListener(new OnFragmentLoadedListener() {
            @Override
            public void onFragmentLoaded(View... views) {
                System.out.println();
            }
        });
    }

    @Inject @SuppressWarnings("unchecked")
    public void setSubjectMaterialModel(SubjectMaterialsModel model) {
        subjectMaterialsFragment.getPresenter().setModel(model);

    }

    @Inject
    public void setSubjectTimelinePresenter(SubjectTimelinePresenter presenter) {
//        mPresenters.add(presenter);
    }

    @Inject
    public void setSubjectTimelinelModel(SubjectTimelineModel model) {
//        mModels.add(model);
    }

}
