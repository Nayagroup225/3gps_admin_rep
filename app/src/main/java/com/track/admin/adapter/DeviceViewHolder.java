package com.track.admin.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.R;
import com.track.admin.model.UserListInfo;

import lib.kingja.switchbutton.SwitchMultiButton;

public class DeviceViewHolder extends RecyclerView.ViewHolder {
    public TextView deviceId;
    public LinearLayout tvStateGps, tvStateSms, tvStateRecord;
    public ImageView ivStateGps, ivStateSms, ivStateRecord;
    EditText nickname;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceId=itemView.findViewById(R.id.tv_device_id);
        nickname = itemView.findViewById(R.id.et_nickname);
//        locationDistance = itemView.findViewById(R.id.tv_distance);
        tvStateGps = itemView.findViewById(R.id.li_state_gps);
        tvStateSms = itemView.findViewById(R.id.li_state_sms);
        tvStateRecord = itemView.findViewById(R.id.li_state_record);

        ivStateGps = itemView.findViewById(R.id.iv_state_gps);
        ivStateSms = itemView.findViewById(R.id.iv_state_sms);
        ivStateRecord = itemView.findViewById(R.id.iv_state_record);

    }

    public void setData(UserListInfo data){

        deviceId.setText(data.getDeviceId());
        nickname.setText(data.getNickName());
        if(data.getStateGps() == 1){
            ivStateGps.setImageResource(android.R.drawable.presence_online);
        }else{
            ivStateGps.setImageResource(android.R.drawable.presence_offline);
        }

        if(data.getStateSms() == 1){
            ivStateSms.setImageResource(android.R.drawable.presence_online);
        }else{
            ivStateSms.setImageResource(android.R.drawable.presence_offline);
        }

        if(data.getStateRecord() == 1){
            ivStateRecord.setImageResource(android.R.drawable.presence_online);
        }else{
            ivStateRecord.setImageResource(android.R.drawable.presence_offline);
        }
    }
}
