package com.example.zulqarnain.campusrecruitment.company;

/**
 * Created by Zul Qarnain on 11/7/2017.
 */

public class Jobs {

    private String jobType;
    private String jobKey;
    private String jobDescription;
    private String jobVacancy;

    public Jobs(String jobType, String jobKey, String jobDescription, String jobVacancy) {
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.jobVacancy = jobVacancy;
        this.jobKey = jobKey;
    }

    public Jobs(){}
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobVacancy() {
        return jobVacancy;
    }

    public void setJobVacancy(String jobVacancy) {
        this.jobVacancy = jobVacancy;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
