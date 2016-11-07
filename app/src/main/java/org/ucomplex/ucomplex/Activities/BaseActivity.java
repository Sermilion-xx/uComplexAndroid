package org.ucomplex.ucomplex.Activities;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import org.ucomplex.ucomplex.NavDrawer.FacadeDrawer;
import org.ucomplex.ucomplex.NavDrawer.DrawerAdapter;
import org.ucomplex.ucomplex.NavDrawer.DrawerListItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    protected static String INTENT_PARAM_PREFIX = "com.ucomplex.ucomplex.";
    protected DrawerLayout          mDrawer;
    protected ActionBarDrawerToggle mActionBarDrawerToggle;
    protected Toolbar               mToolbar;
    protected String[]              mDrawerTitles;
    protected int[]                 mDrawerIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        setUpToolbar("uComplex");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        User user = FacadePreferences.getUserDataFromPref(this);
        if(user == null){
            user = setupTempUser();
        }
        setupDrawerItemListForUser(user);
        DrawerListItem headerItem = new DrawerListItem(user.getPhotoBitmap(), user.getName(),
                FacadeCommon.getStringUserType(this, user.getType()));
        ArrayList<DrawerListItem> drawerListItems = setupDrawerArrayList(headerItem, mDrawerIcons, mDrawerTitles);
        setupDrawerView(drawerListItems);
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

    @SuppressWarnings("unchecked")
    protected <T extends View> T find(int id) {
        return (T) findViewById(id);
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

    private void setupDrawerItemListForUser(User user) {
        if (FacadeCommon.USER_TYPE == -1) {
            FacadeCommon.USER_TYPE = user.getType();
        }
        if (FacadeCommon.USER_TYPE == 0) {
            Pair<int[], String[]> iconsAndItems = FacadeDrawer.getInstance(this).getDrawerItemsUser0();
            mDrawerIcons = iconsAndItems.getValue0();
            mDrawerTitles = iconsAndItems.getValue1();
        }
    }

    public void setUpToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    private void setupDrawerView(ArrayList<DrawerListItem> drawerListItems) {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerAdapter mDrawerAdapter = new DrawerAdapter(drawerListItems);
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
    }

    private User setupTempUser() {
        User user = new User();
        user.setName("Marshal Maters");
        user.setRole(0);
        user.setType(0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_user);
        user.setPhotoBitmap(icon);
        return user;
    }

}



