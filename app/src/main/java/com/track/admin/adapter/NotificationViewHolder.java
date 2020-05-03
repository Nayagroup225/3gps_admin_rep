package com.track.admin.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.R;
import com.track.admin.model.NotificationInfo;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView message, date;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        message=itemView.findViewById(R.id.tv_message);
        date = itemView.findViewById(R.id.tv_date);
    }

    public void setData(NotificationInfo data){
        date.setText(data.getCreatedAt());
        message.setText(data.getMsg());
    }
}
