package org.ucomplex.ucomplex.Utility;

import android.content.Context;

import org.ucomplex.ucomplex.R;

/**
 * Created by Sermilion on 02/11/2016.
 */

public class FacadeCommon {

    public static int USER_TYPE = -1;

    public static String getStringUserType(Context context, int type) {
        String typeStr = null;
        if (type == 0) {
            typeStr = context.getResources().getString(R.string.sotrudnik);
        }
        if (type == 1) {
            typeStr = context.getResources().getString(R.string.administrator);
        }
        if (type == 2) {
            typeStr = context.getResources().getString(R.string.sub_administrator);
        } else if (type == 3) {
            typeStr = context.getResources().getString(R.string.prepodvatel);
        } else if (type == 4) {
            typeStr = context.getResources().getString(R.string.student);
        } else if (type == 5) {
            typeStr = context.getResources().getString(R.string.metodist_po_raspisaniyu);
        } else if (type == 6) {
            typeStr = context.getResources().getString(R.string.metodist_ko);
        } else if (type == 7) {
            typeStr = context.getResources().getString(R.string.bibliotekar);
        } else if (type == 8) {
            typeStr = context.getResources().getString(R.string.tehsekretar);
        } else if (type == 9) {
            typeStr = context.getResources().getString(R.string.abiturient);
        } else if (type == 10) {
            typeStr = context.getResources().getString(R.string.uchebny_otdel);
        } else if (type == 11) {
            typeStr = context.getResources().getString(R.string.rukovoditel);
        } else if (type == 12) {
            typeStr = context.getResources().getString(R.string.monitoring);
        } else if (type == 13) {
            typeStr = context.getResources().getString(R.string.dekan);
        } else if (type == 14) {
            typeStr = context.getResources().getString(R.string.otdel_kadrov);
        }
        return typeStr;
    }

}
