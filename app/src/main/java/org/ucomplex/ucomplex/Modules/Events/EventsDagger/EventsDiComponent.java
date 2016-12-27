package org.ucomplex.ucomplex.Modules.Events.EventsDagger;

import org.ucomplex.ucomplex.Modules.Events.EventsActivity;

import javax.inject.Singleton;

import dagger.Component;
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
@Singleton
@Component(modules = {EventsModule.class})

public interface EventsDiComponent {
    void inject(EventsActivity activity);
}
