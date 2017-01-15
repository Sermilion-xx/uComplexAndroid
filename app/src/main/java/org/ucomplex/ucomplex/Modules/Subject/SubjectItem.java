package org.ucomplex.ucomplex.Modules.Subject;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.ucomplex.ucomplex.Domain.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Domain.Materials.ProgressItem;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;

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

public class SubjectItem extends IRecyclerItem {
    private int                      id;
    private int                      course;
    private int                      group;
    private int                      table;
    private int                      client;
    private int                      course_id;
    private ArrayList<UserInterface> teachers = new ArrayList<>();
    private ArrayList<MaterialItem>  files = new ArrayList<>();
    private DepartmentItem           department;
    private ProgressItem             progress;
    private String                   name;
    private String                   description;

    void addTeacher(UserInterface teacher){
        this.teachers.add(teacher);
    }

    SubjectItem() {
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

    void setCourse(int course) {
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

    public ArrayList<UserInterface> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<UserInterface> teachers) {
        this.teachers = teachers;
    }

    public ArrayList<MaterialItem> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MaterialItem> files) {
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
