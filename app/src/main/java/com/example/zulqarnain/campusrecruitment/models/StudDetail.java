package com.example.zulqarnain.campusrecruitment.models;

/**
 * Created by Zul Qarnain on 12/27/2017.
 */

public class StudDetail {
    private String name;
    private Student details;
    private String uid;

    public StudDetail(String name, Student detail) {
        this.name = name;
        this.details = detail;
    }

    public StudDetail(String name, Student detail,String uid) {
        this.name = name;
        this.details = detail;
        this.uid=uid;
    }

    public StudDetail() {
    }

    public Student getDetails() {
        return details;
    }

    public void setDetails(Student details) {
        this.details = details;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getDetail() {
        return details;
    }

    public void setDetail(Student detail) {
        this.details = detail;
    }
}

