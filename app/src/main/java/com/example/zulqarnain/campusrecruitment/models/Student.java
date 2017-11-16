package com.example.zulqarnain.campusrecruitment.models;

/**
 * Created by Zul Qarnain on 11/16/2017.
 */

public class Student {
    public String name;
    public String semister;

    public Student() {
    }

    public Student(String name,String semister) {
        this.semister=semister;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }
}
