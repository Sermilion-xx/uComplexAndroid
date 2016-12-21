package org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies;

import com.google.gson.JsonElement;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 21/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface DeserializationStrategy<T> {

    T deserealize(JsonElement json);

}
