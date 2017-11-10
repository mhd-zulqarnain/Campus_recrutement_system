package com.example.zulqarnain.campusrecruitment.students;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        auth = FirebaseAuth.getInstance();
        mViewPager= (ViewPager) findViewById(R.id.student_view_pager);
        StudentPagerAdapter adapter = new StudentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(StudentActivity.this,LoginActivity.class));
            finish();
        }
        return  true;
    }

    public class StudentPagerAdapter extends FragmentStatePagerAdapter{

        public StudentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new StudentDashboardFragment();
            }
            else if(position==1){
                return new ProfileFragment();

            }
            return null;
        }
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "Student dashboard";
            else
                return "Student Profile";



        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
