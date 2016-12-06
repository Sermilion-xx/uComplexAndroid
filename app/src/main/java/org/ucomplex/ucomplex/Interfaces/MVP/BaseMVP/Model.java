package org.ucomplex.ucomplex.Interfaces.MVP.BaseMVP;

import android.os.Bundle;

import org.ucomplex.ucomplex.Interfaces.OnDataLoadedListener;
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
    void loadData();
    void setBundle(Bundle data);
    void setUser(UserInterface user);
    void setRepository(Repository repository);
    UserInterface getUser();
    void setOnDataLoadedListener(OnDataLoadedListener onDataLoadListener);
}