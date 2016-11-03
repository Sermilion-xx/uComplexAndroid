package org.ucomplex.ucomplex.Activities;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
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

import org.ucomplex.ucomplex.Adapters.DrawerAdapter;
import org.ucomplex.ucomplex.Model.DrawerListItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    protected static String INTENT_PARAM_PREFIX = "com.ucomplex.ucomplex.";
    protected DrawerLayout mDrawer;
    protected ActionBarDrawerToggle mDrawerToggle;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    protected String[] drawerTitles;
    protected int[] drawerIcons;

    protected int[] getDrawerIconsUser0(){
        return new int[]{
                R.drawable.ic_menu_events,
                R.drawable.ic_menu_users,
                R.drawable.ic_menu_messages,
                R.drawable.ic_menu_settings,
                R.drawable.ic_menu_exit};
    }

    protected String[] getDrawerTitleUser0(){
        return new String[] {
                getResourceString(R.drawable.ic_menu_events),
                getResourceString(R.drawable.ic_menu_users),
                getResourceString(R.string.materials),
                getResourceString(R.string.settings),
                getResourceString(R.string.logout)};
    }

    private ArrayList<DrawerListItem> prepareDrawerItems(DrawerListItem header,
                                                         int[] icons,
                                                         String[] titles){
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(header);
        for(int i = 0; i<icons.length; i++){
            drawerListItems.add(new DrawerListItem(icons[i], titles[i]));
        }
        return drawerListItems;
    }

    private void setupDrawerTitlesAndEvents(){
        drawerTitles = new String[] {
                getResourceString(R.string.events),
                getResourceString(R.string.disciplines),
                getResourceString(R.string.materials),
                getResourceString(R.string.users),
                getResourceString(R.string.messages),
                getResourceString(R.string.calendar),
                getResourceString(R.string.settings),
                getResourceString(R.string.logout)};
        drawerIcons = new int[] {
                R.drawable.ic_menu_events,
                R.drawable.ic_menu_subjects,
                R.drawable.ic_menu_materials,
                R.drawable.ic_menu_users,
                R.drawable.ic_menu_messages,
                R.drawable.ic_menu_timetable,
                R.drawable.ic_menu_settings,
                R.drawable.ic_menu_exit
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupDrawerTitlesAndEvents();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        User user = FacadePreferences.getUserDataFromPref(this);
        if(user == null){
            user = new User();
            user.setName("temp");
            user.setRole(0);
            user.setType(0);
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_as_friend);
            user.setPhotoBitmap(icon);
        }
        DrawerListItem headerItem = new DrawerListItem(user.getPhotoBitmap(),user.getName(),
                FacadeCommon.getStringUserType(this, user.getType()));
        ArrayList<DrawerListItem> drawerListItems = prepareDrawerItems(headerItem, drawerIcons, drawerTitles);
        DrawerAdapter mDrawerAdapter = new DrawerAdapter(drawerListItems);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mDrawerAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_closed);
        mDrawer.setDrawerListener(actionBarDrawerToggle);

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


}



