package com.example.zulqarnain.campusrecruitment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.students.StudentActivity;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Zul Qarnain on 11/28/2017.
 */

public class AdminActivity extends AppCompatActivity{

    private ViewPager adminPager;
    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");
        auth=FirebaseAuth.getInstance();
        adminPager = (ViewPager) findViewById(R.id.admin_view_pager);
        PagerAdapeter adapeter = new PagerAdapeter(getSupportFragmentManager());
        adminPager.setAdapter(adapeter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            auth.signOut();
            Intent intent=new Intent(AdminActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public class PagerAdapeter extends FragmentStatePagerAdapter{

        public PagerAdapeter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    return  new StudentListFragment();
                case 1:
                    return new CompanyListFragement();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return  "Students";
                case 1:
                    return "Company";
            }
            return super.getPageTitle(position);
        }
    }
}
