package com.example.zulqarnain.campusrecruitment.company;

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

public class CompanyActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        setTitle("Company Dashboard");
        auth = FirebaseAuth.getInstance();
        mViewPager= (ViewPager) findViewById(R.id.company_view_pager);
        CompanyPagerAdapter adapter = new CompanyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
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
            startActivity(new Intent(CompanyActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }

    public class CompanyPagerAdapter extends FragmentStatePagerAdapter {

        public CompanyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new CompanyDashboardFragment();

            }
            else if(position==1){
                return new AddingJobFragment();
            }else if(position==2){
                return new CompanyJobsAppliedDetails();
            }
            return null;
        }
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "Company dashboard";
            else if(position==1)
                return "Job Portal";
            else
                return "Applied ";

        }
        @Override
        public int getCount() {
            return 3;
        }
    }

}
