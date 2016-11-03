package org.ucomplex.ucomplex.Activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.ucomplex.ucomplex.Fragments.EventsFragment;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

public class EventsActivity extends BaseActivity implements OnTaskCompleteListener {

    MediaPlayer mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View contentView = inflater.inflate(R.layout.activity_main, null, false);
//        mDrawer.addView(contentView, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("События");
        setSupportActionBar(toolbar);
        mAlert = MediaPlayer.create(this, R.raw.alert);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        User user = FacadePreferences.getUserDataFromPref(this);
        if(user == null){
            user = new User();
            user.setName("temp");
            user.setRole(0);
            user.setType(0);
        }
        setupDrawer(user);
        addFragment(R.id.container, new EventsFragment(), "EventsFragment");
    }

    private void setupDrawer(User user){
        if(FacadeCommon.USER_TYPE == -1){
            FacadeCommon.USER_TYPE = user.getType();
        }
        if(FacadeCommon.USER_TYPE == 0){
            drawerIcons = getDrawerIconsUser0();
            drawerTitles = getDrawerTitleUser0();
        }
    }

    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {

    }



}
