package com.scriptech.basicbankingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_selectUser extends RecyclerView.Adapter<ViewHolder> {

    selectUser selectUser;
    List<Model> modelList;
    String s;

    public Adapter_selectUser(selectUser sentoUser, List<Model> modelList) {
        this.selectUser = sentoUser;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectUser.selectuser(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText(modelList.get(position).getName());
        holder.mPhonenumber.setText(modelList.get(position).getPhoneno());
        holder.mBalance.setText(modelList.get(position).getBalance());
        s=modelList.get(position).getImage();
        int i = Integer.parseInt(s);
        if(i==1){
            holder.mImage.setImageResource(R.drawable.user_type1);
        }
        else if(i==2){
            holder.mImage.setImageResource(R.drawable.user_type2);
        }
        else if(i==3){
            holder.mImage.setImageResource(R.drawable.user_type3);
        }
        else if(i==4){
            holder.mImage.setImageResource(R.drawable.user_type4);
        }
        else {
            holder.mImage.setImageResource(R.drawable.user_type5);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
