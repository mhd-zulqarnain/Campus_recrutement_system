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
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.example.zulqarnain.campusrecruitment.students.adapter.AdapterNewJob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/6/2017.
 */

public class NewJobFragment extends Fragment {
    private RecyclerView mRecyclerDash;
    private DatabaseReference ref;
    private String TAG = "test";
    ArrayList<Jobs> jobList;
    AdapterNewJob adapter;
    String userUID;
    String userName;
    ChildEventListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.student_dashboard_fragment, container, false);
        mRecyclerDash = v.findViewById(R.id.std_recycler_dash);
        mRecyclerDash.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return v;
    }

    private void updateUi() {
        jobList = new ArrayList<>();
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        ref = FirebaseDatabase.getInstance().getReference("jobs");
        adapter = new AdapterNewJob(getContext(), jobList);
        mRecyclerDash.setAdapter(adapter);

        mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot snp = dataSnapshot.child("canidates").child(userUID);

                if (!snp.exists()) {
                    DataSnapshot snapshot = dataSnapshot.child("details");
                    Jobs job = snapshot.getValue(Jobs.class);
                    jobList.add(job);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DataSnapshot snapshot = dataSnapshot.child("details");
                Jobs job = snapshot.getValue(Jobs.class);

                Log.d(TAG, "onChildChanged: " + snapshot);
                DataSnapshot snp = dataSnapshot.child("canidates").child(userUID);
                if (snp.exists()) {

                    int index = getIndex(job.getJobKey());
                    if (index != -1) {
                        jobList.remove(index);
                        adapter.notifyItemRemoved(index);
                    }
                } else {
                    int index = getIndex(job.getJobKey());
                    if (index != -1) {
                        jobList.set(index, job);
                        adapter.notifyItemChanged(index);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child("details");
                Jobs job = snapshot.getValue(Jobs.class);
                int index = getIndex(job.getJobKey());
                if (index != -1) {
                    jobList.remove(index);
                    adapter.notifyItemRemoved(index);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addChildEventListener(mListener);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            Log.d(TAG, "setUserVisibleHint:new  update");
            if (mRecyclerDash != null) {
                ref.removeEventListener(mListener);
                updateUi();
            }

        }
    }

    public int getIndex(String jobkey) {
        for (int i = 0; i < jobList.size(); i++) {
            if (jobkey.equals(jobList.get(i).getJobKey())) {
                return i;
            }
        }
        return -1;
    }

}

