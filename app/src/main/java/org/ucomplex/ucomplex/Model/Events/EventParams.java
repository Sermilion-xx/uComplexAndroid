package org.ucomplex.ucomplex.Model.Events;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.Data;

/**
 * Created by Sermilion on 01/11/2016.
 */
@Data
public class EventParams implements Serializable {

    private String name;
    private int id;
    private int photo;
    private String code;
    private int gcourse;
    private String courseName;
    private int hourType;
    private int type;

    public EventParams(){

    }

    public EventParams id(int id){
        this.id = id;
        return this;
    }

    public EventParams name(String name){
        this.name = name;
        return this;
    }

    public EventParams photo(int photo){
        this.photo = photo;
        return this;
    }
    public EventParams code(String code){
        this.code = code;
        return this;
    }

    public EventParams gcourse(int gcourse){
        this.gcourse = gcourse;
        return this;
    }
    public EventParams courseName(String courseName){
        this.courseName = courseName;
        return this;
    }

    public EventParams hourType(int hourType){
        this.hourType = hourType;
        return this;
    }

    public EventParams type(int type){
        this.type = type;
        return this;
    }

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

    /** Included for serialization - read this object from the supplied input stream. */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        this.name = (String)in.readObject();
        this.code = (String)in.readObject();
        this.courseName = (String)in.readObject();
        this.gcourse = in.readInt();
        this.hourType = in.readInt();
        this.id = in.readInt();
        this.photo = in.readInt();
        this.type = in.readInt();
    }

}
