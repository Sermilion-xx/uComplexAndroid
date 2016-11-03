package org.ucomplex.ucomplex.Model;

import android.graphics.Bitmap;

import lombok.Data;

/**
 * Created by Sermilion on 02/11/2016.
 */

@Data
public class DrawerListItem {
    private Bitmap profileBitmap;
    private String title1;
    private String title2;
    private int icon;

    public DrawerListItem(int icon, String title){
        this.icon = icon;
        this.title1 = title;
    }

    public DrawerListItem(Bitmap bitmap, String name, String type){
        this.profileBitmap = bitmap;
        this.title1 = name;
        this.title2 = type;
    }
}
