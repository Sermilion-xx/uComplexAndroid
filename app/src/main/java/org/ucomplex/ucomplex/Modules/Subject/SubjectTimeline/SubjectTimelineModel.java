package org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel.EXTRA_KEY_SUBJECT_ID;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectTimelineModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    private static final String EXTRA_KEY_GCOURSE = "gcourse";
    public static final String EXTRA_KEY_GCOURSE_START = "start";

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth = bundle[0].getString(AUTH_STRING);
        HashMap<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_SUBJECT_ID, Integer.toString(bundle[0].getInt(EXTRA_KEY_SUBJECT_ID)));

        if(bundle[0].containsKey(EXTRA_KEY_GCOURSE_START)){
            params.put(EXTRA_KEY_GCOURSE_START, bundle[0].getString(EXTRA_KEY_GCOURSE_START));
        }

        HttpFactory.getInstance().httpVolley(HttpFactory.CALENDAR_BELT_URL,
                encodedAuth,
                mContext,
                params,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        List<IRecyclerItem> newItems = processJson(s);
                        mvpCallback.onSuccess(newItems);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });

    }

    @Override
    public List<IRecyclerItem> processJson(String s) {
        JSONObject courseJson;
        List<IRecyclerItem> feedItems = new ArrayList<>();
        try {
            if (s != null) {
                courseJson = new JSONObject(s);
                HashMap<String, String> teachersMap = (HashMap<String, String>) FacadeCommon.parseJsonKV(courseJson.getJSONObject("teachers"));
                HashMap<String, String> coursesMap = (HashMap<String, String>) FacadeCommon.parseJsonKV(courseJson.getJSONObject("courses"));
                JSONArray marksArray = courseJson.getJSONArray("marks");

                for (int i = 0; i < marksArray.length(); i++) {
                    JSONObject marksItemJson = marksArray.getJSONObject(i);
                    int mark = marksItemJson.getInt("mark");
                    Date date = new java.util.Date((long) marksItemJson.getInt("time") * 1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("Ru"));
                    String time = sdf.format(date);
                    String teacherName = teachersMap.get(marksItemJson.getString("teacher"));
                    String courseName = coursesMap.get(marksItemJson.getString("course"));
                    String name;
                    name = teacherName;
                    SubjectTimelineItem subjectTimelineItem = new SubjectTimelineItem();
                    subjectTimelineItem.setmMark(mark);
                    subjectTimelineItem.setmName(name);
                    subjectTimelineItem.setmTime(time.split(" ")[0]);
                    subjectTimelineItem.setType(marksItemJson.getInt("type"));
                    feedItems.add(subjectTimelineItem);
                }
            }
            return feedItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedItems;
    }
}
