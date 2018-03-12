package com.example.zulqarnain.campusrecruitment.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zul Qarnain on 11/6/2017.
 */

public class AddingJobFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private Spinner jobSpinner;
    private EditText jDescription;
    private EditText jVacancy;
    private Button mAddJob;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.company_add_job_fragment,container,false);
        jobSpinner= view.findViewById(R.id.job_type);
        jDescription=view.findViewById(R.id.ed_job_description);
        jVacancy=view.findViewById(R.id.ed_job_vaca);
        mAddJob=view.findViewById(R.id.com_add_job_btn);
        mAddJob.setOnClickListener(this);
        updateUi();
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("jobs");
    }


    private void updateUi() {
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(),R.array.job_type
                ,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobSpinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        String jDes= jDescription.getText().toString();
        String jVac= jVacancy.getText().toString();
        String jType= jobSpinner.getSelectedItem().toString();
        utils.hideKeyboard(getActivity());
        if(TextUtils.isEmpty(jDes)){
            Messege.messege(getContext(),"Enter the job description");
            return;
        }
        if(TextUtils.isEmpty(jVac)){
            Messege.messege(getContext(),"Enter number of vacancy");
            return;
        }
        String comkey=auth.getCurrentUser().getUid();
        String key=comkey;
        Jobs job = new Jobs(jType,key,jDes,jVac,comkey);
        mDatabase.child(key).child("details").setValue(job);

        jDescription.setText("");
        jVacancy.setText("");
        Messege.messege(getActivity(),"Job added successfully");
    }
}
