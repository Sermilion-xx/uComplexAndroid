package org.ucomplex.ucomplex.Modules.Events;


import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.AbstractModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.HttpFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;


/**
 * Model layer on Model View PresenterToViewInterface Pattern
 * <p>
 * ---------------------------------------------------
 * Created by @Sermilion on 07/11/16.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
public class EventsModel extends AbstractModelRecycler<String, List<IRecyclerItem>> {

    private static final String KEY_JSON_EVENTS = "events";
    private static final String EVENT_PARAMS = "params";
    private static final String EVENT_TIME = "time";
    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "courseName";
    private static final String EVENT_PHOTO = "photo";
    private static final String EVENT_GCOURSE = "gcourse";
    private static final String EVENT_COURSE_NAME = "courseName";
    private static final String HOUR_TYPE = "hourType";
    private static final String EVENT_HOUR_TYPE = HOUR_TYPE;
    private static final String EVENT_CODE = "code";
    private static final String EVENT_SEEN = "seen";
    private static final String EVENT_EVENT_NAME = "eventName";
    private static final String EVENT_DATE = "date";
    private static final String EVENT_AUTHOR = "author";
    private static final String EVENT_TYPE = "type";
    private static final String EVENT_TYPE_1 = "1";
    private static final String EVENT_MESSAGE = "message";
    private static final String EVENT_SEMESTER = "semester";
    private static final String EVENT_YEAR = "year";
    static final String EVENTS_START = "start";
    public static boolean INITIAL_EVENTS_LOADED = false;


    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth = "";
        HashMap<String, String> params = new HashMap<>();
        int start;
        encodedAuth = bundle[0].getString(AUTH_STRING);
        if (bundle[0].containsKey(EVENTS_START)) {
            start = bundle[0].getInt(EVENTS_START);
            params.put(EVENTS_START, Integer.toString(start));
        }

        HttpFactory.getInstance().httpVolley(HttpFactory.USER_EVENTS_URL,
                encodedAuth,
                mContext,
                params,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        List<IRecyclerItem> newItems = processJson(s);
                        mvpCallback.onSuccess(newItems);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });
    }


    @Override
    public List<IRecyclerItem> processJson(String s) {
        List<IRecyclerItem> displayEventsArray = new ArrayList<>();
        try {
            JSONObject eventJson = new JSONObject(s);
            JSONArray eventsArray = eventJson.getJSONArray(KEY_JSON_EVENTS);

            for (int i = 0; i < eventsArray.length(); i++) {
                EventItem item = new EventItem();
                EventItem.EventParams params = item.getParams();
                JSONObject event = eventsArray.getJSONObject(i);
                int type = Integer.parseInt(event.getString(EVENT_TYPE));
                JSONObject paramsJson = new JSONObject(event.getString(EVENT_PARAMS));
                String displayEvent = makeEvent(type, paramsJson);
                String time = FacadeCommon.makeDate(event.getString(EVENT_TIME));

                if (type != 6) {
                    if (paramsJson.has(EVENT_TYPE)) {
                        params.setType(paramsJson.getInt(EVENT_TYPE));
                    }
                    if (paramsJson.has(EVENT_ID)) {
                        params.setId(paramsJson.getInt(EVENT_ID));
                    }
                    if (paramsJson.has(EVENT_NAME)) {
                        params.setName(paramsJson.getString(EVENT_NAME));
                    }
                    if (paramsJson.has(EVENT_PHOTO)) {
                        params.setPhoto(paramsJson.getInt(EVENT_PHOTO));
                    }
                    if (paramsJson.has(EVENT_GCOURSE)) {
                        params.setGcourse(paramsJson.getInt(EVENT_GCOURSE));
                    }
                    if (paramsJson.has(EVENT_COURSE_NAME)) {
                        params.setCourseName(paramsJson.getString(EVENT_COURSE_NAME));
                    }
                    if (paramsJson.has(EVENT_HOUR_TYPE)) {
                        params.setHourType(paramsJson.getInt(EVENT_HOUR_TYPE));
                    }
                    if (paramsJson.has(EVENT_CODE)) {
                        params.setCode(paramsJson.getString(EVENT_CODE));
                    }
                } else {
                    try {
                        int param_type = paramsJson.getInt(EVENT_TYPE);
                        params.setType(param_type);
                    } catch (JSONException ignored) {
                    }
                }
                item.setParams(params);
                item.setEventText(displayEvent);
                item.setTime(time);
                item.setSeen(Integer.parseInt(event.getString(EVENT_SEEN)));
                item.setType(type);
                displayEventsArray.add(item);
            }
        } catch (JSONException ignored) {
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
                courseName = params.getString(EVENT_COURSE_NAME);
                result = "Загружен материал по дисциплине " + courseName + ".";
                break;

            case 2:
                int hourType = Integer.parseInt(params.getString(HOUR_TYPE));
                courseName = params.getString(EVENT_COURSE_NAME);
                name = params.getString(EVENT_NAME);
                result = "Преподаватель " + name + " заполнил "
                        + hourTypes[hourType] + " по дисциплине " + courseName
                        + ".";
                break;

            case 3:
                String semestr = params.getString(EVENT_SEMESTER);
                String year = params.getString(EVENT_YEAR);
                result = "Вы произвели оплату за " + semestr + "-й семестр " + year
                        + " учебного года.";
                break;

            case 4:
                String eventName = params.getString(EVENT_EVENT_NAME);
                String date = params.getString(EVENT_DATE);
                result = "Вы приглашены на участие в мероприятии " + eventName
                        + ", которое состоится " + date;
                break;

            case 5:
                name = params.getString(EVENT_NAME);
                String author = params.getString(EVENT_AUTHOR);
                result = "В книжную полку добавлена книга " + name + ", " + author;
                break;

            case 6:
                String message = "";
                if (params.has(EVENT_TYPE)) {
                    String t = params.getString(EVENT_TYPE);
                    if (t.equals(EVENT_TYPE_1)) {
                        message = "Ваша фотография отклонена из-за несоответствия условиям загрузки личной фотографии.";
                    }
                } else {
                    message = params.getString(EVENT_MESSAGE);
                }
                result = "СИСТЕМНОЕ СООБЩЕНИЕ: \n" + message;
                break;
        }
        return result;
    }
}
