package org.ucomplex.ucomplex.Modules.Events.EventsDagger;

import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsPresenter;
import org.ucomplex.ucomplex.Modules.Events.EventsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Module
class EventsModule {
    @Provides
    @Singleton
    EventsPresenter getEventsPresenter(){
        return new EventsPresenter();
    }

    @Provides
    @Singleton
    EventsModel getEventsModel(){
        return new EventsModel();
    }

    @Provides
    @Singleton
    EventsRepository getEventsRepository(){
        return new EventsRepository();
    }

}
