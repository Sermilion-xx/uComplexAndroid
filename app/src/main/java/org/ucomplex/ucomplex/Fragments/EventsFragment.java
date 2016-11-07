package org.ucomplex.ucomplex.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import org.ucomplex.ucomplex.Model.Events.EventItem;

import java.util.ArrayList;

/**
 * Created by Sermilion on 02/11/2016.
 */

public class EventsFragment extends BaseFragment {

    private static final String EVENTS_KEY = "eventsArray";

    private ArrayList<EventItem> eventsArray = new ArrayList<>();
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
            eventsArray = (ArrayList<EventItem>) savedInstanceState.getSerializable("eventsArray");
        }
    }
}
