package com.example.zulqarnain.campusrecruitment.admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.StudentDetailDialogFragment;
import com.example.zulqarnain.campusrecruitment.models.StudDetail;
import com.example.zulqarnain.campusrecruitment.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 12/27/2017.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StuViewHolder> {

    private ArrayList<StudDetail> stdList;
    private Context ctx;
    private DatabaseReference ref;
    TextView noData;
    public StudentListAdapter(Context ctx, ArrayList<StudDetail> stdList, TextView noData) {
        this.ctx = ctx;
        this.stdList = stdList;
        this.noData=noData;
    }

    @Override
    public StudentListAdapter.StuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_student_view, parent, false);
        StuViewHolder holder = new StuViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentListAdapter.StuViewHolder holder, int position) {
        StudDetail detail = stdList.get(position);
        holder.bindView(detail);
        if(stdList.size()==0){
            noData.setVisibility(View.VISIBLE);
        }
        else
            noData.setVisibility(View.GONE);

    }

    public void notifyCustomDataRemove(int index)
    {
        notifyItemRemoved(index);
        super.notifyDataSetChanged();
        if(stdList.size()==0){
            noData.setVisibility(View.VISIBLE);
        }
        else
            noData.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return stdList.size();
    }

    public class StuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView prfImage;
        private TextView stName;
        private ImageButton delBtn;
        private ImageButton detBtn;
        private StudDetail std;
        private String uid;

        public StuViewHolder(View itemView) {
            super(itemView);
            prfImage = itemView.findViewById(R.id.st_prf_img);
            delBtn = itemView.findViewById(R.id.del_btn_stu);
            detBtn = itemView.findViewById(R.id.btn_detail);
            stName = itemView.findViewById(R.id.st_name);
            delBtn.setOnClickListener(this);
            detBtn.setOnClickListener(this);

        }

        public void bindView(StudDetail std) {
            this.std = std;
            String url = null;
            if (std.getDetail() != null) {
                url = std.getDetail().getImgUrl();
                if (url != null)

                    Picasso.with(ctx).load(url).into(prfImage);
            }
            stName.setText(std.getName());


        }

        @Override
        public void onClick(View view) {
            uid = std.getUid();

            if (view.getId() == R.id.del_btn_stu) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Warning")
                        .setMessage("Do you really want to Delete this user?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref = FirebaseDatabase.getInstance().getReference("student");
                                ref.child(uid).removeValue();
                                ref = FirebaseDatabase.getInstance().getReference("users");
                                ref.child(uid).child("disabled").setValue("true");
                            }
                        }).setNegativeButton("No", null).show();

            } else if (view.getId() == R.id.btn_detail) {
                DialogFragment fragment = StudentDetailDialogFragment.newInstance(uid, std.getName());
                fragment.show(((FragmentActivity) ctx).getSupportFragmentManager().beginTransaction(), "mydialog");
            }
        }
    }
}
