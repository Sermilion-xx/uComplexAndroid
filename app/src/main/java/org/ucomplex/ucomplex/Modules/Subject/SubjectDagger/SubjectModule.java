package org.ucomplex.ucomplex.Modules.Subject.SubjectDagger;

import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDetails.SubjectDetailsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelineModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectTimeline.SubjectTimelinePresenter;

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
    SubjectDetailsPresenter getSubjectDetailsPresenter(){
        return new SubjectDetailsPresenter();
    }

    @Provides
    @Singleton
    SubjectDetailsModel getSubjectDetailsModel(){
        return new SubjectDetailsModel();
    }

    @Provides
    @Singleton
    SubjectMaterialsPresenter getSubjectMaterialsPresenter(){
        return new SubjectMaterialsPresenter();
    }

    @Provides
    @Singleton
    SubjectMaterialsModel getSubjectMaterialsModel(){
        return new SubjectMaterialsModel();
    }

    @Provides
    @Singleton
    SubjectTimelinePresenter getSubjectTimelinePresenter(){
        return new SubjectTimelinePresenter();
    }

    @Provides
    @Singleton
    SubjectTimelineModel getSubjectTimelineModel(){
        return new SubjectTimelineModel();
    }
}