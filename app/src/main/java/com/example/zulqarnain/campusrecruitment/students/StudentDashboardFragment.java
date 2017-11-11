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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.example.zulqarnain.campusrecruitment.utils.Messege;
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
 * Created by Zul Qarnain on 11/6/2017.
 */

public class StudentDashboardFragment extends Fragment {
    private Spinner mSpinner;
    private RecyclerView mRecyclerDash;
    private DatabaseReference ref;
    private String TAG = "test";
    ArrayList<Jobs> jobList;
    AdapterNewJob adapter;
    static boolean applied = false;

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
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateUi(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Messege.messege(getContext(), "sleceted nothing");

            }
        });
        return v;
    }

    private void updateUi(int option) {
        jobList = new ArrayList<>();
        adapter = new AdapterNewJob(getContext(), jobList);
        mRecyclerDash.setAdapter(adapter);

        if (option == 0) {
            final String selected = "newJob";
            ref = FirebaseDatabase.getInstance().getReference("jobs");
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "add", selected);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "changed", selected);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "remove", selected);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else if (option == 1) {
            final String selected = "applied";

            ref = FirebaseDatabase.getInstance().getReference("jobs");
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "add", selected);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "changed", selected);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    hasApply(job, "remove", selected);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public int getIndex(String jobkey) {
        for (int i = 0; i < jobList.size(); i++) {
            if (jobkey.equals(jobList.get(i).getJobKey())) {
                return i;
            }
        }
        return 0;
    }

    public Boolean hasApply(final Jobs job, final String status, final String option) {
        applied = false;
        final String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("applied").child(job.getJobKey());

        if (option.equals("newJob")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.child(studentID).exists()) {
                        if (status.equals("added")) {
                        jobList.add(job);
                            adapter.notifyDataSetChanged();
                        } else if (status.equals("changed")) {
                            int index = getIndex(job.getJobKey());
                            jobList.set(index, job);
                            adapter.notifyItemChanged(index);
                        } else if (status.equals("remove")) {
                            int index = getIndex(job.getJobKey());
                            jobList.remove(index);
                            adapter.notifyItemRemoved(index);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else if (option.equals("applied")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(studentID).exists()) {
                        Log.d(TAG, "onDataChange: exists");
                        if (status.equals("add")) {
                            jobList.add(job);
                            adapter.notifyDataSetChanged();
                        } else if (status.equals("changed")) {
                            int index = getIndex(job.getJobKey());
                            jobList.set(index, job);
                            adapter.notifyItemChanged(index);
                        } else if (status.equals("remove")) {
                            int index = getIndex(job.getJobKey());
                            jobList.remove(index);
                            adapter.notifyItemRemoved(index);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        return applied;


    }
}

