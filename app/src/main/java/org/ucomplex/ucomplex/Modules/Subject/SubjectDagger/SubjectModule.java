package org.ucomplex.ucomplex.Modules.Subject.SubjectDagger;

import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Module
public class SubjectModule {
    @Provides
    @Singleton
    SubjectDetailsPresenter getSubjectPresenter(){
        return new SubjectDetailsPresenter();
    }

    @Provides
    @Singleton
    SubjectDetailsModel getSubjectModel(){
        return new SubjectDetailsModel();
    }
}