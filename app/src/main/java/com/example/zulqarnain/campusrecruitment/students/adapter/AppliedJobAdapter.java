package com.example.zulqarnain.campusrecruitment.students.adapter;

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
import com.example.zulqarnain.campusrecruitment.company.Jobs;
import com.example.zulqarnain.campusrecruitment.students.JobDetailDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
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
 * Created by Zul Qarnain on 11/8/2017.
 */

public class AppliedJobAdapter extends RecyclerView.Adapter<AppliedJobAdapter.NewJobHolder> {
    private DatabaseReference mDatabase;
    private ArrayList<Jobs> mList;
    private Context mContext;
    private String key;

    public AppliedJobAdapter(Context mContext, ArrayList<Jobs> jobs) {
        this.mContext = mContext;
        this.mList = jobs;
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
        Jobs jobs = mList.get(position);
        holder.bindView(jobs);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NewJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tp;
        private TextView ds;
        private TextView vc;
        private Button bDetail;
        private Jobs mjob;
        private Button mBtn;
        private HashMap<DatabaseReference, ValueEventListener> hashMap = new HashMap<>();

        public NewJobHolder(View itemView) {
            super(itemView);
            tp = itemView.findViewById(R.id.job_type);
            ds = itemView.findViewById(R.id.job_desc);
            vc = itemView.findViewById(R.id.job_vacan);
            bDetail = itemView.findViewById(R.id.btn_detail);
            bDetail.setOnClickListener(this);
            mBtn = itemView.findViewById(R.id.btn_apply);
        }

        public void bindView(Jobs mjob) {
            mBtn.setVisibility(View.GONE);
            bDetail.setText("Delete");
            this.mjob = mjob;
            tp.setText("Type:" + mjob.getJobType());
            ds.setText("Descripton:" + mjob.getJobDescription());
            vc.setText("Vacancy:" + mjob.getJobVacancy());
        }

        @Override
        public void onClick(View view) {
            String uuid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (view.getId() == R.id.btn_detail) {
                FirebaseDatabase.getInstance().getReference("jobs").child(mjob.getJobKey()).child("canidates").child(uuid).removeValue();
            }
        }



        public String getName() {
            return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }


    }
}
