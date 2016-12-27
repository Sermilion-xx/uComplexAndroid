package org.ucomplex.ucomplex.Modules.SubjectsList;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

class SubjectListItem extends IRecyclerItem {

    private int courseId;
    private String courseName;
    private int assessmentType;

    SubjectListItem(int courseId, String courseName, int assessmentType){
        this.courseId = courseId;
        this.courseName = courseName;
        this.assessmentType = assessmentType;
    }

    public SubjectListItem() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(int assessmentType) {
        this.assessmentType = assessmentType;
    }
}
