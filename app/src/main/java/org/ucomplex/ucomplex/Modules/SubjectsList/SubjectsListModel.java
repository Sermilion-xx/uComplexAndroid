package org.ucomplex.ucomplex.Modules.SubjectsList;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.USER_TYPE_TEACHER;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    private final int[] assessmentType = {R.string.zachet, R.string.exam, R.string.samostoyatelnaya, R.string.empty};
    private int userType;



    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth = bundle[0].getString(AUTH_STRING);
        userType = bundle[0].getInt(Constants.EXTRA_KEYT_USER_TYPE);
        String url = HttpFactory.USER_SUBJECTS_USER_URL;
        if (userType == USER_TYPE_TEACHER) {
            url = HttpFactory.USER_SUBJECTS_TEACHER_URL;
        }
        HttpFactory.getInstance().httpVolley(url,
                encodedAuth,
                mContext,
                null,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        List<IRecyclerItem> newItems = processJson(s);
                        if (newItems.size() == 0) {
                            MVPUtility.addEmptyItem(newItems);
                        }
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
        List<IRecyclerItem> subjectsListArray = new ArrayList<>();
        JSONObject subjectsJson;
        try {
            subjectsJson = new JSONObject(s);
            if (userType == Constants.USER_TYPE_STUDENT) {
                JSONObject courses = subjectsJson.getJSONObject("courses");
                JSONObject coursesForms = subjectsJson.getJSONObject("courses_forms");
                JSONArray studentSubjectsList = subjectsJson.getJSONArray("studentSubjectsList");
                HashMap<String, String> hashCourses = (HashMap<String, String>) FacadeCommon.parseJsonKV(courses);
                HashMap<String, String> hashCoursesForms = (HashMap<String, String>) FacadeCommon.parseJsonKV(coursesForms);

                List<HashMap<String, String>> studentSubjectsListHashMap = new ArrayList<>();
                for (int i = 0; i < studentSubjectsList.length(); i++) {
                    HashMap<String, String> hashSubj = (HashMap<String, String>) FacadeCommon.parseJsonKV(studentSubjectsList.getJSONObject(i));
                    studentSubjectsListHashMap.add(hashSubj);
                }
                for (int i = 0; i < studentSubjectsListHashMap.size(); i++) {
                    int gcourse = Integer.parseInt(studentSubjectsListHashMap.get(i).get("id"));
                    String _courseNameId = studentSubjectsListHashMap.get(i).get("course");
                    String courseName = hashCourses.get(_courseNameId);
                    int courseFrom = 3;
                    if (hashCoursesForms.get(_courseNameId) != null) {
                        courseFrom = Integer.parseInt(hashCoursesForms.get(_courseNameId));
                    }
                    SubjectListItem subjectListItem = new SubjectListItem(gcourse, courseName, assessmentType[courseFrom]);
                    subjectsListArray.add(subjectListItem);
                }
            } else if (userType == Constants.USER_TYPE_TEACHER) {
                JSONObject groursJson = subjectsJson.getJSONObject("groups");
                JSONObject coursesJson = subjectsJson.getJSONObject("courses");
                List<String> coursesKeys = FacadeCommon.getKeys(coursesJson);
                JSONObject subjectsListJson = subjectsJson.getJSONObject("subjectsList");

                HashMap<String, Pair<String, String>> subjectsListMap = new HashMap<>();
                for (int i = 0; i < subjectsListJson.length(); i++) {
                    JSONArray subjectListItem = subjectsListJson.getJSONArray(coursesKeys.get(i));
                    for (int j = 0; j < subjectListItem.length(); j++) {
                        subjectsListMap.put(subjectListItem.getJSONObject(j).getString("group"), new Pair(coursesKeys.get(i), subjectListItem.getJSONObject(j).getInt("id")));
                    }
                }
                List<String> keys = FacadeCommon.getKeys(groursJson);
                for (String key : keys) {
                    JSONObject courseJson = groursJson.getJSONObject(key);
                    SubjectListItem courseItem = new SubjectListItem(Integer.valueOf(subjectsListMap.get(key).getValue1()),
                            courseJson.getString("courseName"),
                            coursesJson.getInt(subjectsListMap.get(key).getValue0()));
                    subjectsListArray.add(courseItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return subjectsListArray;
    }
}
