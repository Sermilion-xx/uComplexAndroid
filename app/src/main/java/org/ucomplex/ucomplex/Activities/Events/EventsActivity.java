package org.ucomplex.ucomplex.Activities.Events;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.ucomplex.ucomplex.Activities.BaseActivity;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.R;

public class EventsActivity extends BaseActivity implements OnTaskCompleteListener {

    MediaPlayer mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        setUpToolbar("События");
        setSupportActionBar(mToolbar);
        mAlert = MediaPlayer.create(this, R.raw.alert);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
//        addFragment(R.id.container, null, "EventsFragment");
    }



    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {

    }



}
