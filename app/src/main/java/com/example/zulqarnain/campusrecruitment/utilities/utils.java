package com.example.zulqarnain.campusrecruitment.utilities;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.example.zulqarnain.campusrecruitment.models.ServiceListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zul Qarnain on 11/28/2017.
 */

public class utils {

    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static String usertype = null;
    private static DatabaseReference ref;
    private static String comName="";

    public static String getDeviceName() {
        String maufacaturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(maufacaturer)) {
            return maufacaturer.toUpperCase();
        } else
            return maufacaturer.toLowerCase() + " " + model;
    }

    public static String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static void updateFcm(String refreshedToken) {
        String mKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, String> fcm = new HashMap<String, String>();
        fcm.put("token", refreshedToken);
        fcm.put("device", utils.getDeviceName());

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("users").child(mKey).child("fcm").child(getIMEI());
        ref.setValue(fcm);
    }

    public static String getuseype() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String key = auth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                usertype = dataSnapshot.child("type").getValue(String.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return usertype;

    }

    public static void getCompanyName(String uid , final ServiceListener  listener) {
        ref = FirebaseDatabase.getInstance().getReference("company").child(uid).child("name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comName = (String) dataSnapshot.getValue();
                if(!comName.equals(null))
                listener.success(comName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.success(databaseError);
            }
        });
    }
  /*  public static<T> int getDataIndex(ArrayList<T> data, T elm){
        for(int i = 0 ;i<data.size();i++){
            if(d)
        }
        return  0;
    }*/

}
