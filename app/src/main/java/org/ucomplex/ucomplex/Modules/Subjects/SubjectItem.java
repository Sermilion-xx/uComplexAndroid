package org.ucomplex.ucomplex.Modules.Subjects;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class SubjectItem implements IRecyclerItem {

    private int courseId;
    private String name;
    private String assesmentType;

}
