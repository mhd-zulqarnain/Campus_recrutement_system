package com.example.zulqarnain.campusrecruitment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zul Qarnain on 11/16/2017.
 */

public class Student implements Parcelable {
    public String semister;
    public String department;
    public String imgUrl;

    public Student() {
    }

    public Student(String semister, String department, String imgUrl) {
        this.semister = semister;
        this.department = department;
        this.imgUrl = imgUrl;
    }

    public String getSemister() {
        return semister;
    }

    public Student(String semister, String department) {
        this.semister = semister;
        this.department = department;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "image url"+imgUrl+" department"+department;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.semister);
        dest.writeString(this.department);
        dest.writeString(this.imgUrl);
    }

    protected Student(Parcel in) {
        this.semister = in.readString();
        this.department = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}

