package org.ucomplex.ucomplex.BaseComponents;

import android.app.Application;

import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsDagger.DaggerEventsDiComponent;
import org.ucomplex.ucomplex.Modules.Events.EventsDagger.EventsDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.DaggerLoginDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.LoginDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.DaggerRoleDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.RoleDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.DaggerSubjectDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.SubjectDiComponent;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger.DaggerSubjectsListDiComponent;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger.SubjectsListDiComponent;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 10/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class DaggerApplication extends Application{

    public String authString;
    private UserInterface sharedUser;
    private LoginDiComponent loginDiComponent;
    private RoleDiComponent roleDiComponent;
    private EventsDiComponent eventsDiComponent;
    private SubjectsListDiComponent subjectsListDiComponent;
    private SubjectDiComponent subjectDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedUser              = FacadePreferences.getUserDataFromPref(this);
        loginDiComponent        = DaggerLoginDiComponent.builder().build();
        roleDiComponent         = DaggerRoleDiComponent.builder().build();
        eventsDiComponent       = DaggerEventsDiComponent.builder().build();
        subjectsListDiComponent = DaggerSubjectsListDiComponent.builder().build();
        subjectDiComponent      = DaggerSubjectDiComponent.builder().build();
    }

    public LoginDiComponent getLoginDiComponent() {
        return loginDiComponent;
    }

    public RoleDiComponent getRoleDiComponent() {
        return roleDiComponent;
    }

    public EventsDiComponent getEventsDiComponent() {
        return eventsDiComponent;
    }

    public SubjectsListDiComponent getSubjectsListDiComponent() {
        return subjectsListDiComponent;
    }

    public SubjectDiComponent getSubjectDiComponent(){
        return subjectDiComponent;
    }

    public UserInterface getSharedUser() {
        if(sharedUser == null){
            sharedUser = FacadePreferences.getUserDataFromPref(this);
        }
        return sharedUser;
    }

    public void setSharedUser(UserInterface sharedUser) {
        this.sharedUser = sharedUser;
    }

    public String getAuthString() {
        if(authString == null){
            authString = FacadePreferences.getLoginDataFromPref(this);
        }
        return authString;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }

}
