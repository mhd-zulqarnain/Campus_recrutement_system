package com.example.zulqarnain.campusrecruitment.company;

import android.content.Intent;
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
import com.example.zulqarnain.campusrecruitment.admin.AdminActivity;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class CompanyActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ViewPager mViewPager;
    private String userKey;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        setTitle("Company Dashboard");
        auth = FirebaseAuth.getInstance();
        mViewPager= (ViewPager) findViewById(R.id.company_view_pager);
        CompanyPagerAdapter adapter = new CompanyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        userKey= auth.getCurrentUser().getUid();

        Log.d("", "onCreate:admin "+ utils.getuseype());

        ref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("disabled");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String isAccountDisabled=dataSnapshot.getValue(String.class);
                Log.d("", "on log out: "+isAccountDisabled);
                if(isAccountDisabled.equals("true")){
                    auth.signOut();
                    Intent intent = new Intent(CompanyActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Messege.messege(getBaseContext(), "Account has been disabled");
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.company_menu, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
