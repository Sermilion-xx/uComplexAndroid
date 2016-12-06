package org.ucomplex.ucomplex.Modules.Subject;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Modules.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Materials.ProgressItem;

import java.util.ArrayList;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class SubjectItem extends IRecyclerItem {
    private int                      id;
    private int                      course;
    private int                      group;
    private int                      table;
    private int                      client;
    private int                      course_id;
    private ArrayList<UserInterface> teachers;
    private ArrayList<MaterialItem>  files;
    private DepartmentItem           department;
    private ProgressItem             progress;
    private String                   name;
    private String                   description;

    public void addTeacher(UserInterface teacher){
        this.teachers.add(teacher);
    }
}
