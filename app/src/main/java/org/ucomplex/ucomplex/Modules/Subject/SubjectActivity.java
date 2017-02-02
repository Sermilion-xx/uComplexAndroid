package org.ucomplex.ucomplex.Modules.Subject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineFragment;
import org.ucomplex.ucomplex.R;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.FacadeCommon.REQUEST_EXTERNAL_STORAGE;

public class SubjectActivity extends BaseActivity {

    private static final String EXTRA_KEY_COURSE_NAME = "courseName";
    private SubjectMaterialsFragment subjectMaterialsFragment;
    private SubjectDetailsFragment subjectDetailsFragment;
    private SubjectTimelineFragment subjectTimelineFragment;
    private int currentPage;


    public static void receiveIntent(Context context, int courseId, String courseName) {
        Intent intent = new Intent(context, SubjectActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, courseId);
        extras.putString(EXTRA_KEY_COURSE_NAME, courseName);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, "subjectMaterialsFragment", subjectMaterialsFragment);
        getFragmentManager().putFragment(outState, "subjectDetailsFragment", subjectDetailsFragment);
        getFragmentManager().putFragment(outState, "subjectTimelineFragment", subjectTimelineFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerApplication application = (DaggerApplication) getAppContext();
        setContentViewWithNavDrawer(R.layout.activity_subject);

        Intent intent = getIntent();
        setupToolbar(intent.getStringExtra(EXTRA_KEY_COURSE_NAME));
        setupDrawer();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null) {
            subjectDetailsFragment = (SubjectDetailsFragment) getFragmentManager().getFragment(savedInstanceState, "subjectDetailsFragment");
            subjectDetailsFragment.setContext(this);
            subjectMaterialsFragment = (SubjectMaterialsFragment) getFragmentManager().getFragment(savedInstanceState, "subjectMaterialsFragment");
            subjectMaterialsFragment.setContext(this);
            subjectTimelineFragment = (SubjectTimelineFragment) getFragmentManager().getFragment(savedInstanceState, "subjectTimelineFragment");
            subjectTimelineFragment.setContext(this);
        } else {
            subjectDetailsFragment = SubjectDetailsFragment.getInstance(this);
            subjectMaterialsFragment = SubjectMaterialsFragment.getFragment(this);
            subjectTimelineFragment = SubjectTimelineFragment.getFragment(this);

            Bundle bundle = new Bundle();
            bundle.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, intent.getIntExtra(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, -1));
            bundle.putString(AUTH_STRING, application.getAuthString());

            subjectDetailsFragment.setArguments(bundle);
            subjectMaterialsFragment.setArguments(bundle);
            subjectTimelineFragment.setArguments(bundle);
        }

        viewPagerAdapter.addFragment(subjectDetailsFragment, "Дисциплина");
        viewPagerAdapter.addFragment(subjectMaterialsFragment, "Материалы");
        viewPagerAdapter.addFragment(subjectTimelineFragment, "Лента");
        viewPager.setAdapter(viewPagerAdapter);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FacadeCommon.checkPermissions(this);
        }
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            if(position==1){
                subjectMaterialsFragment.onFragmentVisible(false);
            }else if(position==2){
                subjectTimelineFragment.onFragmentVisible();
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    };

    @Override
    public void onBackPressed() {
        if(currentPage==1 && subjectMaterialsFragment.getCurrentPage()>0){
            subjectMaterialsFragment.onBackPress();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, getString(R.string.storage_access_denied), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
