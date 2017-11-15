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
import com.example.zulqarnain.campusrecruitment.students.adapter.AdapterNewJob;
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
    static boolean applied = false;
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
        ref = FirebaseDatabase.getInstance().getReference("jobs");
            jobList.clear();
            adapter = new AdapterNewJob(getContext(), jobList);
            mRecyclerDash.setAdapter(adapter);

                if(mListener == null) {
                    mListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Jobs job = dataSnapshot.getValue(Jobs.class);
                            String d = dataSnapshot.getKey();

                            jobList.add(job);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Jobs job = dataSnapshot.getValue(Jobs.class);
                            int index = getIndex(job.getJobKey());
                            if (index != -1) {
                                jobList.set(index, job);
                                adapter.notifyItemChanged(index);
                            }
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Jobs job = dataSnapshot.getValue(Jobs.class);
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
        /*} else if (option == 1) {
            Log.d(TAG, "updateUi: applied");
            appliedadapter = new AppliedJobAdapter(getContext(), jobList);
            mRecyclerDash.setAdapter(appliedadapter);

            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    jobList.add(job);

                    appliedadapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    int index = getIndex(job.getJobKey());
                    jobList.set(index, job);
                    appliedadapter.notifyItemChanged(index);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Jobs job = dataSnapshot.getValue(Jobs.class);
                    int index = getIndex(job.getJobKey());
                    jobList.remove(index);
                    appliedadapter.notifyItemRemoved(index);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser==true){
            if (mRecyclerDash!=null){
                updateUi();
                Log.d(TAG, "setUserVisibleHint:new  update");

        }else
            {
                Log.d(TAG, "setUserVisibleHint:new  remove listner");
                if(mListener!=null&&mRecyclerDash!=null){
//                    ref.removeEventListener(mListener);
                }

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



    /*public Boolean hasApply(final Jobs job, final String status, final String option) {
        applied = false;

        final String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("applied").child(job.getJobKey());

        if (option.equals("newJob")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(studentID).exists()) {
                        if (status.equals("add")) {
                            if(getIndex(job.getJobKey())==0){
                                jobList.add(job);
                                Log.d(TAG, "onDataChange: apply");
                                adapter.notifyDataSetChanged();

                            }
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


    }*/
}

