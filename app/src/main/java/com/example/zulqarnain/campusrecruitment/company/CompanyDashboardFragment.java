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
import android.widget.ArrayAdapter;

import com.example.zulqarnain.campusrecruitment.R;
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

public class CompanyDashboardFragment extends Fragment {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private RecyclerView rJoblist;
    private ArrayList<Jobs> jobs;
    private final String TAG = "test";
    String comkey;

    JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.company_dashboard_fragment, container, false);
        rJoblist = v.findViewById(R.id.job_list_view);
        updateUi();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        comkey = auth.getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("jobs");
    }

    private void updateUi() {
        jobs = new ArrayList<>();
        adapter = new JobAdapter(getContext(), jobs, ref);
        rJoblist.setLayoutManager(new LinearLayoutManager(getContext()));
        rJoblist.setAdapter(adapter);

        Log.d(TAG, "updateUi: apicalled");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Jobs job = dataSnapshot.getValue(Jobs.class);
                if (job.getCompanyKey().equals(comkey)) {
                    jobs.add(job);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
