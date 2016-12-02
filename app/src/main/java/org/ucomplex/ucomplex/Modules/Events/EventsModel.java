package org.ucomplex.ucomplex.Modules.Events;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.MVP.AbstractMVP.AbstractModel;
import org.ucomplex.ucomplex.Model.Users.UserInterface;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;

import java.util.ArrayList;


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
public class EventsModel extends AbstractModel implements MVP_Events.ModelInterface {

    private static final String KEY_JSON_EVENTS = "events";
    private static final String EVENT_PARAMS = "params";
    private static final String EVENT_TIME = "time";
    private static final String EVENT_ID = "id";
    private static final String EVENT_NAME = "name";
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

    private ArrayList<EventItem> mEventItems;

    public EventsModel() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setData(Object data) {
        if (data instanceof ArrayList)
            this.mEventItems = (ArrayList<EventItem>) data;
        else
            this.mUser = (UserInterface) data;
    }

    @Override
    public UserInterface getUser() {
        return this.mUser;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        super.onDestroy(isChangingConfiguration);
        if (!isChangingConfiguration) {
            mEventItems = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadData() {
        mRepository.loadData();
    }

    @Override
    public void loadMoreEvents(int start) {
        mRepository.loadData(start);
    }

    @Override
    public EventItem getItem(int position) {
        return mEventItems.get(position);
    }

    @Override
    public int getItemCount() {
        if (mEventItems != null)
            return mEventItems.size();
        return 0;
    }

    @Override
    public void onTaskComplete(int requestType, Object... o) {
        try {
            String result = (String) o[0];
            ArrayList<EventItem> newItems = getEventsDataFromJson(result);
            int start;
            int end;
            int oldEnd = -1;
            if (requestType == Constants.REQUEST_MORE_EVENTS) {
                start = mEventItems.size();
                mEventItems.addAll(newItems);
                end = mEventItems.size();
            } else {
                start = 0;
                if(mEventItems==null){
                    oldEnd = newItems.size();
                }else {
                    oldEnd = mEventItems.size() + newItems.size();
                }
                mEventItems = newItems;
                end = newItems.size();

            }
            mOnDataLoadedListener.dataLoaded(result != null, start, end, oldEnd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<EventItem> getEventsDataFromJson(String eventsJsonStr)
            throws JSONException {
        ArrayList<EventItem> displayEventsArray = new ArrayList<>();
        JSONObject eventJson = new JSONObject(eventsJsonStr);
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
