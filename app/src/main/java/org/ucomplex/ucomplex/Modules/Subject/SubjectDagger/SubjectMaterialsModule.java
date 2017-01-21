package org.ucomplex.ucomplex.Modules.Subject.SubjectDagger;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPPresenterRecycler;

import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 19/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Module
public class SubjectMaterialsModule {

    @Provides
    @Singleton
    MVPPresenterRecycler<String, List<IRecyclerItem>> getSubjectMaterialsPresenter(){
        return new SubjectMaterialsPresenter();
    }

    @Provides
    @Singleton
    MVPModelRecycler<String, List<IRecyclerItem>> getSubjectMaterialsModel(){
        return new SubjectMaterialsModel();
    }
}
