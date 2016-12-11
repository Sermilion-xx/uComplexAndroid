package org.ucomplex.ucomplex.NavDrawer;

/**
 * Created by Sermilion on 02/11/2016.
 */


public class DrawerListItem {
    private int id;
    private String profileBitmapCode;
    private String title1;
    private String title2;
    private int icon;

    public DrawerListItem(int icon, String title){
        this.icon = icon;
        this.title1 = title;
    }

    public DrawerListItem(String code, String name, String type, int id){
        this.profileBitmapCode = code;
        this.title1 = name;
        this.title2 = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileBitmapCode() {
        return profileBitmapCode;
    }

    public void setProfileBitmapCode(String profileBitmapCode) {
        this.profileBitmapCode = profileBitmapCode;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
