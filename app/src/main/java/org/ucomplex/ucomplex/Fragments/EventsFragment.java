package org.ucomplex.ucomplex.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import org.ucomplex.ucomplex.Activities.EventsActivity;
import org.ucomplex.ucomplex.AsyncTasks.EventsTask;
import org.ucomplex.ucomplex.Model.Events.EventRowItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;

import java.util.ArrayList;

/**
 * Created by Sermilion on 02/11/2016.
 */

public class EventsFragment extends BaseFragment {

    private static final String EVENTS_KEY = "eventsArray";

    private ArrayList<EventRowItem> eventsArray = new ArrayList<>();
    private EventsTask mEventsTasks;
    private ProgressDialog progressDialog;


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable(EVENTS_KEY, eventsArray);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(EVENTS_KEY)) {
            eventsArray = (ArrayList<EventRowItem>) savedInstanceState.getSerializable("eventsArray");
        }
    }
}
