package org.ucomplex.ucomplex.Modules.Materials;

import org.ucomplex.ucomplex.Interfaces.IRecyclerItem;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Model.Users.UserInterface;

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
public class MaterialItem extends IRecyclerItem {
    private String id;
    private UserInterface owner;
    private String name;
    private String address;
    private String type;
    private int size;
    private String time;
    private String data;
    private String checkTime;
    private int message;
    private int from;
    private String folder;
    private int ID;
}
