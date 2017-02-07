package org.ucomplex.ucomplex.LoginModuleTests;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.junit.Test;
import org.ucomplex.ucomplex.Modules.UserProfile.UserProfileModel;
import org.ucomplex.ucomplex.Modules.Users.UsersModel;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ModelTest {

    String usersString = "[\n" +
            "    {\n" +
            "      \"id\": \"50772\",\n" +
            "      \"person\": \"50772\",\n" +
            "      \"name\": \"Техподдержка\",\n" +
            "      \"alias\": \"\",\n" +
            "      \"type\": \"1\",\n" +
            "      \"code\": \"0cbdecde6f413192\",\n" +
            "      \"photo\": \"1\",\n" +
            "      \"agent\": \"0\",\n" +
            "      \"online\": \"1486070404\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"27777\",\n" +
            "      \"person\": \"27777\",\n" +
            "      \"name\": \"Асхабов Беслан Хамзатович\",\n" +
            "      \"alias\": \"\",\n" +
            "      \"type\": \"1\",\n" +
            "      \"code\": \"f295bc7ba59c9e7e\",\n" +
            "      \"photo\": \"1\",\n" +
            "      \"agent\": \"0\",\n" +
            "      \"online\": \"1486117959\",\n" +
            "      \"MAX(online)\": \"1486117959\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"56475\",\n" +
            "      \"person\": \"56475\",\n" +
            "      \"name\": \"Хожаева Хава Урашевна\",\n" +
            "      \"alias\": \"\",\n" +
            "      \"type\": \"4\",\n" +
            "      \"code\": \"ad850e528079cc4f\",\n" +
            "      \"photo\": \"1\",\n" +
            "      \"agent\": \"1\",\n" +
            "      \"online\": \"1486117959\",\n" +
            "      \"MAX(online)\": \"1486117959\"\n" +
            "    }\n" +
            "  ]";

    @Test
    public void usersProcessingTest(){
        UsersModel model = new UsersModel();
        List<IRecyclerItem> list = model.processJson(usersString);
        assertThat("Количество полученных пользователей меньше трех.", list.size()==3);
    }

    String userProfile = "{\n" +
            "  \"id\": \"26198\",\n" +
            "  \"name\": \"Лорсункаева Радима Альвиевна\",\n" +
            "  \"email\": \"\",\n" +
            "  \"code\": \"e1ee716032d4ea1d\",\n" +
            "  \"photo\": \"1\",\n" +
            "  \"statuses\": null,\n" +
            "  \"academic_awards\": \"\",\n" +
            "  \"academic_rank\": \"0\",\n" +
            "  \"academic_degree\": \"0\",\n" +
            "  \"upqualification\": \"\",\n" +
            "  \"phone_work\": \"\",\n" +
            "  \"bio\": \"\",\n" +
            "  \"roles\": [\n" +
            "    {\n" +
            "      \"id\": \"26198\",\n" +
            "      \"role\": \"26198\",\n" +
            "      \"person\": \"26198\",\n" +
            "      \"type\": \"4\",\n" +
            "      \"name\": \"Лорсункаева Радима Альвиевна\",\n" +
            "      \"group\": \"1463\",\n" +
            "      \"position\": \"1463\",\n" +
            "      \"major\": \"29\",\n" +
            "      \"study\": \"1\",\n" +
            "      \"year\": \"2013\",\n" +
            "      \"payment\": \"1\",\n" +
            "      \"contract_year\": \"2013\",\n" +
            "      \"position_name\": \"Налоги и налогообложение\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"black\": {\n" +
            "    \"me_black\": false,\n" +
            "    \"is_black\": false\n" +
            "  },\n" +
            "  \"friends\": {\n" +
            "    \"is_friend\": false,\n" +
            "    \"req_sent\": true\n" +
            "  }\n" +
            "}";

    @Test
    public void profileUserModelTest(){
        UserProfileModel model = new UserProfileModel();
        model.processJson(userProfile);
    }
}
