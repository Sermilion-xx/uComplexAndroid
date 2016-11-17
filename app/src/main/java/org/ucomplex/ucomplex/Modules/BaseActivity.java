package org.ucomplex.ucomplex.Modules;

import android.annotation.TargetApi;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.WindowManager;

import org.javatuples.Pair;
import org.ucomplex.ucomplex.Interfaces.MVP.Model;
import org.ucomplex.ucomplex.Interfaces.MVP.Presenter;
import org.ucomplex.ucomplex.Interfaces.MVP.Repository;
import org.ucomplex.ucomplex.Interfaces.MVP.ViewToPresenter;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.NavDrawer.FacadeDrawer;
import org.ucomplex.ucomplex.NavDrawer.DrawerAdapter;
import org.ucomplex.ucomplex.NavDrawer.DrawerListItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadeMedia;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.StateMaintainer;

import java.io.IOException;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout          mDrawer;
    protected ActionBarDrawerToggle mActionBarDrawerToggle;
    protected Toolbar               mToolbar;
    protected String[]              mDrawerTitles;
    protected int[]                 mDrawerIcons;

    protected StateMaintainer mStateMaintainer;
    protected Presenter mPresenter;
    protected Model mModel;
    protected Repository mRepository;
    protected String mTitle;
    private boolean noDrawer;
    protected UserInterface mUser;


    public void setmUser(UserInterface mUser) {
        this.mUser = mUser;
    }

    public void setNoDrawer(boolean loginActivity) {
        noDrawer = loginActivity;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setupToolbar(mTitle);
        if(!noDrawer)
            setupDrawerView(setupDrawerList());
    }

    private ArrayList<DrawerListItem> setupDrawerList(){
        setupDrawerItemListForUser(mUser);
        Bitmap profileBitmap = null;
        try {
            profileBitmap = FacadeMedia.getThumbnail(mUser.getBitmapUri(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DrawerListItem headerItem = new DrawerListItem(profileBitmap, mUser.getName().split(" ")[1],
                FacadeCommon.getStringUserType(this, mUser.getType()), mUser.getId());
        return setupDrawerArrayList(headerItem, mDrawerIcons, mDrawerTitles);
    }

    protected void setupToolbar(String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    protected void setupMVP(ViewToPresenter viewToPresenter, Class<?> type){
        mStateMaintainer = new StateMaintainer(getFragmentManager(), type.getName());
        if (mStateMaintainer.firstTimeIn()) {
            mPresenter.setView(viewToPresenter);
            mModel.setPresenter(mPresenter);
            mRepository.setContext(mPresenter.getAppContext());
            mModel.setRepository(mRepository);
            mPresenter.setModel(mModel);
            mStateMaintainer.put(mPresenter);
            mStateMaintainer.put(mModel);
            mStateMaintainer.put(type.getName(), mPresenter);
        } else {
            mPresenter = mStateMaintainer.get(type.getName());
            mPresenter.setView(viewToPresenter);
        }
    }

    protected void setModelData(Object data){
        mModel.setData(data);
    }

    protected void setStatusBarTranslucent(Boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (makeTranslucent) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getFragmentManager().beginTransaction().add(containerViewId, fragment, fragmentTag).disallowAddToBackStack().commit();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getFragmentManager().beginTransaction().replace(containerViewId, fragment, fragmentTag).addToBackStack(backStackStateName).commit();
    }

    @TargetApi(19)
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (makeTranslucent) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
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
        Pair<int[], String[]> iconsAndItems = null;
        if (user.getType() == 0) {
            iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser0();
        }else if(user.getType() == 4){
            iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser4();
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
        mActionBarDrawerToggle.syncState();
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

}



