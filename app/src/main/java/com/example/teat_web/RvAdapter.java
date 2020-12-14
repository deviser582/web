package com.example.teat_web;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder>  {
    private List<RvData> list;

    public RvAdapter(List<RvData> list) {
        this.list = list;
    }

    class RvViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView shareUser;
        public TextView link;

        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            shareUser = itemView.findViewById(R.id.shareUser);
            link = itemView.findViewById(R.id.link);
        }
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        return new RvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder,int position) {
        RvData rvData = list.get(position);

        holder.title.setText(rvData.getTitle());
        holder.shareUser.setText(rvData.getShareUser());
        holder.link.setText(rvData.getLink());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
