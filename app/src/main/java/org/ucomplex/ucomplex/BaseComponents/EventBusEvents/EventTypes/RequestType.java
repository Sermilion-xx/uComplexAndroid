package org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public enum RequestType implements EventType{
    HTTP_SEND, HTTP_RECEIVE, HTTP_LOAD_MORE, DB_INSERT, DB_UPDATE, DB_DELETE
}