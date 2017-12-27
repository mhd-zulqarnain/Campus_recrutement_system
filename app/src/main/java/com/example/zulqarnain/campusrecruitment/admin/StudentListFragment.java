package com.example.zulqarnain.campusrecruitment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.admin.adapter.StudentListAdapter;
import com.example.zulqarnain.campusrecruitment.models.Student;
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
public class StudentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private ArrayList<Student> stuList;
    private String TAG="com.studentList.fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ref = FirebaseDatabase.getInstance().getReference("student");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.admin_student_fragment_view,container,false);
        recyclerView  =v.findViewById(R.id.admin_recycler_student_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateView();
        return  v;
    }
    public void updateView(){
        stuList = new ArrayList<>();
        StudentListAdapter adapter = new StudentListAdapter(getContext(),stuList);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded student: "+dataSnapshot);
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
