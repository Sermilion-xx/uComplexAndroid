package org.ucomplex.ucomplex.Modules.Users.UsersOnline;

import android.app.Activity;
import android.os.Bundle;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsFragment;
import org.ucomplex.ucomplex.R;

import java.util.List;

import javax.inject.Inject;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersOnlineFragment extends MVPViewBaseFragment<String, List<IRecyclerItem>> {

    @Inject
    MVPModelRecycler<String, List<IRecyclerItem>> mModel;

    @Inject @Override
    public void setPresenter(MVPPresenterRecycler<String, List<IRecyclerItem>> presenter) {
        presenter.setView(this);
        super.setPresenter(presenter);
    }

    public static UsersOnlineFragment getInstance(Activity mContext) {
        UsersOnlineFragment fragment = new UsersOnlineFragment();
        fragment.setContext(mContext);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.mFragmentLayout = R.layout.fragment_recycler;
        this.mRecyclerViewId = R.id.recyclerView;
        this.mProgressViewId = R.id.progressBar;
        ((DaggerApplication)mContext.getApplication()).getUsersOnlineDiComponent().inject(this);
        this.setOnFragmentLoadedListener(views -> mPresenter.setModel(mModel, getArguments()));
    }
}
