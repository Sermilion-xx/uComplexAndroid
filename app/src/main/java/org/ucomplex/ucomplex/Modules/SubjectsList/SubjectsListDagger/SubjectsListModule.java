package org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger;

import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListModel;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListPresenter;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListRepository;

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
public class SubjectsListModule {
    @Provides
    @Singleton
    public SubjectsListPresenter getSubjectsPresenter(){
        return new SubjectsListPresenter();
    }

    @Provides
    @Singleton
    public SubjectsListModel getSubjectsModel(){
        return new SubjectsListModel();
    }

    @Provides
    @Singleton
    public SubjectsListRepository getSubjectsRepository(){
        return new SubjectsListRepository();
    }
}
