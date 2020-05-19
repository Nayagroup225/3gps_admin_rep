package com.track.admin.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.R;
import com.track.admin.model.NotificationInfo;
import com.track.admin.model.SMSInfo;

public class SMSViewHolder extends RecyclerView.ViewHolder {
    public TextView body, date, address;
    ImageView ivReadState, ivType;

    public SMSViewHolder(@NonNull View itemView) {
        super(itemView);
        address=itemView.findViewById(R.id.tv_address);
        body=itemView.findViewById(R.id.tv_body);
        date = itemView.findViewById(R.id.tv_date);
        ivReadState = itemView.findViewById(R.id.iv_read_state);
        ivType = itemView.findViewById(R.id.iv_type);
    }

    public void setData(SMSInfo data){
        address.setText(data.getAddress());
        date.setText(data.getDate().replace("T", " ").replace(".000Z", ""));
        body.setText(data.getBody());
        if(data.getType() != 1){
            ivType.setImageResource(R.drawable.icon_send);
        }
        if(data.getReadState() == 0){
            ivReadState.setImageResource(R.drawable.icon_unread);
        }
    }
}
