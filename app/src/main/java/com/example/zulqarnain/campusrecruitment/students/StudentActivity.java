package com.example.zulqarnain.campusrecruitment.students;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager mViewPager;
    private String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setTitle("Student");
        auth = FirebaseAuth.getInstance();
        mViewPager = (ViewPager) findViewById(R.id.student_view_pager);
        StudentPagerAdapter adapter = new StudentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        userKey = auth.getCurrentUser().getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            auth.signOut();
            Intent intent=new Intent(StudentActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.menu_profile) {
            Intent intent=new Intent(StudentActivity.this, StudentProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public class StudentPagerAdapter extends FragmentStatePagerAdapter {

        public StudentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                NewJobFragment fragment = new NewJobFragment();
                return fragment;
            } else if (position == 1) {
                AppliedJobFragment fragment = new AppliedJobFragment();
                return fragment;

            }
            return null;
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "New Jobs";
            else
                return "Applied Jobs";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
