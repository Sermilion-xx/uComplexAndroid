package org.ucomplex.ucomplex.Modules.Users;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.astuetz.PagerSlidingTabStrip;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.ViewPagerAdapter;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersBlackListFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersFragmentFactory;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersFriendsFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersGroupFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersLecturersFragment;
import org.ucomplex.ucomplex.Modules.Users.UsersFragments.UsersOnlineFragment;
import org.ucomplex.ucomplex.R;

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

    public static final String USER_TYPE = "type";
    private static final String TAG_ONLINE_FRAGMENT = "mOnlineFragment";
    private static final String TAG_FRIENDS_FRAGMENT = "mFriendsFragment";
    private static final String TAG_GROUP_FRAGMENT = "mGroupFragment";
    private static final String TAG_LECTURERS_FRAGMENT = "mLecturersFragment";
    private static final String TAG_BLACKLIST_FRAGMENT = "mBlackListFragment";
    UsersOnlineFragment mOnlineFragment;
    UsersFriendsFragment mFriendsFragment;
    UsersGroupFragment mGroupFragment;
    UsersLecturersFragment mLecturersFragment;
    UsersBlackListFragment mBlackListFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mOnlineFragment.isAdded()) {
            getFragmentManager().putFragment(outState, TAG_ONLINE_FRAGMENT, mOnlineFragment);
        }
        if (mFriendsFragment.isAdded()) {
            getFragmentManager().putFragment(outState, TAG_FRIENDS_FRAGMENT, mFriendsFragment);
        }
        if (mGroupFragment.isAdded()) {
            getFragmentManager().putFragment(outState, TAG_GROUP_FRAGMENT, mGroupFragment);
        }
        if (mLecturersFragment.isAdded()) {
            getFragmentManager().putFragment(outState, TAG_LECTURERS_FRAGMENT, mLecturersFragment);
        }
        if (mBlackListFragment.isAdded()) {
            getFragmentManager().putFragment(outState, TAG_BLACKLIST_FRAGMENT, mBlackListFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApplication application = (DaggerApplication) getAppContext();
        setContentViewWithNavDrawer(R.layout.activity_users);
        setupToolbar(getString(R.string.users));
        setupDrawer();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        PagerSlidingTabStrip tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        if (savedInstanceState != null) {
            restoreFragments(savedInstanceState);
        } else {
            initFragments(application.getAuthString());
        }
        viewPagerAdapter.addFragment(mOnlineFragment, getString(R.string.online));
        viewPagerAdapter.addFragment(mFriendsFragment, getString(R.string.friends));
        viewPagerAdapter.addFragment(mGroupFragment, getString(R.string.group));
        viewPagerAdapter.addFragment(mLecturersFragment, getString(R.string.lecturers));
        viewPagerAdapter.addFragment(mBlackListFragment, getString(R.string.blacklist));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setViewPager(viewPager);
    }

    private void initFragments(String authString) {
        mOnlineFragment    = UsersOnlineFragment.getInstance(this);
        mFriendsFragment   = UsersFriendsFragment.getInstance(this);
        mGroupFragment     = UsersGroupFragment.getInstance(this);
        mLecturersFragment = UsersLecturersFragment.getInstance(this);
        mBlackListFragment = UsersBlackListFragment.getInstance(this);

        Bundle bundle = initFragmentArgument(0, authString);
        mOnlineFragment.setArguments(bundle);
        Bundle bundle1 = initFragmentArgument(1, authString);
        mFriendsFragment.setArguments(bundle1);
        Bundle bundle2 = initFragmentArgument(2, authString);
        mGroupFragment.setArguments(bundle2);
        Bundle bundle3 = initFragmentArgument(3, authString);
        mLecturersFragment.setArguments(bundle3);
        Bundle bundle4 = initFragmentArgument(4, authString);
        mBlackListFragment.setArguments(bundle4);
    }

    private Bundle initFragmentArgument(int type, String authString){
        Bundle bundle = new Bundle();
        bundle.putString(AUTH_STRING, authString);
        bundle.putInt(USER_TYPE, type);
        return bundle;
    }

    private MVPViewBaseFragment restoreFragment(int type, String tag, Bundle savedInstanceState, Activity context){
        String authString = ((DaggerApplication) getAppContext()).getAuthString();
        MVPViewBaseFragment fragment = (MVPViewBaseFragment) getFragmentManager().getFragment(savedInstanceState, tag);
        if(fragment == null){
            Bundle bundle = initFragmentArgument(type, authString);
            return UsersFragmentFactory.getUsersFragment(type, context, bundle);
        }
        return fragment;
    }

    private void restoreFragments(Bundle savedInstanceState) {
        mOnlineFragment    = (UsersOnlineFragment) restoreFragment(0, TAG_ONLINE_FRAGMENT, savedInstanceState, this);
        mFriendsFragment   = (UsersFriendsFragment) restoreFragment(1, TAG_FRIENDS_FRAGMENT, savedInstanceState, this);
        mGroupFragment     = (UsersGroupFragment) restoreFragment(2, TAG_GROUP_FRAGMENT, savedInstanceState, this);
        mLecturersFragment = (UsersLecturersFragment) restoreFragment(3, TAG_LECTURERS_FRAGMENT, savedInstanceState, this);
        mBlackListFragment = (UsersBlackListFragment) restoreFragment(4, TAG_BLACKLIST_FRAGMENT, savedInstanceState, this);
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
