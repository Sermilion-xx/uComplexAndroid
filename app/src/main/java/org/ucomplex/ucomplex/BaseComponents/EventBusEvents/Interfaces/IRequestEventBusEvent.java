package org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Interfaces;

import com.android.volley.VolleyError;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.EventType;

import java.util.List;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public interface IRequestEventBusEvent extends IEventBusEvent {

    EventType getEventType();
    void setEventType(EventType eventType);

    String getResult();
    void setResult(String result);

    void addOptionalData(Object object);
    List<Object> getOptionalData();
    Object getDataAtIndex(int i);

    boolean hasError();
    void setError(VolleyError error);

}
