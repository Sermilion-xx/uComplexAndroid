package org.ucomplex.ucomplex.BaseComponents.EventBusEvents;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.EventType;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Interfaces.IEventBusEvent;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public abstract class BaseEventBusEvent implements IEventBusEvent {

    private EventType eventType;

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
