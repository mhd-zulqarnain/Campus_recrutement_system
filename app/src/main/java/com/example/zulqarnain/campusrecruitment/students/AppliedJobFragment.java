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

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.example.zulqarnain.campusrecruitment.students.adapter.AppliedJobAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zul Qarnain on 11/6/2017.
 */
public class AppliedJobFragment extends Fragment {
    private RecyclerView mRecyclerDash;
    private DatabaseReference ref;
    private String TAG = "test";
    ArrayList<Jobs> jobList;
    AppliedJobAdapter appliedAdapter;
    ChildEventListener listner;
    private HashMap<DatabaseReference, ChildEventListener> hashMap = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.student_profile_fragment, container, false);
        mRecyclerDash = v.findViewById(R.id.std_recycler_dash);
        mRecyclerDash.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUi();
        return v;
    }

    private void updateUi() {
        jobList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("jobs");
        jobList.clear();
        appliedAdapter = new AppliedJobAdapter(getContext(), jobList);
        mRecyclerDash.setAdapter(appliedAdapter);
            listner = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    DataSnapshot snapshot = dataSnapshot.child("details");
                    Jobs job = snapshot.getValue(Jobs.class);
                    jobList.add(job);
                    Log.d(TAG, "onChildAdded: ");
                    appliedAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    DataSnapshot snapshot = dataSnapshot.child("details");
                    Jobs job = snapshot.getValue(Jobs.class);
                    int index = getIndex(job.getJobKey());
                    if (index != -1) {
                        jobList.set(index, job);
                        appliedAdapter.notifyItemChanged(index);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    DataSnapshot snapshot = dataSnapshot.child("details");
                    Jobs job = snapshot.getValue(Jobs.class);
                    int index = getIndex(job.getJobKey());
                    if (index != -1) {
                        jobList.remove(index);
                        appliedAdapter.notifyItemRemoved(index);
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            ref.addChildEventListener(listner);
        }

    public int getIndex(String jobkey) {
        for (int i = 0; i < jobList.size(); i++) {
            if (jobkey.equals(jobList.get(i).getJobKey())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            if (mRecyclerDash != null) {
               // updateUi();

            }
        } else {
            Log.d(TAG, "setUserVisibleHint:applied remove");
            if (listner != null) {

                //ref.removeEventListener(listner);
            }

        }
    }

}
