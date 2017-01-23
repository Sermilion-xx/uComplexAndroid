package org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline;

import net.oneread.aghanim.components.utility.IRecyclerItem;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectTimelineItem extends IRecyclerItem {

    private int mMark;
    private String mName;
    private String mTime;
    private int type;

    public SubjectTimelineItem(){

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getmMark() {
        return mMark;
    }

    public void setmMark(int mMark) {
        this.mMark = mMark;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
