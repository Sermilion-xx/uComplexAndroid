package org.ucomplex.ucomplex;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Interfaces.ProgressTracker;
import org.ucomplex.ucomplex.Model.Events.EventRowItem;

import java.util.ArrayList;

public class EventsActivity extends BaseActivity implements OnTaskCompleteListener {

    private ArrayList<EventRowItem> eventsArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onTaskComplete(AsyncTask task, Object... o) {

    }


}
