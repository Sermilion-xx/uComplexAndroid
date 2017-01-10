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
                getString(R.string.events),
                getString(R.string.users),
                getString(R.string.materials),
                getString(R.string.settings),
                getString(R.string.logout)};
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
                getString(R.string.events),
                getString(R.string.disciplines),
                getString(R.string.materials),
                getString(R.string.users),
                getString(R.string.messages),
                getString(R.string.calendar),
                getString(R.string.settings),
                getString(R.string.logout)};
        return new Pair<>(icons, titles);
    }

    protected String getString(int id) {
        return mContext.getString(id);
    }

}
