package com.example.zulqarnain.campusrecruitment.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.adapters.DetailAppliedAdapter;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompanyJobsAppliedActivity extends AppCompatActivity {

    private String jobKey;
    private String jobName;
    private DetailAppliedAdapter detailAppliedAdapter;
    private ArrayList<String> keyList;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private TextView jobTitle;
    private  RecyclerView recyclerView ;
    private String TAG = "student.applied.acitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Applied Student Details");
        setContentView(R.layout.activity_student_applied);


        recyclerView = (RecyclerView) findViewById(R.id.applied_student_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobKey = getIntent().getStringExtra("jobKey");
        jobName = getIntent().getStringExtra("jobName");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jobTitle= (TextView) findViewById(R.id.t_applied_job_tile);
        jobTitle.setText(jobName);
        auth= FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference("jobs").child(jobKey).child("canidates");
        updateUi();
    }

    private void updateUi() {
        keyList = new ArrayList<>();
        Log.d(TAG, "updateUi: sppled ");
        detailAppliedAdapter = new DetailAppliedAdapter(this,keyList);
        recyclerView.setAdapter(detailAppliedAdapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String studentKey = dataSnapshot.getKey();
                keyList.add(studentKey);
                detailAppliedAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.company_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        if (item.getItemId() == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(CompanyJobsAppliedActivity.this, LoginActivity.class));
            finish();
        }
        return true;

    }
}
