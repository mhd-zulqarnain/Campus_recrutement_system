package com.example.zulqarnain.campusrecruitment.company;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zul Qarnain on 11/7/2017.
 */

public class Jobs implements Parcelable {

    private String jobType;
    private String jobKey;
    private String jobDescription;
    private String jobVacancy;
    private String companyKey;

    public Jobs() {
    }

    public Jobs(String jobType, String jobKey, String jobDescription, String jobVacancy, String companyKey) {
        this.jobType = jobType;
        this.jobKey = jobKey;
        this.jobDescription = jobDescription;
        this.jobVacancy = jobVacancy;
        this.companyKey = companyKey;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
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

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }


    /*Making Parcelable object to send in bundle*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jobType);
        dest.writeString(this.jobKey);
        dest.writeString(this.jobDescription);
        dest.writeString(this.jobVacancy);
        dest.writeString(this.companyKey);
    }

    protected Jobs(Parcel in) {
        this.jobType = in.readString();
        this.jobKey = in.readString();
        this.jobDescription = in.readString();
        this.jobVacancy = in.readString();
        this.companyKey = in.readString();
    }

    public static final Parcelable.Creator<Jobs> CREATOR = new Parcelable.Creator<Jobs>() {
        @Override
        public Jobs createFromParcel(Parcel source) {
            return new Jobs(source);
        }

        @Override
        public Jobs[] newArray(int size) {
            return new Jobs[size];
        }
    };
}
