package com.example.zulqarnain.campusrecruitment.company.adapters;

import android.content.Context;
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
import com.example.zulqarnain.campusrecruitment.company.CompanyDialogFragment;
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/7/2017.
 */

public class JobAppliedAdapter extends RecyclerView.Adapter<JobAppliedAdapter.ViewJobHolder> {


    private ArrayList<Jobs> jobList;
    private Context mContext;
    private DatabaseReference ref;

    public JobAppliedAdapter(Context context, ArrayList<Jobs> jobList, DatabaseReference ref) {
        this.jobList = jobList;
        this.mContext = context;
        this.ref = ref;
    }

    @Override
    public JobAppliedAdapter.ViewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.single_view_applied_student, parent, false);
        ViewJobHolder holder = new ViewJobHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewJobHolder holder, int position) {

        Jobs jobs = jobList.get(position);
        holder.bindView(jobs);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewJobHolder extends RecyclerView.ViewHolder {

        private TextView tDes;
        private TextView tAppNum;
       private Jobs mjob;

        public ViewJobHolder(View itemView) {
            super(itemView);
            tDes= itemView.findViewById(R.id.t_com_applied_job_des);
            tAppNum= itemView.findViewById(R.id.t_com_applied_applicant_num);
        }

        public void bindView(Jobs mjob) {
            this.mjob = mjob;
            tDes.setText("Title:" +mjob.getJobDescription());
            filter();

//            tAppNum.setText("Number of applicants:" + mjob.getJobVacancy());
        }
        public void filter(){
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("applied").child(mjob.getJobKey());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  String num= String.valueOf(dataSnapshot.getChildrenCount());
                    tAppNum.setText("Number of applicants: "+num);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
}
