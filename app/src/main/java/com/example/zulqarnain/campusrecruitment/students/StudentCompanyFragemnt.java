package com.example.zulqarnain.campusrecruitment.students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Jobs;
import com.example.zulqarnain.campusrecruitment.students.adapter.AdapterNewJob;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 3/13/2018.
 */

public class StudentCompanyFragemnt extends Fragment {

    private RecyclerView mRecyclerDash;
    private DatabaseReference ref;
    private String TAG = "test";
    ArrayList<Jobs> jobList;
    AdapterNewJob adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.student_dashboard_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

