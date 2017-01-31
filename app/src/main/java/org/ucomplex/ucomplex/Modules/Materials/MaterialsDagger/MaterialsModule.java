package org.ucomplex.ucomplex.Modules.Materials.MaterialsDagger;

import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;

import javax.inject.Singleton;

import dagger.Module;
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

@Module
class MaterialsModule {

    @Provides
    @Singleton
    SubjectMaterialsPresenter getMaterialsPresenter(){
        return new SubjectMaterialsPresenter();
    }

    @Provides
    @Singleton
    SubjectMaterialsModel getMaterialsModel(){
        return new SubjectMaterialsModel();
    }

}
