package org.ucomplex.ucomplex.Modules.Subject.SubjectDetails;

import net.oneread.aghanim.components.utility.IRecyclerItem;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectDetailsItemForList extends IRecyclerItem {
    private int resourceString;
    private String textOne;
    private String textTwo;
    private String code;

    public SubjectDetailsItemForList(int resourceString, String textOne, String textTwo, String code) {
        this.resourceString = resourceString;
        this.textOne = textOne;
        this.textTwo = textTwo;
        this.code = code;
    }

    public int getResourceString() {
        return resourceString;
    }

    public void setResourceString(int resourceString) {
        this.resourceString = resourceString;
    }

    public String getTextOne() {
        return textOne;
    }

    public void setTextOne(String textOne) {
        this.textOne = textOne;
    }

    public String getTextTwo() {
        return textTwo;
    }

    public void setTextTwo(String textTwo) {
        this.textTwo = textTwo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubjectDetailsItemForList() {
    }


}
