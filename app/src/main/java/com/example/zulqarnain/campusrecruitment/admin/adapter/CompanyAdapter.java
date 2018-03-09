package com.example.zulqarnain.campusrecruitment.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.admin.CompanyAdminDetailActivity;
import com.example.zulqarnain.campusrecruitment.models.Company;

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
        View view = LayoutInflater.from(ctx).inflate(android.R.layout.simple_list_item_1, parent, false);
        ComViewHolder holder = new ComViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComViewHolder holder, int position) {
            holder.tv.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ComViewHolder extends RecyclerView.ViewHolder  {
        TextView tv;
        public ComViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, CompanyAdminDetailActivity.class);
                    intent.putExtra("uid",data.get(getAdapterPosition()).getUid());
                    ctx.startActivity(intent);
                }
            });
        }

    }
}
