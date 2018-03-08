package com.example.zulqarnain.campusrecruitment.models;

/**
 * Created by Zul Qarnain on 3/8/2018.
 */

public class Company {
    String name;
    String uid;

    public Company() {
    }

    public Company(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
