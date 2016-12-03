package org.ucomplex.ucomplex.BaseComponents;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import org.javatuples.Pair;
import org.ucomplex.ucomplex.Modules.FragmentFactory;
import org.ucomplex.ucomplex.Interfaces.IViewExtensions;
import org.ucomplex.ucomplex.Interfaces.IFragment;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.Repository;
import org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.NavDrawer.DrawerAdapter;
import org.ucomplex.ucomplex.NavDrawer.DrawerListItem;
import org.ucomplex.ucomplex.NavDrawer.FacadeDrawer;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.StateMaintainer;

import java.io.IOException;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements IViewExtensions{

    protected DrawerLayout          mDrawer;
    protected ActionBarDrawerToggle mActionBarDrawerToggle;
    protected Toolbar               mToolbar;
    protected String[]              mDrawerTitles;
    protected int[]                 mDrawerIcons;

    protected StateMaintainer       mStateMaintainer;
    protected Presenter             mPresenter;
    protected Model                 mModel;
    protected Repository            mRepository;
    protected View mProgressView;

    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setupDrawer(){
        setupDrawerView(setupDrawerListItems());
    }

    private ArrayList<DrawerListItem> setupDrawerListItems(){
        UserInterface user = mPresenter.getUser();
        setupDrawerItemListForUser(user);
        Bitmap profileBitmap = null;
        try {
            profileBitmap = FacadeMedia.getThumbnail(user.getBitmapUri(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DrawerListItem headerItem = new DrawerListItem(profileBitmap, user.getName().split(" ")[1],
                FacadeCommon.getStringUserType(this, user.getType()), user.getId());
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

    public void setupMVP(ViewToPresenter viewToPresenter, Class<?> type, Object ...data){
        mStateMaintainer = new StateMaintainer(getFragmentManager(), type.getName());
        if (mStateMaintainer.firstTimeIn()) {
            mPresenter.setView(viewToPresenter);
            if(data.length>0)
                mModel.setData(data[0]);
            mRepository.setContext(mPresenter.getActivityContext());
            mModel.setRepository(mRepository);
            mPresenter.setModel(mModel);
            mStateMaintainer.put(mModel);
            mStateMaintainer.put(type.getName(), mPresenter);
        } else {
            mPresenter = mStateMaintainer.get(type.getName());
            mPresenter.setView(viewToPresenter);
        }
    }

    protected IFragment setupFragment(FragmentManager fragmentManager, String name, Object...params) {
        IFragment fragment;
        if(fragmentManager.findFragmentByTag(name) == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragment = FragmentFactory.getFragmentWithName(name, params);
            fragmentTransaction.add(R.id.container, (Fragment) fragment, name);
            fragmentTransaction.commit();
        }else{
            fragment = (IFragment) fragmentManager.findFragmentByTag(name);
            if(params.length>0){
                fragment.setParams(params);
            }
        }
        return fragment;
    }

    protected void setModelData(Object data){
        mModel.setData(data);
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
        mDrawer.setDrawerListener(mActionBarDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        mDrawer.closeDrawers();
                        break;
                }
                return false;
            }
        });
        mDrawer.setDrawerListener(mActionBarDrawerToggle);
        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

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

    @Override @SuppressWarnings("unchecked")
    public  <T extends View> T find(int id){
        return (T) findViewById(id);
    }

}



