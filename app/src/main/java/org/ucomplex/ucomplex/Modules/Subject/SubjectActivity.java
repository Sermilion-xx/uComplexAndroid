package org.ucomplex.ucomplex.Modules.Subject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.R;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class SubjectActivity extends BaseActivity {

    private static final String EXTRA_KEY_COURSE_NAME = "courseName";

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
        super.onCreate(savedInstanceState);
        DaggerApplication application = (DaggerApplication) getAppContext();
        setContentViewWithNavDrawer(R.layout.activity_subject);
        Intent intent = getIntent();
        setupToolbar(intent.getStringExtra(EXTRA_KEY_COURSE_NAME));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SubjectDetailsFragment subjectDetailsFragment = SubjectDetailsFragment.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, intent.getIntExtra(SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID, -1));
        bundle.putString(AUTH_STRING, application.getAuthString());
        subjectDetailsFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(subjectDetailsFragment, "Дисциплина");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
