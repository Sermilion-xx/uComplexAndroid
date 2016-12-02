package org.ucomplex.ucomplex.Modules.Subjects.SubjectsDagger;

import org.ucomplex.ucomplex.Modules.Subjects.SubjectsModel;
import org.ucomplex.ucomplex.Modules.Subjects.SubjectsPresenter;
import org.ucomplex.ucomplex.Modules.Subjects.SubjectsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Module
public class SubjectsModule {
    @Provides
    @Singleton
    public SubjectsPresenter getSubjectsPresenter(){
        return new SubjectsPresenter();
    }

    @Provides
    @Singleton
    public SubjectsModel getSubjectsModel(){
        return new SubjectsModel();
    }

    @Provides
    @Singleton
    public SubjectsRepository getSubjectsRepository(){
        return new SubjectsRepository();
    }
}
