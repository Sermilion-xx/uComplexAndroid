package org.ucomplex.ucomplex.AsyncTasks;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;
import org.ucomplex.ucomplex.Interfaces.ProgressTracker;
import org.ucomplex.ucomplex.Model.Events.EventRowItem;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.util.ArrayList;

/**
 * Created by Sermilion on 01/11/2016.
 */

public class EventsTask extends AsyncTask<Integer, Void, ArrayList<EventRowItem>> implements ProgressTracker, DialogInterface.OnCancelListener{

    private String STUDENT_EVENTS_DOMAIN = "student?mobile=1/";
    private String TEACHER_EVENTS_DOMAIN = "teacher?mobile=1/";
    private String GENERIC_EVENTS_DOMAIN = "user/events?mobile=1/";

    private OnTaskCompleteListener mTaskCompleteListener = null;
    private String encodedAuth;

    public EventsTask(String encodedAuth){
        this.encodedAuth = encodedAuth;
    }

    public void setOnTaskCompleteListener(OnTaskCompleteListener listener){
        this.mTaskCompleteListener = listener;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }

    @Override
    protected ArrayList<EventRowItem> doInBackground(Integer... params) {
        String jsonData;
        String jsonBody = "";
        String urlString = "";
        if(params[0]==3){
            urlString = HttpFactory.BASE_URL+TEACHER_EVENTS_DOMAIN;
        }else if(params[0]==4){
            urlString = HttpFactory.BASE_URL+STUDENT_EVENTS_DOMAIN;
        }
        if(params.length>1){
            urlString = HttpFactory.BASE_URL+GENERIC_EVENTS_DOMAIN;
            jsonBody = "\"start\" : \""+String.valueOf(params[1]+"\"");
        }
        jsonData = HttpFactory.httpPost(urlString, encodedAuth, jsonBody);
        return null;
    }

    @Override
    public void onProgress(String message) {

    }

    @Override
    public void onComplete() {

    }
}
