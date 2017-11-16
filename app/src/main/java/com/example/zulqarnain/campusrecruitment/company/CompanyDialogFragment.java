package com.example.zulqarnain.campusrecruitment.company;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zul Qarnain on 11/10/2017.
 */

public class CompanyDialogFragment extends DialogFragment {

    private String posButtonText;
    private String negButtonText;
    private static final String ARGS_DIALOG_TYPE = "appliedJobs";
    private static final String ARGS_DIALOG_JOB_OBJECT = "jobObject";
    private TextView tCompanyName;
    private TextView tJobType;
    private EditText tJobDescription;
    private EditText tJobVacancy;
    private Jobs mJob;
    private String currentUID;
    private static String comName;
    int dialogDetailType;
    private FirebaseAuth auth;

    public static CompanyDialogFragment newInstance(int dialogType, Jobs job) {
        Bundle args = new Bundle();
        args.putInt(ARGS_DIALOG_TYPE, dialogType);
        args.putParcelable(ARGS_DIALOG_JOB_OBJECT, job);
        CompanyDialogFragment fragment = new CompanyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        dialogDetailType = getArguments().getInt(ARGS_DIALOG_TYPE);
        mJob = (Jobs) getArguments().getParcelable(ARGS_DIALOG_JOB_OBJECT);
        auth = FirebaseAuth.getInstance();
        currentUID= auth.getCurrentUser().getUid();

        setButtonName();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.company_detail_dialog_view, null);
        tCompanyName = view.findViewById(R.id.d_company_name);
        tJobType = view.findViewById(R.id.d_job_type);
        tJobDescription = view.findViewById(R.id.d_job_description);
        tJobVacancy = view.findViewById(R.id.d_job_vacancy);
        updateUi();
        Dialog detailDialog =
                new AlertDialog.Builder(getActivity()).
                        setView(view).
                        setPositiveButton(posButtonText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAction();
                            }
                        }).
                        setNegativeButton(negButtonText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CompanyDialogFragment.this.getDialog().cancel();
                            }
                        }).create();


        return detailDialog;
    }

    public void mAction() {

        if (dialogDetailType == R.string.company_appplied_ob_dialog) {
            String vancy = tJobVacancy.getText().toString();
            String des= tJobDescription.getText().toString();

            if (!TextUtils.isEmpty(vancy)&&!TextUtils.isEmpty(des)){
            Jobs jobs = new Jobs(mJob.getJobType(),mJob.getJobKey(),des,vancy,currentUID);
            FirebaseDatabase.getInstance().getReference("jobs").child(mJob.getJobKey()).child("details").setValue(jobs);
            }
        }
    }

    public void setButtonName() {
        negButtonText = "Cancel";
        if (dialogDetailType == R.string.company_appplied_ob_dialog) {
            posButtonText = "Update";
        } else if (dialogDetailType == R.string.company_job_dialog) {
            posButtonText = "Delete";
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser=true){
            updateUi();

        }
    }

    public void updateUi() {
        if (dialogDetailType == R.string.company_appplied_ob_dialog) {
            tJobDescription.setText(mJob.getJobDescription());
            tCompanyName.setText(auth.getCurrentUser().getDisplayName().toString());
            tJobType.setText("Job type: " + mJob.getJobType());
            tJobVacancy.setText( mJob.getJobVacancy());

        } else if (dialogDetailType == R.string.company_job_dialog) {
            posButtonText = "Delete";
        }
    }


}
