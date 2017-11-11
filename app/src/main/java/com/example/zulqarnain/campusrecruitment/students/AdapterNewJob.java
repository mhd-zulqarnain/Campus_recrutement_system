package com.example.zulqarnain.campusrecruitment.students;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.example.zulqarnain.campusrecruitment.utils.Messege;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zul Qarnain on 11/8/2017.
 */

public class AdapterNewJob extends RecyclerView.Adapter<AdapterNewJob.NewJobHolder> {
    private DatabaseReference mDatabase;
    private ArrayList<Jobs> jobList;
    private Context mContext;
    private String key;

    public AdapterNewJob(Context mContext, ArrayList<Jobs> jobs) {
        this.mContext = mContext;
        this.jobList = jobs;
        this.key = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.mDatabase = FirebaseDatabase.getInstance().getReference("applied");

    }

    @Override
    public NewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_student_job_view, parent, false);
        NewJobHolder holder = new NewJobHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewJobHolder holder, int position) {
        Jobs jobs = jobList.get(position);
        holder.bindView(jobs);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class NewJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tp;
        private TextView ds;
        private TextView vc;
        private Button bDetail;
        private Jobs mjob;

        public NewJobHolder(View itemView) {
            super(itemView);
            tp = itemView.findViewById(R.id.job_type);
            ds = itemView.findViewById(R.id.job_desc);
            vc = itemView.findViewById(R.id.job_vacan);
            bDetail = itemView.findViewById(R.id.btn_detail);
            bDetail.setOnClickListener(this);
        }

        public void bindView(Jobs mjob) {
            this.mjob = mjob;
            tp.setText("Type:" + mjob.getJobType());
            ds.setText("Descripton:" + mjob.getJobDescription());
            vc.setText("Vacancy:" + mjob.getJobVacancy());
            btnEnable();
        }

        @Override
        public void onClick(View view) {

                DialogFragment fragment = JobDetailDialogFragment.newInstance(R.string.student_job_dialog,mjob);
                fragment.show(((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction(), "myDialog");
        }

        public String getName() {
            return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }

        public void btnEnable() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("applied").child(mjob.getJobKey());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(key).exists()) {
                        bDetail.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}
