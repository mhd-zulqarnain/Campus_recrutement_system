package com.example.zulqarnain.campusrecruitment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.admin.adapter.StudentListAdapter;
import com.example.zulqarnain.campusrecruitment.models.StudDetail;
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
    private ArrayList<StudDetail> stuList;

    private String TAG="com.studentList.fragment";
    StudentListAdapter adapter;
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        updateView();
        return  v;
    }
    public void updateView(){
        stuList = new ArrayList<>();
        adapter = new StudentListAdapter(getContext(),stuList);
        recyclerView.setAdapter(adapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                StudDetail detail=dataSnapshot.getValue(StudDetail.class);
                stuList.add(detail);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                StudDetail detail=dataSnapshot.getValue(StudDetail.class);
                int index=getStu(detail.getUid());
                if(index!=-1){
                    stuList.set(index,detail);
                    adapter.notifyItemChanged(index);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                StudDetail detail=dataSnapshot.getValue(StudDetail.class);
                int index=getStu(detail.getUid());
                if(index!=-1){
                    stuList.remove(index);
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
    public int getStu(String uid){
        for (int i=0;i<stuList.size();i++){
            if(stuList.get(i).getUid().equals(uid))
                return i;
        }
        return -1;
    }
}
