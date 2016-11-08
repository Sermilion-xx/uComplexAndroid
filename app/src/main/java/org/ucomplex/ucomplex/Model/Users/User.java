package org.ucomplex.ucomplex.Model.Users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

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
public class User {

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

    public void addRole(Role role){
        roles.add(role);
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
