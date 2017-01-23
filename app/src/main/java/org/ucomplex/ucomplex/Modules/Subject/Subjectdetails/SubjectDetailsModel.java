package org.ucomplex.ucomplex.Modules.Subject.SubjectDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;
import org.ucomplex.ucomplex.Domain.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Domain.Materials.ProgressItem;
import org.ucomplex.ucomplex.Domain.Users.Teacher;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectDetailsModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    public static final String EXTRA_KEY_SUBJECT_ID = "subjId";

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth;
        HashMap<String, String> params = new HashMap<>();
        int subjId;
        encodedAuth = bundle[0].getString(AUTH_STRING);
        if (bundle[0].containsKey(EXTRA_KEY_SUBJECT_ID)) {
            subjId = bundle[0].getInt(EXTRA_KEY_SUBJECT_ID);
            params.put(EXTRA_KEY_SUBJECT_ID, Integer.toString(subjId));
        }

        HttpFactory.getInstance().httpVolley(HttpFactory.USER_SUBJECT_URL,
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
        List<IRecyclerItem> listItems = new ArrayList<>();
        JSONObject subjectJson;
        try {
            subjectJson = new JSONObject(s);
            SubjectModel subjectModel = extractSubjectModel(subjectJson);

            SubjectDetailsItemForList itemTitle = new SubjectDetailsItemForList();
            itemTitle.setResourceString(R.string.lecturers);
            listItems.add(itemTitle);

            for (UserInterface teacher : subjectModel.getTeachers()) {
                SubjectDetailsItemForList itemTeacher = new SubjectDetailsItemForList();
                if (teacher.getPhoto() == 1) {
                    itemTeacher.setCode(teacher.getCode());
                }
                itemTeacher.setTextOne(teacher.getName());
                itemTeacher.setTextTwo(Integer.toString(teacher.getId()));
                listItems.add(itemTeacher);
            }

            SubjectDetailsItemForList itemStatistics = new SubjectDetailsItemForList();

            double absence = getAbsence(subjectModel.getProgress());
            absence = FacadeCommon.round(absence, 2);
            itemStatistics.setTextOne(Double.toString(absence));

            double mark = getMark(subjectModel.getProgress());
            mark = FacadeCommon.round(mark, 2);
            itemStatistics.setTextTwo(Double.toString(mark));
            listItems.add(itemStatistics);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return listItems;
    }

    @NonNull
    private SubjectModel extractSubjectModel(JSONObject subjectJson) throws JSONException {
        DepartmentItem department = getDepartment(subjectJson);
        Pair<List<MaterialItem>, List<UserInterface>> filesAndTeacher = getFilesAndTeachers(subjectJson);
        ProgressItem progress = getProgressItem(subjectJson);
        SubjectModel subjectModel = SubjectModel.getInstance();
        subjectModel.setDepartment(department);
        subjectModel.setProgress(progress);
        if (filesAndTeacher != null) {
            subjectModel.setFiles(filesAndTeacher.getValue0());
            subjectModel.setTeachers(filesAndTeacher.getValue1());
        }

        JSONObject courseArray = subjectJson.getJSONObject("course");
        subjectModel.setName(courseArray.getString("name"));
        subjectModel.setId(courseArray.getInt("id"));
        subjectModel.setCourse(courseArray.getInt("course"));
        subjectModel.setGroup(courseArray.getInt("group"));
        subjectModel.setTable(courseArray.getInt("table"));
        subjectModel.setClient(courseArray.getInt("client"));
        subjectModel.setCourse_id(courseArray.getInt("course_id"));
        subjectModel.setDescription(courseArray.getString("description"));
        return subjectModel;
    }

    private double getMark(ProgressItem progress) {
        double mark = 0.0;
        if (progress.getMark() != 0 && progress.getMarkCount() != 0) {
            mark = progress.getMark() / progress.getMarkCount();
        }
        return mark;
    }

    private double getAbsence(ProgressItem progress) {
        int a = progress.getAbsence();
        int b = progress.getHours();
        double absence = 100;
        if (a != 0 && b != 0) {
            absence = ((double) a / (double) b) * 100;
            if (absence == 0.0) {
                absence = 100;
            }
        }
        return absence;
    }

    private Pair<List<MaterialItem>, List<UserInterface>> getFilesAndTeachers(JSONObject subjectJson) throws JSONException {
        List<MaterialItem> files = new ArrayList<>();
        List<UserInterface> teachers = new ArrayList<>();
        JSONArray filesArray = new JSONArray();
        if (!(subjectJson.get("files") instanceof Boolean)) {
            filesArray = subjectJson.getJSONArray("files");
        }
        for (int i = 0; i < filesArray.length(); i++) {
            try {
                JSONObject jsonFiles = filesArray.getJSONObject(i);
                JSONObject teacher = jsonFiles.getJSONObject("teacher");
                JSONArray filesArrayObject = jsonFiles.getJSONArray("files");


                if (!teacher.isNull("id")) {
                    UserInterface teacher1 = new Teacher();
                    teacher1.setName(teacher.getString("name"));
                    teacher1.setCode(teacher.getString("code"));
                    teacher1.setPhoto(teacher.getInt("photo"));
                    teacher1.setId(teacher.getInt("id"));
                    teacher1.setType(Constants.USER_TYPE_TEACHER);
                    teachers.add(teacher1);

                    for (int j = 0; j < filesArrayObject.length(); j++) {
                        JSONObject jsonFile = filesArrayObject.getJSONObject(j);
                        MaterialItem file = new MaterialItem();
                        file.setId(jsonFile.getString("id"));
                        file.setName(jsonFile.getString("name"));
                        file.setAddress(jsonFile.getString("address"));
                        file.setData(jsonFile.getString("data"));
                        file.setSize(jsonFile.getInt("size"));
                        file.setTime(jsonFile.getString("time"));
                        file.setType(jsonFile.getString("type"));
                        file.setOwner(teacher1);
                        files.add(file);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return new Pair<>(files, teachers);
    }

    @NonNull
    private ProgressItem getProgressItem(JSONObject subjectJson) throws JSONException {
        JSONObject progressArray = new JSONObject();
        if (!(subjectJson.get("progress") instanceof Boolean)) {
            progressArray = subjectJson.getJSONObject("progress");
        }
        ProgressItem progress = new ProgressItem();
        if (progressArray.length() > 0) {
            progress.setType(progressArray.getInt("type"));
            progress.set_mark(progressArray.getInt("_mark"));
            progress.setMark(progressArray.getInt("mark"));
            progress.setAbsence(progressArray.getInt("absence"));
            progress.setHours(progressArray.getInt("hours"));
            progress.setIndivid(progressArray.getInt("individ"));
            progress.setMarkCount(progressArray.getInt("marksCount"));
            progress.setStudent(progressArray.getInt("student"));
            progress.setTable(progressArray.getInt("table"));
            progress.setTime(progressArray.getInt("time"));
        }
        return progress;
    }

    private DepartmentItem getDepartment(JSONObject subjectJson) throws JSONException {
        DepartmentItem department = new DepartmentItem();
        if (subjectJson.has("depart")) {
            try {
                subjectJson.getBoolean("depart");
            } catch (JSONException e) {
                JSONObject departmentArray = subjectJson.getJSONObject("depart");
                department.setId(departmentArray.getInt("id"));
                department.setName(departmentArray.getString("name"));
                department.setPostcode(departmentArray.getString("postcode"));
                department.setDescription(departmentArray.getString("description"));
                department.setFax(departmentArray.getString("fax"));
                department.setAddress(departmentArray.getString("address"));
                department.setTel(departmentArray.getString("tel"));
                department.setEmail(departmentArray.getString("email"));
                department.setStruct_file(departmentArray.getString("struct_file"));
                department.setAlias(departmentArray.getString("alias"));
                department.setFaculty(departmentArray.getInt("faculty"));
                department.setClient(departmentArray.getInt("client"));
            }
        }
        return department;
    }


}
