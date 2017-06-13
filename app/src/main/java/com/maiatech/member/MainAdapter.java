package com.maiatech.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kim4p on 6/4/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<Member> arrMember;
    private int resoure;
    private Context context;

    public static final String DATA = "data";

    public MainAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Member> objects) {
        this.context = context;
        this.resoure = resource;
        this.arrMember = objects;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_member, parent, false);
        ViewHolder holder = new ViewHolder(view, context, arrMember);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Member members = arrMember.get(position);
        holder.txtName.setText(members.getName());
        holder.txtRegency.setText(members.getRegency());
        holder.txtPhone.setText(members.getPhone());
    }

    @Override
    public int getItemCount() {
        return arrMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtPhone, txtRegency;
        //        TextView tvAvatar;
        ArrayList<Member> arrMember = new ArrayList<Member>();
        Context context;

        public ViewHolder(final View itemView, Context context, ArrayList<Member> arrMember) {
            super(itemView);
            this.arrMember = arrMember;
            this.context = context;
            itemView.setOnClickListener(this);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRegency = (TextView) itemView.findViewById(R.id.txtRegency);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Member member = this.arrMember.get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("name", member.getName());
            bundle.putString("regency", member.getRegency());
            bundle.putString("address", member.getAddress());
            bundle.putString("age", member.getAge());
            bundle.putString("phone", member.getPhone());
            bundle.putString("email", member.getEmail());
            bundle.putString("skype", member.getSkype());
            bundle.putString("facebook", member.getFacebook());
            bundle.putString("avatar", member.getAvatar());

            intent.putExtra(DATA, bundle);
            this.context.startActivity(intent);
        }
    }
}
