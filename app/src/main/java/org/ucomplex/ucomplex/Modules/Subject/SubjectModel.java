package org.ucomplex.ucomplex.Modules.Subject;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.ucomplex.ucomplex.Domain.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Domain.Materials.ProgressItem;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectModel extends IRecyclerItem {

    private static SubjectModel INSTANCE;

    public static SubjectModel getInstance(){
        if(INSTANCE==null){
            INSTANCE = new SubjectModel();
        }
        return INSTANCE;
    }

    private int                      id;
    private int                      course;
    private int                      group;
    private int                      table;
    private int                      client;
    private int                      course_id;
    private List<UserInterface> teachers = new ArrayList<>();
    private List<MaterialItem>  files = new ArrayList<>();
    private DepartmentItem           department;
    private ProgressItem             progress;
    private String                   name;
    private String                   description;

    public void addTeacher(UserInterface teacher){
        this.teachers.add(teacher);
    }

    private SubjectModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public List<UserInterface> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UserInterface> teachers) {
        this.teachers = teachers;
    }

    public List<MaterialItem> getFiles() {
        return files;
    }

    public void setFiles(List<MaterialItem> files) {
        this.files = files;
    }

    public DepartmentItem getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentItem department) {
        this.department = department;
    }

    public ProgressItem getProgress() {
        return progress;
    }

    public void setProgress(ProgressItem progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
