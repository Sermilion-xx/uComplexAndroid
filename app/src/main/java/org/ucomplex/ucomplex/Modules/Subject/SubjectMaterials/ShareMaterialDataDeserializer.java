package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.util.Pair;


import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 02/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ShareMaterialDataDeserializer extends JsonDeserializer<Pair<Integer, String>> {

    @Override
    public Pair<Integer, String> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int key = jsonParser.getIntValue();
        jsonParser.nextToken();
        String value = jsonParser.getText();
        return new Pair<>(key,value);
    }
}
