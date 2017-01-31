package org.ucomplex.ucomplex.Modules.Materials.MaterialsDagger;

import org.ucomplex.ucomplex.Modules.Materials.MaterialsActivity;

import javax.inject.Singleton;

import dagger.Component;

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
@Component(modules = {MaterialsModule.class})
public interface MaterialsDiComponent {
    void inject(MaterialsActivity activity);
}
