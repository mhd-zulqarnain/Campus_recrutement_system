package com.example.zulqarnain.campusrecruitment.students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.campusrecruitment.R;

/**
 * Created by Zul Qarnain on 11/6/2017.
 */

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_profile_fragment,container,false);
    }
}
