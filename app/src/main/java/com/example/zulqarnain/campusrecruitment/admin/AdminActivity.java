package com.example.zulqarnain.campusrecruitment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.zulqarnain.campusrecruitment.R;

/**
 * Created by Zul Qarnain on 11/28/2017.
 */

public class AdminActivity extends AppCompatActivity{

    private ViewPager adminPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");
        adminPager = (ViewPager) findViewById(R.id.admin_view_pager);
        PagerAdapeter adapeter = new PagerAdapeter(getSupportFragmentManager());
        adminPager.setAdapter(adapeter);
    }

    public class PagerAdapeter extends FragmentStatePagerAdapter{

        public PagerAdapeter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
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
