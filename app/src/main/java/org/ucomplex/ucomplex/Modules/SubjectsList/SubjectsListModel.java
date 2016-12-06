package org.ucomplex.ucomplex.Modules.SubjectsList;

import com.android.volley.VolleyError;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModelRecycler;
import org.ucomplex.ucomplex.Interfaces.MVP.RecyclerMVP.ModelRecycler;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectsListModel extends AbstractModelRecycler implements ModelRecycler {

    public static final int TYPE_USER = 3;
    private final int[] assessmentType = {R.string.zachet, R.string.exam, R.string.samostoyatelnaya,R.string.empty};

    @Override
    public ArrayList<IRecyclerItem> getDataFromJson(String jsonData) {
        ArrayList<IRecyclerItem> subjectsListArray = new ArrayList<>();
        JSONObject subjectsJson;
        try {
            subjectsJson = new JSONObject(jsonData);
            if (getUser().getType() == Constants.TYPE_STUDENT) {
                JSONObject courses = subjectsJson.getJSONObject("courses");
                JSONObject coursesForms = subjectsJson.getJSONObject("courses_forms");
                JSONArray studentSubjectsList = subjectsJson.getJSONArray("studentSubjectsList");
                HashMap<String, String> hashCourses = (HashMap<String, String>) FacadeCommon.parseJsonKV(courses);
                HashMap<String, String> hashCoursesForms = (HashMap<String, String>) FacadeCommon.parseJsonKV(coursesForms);

                ArrayList<HashMap<String, String>> studentSubjectsListHashMap = new ArrayList<>();
                for (int i = 0; i < studentSubjectsList.length(); i++) {
                    HashMap<String, String> hashSubj = (HashMap<String, String>) FacadeCommon.parseJsonKV(studentSubjectsList.getJSONObject(i));
                    studentSubjectsListHashMap.add(hashSubj);
                }
                for (int i = 0; i < studentSubjectsListHashMap.size(); i++) {
                    int gcourse = Integer.parseInt(studentSubjectsListHashMap.get(i).get("id"));
                    String _courseNameId = studentSubjectsListHashMap.get(i).get("course");
                    String courseName = hashCourses.get(_courseNameId);
                    int courseFrom = 3;
                    if(hashCoursesForms.get(_courseNameId)!=null){
                        courseFrom = Integer.parseInt(hashCoursesForms.get(_courseNameId));
                    }
                    SubjectListItem subjectListItem = new SubjectListItem(gcourse, courseName, assessmentType[courseFrom]);
                    subjectsListArray.add(subjectListItem);
                }
            } else if (getUser().getType() == TYPE_USER) {
                JSONObject groursJson = subjectsJson.getJSONObject("groups");
                JSONObject coursesJson = subjectsJson.getJSONObject("courses");
                ArrayList<String> coursesKeys = FacadeCommon.getKeys(coursesJson);
                JSONObject subjectsListJson = subjectsJson.getJSONObject("subjectsList");

                HashMap<String, Pair<String, String>> subjectsListMap = new HashMap<>();
                for (int i = 0; i < subjectsListJson.length(); i++) {
                    JSONArray subjectListItem = subjectsListJson.getJSONArray(coursesKeys.get(i));
                    for (int j = 0; j < subjectListItem.length(); j++) {
                        subjectsListMap.put(subjectListItem.getJSONObject(j).getString("group"), new Pair(coursesKeys.get(i), subjectListItem.getJSONObject(j).getInt("id")));
                    }
                }
                ArrayList<String> keys = FacadeCommon.getKeys(groursJson);
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

    @Override
    public void onTaskComplete(int requestType, Object... o) {
        if (!(o[0] instanceof VolleyError)) {
            String result = (String) o[0];
            mRecyclerItems = getDataFromJson(result);
            end = mRecyclerItems.size();
            mOnDataLoadedListener.dataLoaded(result != null && mRecyclerItems.size() > 0, start, end, oldEnd);
        }else{
            mOnDataLoadedListener.dataLoaded(false);
        }
    }
}
