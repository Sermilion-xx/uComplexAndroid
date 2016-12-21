package org.ucomplex.ucomplex.Retrofit.JsonDeserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies.DeserializationStrategy;

import java.lang.reflect.Type;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 20/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UCJsonDeserializer<T> implements JsonDeserializer<T> {

    private DeserializationStrategy<T> strategy;

    public UCJsonDeserializer(DeserializationStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public T deserialize(JsonElement json, Type type,
                            JsonDeserializationContext context) throws JsonParseException {
        return strategy.deserealize(json);
    }
}
