package org.ucomplex.ucomplex.BaseComponents.EventBusEvents;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.RequestType;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Implementations.BaseHTTPRequestEvent;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventBusFactory {


    public static BaseHTTPRequestEvent getHTTPEvent(RequestType requestType){
//        if(requestType==RequestType.LOGIN){
//            return new LoginRequestEvent();
//        }else if(requestType==RequestType.EVENTS || requestType==RequestType.EVENTS_MORE){
//            return new EventsRequestEvent();
//        }
        return new BaseHTTPRequestEvent(requestType);
    }


}
