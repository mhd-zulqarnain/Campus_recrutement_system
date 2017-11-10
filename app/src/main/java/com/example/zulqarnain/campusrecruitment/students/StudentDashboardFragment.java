package com.example.zulqarnain.campusrecruitment.students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zul Qarnain on 11/6/2017.
 */

public class StudentDashboardFragment extends Fragment {
    private Spinner mSpinner;
    private RecyclerView mRecyclerDash;
    private DatabaseReference ref;
    private String TAG = "test";
    ArrayList<Jobs> jobList;
    AdapterNewJob adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.student_dashboard_fragment, container, false);
        mSpinner = v.findViewById(R.id.std_dash_nav_type);
        mRecyclerDash = v.findViewById(R.id.std_recycler_dash);
        mRecyclerDash.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.dash_type,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        updateUi();
//        checkUi();
        return v;
    }

    private void updateUi() {
        jobList = new ArrayList<>();
        adapter = new AdapterNewJob(getContext(),jobList);
        mRecyclerDash.setAdapter(adapter);
        String option = mSpinner.getSelectedItem().toString();
        if (option.equals("New Jobs")) {
            Log.d(TAG, "updateUi: ");
            ref = FirebaseDatabase.getInstance().getReference("company");

            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    DataSnapshot snaps = dataSnapshot.child("jobs");
                    for (DataSnapshot model : snaps.getChildren()) {
                        Jobs job = model.getValue(Jobs.class);
                        jobList.add(job);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                   /* DataSnapshot snaps = dataSnapshot.child("jobs");
                    for (DataSnapshot model : snaps.getChildren()) {
                        Jobs job = model.getValue(Jobs.class);
                        String index=job.getJobKey();
                        Log.d(TAG, "onChildChanged: index "+index);
                        *//*jobList.set(index,job);
                        adapter.notifyItemChanged(index);*//*
                    }*/
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                   /* DataSnapshot snaps = dataSnapshot.child("jobs");
                    for (DataSnapshot model : snaps.getChildren()) {
                        Jobs job = model.getValue(Jobs.class);
                        int index= jobList.indexOf(job);
                        jobList.remove(job);
                        adapter.notifyItemRemoved(index);
                    }*/
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        if (option.equals("Applied")) {


        }
    }

    /*private void checkUi(){
        jobList = new ArrayList<>();
        adapter = new AdapterNewJob(getContext(),jobList);
        mRecyclerDash.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference("company").getRef().child("jobs");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: "+dataSnapshot);
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
    }*/
    public Jobs getIndex(){
        for(int i=0;i<jobList.size();i++){
//            if( )
        }
        return null;
    }
}
