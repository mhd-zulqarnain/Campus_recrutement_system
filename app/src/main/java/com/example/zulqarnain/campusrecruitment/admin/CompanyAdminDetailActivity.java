package com.example.zulqarnain.campusrecruitment.admin;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.adapters.JobAdapter;
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.example.zulqarnain.campusrecruitment.models.ServiceError;
import com.example.zulqarnain.campusrecruitment.models.ServiceListener;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompanyAdminDetailActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView rJoblist;
    private ArrayList<Jobs> jobs;
    private final String TAG = "test";
    String comkey;
    JobAdapter adapter;
    private TextView noData;
    private static final String ADAPTER_FLAG = "activty";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        comkey = getIntent().getStringExtra("uid");
        rJoblist = (RecyclerView) findViewById(R.id.job_list_view);
        ref = FirebaseDatabase.getInstance().getReference("jobs");
        noData = (TextView) findViewById(R.id.no_data_view);

        updateUi();
    }

    private void updateUi() {
        jobs = new ArrayList<>();
        adapter = new JobAdapter(CompanyAdminDetailActivity.this, jobs, ref, noData, ADAPTER_FLAG);
        rJoblist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rJoblist.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot snapshot = dataSnapshot.child("details");
                Jobs job = snapshot.getValue(Jobs.class);
                if (job.getCompanyKey().equals(comkey)) {
                    jobs.add(job);
                    adapter.notifyDataSetChanged();
                }
                Log.d(TAG, "onChildAdded: data added in detail" + dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DataSnapshot snapshot = dataSnapshot.child("details");
                Jobs job = snapshot.getValue(Jobs.class);
                int index = getIndexOf(job.getJobKey());
                jobs.set(index, job);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child("details");
                Jobs job = snapshot.getValue(Jobs.class);
                int index = getIndexOf(job.getJobKey());
                if (index != -1) {
                    jobs.remove(index);
                    adapter.notifyCustomDataRemove(index);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        utils.getCompanyName(comkey, new ServiceListener() {
            @Override
            public void success(Object obj)
            {
                setTitle("Job offerd by " + obj.toString());
            }

            @Override
            public void error(ServiceError serviceError) {

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
        return -1;
    }


}
