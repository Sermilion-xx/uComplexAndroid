package org.ucomplex.ucomplex.Domain.Materials;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.ucomplex.ucomplex.Domain.Users.User;
import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;
//import org.ucomplex.ucomplex.Modules.Subject.Subjectdetails.SubjectModel;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ProgressItem extends IRecyclerItem {
    private int student;
    private SubjectModel course;
    private User teacher;
    private int table;
    private int time;
    private int mark;
    private int type;
    private int _mark;
    private int markCount;
    private int absence;
    private int individ;
    private int hours;

    public ProgressItem() {
    }

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public SubjectModel getCourse() {
        return course;
    }

    public void setCourse(SubjectModel course) {
        this.course = course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int get_mark() {
        return _mark;
    }

    public void set_mark(int _mark) {
        this._mark = _mark;
    }

    public int getMarkCount() {
        return markCount;
    }

    public void setMarkCount(int markCount) {
        this.markCount = markCount;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getIndivid() {
        return individ;
    }

    public void setIndivid(int individ) {
        this.individ = individ;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
