package org.ucomplex.ucomplex.Modules.Subject;

import android.app.Fragment;
import android.os.Bundle;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class SubjectActivity extends BaseRecyclerActivity {

    public static final String KEY_SUBJECT_ID = "subjId";

    @Inject
    public void setPresenter(SubjectPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(SubjectModel model) {
        super.mModel = model;
    }

    @Inject
    public void setRepository(SubjectRepository repository) {
        super.mRepository = repository;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication) getApplication()).getSubjectDiComponent().inject(this);
        setupToolbar(getResourceString(R.string.subject));
        setContentViewWithNavDrawer(R.layout.activity_subject);

        if(((Fragment)mFragment).getArguments()==null){
            Bundle args = new Bundle();
            args.putInt(KEY_SUBJECT_ID, getIntent().getIntExtra(KEY_SUBJECT_ID, -1));
            mFragment.setArguments(args);
        }
    }
}
