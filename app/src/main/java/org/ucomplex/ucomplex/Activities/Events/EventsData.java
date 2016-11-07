package org.ucomplex.ucomplex.Activities.Events;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Model.Events.EventItem;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.FacadeCommon;
import org.ucomplex.ucomplex.Utility.FacadePreferences;
import org.ucomplex.ucomplex.Utility.HttpFactory;

import java.util.ArrayList;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

class EventsData {

    private Context appContext;
    private int userType;

    public EventsData(Context appContext, int userType) {
        this.appContext = appContext;
        this.userType = userType;
    }

    public ArrayList<EventItem> getAllEvents() throws JSONException {
        if (userType == 4) {
            return getAllStudentEvents();
        } else if (userType == 3) {
            return getAllTeacherEvents();
        } else
            throw new IllegalArgumentException(appContext.getResources().getString(R.string.wrong_user_type));
    }

    private ArrayList<EventItem> getAllStudentEvents() throws JSONException {
        String stringResult = HttpFactory.httpPost(HttpFactory.STUDENT_EVENTS_URL, FacadePreferences.getLoginDataFromPref(appContext), "");
        if (stringResult != null) {
            JSONObject jsonObject = new JSONObject(stringResult);
        }
        return null;
    }

    private ArrayList<EventItem> getAllTeacherEvents() throws JSONException {
        String stringResult = HttpFactory.httpPost(HttpFactory.STUDENT_EVENTS_URL, FacadePreferences.getLoginDataFromPref(appContext), "");
        return null;
    }

    ArrayList<EventItem> loadMoreEvents(int start, String params) throws JSONException {
//        String jsonBody = "\"start\":\""+start+"\"";
        String stringResult = HttpFactory.httpPost(HttpFactory.STUDENT_EVENTS_URL, FacadePreferences.getLoginDataFromPref(appContext), params);
        return null;
    }

    private ArrayList<EventItem> getEventsDataFromJson(String eventsJsonStr)
            throws JSONException {
        ArrayList<EventItem> displayEventsArray = new ArrayList<>();

        JSONObject eventJson = new JSONObject(eventsJsonStr);
        JSONArray eventsArray = eventJson.getJSONArray("events");

        for (int i = 0; i < eventsArray.length(); i++) {
            EventItem item = new EventItem();
            EventItem.EventParams params = item.getParams();
            JSONObject event = eventsArray.getJSONObject(i);
            int type = Integer.parseInt(event.getString("type"));
            JSONObject paramsJson = new JSONObject(event.getString("params"));
            String displayEvent = makeEvent(type, paramsJson);
            String time = FacadeCommon.makeDate(event.getString("time"));

            if (type != 6) {
                if (paramsJson.has("type")) {
                    params.setType(paramsJson.getInt("type"));
                }
                if (paramsJson.has("id")) {
                    params.setId(paramsJson.getInt("id"));
                }
                if (paramsJson.has("name")) {
                    params.setName(paramsJson.getString("name"));
                }
                if (paramsJson.has("photo")) {
                    params.setPhoto(paramsJson.getInt("photo"));
                }
                if (paramsJson.has("gcourse")) {
                    params.setGcourse(paramsJson.getInt("gcourse"));
                }
                if (paramsJson.has("courseName")) {
                    params.setCourseName(paramsJson.getString("courseName"));
                }
                if (paramsJson.has("hourType")) {
                    params.setHourType(paramsJson.getInt("hourType"));
                }
                if (paramsJson.has("code")) {
                    params.setCode(paramsJson.getString("code"));
                }
            } else {
                try {
                    int param_type = paramsJson.getInt("type");
                    params.setType(param_type);
                } catch (JSONException ignored) {
                }
            }
            item.setParams(params);
            item.setEventText(displayEvent);
            item.setTime(time);
            item.setSeen(Integer.parseInt(event.getString("seen")));
            item.setType(type);
            displayEventsArray.add(item);
        }
        return displayEventsArray;
    }

    private String makeEvent(int type, JSONObject params)
            throws NumberFormatException, JSONException {
        String result = "";
        String[] hourTypes = new String[]{"протокол занятия",
                "протокол рубежного контроля", "экзаменационную ведомость",
                "индивидуальное занятие"};
        String courseName;
        String name;
        switch (type) {

            case 1:
                courseName = params.getString("courseName");
                result = "Загружен материал по дисциплине " + courseName + ".";
                break;

            case 2:
                int hourType = Integer.parseInt(params.getString("hourType"));
                courseName = params.getString("courseName");
                name = params.getString("name");
                result = "Преподаватель " + name + " заполнил "
                        + hourTypes[hourType] + " по дисциплине " + courseName
                        + ".";
                break;

            case 3:
                String semestr = params.getString("semester");
                String year = params.getString("year");
                result = "Вы произвели оплату за " + semestr + "-й семестр " + year
                        + " учебного года.";
                break;

            case 4:
                String eventName = params.getString("eventName");
                String date = params.getString("date");
                result = "Вы приглашены на участие в мероприятии " + eventName
                        + ", которое состоится " + date;
                break;

            case 5:
                name = params.getString("name");
                String author = params.getString("author");
                result = "В книжную полку добавлена книга " + name + ", " + author;
                break;

            case 6:
                String message = "";
                if (params.has("type")) {
                    String t = params.getString("type");
                    if (t.equals("1")) {
                        message = "Ваша фотография отклонена из-за несоответствия условиям загрузки личной фотографии.";
                    }
                } else {
                    message = params.getString("message");
                }
                result = "СИСТЕМНОЕ СООБЩЕНИЕ: \n" + message;
                break;
        }
        return result;
    }
}