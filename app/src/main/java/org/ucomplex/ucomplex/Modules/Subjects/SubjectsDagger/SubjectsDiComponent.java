package org.ucomplex.ucomplex.Modules.Subjects.SubjectsDagger;

import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectActivity;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.RoleModule;
import org.ucomplex.ucomplex.Modules.Subjects.SubjectsActivity;

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
@Component(modules = {SubjectsModule.class})
public interface SubjectsDiComponent {
    void inject(SubjectsActivity activity);
}
