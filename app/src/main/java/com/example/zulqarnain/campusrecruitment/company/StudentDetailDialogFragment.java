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
import com.example.zulqarnain.campusrecruitment.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zul Qarnain on 11/10/2017.
 */

public class StudentDetailDialogFragment extends DialogFragment {


    private TextView tStudentName;
    private TextView tStudentSemister;
    private TextView tStudentDept;
    private  String studentKey;
    private  String studentName;
    private String currentUID;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    private static final String  STUDENT_ARGS_KEY="student.com.key";
    private static final String  STUDENT_ARGS_NAME="student.com.name";
    public static StudentDetailDialogFragment newInstance(String stdKey,String studentName) {
        Bundle args = new Bundle();
        args.putString(STUDENT_ARGS_KEY,stdKey);
        args.putString(STUDENT_ARGS_NAME,studentName);
        StudentDetailDialogFragment fragment = new StudentDetailDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
//        currentUID = auth.getCurrentUser().getUid();
        studentKey = getArguments().getString(STUDENT_ARGS_KEY);
        studentName = getArguments().getString(STUDENT_ARGS_NAME);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.company_student_detail_dialog_view, null);
        tStudentName = view.findViewById(R.id.d_studet_applied_name);
        tStudentSemister = view.findViewById(R.id.d_applied_semister_number);
        tStudentDept = view.findViewById(R.id.d_applied_dept_name);
        ref = FirebaseDatabase.getInstance().getReference("student").child(studentKey);
        tStudentName.setText(studentName);
//        ref = FirebaseDatabase.getInstance().getReference("student");

        updateUi();
        Dialog detailDialog =
                new AlertDialog.Builder(getActivity()).
                        setView(view).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        }).create();
        return detailDialog;
    }

    public void updateUi() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();
                if(key.equals("details")){
                    Student student  = dataSnapshot.getValue(Student.class);
                    tStudentSemister.setText(student.getSemister());
                    tStudentDept.setText(student.getDepartment());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
