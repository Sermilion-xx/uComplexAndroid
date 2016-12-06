package org.ucomplex.ucomplex.Modules.Materials;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;

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
public class DepartmentItem extends IRecyclerItem {
    private int    id;
    private String name;
    private String postcode;
    private String description;
    private String fax;
    private String address;
    private String tel;
    private String email;
    private String struct_file;
    private String alias;
    private int    faculty;
    private int    client;
}
