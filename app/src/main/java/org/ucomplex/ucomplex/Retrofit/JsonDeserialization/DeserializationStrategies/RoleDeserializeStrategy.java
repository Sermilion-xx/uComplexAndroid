package org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.ucomplex.ucomplex.Model.Users.Role;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 21/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class RoleDeserializeStrategy implements DeserializationStrategy<Role> {

    @Override
    public Role deserealize(JsonElement json) {
        final JsonObject jsonObject = json.getAsJsonObject();
        Role role = new Role();
        role.setName(jsonObject.get("name").getAsString());
        role.setId(jsonObject.get("id").getAsInt());
        role.setType(jsonObject.get("type").getAsInt());
        role.setPerson(jsonObject.get("person").getAsInt());
        return role;
    }
}
