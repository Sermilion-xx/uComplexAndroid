package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.os.Bundle;
import android.widget.FrameLayout;


import net.oneread.aghanim.components.base.BaseRecyclerFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.recyclermvp.PresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.ViewRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.Modules.Events.EventViewHolder;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsPresenter;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class SubjectsListActivity extends BaseRecyclerActivity implements ViewRecycler {

    @Inject
    public void setPresenter(SubjectsListPresenter presenter) {
        super.mPresenter = presenter;
        ((PresenterRecycler)super.mPresenter).setItemLayout(R.layout.list_item_subject);
    }

    @Inject
    public void setModel(SubjectsListModel model) {
        super.mModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getSubjectsListDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_subjects_list);
        setupToolbar(getResourceString(R.string.subjects));

        //mvp
        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication)getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putInt(Constants.EXTRA_KEYT_USER_TYPE, application.getSharedUser().getType());
        mFragment = setupRecyclerFragment(savedInstanceState,
                BaseRecyclerFragment.class.getName(),
                mPresenter,
                R.layout.fragment_recycler,
                R.id.recyclerView);
        mFragment.setProgressViewId(R.id.progressBar);
        mFragment.setOnFragmentLoadedListener(views -> {
            setupMVP(this, BaseActivity.class, bundle);
            setupDrawer();
            initPresenter();
        });
    }

    private void initPresenter() {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            int position = clickListener.getPosition();
            if(position == ((PresenterRecycler)mPresenter).getItemCount()-1){
                Bundle bundle = new Bundle();

            }
        };
        clickListener.setStrategy(strategy);
        ((PresenterRecycler) mPresenter).setBaseOnClickListener(clickListener);
        ((PresenterRecycler)mPresenter).setCreator((view, i) -> new SubjectListViewHolder(view));
        ((PresenterRecycler)mPresenter).setItemLayout(R.layout.list_item_subject);
    }

}
