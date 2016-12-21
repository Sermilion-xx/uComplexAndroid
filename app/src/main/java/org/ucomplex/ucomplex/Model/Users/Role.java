package org.ucomplex.ucomplex.Model.Users;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class Role implements Parcelable, Comparable{

    private int id;
    private int person;
    private int type;
    private String name;

    public Role() {
    }

    protected Role(Parcel in) {
        id = in.readInt();
        person = in.readInt();
        type = in.readInt();
        name = in.readString();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(person);
        parcel.writeInt(type);
        parcel.writeString(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Creator<Role> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int compareTo(Object o) {
        Role role = (Role) o;
        if(this.name.equals(role.getName()) &&
                this.type == role.getType() &&
                this.id == role.getId() &&
                this.person == role.getPerson()){
            return 0;

        }else if(this.type<role.getType()){
            return 1;
        }else {
            return -1;
        }

    }
}
