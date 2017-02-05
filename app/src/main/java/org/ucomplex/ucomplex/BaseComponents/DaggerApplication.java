package org.ucomplex.ucomplex.BaseComponents;

import android.app.Application;

import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;
import org.ucomplex.ucomplex.CommonDependencies.Network.NullHostNameVerifier;
import org.ucomplex.ucomplex.Domain.Users.UserInterface;
import org.ucomplex.ucomplex.Modules.Events.EventsDagger.DaggerEventsDiComponent;
import org.ucomplex.ucomplex.Modules.Events.EventsDagger.EventsDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.DaggerLoginDiComponent;
import org.ucomplex.ucomplex.Modules.Login.LoginDagger.LoginDiComponent;
import org.ucomplex.ucomplex.Modules.Materials.MaterialsDagger.DaggerMaterialsDiComponent;
import org.ucomplex.ucomplex.Modules.Materials.MaterialsDagger.MaterialsDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.DaggerRoleDiComponent;
import org.ucomplex.ucomplex.Modules.RoleSelect.RoleSelectDagger.RoleDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.DaggerSubjectDetailsDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.DaggerSubjectMaterialsDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.DaggerSubjectTimelineDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.SubjectDetailsDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.SubjectMaterialsDiComponent;
import org.ucomplex.ucomplex.Modules.Subject.SubjectDagger.SubjectTimelineDiComponent;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger.DaggerSubjectsListDiComponent;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListDagger.SubjectsListDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.DaggerUsersBlackListDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.DaggerUsersFriendsDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.DaggerUsersGroupDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.DaggerUsersLecturersDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.DaggerUsersOnlineDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.UsersBlackListDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.UsersFriendsDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.UsersGroupDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.UsersLecturersDiComponent;
import org.ucomplex.ucomplex.Modules.Users.UsersDagger.UsersOnlineDiComponent;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    private String authString;
    private UserInterface sharedUser;
    private LoginDiComponent loginDiComponent;
    private RoleDiComponent roleDiComponent;
    private EventsDiComponent eventsDiComponent;
    private SubjectsListDiComponent subjectsListDiComponent;
    private SubjectDetailsDiComponent subjectDetailsDiComponent;
    private SubjectMaterialsDiComponent subjectMaterialsDiComponent;
    private SubjectTimelineDiComponent subjectTimelineDiComponent;
    private MaterialsDiComponent materialsDiComponent;
    private UsersOnlineDiComponent usersOnlineDiComponent;
    private UsersFriendsDiComponent usersFriendsDiComponent;
    private UsersGroupDiComponent usersGroupDiComponent;
    private UsersLecturersDiComponent usersLecturersDiComponent;
    private UsersBlackListDiComponent usersBlackListDiComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        sharedUser                  = FacadePreferences.getUserDataFromPref(this);
        loginDiComponent            = DaggerLoginDiComponent.builder().build();
        roleDiComponent             = DaggerRoleDiComponent.builder().build();
        eventsDiComponent           = DaggerEventsDiComponent.builder().build();
        subjectsListDiComponent     = DaggerSubjectsListDiComponent.builder().build();
        subjectDetailsDiComponent   = DaggerSubjectDetailsDiComponent.builder().build();
        subjectMaterialsDiComponent = DaggerSubjectMaterialsDiComponent.builder().build();
        subjectTimelineDiComponent  = DaggerSubjectTimelineDiComponent.builder().build();
        materialsDiComponent        = DaggerMaterialsDiComponent.builder().build();
        usersOnlineDiComponent      = DaggerUsersOnlineDiComponent.builder().build();
        usersFriendsDiComponent     = DaggerUsersFriendsDiComponent.builder().build();
        usersGroupDiComponent       = DaggerUsersGroupDiComponent.builder().build();
        usersLecturersDiComponent   = DaggerUsersLecturersDiComponent.builder().build();
        usersBlackListDiComponent   = DaggerUsersBlackListDiComponent.builder().build();
        configureConnectionTrust();
    }

    void configureConnectionTrust(){
        HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
        SSLContext context;
        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
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


    public SubjectDetailsDiComponent getSubjectDetailsDiComponent(){
        return subjectDetailsDiComponent;
    }

    public SubjectMaterialsDiComponent getSubjectMaterialsDiComponent() {
        return subjectMaterialsDiComponent;
    }

    public SubjectTimelineDiComponent getSubjectTimelineDiComponent() {
        return subjectTimelineDiComponent;
    }

    public MaterialsDiComponent getMaterialsDiComponent() {
        return materialsDiComponent;
    }

    public UsersOnlineDiComponent getUsersOnlineDiComponent() {
        return usersOnlineDiComponent;
    }

    public UsersFriendsDiComponent getUsersFriendsDiComponent() {
        return usersFriendsDiComponent;
    }

    public UsersLecturersDiComponent getUsersLecturersDiComponent() {
        return usersLecturersDiComponent;
    }

    public UsersGroupDiComponent getUsersGroupDiComponent() {
        return usersGroupDiComponent;
    }

    public void setSharedUser(UserInterface sharedUser) {
        this.sharedUser = sharedUser;
    }

    public UsersBlackListDiComponent getUsersBlackListDiComponent() {
        return usersBlackListDiComponent;
    }

    public UserInterface getSharedUser() {
        if(sharedUser == null){
            sharedUser = FacadePreferences.getUserDataFromPref(this);
        }
        return sharedUser;
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


    private static TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    // not implemented
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    // not implemented
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            }
    };

}
