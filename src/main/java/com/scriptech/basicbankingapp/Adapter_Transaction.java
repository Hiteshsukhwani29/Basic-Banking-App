package com.scriptech.basicbankingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Transaction extends RecyclerView.Adapter<ViewHolder> {

    transactions HistoryList;
    List<Model> modelList;

    public Adapter_Transaction(transactions historyList, List<Model> modelList) {
        this.HistoryList = historyList;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName1.setText(modelList.get(position).getName1());
        holder.mName2.setText(modelList.get(position).getName2());
        holder.mBalance.setText(modelList.get(position).getBalance());
        holder.mDate.setText(modelList.get(position).getDate());
        holder.mDesc.setText(modelList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
