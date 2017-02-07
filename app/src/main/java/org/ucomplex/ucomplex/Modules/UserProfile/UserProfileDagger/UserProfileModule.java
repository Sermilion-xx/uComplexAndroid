package org.ucomplex.ucomplex.Modules.UserProfile.UserProfileDagger;

import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Events.EventsPresenter;
import org.ucomplex.ucomplex.Modules.UserProfile.UserProfileModel;
import org.ucomplex.ucomplex.Modules.UserProfile.UserProfilePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Module
public class UserProfileModule {

    @Provides
    @Singleton
    UserProfilePresenter getUserProfilePresenter(){
        return new UserProfilePresenter();
    }

    @Provides
    @Singleton
    UserProfileModel getUserProfileModel(){
        return new UserProfileModel();
    }

}
