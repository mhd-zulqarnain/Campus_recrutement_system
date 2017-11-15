package com.example.zulqarnain.campusrecruitment.company;

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
import com.example.zulqarnain.campusrecruitment.company.adapters.JobAdapter;
import com.example.zulqarnain.campusrecruitment.company.adapters.JobAppliedAdapter;
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

public class CompanyJobsAppliedDetails extends Fragment {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private RecyclerView rJoblist;
    private ArrayList<Jobs> jobs;
    private final String TAG = "test";
    String comkey;

    JobAppliedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.company_jobs_applied_layout, container, false);
        rJoblist = v.findViewById(R.id.com_job_applied_recycler);
        updateUi();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        comkey = auth.getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("jobs").child("details");
    }

    private void updateUi() {
        jobs = new ArrayList<>();
        adapter = new JobAppliedAdapter(getContext(), jobs, ref);
        rJoblist.setLayoutManager(new LinearLayoutManager(getContext()));
        rJoblist.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Jobs job = dataSnapshot.getValue(Jobs.class);
                if (job.getCompanyKey().equals(comkey)) {
                    jobs.add(job);
                    Log.d(TAG, "onChildAdded: applied student com");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Jobs job= dataSnapshot.getValue(Jobs.class);
                int index = getIndexOf(job.getJobKey());
                jobs.set(index,job);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Jobs job= dataSnapshot.getValue(Jobs.class);
                int index = getIndexOf(job.getJobKey());
                jobs.remove(index);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public int getIndexOf(String key) {
        for (int i = 0; i < jobs.size(); i++) {
            Jobs mj = jobs.get(i);
            if (mj.getJobKey().equals(key)) {
                return jobs.indexOf(mj);
            }
        }
        return 0;
    }
}
