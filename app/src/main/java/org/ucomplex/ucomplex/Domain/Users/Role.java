package org.ucomplex.ucomplex.Domain.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class Role implements Parcelable{

    private int id;
    private int person;
    private int type;
    private String name;
    private int role;
    private int group;
    private int position;
    private int major;
    private int study;
    private int year;
    private int payment;
    private int contract_year;
    private String position_name;
    @SerializedName("black")
    private BlackList black;
    @SerializedName("friends")
    private FriendList friends;

    public Role() {
    }

    protected Role(Parcel in) {
        id = in.readInt();
        person = in.readInt();
        type = in.readInt();
        name = in.readString();
        role = in.readInt();
        group = in.readInt();
        position = in.readInt();
        major = in.readInt();
        study = in.readInt();
        year = in.readInt();
        payment = in.readInt();
        contract_year = in.readInt();
        position_name = in.readString();
        black = in.readParcelable(BlackList.class.getClassLoader());
        friends = in.readParcelable(FriendList.class.getClassLoader());

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
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(person);
        parcel.writeInt(type);
        parcel.writeString(name);
        parcel.writeInt(role);
        parcel.writeInt(group);
        parcel.writeInt(position);
        parcel.writeInt(major);
        parcel.writeInt(study);
        parcel.writeInt(year);
        parcel.writeInt(payment);
        parcel.writeInt(contract_year);
        parcel.writeString(position_name);
        parcel.writeParcelable(black, flags);
        parcel.writeParcelable(friends, flags);
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getContract_year() {
        return contract_year;
    }

    public void setContract_year(int contract_year) {
        this.contract_year = contract_year;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public BlackList getBlack() {
        return black;
    }

    public void setBlack(BlackList black) {
        this.black = black;
    }

    public FriendList getFriends() {
        return friends;
    }

    public void setFriends(FriendList friends) {
        this.friends = friends;
    }
}
