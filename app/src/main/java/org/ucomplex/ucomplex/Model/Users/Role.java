package org.ucomplex.ucomplex.Model.Users;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class Role {
    private int id;
    private int person;
    private int type;
    private String name;
}
