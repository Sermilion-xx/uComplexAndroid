package org.ucomplex.ucomplex.Modules.Subject;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class SubjectItemForList extends IRecyclerItem {
    private int resourceString;
    private String textOne;
    private String textTwo;
    private String code;
}
