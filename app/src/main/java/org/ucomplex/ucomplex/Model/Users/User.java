package org.ucomplex.ucomplex.Model.Users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.Data;

/**
 * Created by Sermilion on 01/11/2016.
 */
@Data
public class User {

    private String login;
    private String password;
    private int role;
    private Bitmap photoBitmap;
    private int type;
    private String name;

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
