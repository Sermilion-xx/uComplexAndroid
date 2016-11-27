package org.ucomplex.ucomplex;

import android.view.View;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 27/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface IViewExtensions {

    <T extends View> T find(int id);
}
