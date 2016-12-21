package org.ucomplex.ucomplex.LoginModuleTests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;
import org.ucomplex.ucomplex.Model.Users.Role;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies.DeserializationStrategy;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.DeserializationStrategies.RoleDeserializeStrategy;
import org.ucomplex.ucomplex.Retrofit.JsonDeserialization.UCJsonDeserializer;

import static org.junit.Assert.assertTrue;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 21/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class LoginTest {

    String userJson = "{" +
            "roles: [" +
            "{" +
            "id: 1951," +
            "person: 1951," +
            "type: 4," +
            "name: студент" +
            "}" +
            "]," +
            "session: {" +
            "demo: null," +
            "debug: null," +
            "app: null," +
            "mobile: 1," +
            "code: 5d35d3846e2be15d," +
            "photo: 0," +
            "name: Авторханова Мадина Исаевна," +
            "login: id1951," +
            "pass: 1234," +
            "phone: +79888888888," +
            "email: email1@mail.ru," +
            "session: i7p3uik4id49voeq5ep85vmdl3," +
            "person: 1951," +
            "client: 1," +
            "except: [" +
            "demo," +
            "debug," +
            "app," +
            "mobile," +
            "code," +
            "photo," +
            "name," +
            "login," +
            "pass," +
            "phone," +
            "email," +
            "session," +
            "person," +
            "client," +
            "except" +
            "]," +
            "id: 1951," +
            "type: 4," +
            "controller: student," +
            "data: null," +
            "group: 148," +
            "major: 88," +
            "faculty: 95," +
            "year: 2012," +
            "study: 1," +
            "contract_year: 2016," +
            "payment: 1," +
            "table: 0," +
            "online: 1482266074," +
            "roles: [" +
            "{" +
            "id: 1951," +
            "person: 1951," +
            "type: 4," +
            "name: студент" +
            "}" +
            "]" +
            "}" +
            "}";

//    @Test
//    public void deserializedUser(){
//        UCJsonDeserializer jsonDeserializer = new UCJsonDeserializer();
//        JsonParser parser = new JsonParser();
//        JsonObject o = parser.parse(userJson).getAsJsonObject();
//        jsonDeserializer.deserialize(o.get("roles"), Role.class, null);
//    }

    String roles = "{" +
            "roles: [" +
            "{" +
            "id: 1951," +
            "person: 1951," +
            "type: 4," +
            "name: студент" +
            "}" +
            "]" +
            "}";

    @Test
    public void deserializedRole() {
        DeserializationStrategy deserializeRole = new RoleDeserializeStrategy();
        UCJsonDeserializer<Role> ucJsonDeserializer = new UCJsonDeserializer<>(deserializeRole);

        JsonParser parser = new JsonParser();
        JsonArray roleArray = parser.parse(roles).getAsJsonObject().get("roles").getAsJsonArray();
        JsonObject roleObject = roleArray.get(0).getAsJsonObject();

        Role actual = ucJsonDeserializer.deserialize(roleObject, Role.class, null);
        assertTrue("Did not deserialize", actual !=null);
    }
}
