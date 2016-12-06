package org.ucomplex.ucomplex.Model.Users;

import android.os.Parcelable;

import org.ucomplex.ucomplex.Modules.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Modules.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Materials.TimetableEntryItem;

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
public class Teacher extends User implements Parcelable {

    private int post;
    private int experience;
    private int dep_experience;
    private ArrayList<String> courses;
    private int rank;
    private int degree;
    private String bio;
    private int plan;
    private int fact;
    private int fails;
    private String activity_update;
    private int selection;
    private DepartmentItem department;
    private int departmentId;
    private String departmentName;
    private int closed;
    private ArrayList<MaterialItem> files;
    private int sex;
    private String series;
    private String number;
    private String documentDate;
    private String documentDepart;
    private String documentDepartCode;
    private int academicDegree;
    private int academicRank;
    private String statuses;
    private String academicAwards;
    private String upqualification;
    private int rate;
    private int section;
    private String sectionName;
    private int lead;
    private int activity;
    private String facultyName;
    private ArrayList<TimetableEntryItem> timetableEntries;

}
