package org.ucomplex.ucomplex.Domain.Users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Sermilion on 01/11/2016.
 */

public class User implements UserInterface, Parcelable{

    private int id;
    private String login;
    private String password;
    private String email;
    private String phone;
    private int role;
    private int person;
    private Bitmap photoBitmap;
    private String bitmapUriString;
    private int photo;
    private String code;
    private int client;
    private int type;
    private String session;
    private String name;
    private List<Role> roles;

    public User(){

    }
    public String getBitmapUriStringFromUri(Uri bitmapUri){
        return bitmapUri.toString();
    }

    public Uri getBitmapUriFromUriString(){
        if(bitmapUriString!=null){
            return Uri.parse(bitmapUriString);
        } else {
            return null;
        }

    }

    protected User(Parcel in) {
        id = in.readInt();
        login = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        role = in.readInt();
        person = in.readInt();
        photoBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        bitmapUriString = in.readParcelable(Uri.class.getClassLoader());
        photo = in.readInt();
        code = in.readString();
        client = in.readInt();
        type = in.readInt();
        session = in.readString();
        name = in.readString();
        roles = in.createTypedArrayList(Role.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void addRole(Role role){
        roles.add(role);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(login);
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeInt(role);
        parcel.writeInt(person);
        parcel.writeParcelable(photoBitmap, i);
        parcel.writeString(bitmapUriString);
        parcel.writeInt(photo);
        parcel.writeString(code);
        parcel.writeInt(client);
        parcel.writeInt(type);
        parcel.writeString(session);
        parcel.writeString(name);
        parcel.writeTypedList(roles);
    }


    protected class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        public byte[] imageByteArray;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {

        if(this.photoBitmap!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            BitmapDataObject bitmapDataObject = new BitmapDataObject();
            bitmapDataObject.imageByteArray = stream.toByteArray();
            out.writeObject(bitmapDataObject);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        if(this.photoBitmap!=null) {
            BitmapDataObject bitmapDataObject = (BitmapDataObject) in.readObject();
            this.photoBitmap = BitmapFactory.decodeByteArray(bitmapDataObject.imageByteArray, 0, bitmapDataObject.imageByteArray.length);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    @Override
    public String getBitmapUriString() {
        return bitmapUriString;
    }

    @Override
    public void setBitmapUriString(String bitmapUriString) {
        this.bitmapUriString = bitmapUriString;
    }

    @Override
    public int getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(int photo) {
        this.photo = photo;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }
}
