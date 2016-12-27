package org.ucomplex.ucomplex.Modules.Subject.SubjectDagger;

import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectRepository;

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
    SubjectPresenter getSubjectPresenter(){
        return new SubjectPresenter();
    }

    @Provides
    @Singleton
    SubjectModel getSubjectModel(){
        return new SubjectModel();
    }

    @Provides
    @Singleton
    SubjectRepository getSubjectRepository(){
        return new SubjectRepository();
    }
}