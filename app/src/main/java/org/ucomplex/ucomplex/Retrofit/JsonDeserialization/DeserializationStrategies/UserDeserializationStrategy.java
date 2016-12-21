package org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Model.Users.Role;
import org.ucomplex.ucomplex.Model.Users.User;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.UCJsonDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 21/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UserDeserializationStrategy implements DeserializationStrategy<User> {
    @Override
    public User deserealize(JsonElement json) {
//        JsonParser parser = new JsonParser();
//        JsonArray roleArray = json.getAsJsonObject().get("roles").getAsJsonArray();
//        DeserializationStrategy deserializeRole = new RoleDeserializeStrategy();
//        UCJsonDeserializer<Role> ucJsonDeserializer = new UCJsonDeserializer<>(deserializeRole);
//        User user = new User();
//        List<Role> roles = new ArrayList<>();
//        for(JsonElement roleElement: roleArray){
//            JsonObject roleObject = roleElement.getAsJsonObject();
//            Role role = ucJsonDeserializer.deserialize(roleObject, Role.class, null);
//            roles.add(role);
//        }
//        user.setRoles(roles);
        Gson gson = new Gson();
        JsonObject userSession = json.getAsJsonObject().get("session").getAsJsonObject();
        return gson.fromJson(userSession.toString(), User.class);

    }
}
