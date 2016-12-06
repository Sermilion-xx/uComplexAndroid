package org.ucomplex.ucomplex.Modules.Subject;

import android.os.Bundle;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class SubjectActivity extends BaseRecyclerActivity {

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

        Bundle args = new Bundle();
        args.putInt("subjId", getIntent().getIntExtra("subjId", -1));
        mFragment.setArguments(args);
    }
}
