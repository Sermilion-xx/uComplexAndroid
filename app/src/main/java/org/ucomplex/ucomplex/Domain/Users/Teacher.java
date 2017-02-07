package org.ucomplex.ucomplex.Domain.Users;

import android.os.Parcelable;

import org.ucomplex.ucomplex.Domain.Materials.DepartmentItem;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Domain.Materials.TimetableEntryItem;

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

public class Teacher extends User implements Parcelable {

    private int post;
    private int experience;
    private int dep_experience;
    private ArrayList<String> courses;
    private int rank;
    private int degree;
    private String bio;
    private String phone_work;
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
    private int academic_degree;
    private int academic_rank;
    private String statuses;
    private String academic_awards;
    private String upqualification;
    private int rate;
    private int section;
    private String sectionName;
    private int lead;
    private int activity;
    private String facultyName;
    private ArrayList<TimetableEntryItem> timetableEntries;

    public String getPhone_work() {
        return phone_work;
    }

    public void setPhone_work(String phone_work) {
        this.phone_work = phone_work;
    }

    public int getAcademic_degree() {
        return academic_degree;
    }

    public void setAcademic_degree(int academic_degree) {
        this.academic_degree = academic_degree;
    }

    public int getAcademic_rank() {
        return academic_rank;
    }

    public void setAcademic_rank(int academic_rank) {
        this.academic_rank = academic_rank;
    }

    public String getAcademic_awards() {
        return academic_awards;
    }

    public void setAcademic_awards(String academic_awards) {
        this.academic_awards = academic_awards;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getDep_experience() {
        return dep_experience;
    }

    public void setDep_experience(int dep_experience) {
        this.dep_experience = dep_experience;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getFact() {
        return fact;
    }

    public void setFact(int fact) {
        this.fact = fact;
    }

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }

    public String getActivity_update() {
        return activity_update;
    }

    public void setActivity_update(String activity_update) {
        this.activity_update = activity_update;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public DepartmentItem getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentItem department) {
        this.department = department;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public ArrayList<MaterialItem> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MaterialItem> files) {
        this.files = files;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentDepart() {
        return documentDepart;
    }

    public void setDocumentDepart(String documentDepart) {
        this.documentDepart = documentDepart;
    }

    public String getDocumentDepartCode() {
        return documentDepartCode;
    }

    public void setDocumentDepartCode(String documentDepartCode) {
        this.documentDepartCode = documentDepartCode;
    }

    public int getAcademicDegree() {
        return academic_degree;
    }

    public void setAcademicDegree(int academicDegree) {
        this.academic_degree = academicDegree;
    }

    public int getAcademicRank() {
        return academic_rank;
    }

    public void setAcademicRank(int academicRank) {
        this.academic_rank = academicRank;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getAcademicAwards() {
        return academic_awards;
    }

    public void setAcademicAwards(String academic_awards) {
        this.academic_awards = academic_awards;
    }

    public String getUpqualification() {
        return upqualification;
    }

    public void setUpqualification(String upqualification) {
        this.upqualification = upqualification;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getLead() {
        return lead;
    }

    public void setLead(int lead) {
        this.lead = lead;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public ArrayList<TimetableEntryItem> getTimetableEntries() {
        return timetableEntries;
    }

    public void setTimetableEntries(ArrayList<TimetableEntryItem> timetableEntries) {
        this.timetableEntries = timetableEntries;
    }
}
