package com.example.zulqarnain.campusrecruitment.company;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.utils.Messege;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/7/2017.
 */

public class JobAdapter extends RecyclerView.Adapter< JobAdapter.ViewJobHolder> {

    private ArrayList<Jobs> jobList;
    private Context mContext;
    private DatabaseReference ref;
    public JobAdapter(Context context,ArrayList<Jobs> jobList,DatabaseReference ref){
        this.jobList=jobList;
        this.mContext=context;
        this.ref=ref;
    }

    @Override
    public ViewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.single_job_row_view,parent,false);
        ViewJobHolder holder  = new ViewJobHolder(view);

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

    public class ViewJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tp;
        private TextView ds;
        private TextView vc;
        private Button mBtn;
        private Jobs mjob;
        public ViewJobHolder(View itemView) {
            super(itemView);
            tp=itemView.findViewById(R.id.job_type);
            ds=itemView.findViewById(R.id.job_desc);
            vc=itemView.findViewById(R.id.job_vacan);
            mBtn=itemView.findViewById(R.id.btn_dlt);
            mBtn.setOnClickListener(this);
        }

        public void bindView(Jobs mjob) {
            this.mjob=mjob;
            tp.setText("Type:"+mjob.getJobType());
            ds.setText("Descripton:"+mjob.getJobDescription());
            vc.setText("Vacancy:"+mjob.getJobVacancy());
            Log.d("", "bindView: binded");
        }

        @Override
        public void onClick(View view) {
            ref.child(mjob.getJobKey()).removeValue();
        }
    }
}
