package org.ucomplex.ucomplex.Model.Users;

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
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Sermilion on 01/11/2016.
 */
@Data
public class User implements UserInterface, Parcelable{

    private int id;
    private String login;
    private String password;
    private String email;
    private String phone;
    private int role;
    private int person;
    private Bitmap photoBitmap;
    private Uri bitmapUri;
    private int photo;
    private String code;
    private int client;
    private int type;
    private String session;
    private String name;
    private List<Role> roles;

    public User(){

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
        bitmapUri = in.readParcelable(Uri.class.getClassLoader());
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
        parcel.writeParcelable(bitmapUri, i);
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
}
