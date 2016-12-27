package org.ucomplex.ucomplex.Modules.Subject;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModelRecycler;
import org.ucomplex.ucomplex.Model.Users.Teacher;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Modules.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Materials.ProgressItem;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectModel extends AbstractModelRecycler {

    private static final String JSON_KEY_COURSE = "course";


    @Override
    public ArrayList<IRecyclerItem> getDataFromJson(String jsonString) throws JSONException {
        ArrayList<IRecyclerItem> listItems = new ArrayList<>();
        JSONObject subjectJson;
        SubjectItem subjectItem = new SubjectItem();
        try {
            subjectJson = new JSONObject(jsonString);

            DepartmentItem          department = getDepartment(subjectJson, subjectItem);
            ArrayList<MaterialItem> files = getFiles(subjectJson, subjectItem);
            ProgressItem            progress = getProgressItem(subjectJson);

            subjectItem.setDepartment(department);
            subjectItem.setProgress(progress);
            subjectItem.setFiles(files);

            JSONObject courseArray = subjectJson.getJSONObject(JSON_KEY_COURSE);
            subjectItem.setName(courseArray.getString("name"));
            subjectItem.setId(courseArray.getInt("id"));
            subjectItem.setCourse(courseArray.getInt("course"));
            subjectItem.setGroup(courseArray.getInt("group"));
            subjectItem.setTable(courseArray.getInt("table"));
            subjectItem.setClient(courseArray.getInt("client"));
            subjectItem.setCourse_id(courseArray.getInt("course_id"));
            subjectItem.setDescription(courseArray.getString("description"));

            SubjectItemForList itemTitle = new SubjectItemForList();
            itemTitle.setResourceString(R.string.lecturers);
            listItems.add(itemTitle);

            for(UserInterface teacher: subjectItem.getTeachers()){
                SubjectItemForList itemTeacher = new SubjectItemForList();
                if(teacher.getPhoto()==1){
                    itemTeacher.setCode(teacher.getCode());
                }
                itemTeacher.setTextOne(teacher.getName());
                itemTeacher.setTextTwo(Integer.toString(teacher.getId()));
                listItems.add(itemTeacher);
            }

            SubjectItemForList itemStatistics = new SubjectItemForList();

            double absence = getAbsence(progress);
            absence = FacadeCommon.round(absence, 2);
            itemStatistics.setTextOne(Double.toString(absence));

            double mark = getMark(progress);
            mark = FacadeCommon.round(mark, 2);
            itemStatistics.setTextTwo(Double.toString(mark));
            listItems.add(itemStatistics);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return listItems;
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

    private ArrayList<MaterialItem> getFiles(JSONObject subjectJson, SubjectItem subjectItem) throws JSONException {
        ArrayList<MaterialItem> files = new ArrayList<>();
        JSONArray filesArray = new JSONArray();
        if (!(subjectJson.get("files") instanceof Boolean)) {
            filesArray = subjectJson.getJSONArray("files");
        }
        for (int i = 0; i < filesArray.length(); i++) {
            try {
                JSONObject jsonFiles = filesArray.getJSONObject(i);
                JSONObject teacher = jsonFiles.getJSONObject("teacher");
                JSONArray filesArrayObject = jsonFiles.getJSONArray("files");
                UserInterface teacher1;

                if (!teacher.isNull("id")) {
                    teacher1 = new Teacher();
                    teacher1.setName(teacher.getString("name"));
                    teacher1.setCode(teacher.getString("code"));
                    teacher1.setPhoto(teacher.getInt("photo"));
                    teacher1.setId(teacher.getInt("id"));
                    teacher1.setType(Constants.USER_TYPE_TEACHER);
                    subjectItem.addTeacher(teacher1);

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
        return files;
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
            progress.setCourse(null);
            progress.setHours(progressArray.getInt("hours"));
            progress.setIndivid(progressArray.getInt("individ"));
            progress.setMarkCount(progressArray.getInt("marksCount"));
            progress.setStudent(progressArray.getInt("student"));
            progress.setTable(progressArray.getInt("table"));
            progress.setTime(progressArray.getInt("time"));
        }
        return progress;
    }

    private DepartmentItem getDepartment(JSONObject subjectJson, SubjectItem subjectItem) throws JSONException {
        DepartmentItem department = new DepartmentItem();
        if (subjectJson.has("depart")) {
            try {
                subjectJson.getBoolean("depart");
                subjectItem.setDepartment(new DepartmentItem());
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
