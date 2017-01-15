package org.ucomplex.ucomplex.Modules.Subject;

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

class SubjectItemForList extends IRecyclerItem {
    private int resourceString;
    private String textOne;
    private String textTwo;
    private String code;

    SubjectItemForList(int resourceString, String textOne, String textTwo, String code) {
        this.resourceString = resourceString;
        this.textOne = textOne;
        this.textTwo = textTwo;
        this.code = code;
    }

    int getResourceString() {
        return resourceString;
    }

    void setResourceString(int resourceString) {
        this.resourceString = resourceString;
    }

    String getTextOne() {
        return textOne;
    }

    void setTextOne(String textOne) {
        this.textOne = textOne;
    }

    String getTextTwo() {
        return textTwo;
    }

    void setTextTwo(String textTwo) {
        this.textTwo = textTwo;
    }

    String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public SubjectItemForList() {
    }


}
