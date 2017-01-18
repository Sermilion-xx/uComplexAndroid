package org.ucomplex.ucomplex.Modules.Subject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsPresenter;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class SubjectActivity extends BaseRecyclerActivity {

    private static final String EXTRA_KEY_COURSE_NAME = "courseName";

    public static void receiveIntent(Context context, int courseId, String courseName){
        Intent intent = new Intent(context, SubjectActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, courseId);
        extras.putString(EXTRA_KEY_COURSE_NAME, courseName);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


    @Inject
    public void setPresenter(SubjectDetailsPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(SubjectDetailsModel model) {
        super.mModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getSubjectDiComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentViewWithNavDrawer(R.layout.activity_subject);
        Intent intent = getIntent();
        //mvp
        Bundle bundle = new Bundle();
        bundle.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, intent.getIntExtra(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID,-1));
        setupToolbar(intent.getStringExtra(EXTRA_KEY_COURSE_NAME));
        DaggerApplication application = (DaggerApplication)getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());

        mFragment = setupFragment(this,
                savedInstanceState,
                bundle,
                R.layout.fragment_recycler,
                R.id.recyclerView,
                R.id.progressBar);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(pageChangeListener);

    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
