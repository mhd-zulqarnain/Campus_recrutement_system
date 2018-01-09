package com.example.zulqarnain.campusrecruitment.services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Zul Qarnain on 11/22/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        if(auth.getCurrentUser()!=null){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid()).child("token");
            ref.setValue(refreshedToken);
        }

        Log.d("token", "onTokenRefresh: " + refreshedToken);

    }
}
