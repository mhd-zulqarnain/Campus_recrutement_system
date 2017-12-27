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

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 12/27/2017.
 */

public class CompanyListFragement extends Fragment {
    private RecyclerView recyclerView;
//    private ArrayList<Compan> ComList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_company_view_fragment,container,false);
        recyclerView  =v.findViewById(R.id.admin_recycler_company_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void updateView(){
//        ComList = new ArrayList<>();

    }

}
