package org.ucomplex.ucomplex.Modules.Users;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Users.UsersBlackList.UsersBlackListFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersBlackList.UsersBlackListModel;
import org.ucomplex.ucomplex.Modules.Users.UsersFriends.UsersFriendsFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersFriends.UsersFriendsModel;
import org.ucomplex.ucomplex.Modules.Users.UsersGroup.UsersGroupFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersGroup.UsersGroupModel;
import org.ucomplex.ucomplex.Modules.Users.UsersLecturers.UsersLecturersFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersLecturers.UsersLecturersModel;
import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlineFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlineModel;
import org.ucomplex.ucomplex.R;

import java.util.List;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersActivity extends BaseActivity {

    UsersOnlineFragment mOnlineFragment;
    UsersFriendsFragment mFriendsFragment;
    UsersGroupFragment mGroupFragment;
    UsersLecturersFragment mLecturersFragment;
    UsersBlackListFragment mBlackListFragment;

    @Inject
    UsersOnlineModel mUserOnlineModel;
    @Inject
    UsersFriendsModel mUserFriendsModel;
    @Inject
    UsersGroupModel mUserGroupModel;
    @Inject
    UsersLecturersModel mUserLecturersModel;
    @Inject
    UsersBlackListModel mUserBlackListModel;
    @Inject
    MVPPresenterRecycler<String, List<IRecyclerItem>> mPresenter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, "mOnlineFragment", mOnlineFragment);
        getFragmentManager().putFragment(outState, "mFriendsFragment", mFriendsFragment);
        getFragmentManager().putFragment(outState, "mGroupFragment", mGroupFragment);
        getFragmentManager().putFragment(outState, "mLecturersFragment", mLecturersFragment);
        getFragmentManager().putFragment(outState, "mBlackListFragment", mBlackListFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication application = (DaggerApplication) getAppContext();
        application.getUsersDiComponent().inject(this);
        setContentViewWithNavDrawer(R.layout.activity_users);
        setupDrawer();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null) {
            restoreFragments(savedInstanceState);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(AUTH_STRING, application.getAuthString());
            initFragments(bundle);
            viewPagerAdapter.addFragment(mOnlineFragment, getString(R.string.online));
//            viewPagerAdapter.addFragment(mFriendsFragment, getString(R.string.friends));
//            viewPagerAdapter.addFragment(mGroupFragment, getString(R.string.group));
//            viewPagerAdapter.addFragment(mLecturersFragment, getString(R.string.lecturers));
//            viewPagerAdapter.addFragment(mLecturersFragment, getString(R.string.blacklist));
            viewPager.setAdapter(viewPagerAdapter);
        }
    }

    private void initFragments(Bundle bundle) {
        mOnlineFragment    = UsersOnlineFragment.getInstance(this);
        mFriendsFragment   =  UsersFriendsFragment.getInstance(this);
        mGroupFragment     =  UsersGroupFragment.getInstance(this);
        mLecturersFragment =  UsersLecturersFragment.getInstance(this);
        mBlackListFragment =  UsersBlackListFragment.getInstance(this);

        mOnlineFragment.setArguments(bundle);
        mFriendsFragment.setArguments(bundle);
        mGroupFragment.setArguments(bundle);
        mLecturersFragment.setArguments(bundle);
        mBlackListFragment.setArguments(bundle);
    }

    private void restoreFragments(Bundle savedInstanceState) {
        mOnlineFragment = (UsersOnlineFragment) getFragmentManager().getFragment(savedInstanceState, "mOnlineFragment");
        mOnlineFragment.setContext(this);
        mFriendsFragment = (UsersFriendsFragment) getFragmentManager().getFragment(savedInstanceState, "mFriendsFragment");
        mFriendsFragment.setContext(this);
        mGroupFragment = (UsersGroupFragment) getFragmentManager().getFragment(savedInstanceState, "mGroupFragment");
        mGroupFragment.setContext(this);
        mLecturersFragment = (UsersLecturersFragment) getFragmentManager().getFragment(savedInstanceState, "mLecturersFragment");
        mLecturersFragment.setContext(this);
        mBlackListFragment = (UsersBlackListFragment) getFragmentManager().getFragment(savedInstanceState, "mBlackListFragment");
        mBlackListFragment.setContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("Поиск");
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void performSearch(final String searchTerm) {

    }


}
