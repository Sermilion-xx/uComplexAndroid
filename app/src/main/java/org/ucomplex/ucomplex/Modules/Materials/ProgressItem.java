package org.ucomplex.ucomplex.Modules.Materials;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Modules.Subject.SubjectItem;

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
public class ProgressItem extends IRecyclerItem {
    private int student;
    private SubjectItem course;
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
}
