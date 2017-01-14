package org.ucomplex.ucomplex.Interfaces;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 14/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface Function<T, E> {

    E apply(T...arg);

}
