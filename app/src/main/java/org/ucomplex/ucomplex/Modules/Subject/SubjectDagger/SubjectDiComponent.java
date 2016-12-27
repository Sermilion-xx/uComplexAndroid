package org.ucomplex.ucomplex.Modules.Subject.SubjectDagger;

import org.ucomplex.ucomplex.Modules.Login.LoginActivityView;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.LoginModule;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 06/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

@Singleton
@Component(modules = {SubjectModule.class})
public interface SubjectDiComponent {
    void inject(SubjectActivity activity);
}
