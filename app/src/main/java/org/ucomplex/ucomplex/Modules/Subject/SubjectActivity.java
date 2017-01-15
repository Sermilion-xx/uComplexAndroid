package org.ucomplex.ucomplex.Modules.Subject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.R;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

public class SubjectActivity extends BaseRecyclerActivity {

    private static final String EXTRA_KEY_GCOURSE = "gcourse";
    private static final String EXTRA_KEY_COURSE_NAME = "courseName";

    public static void receiveIntent(Context context, int courseId, String courseName){
        Intent intent = new Intent(context, SubjectActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_KEY_GCOURSE, courseId);
        extras.putString(EXTRA_KEY_COURSE_NAME, courseName);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


    @Inject
    public void setPresenter(SubjectPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(SubjectModel model) {
        super.mModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getSubjectDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setupToolbar(getResourceString(R.string.subject));
        setContentViewWithNavDrawer(R.layout.activity_subject);
        //mvp
        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication)getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        mFragment = setupRecyclerFragment(savedInstanceState,
                MVPBaseRecyclerFragment.class.getName(),
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
    //mvp
    private void initPresenter() {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            int position = clickListener.getPosition();
            if(position == ((MVPPresenterRecycler)mPresenter).getItemCount()-1){

            }
        };
        clickListener.setStrategy(strategy);
        ((MVPPresenterRecycler) mPresenter).setBaseOnClickListener(clickListener);
        ((MVPPresenterRecycler)mPresenter).setCreator((view, i) -> new SubjectViewHolder(view,0));
        ((MVPPresenterRecycler)mPresenter).setItemLayout(R.layout.list_item_subject);
    }
}
