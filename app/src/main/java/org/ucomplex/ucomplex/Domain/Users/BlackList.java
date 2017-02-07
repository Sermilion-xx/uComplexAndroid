package org.ucomplex.ucomplex.Domain.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class BlackList implements Parcelable{
    @SerializedName("me_black")
    private boolean me_black;
    @SerializedName("is_black")
    private boolean is_black;

    protected BlackList(Parcel in) {
        me_black = in.readByte() != 0;
        is_black = in.readByte() != 0;
    }

    public static final Creator<BlackList> CREATOR = new Creator<BlackList>() {
        @Override
        public BlackList createFromParcel(Parcel in) {
            return new BlackList(in);
        }

        @Override
        public BlackList[] newArray(int size) {
            return new BlackList[size];
        }
    };

    public boolean isMe_black() {
        return me_black;
    }

    public void setMe_black(boolean me_black) {
        this.me_black = me_black;
    }

    public boolean is_black() {
        return is_black;
    }

    public void setIs_black(boolean is_black) {
        this.is_black = is_black;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (me_black ? 1 : 0));
        dest.writeByte((byte) (is_black ? 1 : 0));
    }
}
