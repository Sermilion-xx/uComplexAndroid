package org.ucomplex.ucomplex.BaseComponents;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import net.oneread.aghanim.components.base.BaseRecyclerFragment;
import net.oneread.aghanim.components.base.MVPBaseActivity;
import net.oneread.aghanim.components.utility.IFragment;
import net.oneread.aghanim.mvp.basemvp.MVPPresenter;

import org.javatuples.Pair;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.CommonDependencies.FragmentFactory;
import org.ucomplex.ucomplex.NavDrawer.DrawerAdapter;
import org.ucomplex.ucomplex.NavDrawer.DrawerListItem;
import org.ucomplex.ucomplex.NavDrawer.FacadeDrawer;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

public class BaseActivity extends MVPBaseActivity{

    protected DrawerLayout          mDrawer;
    protected ActionBarDrawerToggle mActionBarDrawerToggle;
    protected Toolbar               mToolbar;
    protected String[]              mDrawerTitles;
    protected int[]                 mDrawerIcons;
    protected UserInterface         mUser;
    protected IFragment             mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = ((DaggerApplication)getApplicationContext()).getSharedUser();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setupDrawer(){
        setupDrawerView(setupDrawerListItems());
    }

    private ArrayList<DrawerListItem> setupDrawerListItems(){
        setupDrawerItemListForUser(mUser);
        DrawerListItem headerItem = new DrawerListItem(mUser.getCode(), mUser.getName().split(" ")[1],
                FacadeCommon.getStringUserType(this, mUser.getType()), mUser.getPerson());
        return setupDrawerArrayList(headerItem, mDrawerIcons, mDrawerTitles);
    }

    protected void setContentViewWithNavDrawer(int layout) {
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layout, contentFrameLayout);
    }

    protected void setupToolbar(String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentManager manager = getFragmentManager();
        manager.putFragment(outState, mFragment.getClass().getName(), (Fragment) mFragment);
        super.onSaveInstanceState(outState);
    }

    protected IFragment setupRecyclerFragment(Bundle inState, String name, MVPPresenter presenter, int fragmentLayout, int recyclerId) {
        IFragment fragment;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (inState != null) {
            fragment = (BaseRecyclerFragment) manager.getFragment(inState, name);
        } else {
            fragment = FragmentFactory.getFragmentWithName(name, presenter, fragmentLayout, recyclerId);
            transaction.add(R.id.container, (Fragment) fragment , name);
            transaction.commit();
        }
        return fragment;
    }

    protected String getResourceString(int id) {
        return getResources().getString(id);
    }

    //=================Setup methods================//
    private ArrayList<DrawerListItem> setupDrawerArrayList(DrawerListItem header, int[] icons, String[] titles) {
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(header);
        for (int i = 0; i < icons.length; i++) {
            drawerListItems.add(new DrawerListItem(icons[i], titles[i]));
        }
        return drawerListItems;
    }

    private void setupDrawerItemListForUser(UserInterface user) {
        Pair<int[], String[]> iconsAndItems;
        if (user.getType() == 0) {
            iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser0();
        }else if(user.getType() == 4){
            iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser4();
        }else{
            iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser0();
        }
        mDrawerIcons = iconsAndItems.getValue0();
        mDrawerTitles = iconsAndItems.getValue1();
    }

    private void setupDrawerView(ArrayList<DrawerListItem> drawerListItems) {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerAdapter mDrawerAdapter = new DrawerAdapter(drawerListItems, this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mDrawerAdapter);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_closed);
        mDrawer.addDrawerListener(mActionBarDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    mDrawer.closeDrawers();
                    break;
            }
            return false;
        });
        mDrawer.addDrawerListener(mActionBarDrawerToggle);
        mDrawer.post(() -> mActionBarDrawerToggle.syncState());

    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
//            refresh();
            return true;
        }else if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getActivityContext() {
        return this;
    }
}



