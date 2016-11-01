package org.ucomplex.ucomplex.Model.Events;

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
public class EventRowItem implements Serializable {
    private int id;
    private EventParams params;
    private int type;
    private String time;
    private int seen;
    private Bitmap eventImageBitmap;
    private String eventText;

    public EventRowItem(){

    }

    public EventRowItem id(int id){
        this.id = id;
        return this;
    }

    public EventRowItem params(EventParams params){
        this.params = params;
        return this;
    }

    public EventRowItem type(int type){
        this.type = type;
        return this;
    }

    public EventRowItem time(String time){
        this.time = time;
        return this;
    }

    public EventRowItem seen(int seen){
        this.seen = seen;
        return this;
    }
    public EventRowItem eventImageBitmap(Bitmap eventImageBitmap){
        this.eventImageBitmap = eventImageBitmap;
        return this;
    }

    public EventRowItem eventText(String eventText){
        this.eventText = eventText;
        return this;
    }

    private class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        byte[] imageByteArray;
    }

    /** Included for serialization - write this layer to the output stream. */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeObject(this.params);
        out.writeInt(this.type);
        out.writeObject(this.time);
        out.writeInt(this.seen);
        out.writeObject(this.eventText);

        if(this.eventImageBitmap!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            eventImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            BitmapDataObject bitmapDataObject = new BitmapDataObject();
            bitmapDataObject.imageByteArray = stream.toByteArray();
            out.writeObject(bitmapDataObject);
        }
    }

    /** Included for serialization - read this object from the supplied input stream. */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        this.id = in.readInt();
        this.params = (EventParams) in.readObject();
        this.type = in.readInt();
        this.time = (String) in.readObject();
        this.seen = in.readInt();
        this.eventText = (String) in.readObject();

        if(this.eventImageBitmap!=null) {
            BitmapDataObject bitmapDataObject = (BitmapDataObject) in.readObject();
            this.eventImageBitmap = BitmapFactory.decodeByteArray(bitmapDataObject.imageByteArray, 0, bitmapDataObject.imageByteArray.length);
        }

    }
}
