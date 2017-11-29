package com.example.zulqarnain.campusrecruitment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/28/2017.
 */

public class AdminStudentDetail extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Student> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.admin_student_fragment_view,container,false);
//        recyclerView = v.findViewById(R.id.admin_student_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public void populateList(){
        mList = new ArrayList<>();

    }

}
