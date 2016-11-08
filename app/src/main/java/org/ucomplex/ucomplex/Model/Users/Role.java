package org.ucomplex.ucomplex.Model.Users;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */
@Data
public class Role implements Parcelable{

    private int id;
    private int person;
    private int type;
    private String name;

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
}
