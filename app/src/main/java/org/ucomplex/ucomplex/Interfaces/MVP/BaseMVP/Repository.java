package org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP;

import android.content.Context;

import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 14/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface Repository {
    void setContext(Context context);
    void loadData(Object...param);
    void setTaskCompleteListener(OnTaskCompleteListener mTaskCompleteListener);
}
