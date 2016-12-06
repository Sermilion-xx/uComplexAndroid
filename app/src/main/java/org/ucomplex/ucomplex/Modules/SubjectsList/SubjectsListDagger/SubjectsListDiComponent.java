package org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger;

import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Singleton
@Component(modules = {SubjectsListModule.class})
public interface SubjectsListDiComponent {
    void inject(SubjectsListActivity activity);
}
