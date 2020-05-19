package com.track.admin.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.BaseRecycler;
import com.track.admin.R;
import com.track.admin.SMSDetailActivity;
import com.track.admin.model.SMSInfo;

public class SMSRecyclerAdapter extends BaseRecycler<SMSInfo> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SMSViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_sms, parent, false));
    }

    public SMSRecyclerAdapter() {
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        SMSInfo info = list.get(position);
        ((SMSViewHolder)holder).setData(info);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SMSDetailActivity.class);
                intent.putExtra("type", info.getType());
                intent.putExtra("read_state", info.getReadState());
                intent.putExtra("body", info.getBody());
                intent.putExtra("address", info.getAddress());
                intent.putExtra("date", info.getDate().replace("T", " ").replace(".000Z", ""));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
