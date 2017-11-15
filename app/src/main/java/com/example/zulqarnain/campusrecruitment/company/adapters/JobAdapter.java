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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/7/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewJobHolder> {

    private ArrayList<Jobs> jobList;
    private Context mContext;
    private DatabaseReference ref;

    public JobAdapter(Context context, ArrayList<Jobs> jobList, DatabaseReference ref) {
        this.jobList = jobList;
        this.mContext = context;
        this.ref = ref;
    }

    @Override
    public ViewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.single_job_row_view, parent, false);
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

    public class ViewJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tp;
        private TextView ds;
        private TextView vc;
        private Button bDlt;
        private Button bApplied;
        private Jobs mjob;

        public ViewJobHolder(View itemView) {
            super(itemView);
            tp = itemView.findViewById(R.id.job_type);
            ds = itemView.findViewById(R.id.job_desc);
            vc = itemView.findViewById(R.id.job_vacan);
            bDlt = itemView.findViewById(R.id.btn_dlt);
            bApplied =itemView.findViewById(R.id.com_btn_applied);
            bApplied.setOnClickListener(this);
            bDlt.setOnClickListener(this);
        }

        public void bindView(Jobs mjob) {
            this.mjob = mjob;
            tp.setText("Type:" + mjob.getJobType());
            ds.setText("Descripton:" + mjob.getJobDescription());
            vc.setText("Vacancy:" + mjob.getJobVacancy());
            Log.d("", "bindView: binded");
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_dlt) {
                String key = mjob.getJobKey();
                ref.child(key).removeValue();
                Log.d("viewholder", "onClick: " + getAdapterPosition());
                FirebaseDatabase.getInstance().getReference("applied").child(mjob.getJobKey()).removeValue();
            }
            else if(view.getId()==R.id.com_btn_applied){
                DialogFragment dialogFragment = CompanyDialogFragment.newInstance(R.string.company_appplied_ob_dialog,mjob);
                dialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction(), "applied");

            }

        }
    }
}
