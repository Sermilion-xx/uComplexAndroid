package org.ucomplex.ucomplex.Interfaces.MVP;

import android.content.Context;

import org.ucomplex.ucomplex.Model.Users.UserInterface;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 12/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface Model {
    void onDestroy(boolean isChangingConfiguration);
    boolean loadData();
    void setContext(Context context);
    void setData(Object data);
    void setRepository(Repository repository);
    UserInterface getUser();
}
