package org.ucomplex.ucomplex.Modules.Subject;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModelRecycler;
import org.ucomplex.ucomplex.Model.Users.Teacher;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Modules.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Materials.ProgressItem;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.Modules.Subject.SubjectItem;

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

    public static final String JSON_KEY_COURSE = "course";
    private int subjId;

    @Override
    public void setData(Object data) {
        subjId= (int)data;
    }

    @Override
    public void loadData() {
        mRepository.loadData(subjId);
    }

    @Override
    public ArrayList<IRecyclerItem> getDataFromJson(String jsonString) throws JSONException {

            JSONObject subjectJson;
            SubjectItem subjectItem = new SubjectItem();
            try {
                subjectJson = new JSONObject(jsonString);
                JSONObject courseArray = subjectJson.getJSONObject(JSON_KEY_COURSE);

                JSONObject departmentArray;
                DepartmentItem department = new DepartmentItem();
                if (subjectJson.has("depart")) {
                    try {
                        boolean a = subjectJson.getBoolean("depart");
                        subjectItem.setDepartment(new DepartmentItem());
                    } catch (JSONException e) {
                        departmentArray = subjectJson.getJSONObject("depart");
                        //used
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
                        subjectItem.setDepartment(department);
                    }
                }
                JSONObject progressArray = new JSONObject();
                JSONArray filesArray = new JSONArray();
                if(!(subjectJson.get("progress") instanceof Boolean)){
                    progressArray = subjectJson.getJSONObject("progress");
                }
                if(!(subjectJson.get("files") instanceof Boolean)){
                    filesArray = subjectJson.getJSONArray("files");
                }
                //used
                ArrayList<MaterialItem> files = new ArrayList<>();
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
                            teacher1.setType(3);
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
                    }
                }
//used
                ProgressItem progress = new ProgressItem();
                if(progressArray.length()>0) {
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

                subjectItem.setName(courseArray.getString("name"));
                subjectItem.setId(courseArray.getInt("id"));
                subjectItem.setCourse(courseArray.getInt("subjectItem"));
                subjectItem.setGroup(courseArray.getInt("group"));
                subjectItem.setTable(courseArray.getInt("table"));
                subjectItem.setClient(courseArray.getInt("client"));
                subjectItem.setCourse_id(courseArray.getInt("course_id"));
                subjectItem.setDescription(courseArray.getString("description"));

                subjectItem.setProgress(progress);
                subjectItem.setFiles(files);
                ArrayList<IRecyclerItem> subjects = new ArrayList<>();
                subjects.add(subjectItem);
                return subjects;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }

}
