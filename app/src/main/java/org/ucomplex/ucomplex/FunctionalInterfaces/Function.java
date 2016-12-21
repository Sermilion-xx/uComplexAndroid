package org.ucomplex.ucomplex.FunctionalInterfaces;

import org.json.JSONException;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 20/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface Function<T, R> {
    T apply(R arg);
}
