package com.example.zulqarnain.campusrecruitment.models;

/**
 * Created by Zul Qarnain on 11/16/2017.
 */

public class Student {
    public String semister;
    public String department;

    public Student(String semister, String department) {
        this.semister = semister;
        this.department = department;
    }

    public Student() {

    }

    public String getSemister() {
        return semister;
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
}
