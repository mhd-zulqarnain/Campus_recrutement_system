package com.example.zulqarnain.campusrecruitment.students;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Zul Qarnain on 11/10/2017.
 */

public class JobDetailDialogFragment extends DialogFragment {

    private String posButtonText;
    private String negButtonText;
    private static final String ARGS_DIALOG_TYPE = "persontype";
    private static final String ARGS_DIALOG_JOB_OBJECT = "jobObject";
    private TextView tCompanyName;
    private TextView tJobType;
    private TextView tJobDescription;
    private TextView tJobVacancy;
    private Jobs mJob;
    private String currentUID;
    private static String comName;
    int dialogDetailType;

    public static JobDetailDialogFragment newInstance(int dialogType, Jobs job) {
        Bundle args = new Bundle();
        args.putInt(ARGS_DIALOG_TYPE, dialogType);
        args.putParcelable(ARGS_DIALOG_JOB_OBJECT, job);
        JobDetailDialogFragment fragment = new JobDetailDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        dialogDetailType = getArguments().getInt(ARGS_DIALOG_TYPE);
        mJob = (Jobs) getArguments().getParcelable(ARGS_DIALOG_JOB_OBJECT);
        currentUID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        setButtonName();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.student_detail_dialog_view, null);
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
                                dismiss();
                            }
                        }).
                        setNegativeButton(negButtonText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                JobDetailDialogFragment.this.getDialog().cancel();
                            }
                        }).create();


        return detailDialog;
    }

    /*public void mAction() {

        if (dialogDetailType == R.string.student_job_dialog) {
            HashMap<String,String> detials = new HashMap<>();
            detials.put(currentUID,FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            FirebaseDatabase.getInstance().getReference("applied").child(mJob.getJobKey()).setValue(detials);
        }
    }*/

    public void setButtonName() {
        negButtonText = "Cancel";
        if (dialogDetailType == R.string.student_job_dialog) {
            posButtonText = "OK";
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
        if (dialogDetailType == R.string.student_job_dialog) {
            tJobDescription.setText(mJob.getJobDescription());
            setCompanyName(mJob.getCompanyKey());
            tJobType.setText("Job type: " + mJob.getJobType());
            tJobVacancy.setText("Vacancy: " + mJob.getJobVacancy());

        } else if (dialogDetailType == R.string.company_job_dialog) {
            posButtonText = "Delete";
        }
    }

    public void setCompanyName(String key) {
        comName = "";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("company").child(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comName = dataSnapshot.child("name").getValue(String.class);
                tCompanyName.setText(comName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
