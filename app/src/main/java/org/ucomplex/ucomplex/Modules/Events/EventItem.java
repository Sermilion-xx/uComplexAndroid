package org.ucomplex.ucomplex.Modules.Events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



/**
 * ---------------------------------------------------
 * Created by Sermilion on 01/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
//extend IRecyclerItem for mvp
class EventItem extends IRecyclerItem implements Parcelable {
    private int id;
    private EventParams params;
    private int type;
    private String time;
    private int seen;
    private Bitmap eventImageBitmap;
    private String eventText;

    public static Creator<EventItem> getCREATOR() {
        return CREATOR;
    }

    EventItem() {
        params = new EventParams();
    }

    private EventItem(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        time = in.readString();
        seen = in.readInt();
        eventImageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        eventText = in.readString();
    }

    public static final Creator<EventItem> CREATOR = new Creator<EventItem>() {
        @Override
        public EventItem createFromParcel(Parcel in) {
            return new EventItem(in);
        }

        @Override
        public EventItem[] newArray(int size) {
            return new EventItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(type);
        parcel.writeString(time);
        parcel.writeInt(seen);
        parcel.writeParcelable(eventImageBitmap, i);
        parcel.writeString(eventText);
    }


    private class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        byte[] imageByteArray;
    }

    /**
     * Included for serialization - write this layer to the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeObject(this.params);
        out.writeInt(this.type);
        out.writeObject(this.time);
        out.writeInt(this.seen);
        out.writeObject(this.eventText);

        if (this.eventImageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            eventImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            BitmapDataObject bitmapDataObject = new BitmapDataObject();
            bitmapDataObject.imageByteArray = stream.toByteArray();
            out.writeObject(bitmapDataObject);
        }
    }

    /**
     * Included for serialization - read this object from the supplied input stream.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        this.params = (EventParams) in.readObject();
        this.type = in.readInt();
        this.time = (String) in.readObject();
        this.seen = in.readInt();
        this.eventText = (String) in.readObject();

        if (this.eventImageBitmap != null) {
            BitmapDataObject bitmapDataObject = (BitmapDataObject) in.readObject();
            this.eventImageBitmap = BitmapFactory.decodeByteArray(bitmapDataObject.imageByteArray, 0, bitmapDataObject.imageByteArray.length);
        }

    }

    class EventParams implements Parcelable {

        private String message;
        private String name;
        private int id;
        private int photo;
        private String code;
        private int gcourse;
        private String courseName;
        private int hourType;
        private int type;

        EventParams() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPhoto() {
            return photo;
        }

        public void setPhoto(int photo) {
            this.photo = photo;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getGcourse() {
            return gcourse;
        }

        public void setGcourse(int gcourse) {
            this.gcourse = gcourse;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getHourType() {
            return hourType;
        }

        public void setHourType(int hourType) {
            this.hourType = hourType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Creator<EventParams> getCREATOR() {
            return CREATOR;
        }

        EventParams(Parcel in) {
            message = in.readString();
            name = in.readString();
            id = in.readInt();
            photo = in.readInt();
            code = in.readString();
            gcourse = in.readInt();
            courseName = in.readString();
            hourType = in.readInt();
            type = in.readInt();
        }

        public final Creator<EventParams> CREATOR = new Creator<EventParams>() {
            @Override
            public EventParams createFromParcel(Parcel in) {
                return new EventParams(in);
            }

            @Override
            public EventParams[] newArray(int size) {
                return new EventParams[size];
            }
        };
        
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(this.name);
            out.writeObject(this.code);
            out.writeObject(this.courseName);
            out.writeInt(this.gcourse);
            out.writeInt(this.hourType);
            out.writeInt(this.id);
            out.writeInt(this.photo);
            out.writeInt(this.type);
        }

        /**
         * Included for serialization - read this object from the supplied input stream.
         */
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            this.name = (String) in.readObject();
            this.code = (String) in.readObject();
            this.courseName = (String) in.readObject();
            this.gcourse = in.readInt();
            this.hourType = in.readInt();
            this.id = in.readInt();
            this.photo = in.readInt();
            this.type = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(message);
            parcel.writeString(name);
            parcel.writeInt(id);
            parcel.writeInt(photo);
            parcel.writeString(code);
            parcel.writeInt(gcourse);
            parcel.writeString(courseName);
            parcel.writeInt(hourType);
            parcel.writeInt(type);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventParams getParams() {
        return params;
    }

    public void setParams(EventParams params) {
        this.params = params;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public Bitmap getEventImageBitmap() {
        return eventImageBitmap;
    }

    public void setEventImageBitmap(Bitmap eventImageBitmap) {
        this.eventImageBitmap = eventImageBitmap;
    }

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }
}
