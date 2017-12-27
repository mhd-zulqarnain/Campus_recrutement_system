package com.example.zulqarnain.campusrecruitment.admin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.models.Student;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 12/27/2017.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StuViewHolder> {

    private ArrayList<Student> stdList;
    private Context ctx;
    public StudentListAdapter(Context ctx, ArrayList<Student> stdList){
        this.ctx=ctx;
        this.stdList=stdList;
    }
    @Override
    public StudentListAdapter.StuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StudentListAdapter.StuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stdList.size();
    }

    public class StuViewHolder extends RecyclerView.ViewHolder{
        public StuViewHolder(View itemView) {
            super(itemView);
        }
    }
}
