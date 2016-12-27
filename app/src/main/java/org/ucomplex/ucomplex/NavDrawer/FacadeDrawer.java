package org.ucomplex.ucomplex.NavDrawer;

import android.content.Context;

import org.javatuples.Pair;
import org.ucomplex.ucomplex.R;

/**
 * Created by Sermilion on 03/11/2016.
 */

public class FacadeDrawer {

    private static FacadeDrawer instance;
    private Context mContext;

    public static FacadeDrawer getInstance(Context context) {
        if(instance == null){
            instance = new  FacadeDrawer(context);
        }
        return instance;
    }

    private FacadeDrawer(Context context){
        this.mContext = context;
    }

    public Pair<int[], String[]> getDrawerItemsUser0(){
        int[] icons = new int[]{
                R.drawable.ic_menu_events,
                R.drawable.ic_menu_users,
                R.drawable.ic_menu_messages,
                R.drawable.ic_menu_settings,
                R.drawable.ic_menu_exit};
        String[] titles = new String[] {
                getResourceString(R.string.events),
                getResourceString(R.string.users),
                getResourceString(R.string.materials),
                getResourceString(R.string.settings),
                getResourceString(R.string.logout)};
        return new Pair<>(icons, titles);
    }

    public Pair<int[], String[]> getDrawerItemsUser4() {
        int[] icons = new int[]{
                R.drawable.ic_menu_events,
                R.drawable.ic_menu_subjects,
                R.drawable.ic_menu_materials,
                R.drawable.ic_menu_users,
                R.drawable.ic_menu_messages,
                R.drawable.ic_menu_timetable,
                R.drawable.ic_menu_settings,
                R.drawable.ic_menu_exit
        };
        String[] titles = new String[]{
                getResourceString(R.string.events),
                getResourceString(R.string.disciplines),
                getResourceString(R.string.materials),
                getResourceString(R.string.users),
                getResourceString(R.string.messages),
                getResourceString(R.string.calendar),
                getResourceString(R.string.settings),
                getResourceString(R.string.logout)};
        return new Pair<>(icons, titles);
    }

    protected String getResourceString(int id) {
        return mContext.getResources().getString(id);
    }

}
