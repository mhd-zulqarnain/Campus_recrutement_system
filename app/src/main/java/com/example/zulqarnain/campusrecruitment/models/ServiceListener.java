package com.example.zulqarnain.campusrecruitment.models;

/**
 * Created by Zul Qarnain on 3/8/2018.
 */

public interface ServiceListener<T> {
    public void success(T obj);
    public void error(ServiceError serviceError);

}
