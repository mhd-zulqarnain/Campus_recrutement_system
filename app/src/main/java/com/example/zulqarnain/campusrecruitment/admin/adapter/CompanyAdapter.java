package com.example.zulqarnain.campusrecruitment.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.admin.CompanyAdminDetailActivity;
import com.example.zulqarnain.campusrecruitment.models.Company;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 3/8/2018.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ComViewHolder> {
    ArrayList<Company> data;
    Context ctx;
    public CompanyAdapter(Context ctx, ArrayList<Company> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public ComViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.admin_single_row_view, parent, false);
        ComViewHolder holder = new ComViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComViewHolder holder, int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ComViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageButton delBtn;
        ImageButton detailBtn;
        Company company;

        public ComViewHolder(View itemView) {
            super(itemView);
            delBtn = itemView.findViewById(R.id.com_del_btn);
            detailBtn = itemView.findViewById(R.id.com_detail_lbtn);
            tv = itemView.findViewById(R.id.com_name);
            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, CompanyAdminDetailActivity.class);
                    intent.putExtra("uid", data.get(getAdapterPosition()).getUid());
                    ctx.startActivity(intent);
                }
            });
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    /*ref.child("company").child(company.getUid()).removeValue();
                    ref.child("jobs").child(company.getUid()).removeValue();*/


                }
            });
        }

        public void bindView(Company company) {
            this.company = company;
            tv.setText(company.getName());

        }
    }
}
