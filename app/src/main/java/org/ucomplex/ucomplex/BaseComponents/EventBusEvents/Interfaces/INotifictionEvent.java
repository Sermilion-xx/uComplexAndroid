package org.ucomplex.ucomplex.BaseComponents.EventBusEvents.Interfaces;

import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.BaseEventBusEvent;
import org.ucomplex.ucomplex.BaseComponents.EventBusEvents.EventTypes.NotificationType;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class INotifictionEvent extends BaseEventBusEvent {

    NotificationType notificationType;
}
