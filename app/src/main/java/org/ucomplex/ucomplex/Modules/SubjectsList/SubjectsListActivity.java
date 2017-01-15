package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.os.Bundle;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
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
    protected void onCreate(Bundle savedInstanceState) {
        mApplication = ((DaggerApplication) getApplication());
        mApplication.getSubjectsListDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_subjects_list);
        setupToolbar(getResourceString(R.string.subjects));

        //mvp
        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putInt(Constants.EXTRA_KEYT_USER_TYPE, application.getSharedUser().getType());
        mFragment = setupRecyclerFragment(savedInstanceState,
                MVPBaseRecyclerFragment.class.getName(),
                mPresenter,
                R.layout.fragment_recycler,
                R.id.recyclerView);
        mFragment.hasDivider(true);
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
            SubjectListItem item = (SubjectListItem) ((MVPModelRecycler) mModel).getItem(position);
            if (mApplication.getSharedUser().getType() == USER_TYPE_STUDENT) {
                SubjectActivity.receiveIntent(getActivityContext(), item.getCourseId(), item.getCourseName());
            } else if (mApplication.getSharedUser().getType() == USER_TYPE_TEACHER) {
//                    Intent intent = new Intent(getBaseContext(), null);
//                    Bundle extras = new Bundle();
//                    extras.putString("gcourse", String.valueOf(mItems.get(position).getValue2()));
//                    intent.putExtras(extras);
//                    startActivity(intent);
            }
        };
        clickListener.setStrategy(strategy);
        ((MVPPresenterRecycler) mPresenter).setBaseOnClickListener(clickListener);
        ((MVPPresenterRecycler) mPresenter).setCreator((view, i) -> new SubjectListViewHolder(view));
        ((MVPPresenterRecycler) mPresenter).setItemLayout(R.layout.list_item_subject);
    }

}
