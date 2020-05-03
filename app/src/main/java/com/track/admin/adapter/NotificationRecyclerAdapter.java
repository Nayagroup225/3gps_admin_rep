package com.track.admin.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.BaseRecycler;
import com.track.admin.R;
import com.track.admin.model.NotificationInfo;

public class NotificationRecyclerAdapter extends BaseRecycler<NotificationInfo> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_notification, parent, false));
    }

    public NotificationRecyclerAdapter() {
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((NotificationViewHolder)holder).setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
