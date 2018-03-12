package com.example.zulqarnain.campusrecruitment.admin;

import android.app.Notification;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.admin.adapter.CompanyAdapter;
import com.example.zulqarnain.campusrecruitment.models.Company;
import com.example.zulqarnain.campusrecruitment.models.Student;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 12/27/2017.
 */

public class CompanyListFragement extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Company> comList;
    DatabaseReference db;
    CompanyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_company_view_fragment, container, false);
        recyclerView = v.findViewById(R.id.admin_recycler_company_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseDatabase.getInstance().getReference("company");
        comList = new ArrayList<>();
        updateView();
        return v;
    }

    public void updateView() {
        comList = new ArrayList<>();
        adapter = new CompanyAdapter(getActivity(), comList);
        recyclerView.setAdapter(adapter);
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Company company = dataSnapshot.getValue(Company.class);
                Log.d("", "comdded: " + company.getUid());
                Log.d("", "comdded: " + dataSnapshot);
                comList.add(company);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Company company = dataSnapshot.getValue(Company.class);
                Log.d("", "comdded: " + company.getUid());
                Log.d("", "comdded: " + dataSnapshot);
                int index = getIndex(company.getUid());
                if (index != -1) {
                    comList.set(index, company);
                    adapter.notifyItemChanged(index);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Company company = dataSnapshot.getValue(Company.class);
                Log.d("", "comdded: " + company.getUid());
                Log.d("", "comdded: " + dataSnapshot);
                int index = getIndex(company.getUid());
                if (index != -1) {
                    comList.remove(index);
                    adapter.notifyItemRemoved(index);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public int getIndex(String uid) {
        for (int i = 0; i < comList.size(); i++) {
            if (comList.get(i).getUid().equals(uid))
                return i;
        }
        return -1;
    }

}
