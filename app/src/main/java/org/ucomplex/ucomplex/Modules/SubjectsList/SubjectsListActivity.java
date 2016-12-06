package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.os.Bundle;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.PresenterRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ViewToPresenterRecycler;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

public class SubjectsListActivity extends BaseRecyclerActivity implements ViewToPresenterRecycler {

    @Inject
    public void setPresenter(SubjectsListPresenter presenter) {
        super.mPresenter = presenter;
        ((PresenterRecycler)super.mPresenter).setItemLayout(R.layout.list_item_subject);
    }

    @Inject
    public void setModel(SubjectsListModel model) {
        super.mModel = model;
    }

    @Inject
    public void setRepository(SubjectsListRepository repository) {
        super.mRepository = repository;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DaggerApplication) getApplication()).getSubjectsListDiComponent().inject(this);
        setupToolbar(getResourceString(R.string.subjects));
        setContentViewWithNavDrawer(R.layout.activity_subjects);
        getFragment().addDivider();
    }

}
