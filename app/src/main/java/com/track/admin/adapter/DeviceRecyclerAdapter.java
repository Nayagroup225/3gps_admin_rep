package com.track.admin.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.track.admin.AudioActivity;
import com.track.admin.BaseRecycler;
import com.track.admin.MapActivity;
import com.track.admin.R;
import com.track.admin.SMSListActivity;
import com.track.admin.model.UserListInfo;
import com.track.admin.preferences.IRecyclerClickListener;

import lib.kingja.switchbutton.SwitchMultiButton;

public class DeviceRecyclerAdapter extends BaseRecycler<UserListInfo> {
    private IRecyclerClickListener listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_offer, parent, false));
    }

    public DeviceRecyclerAdapter(IRecyclerClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((DeviceViewHolder)holder).setData(list.get(position));
        LinearLayout gpsBtn = holder.itemView.findViewById(R.id.li_state_gps);
        View.OnClickListener goToMapListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("client_id", list.get(position).getId());
                intent.putExtra("index", position);
                if(!list.get(position).getNickName().equals("")){
                    intent.putExtra("client_device_id", list.get(position).getNickName()+"("+list.get(position).getDeviceId()+")");
                }else{
                    intent.putExtra("client_device_id", list.get(position).getDeviceId());
                }
                intent.putExtra("state_gps", list.get(position).getStateGps());
                v.getContext().startActivity(intent);
            }
        };
        gpsBtn.setOnClickListener(goToMapListener);

        holder.itemView.findViewById(R.id.li_state_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SMSListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("client_id", list.get(position).getId());
                intent.putExtra("index", position);
                if(!list.get(position).getNickName().equals("")){
                    intent.putExtra("client_device_id", list.get(position).getNickName()+"("+list.get(position).getDeviceId()+")");
                }else{
                    intent.putExtra("client_device_id", list.get(position).getDeviceId());
                }
                intent.putExtra("state_sms", list.get(position).getStateSms());
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.findViewById(R.id.li_state_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AudioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("client_id", list.get(position).getId());
                intent.putExtra("index", position);
                if(!list.get(position).getNickName().equals("")){
                    intent.putExtra("client_device_id", list.get(position).getNickName()+"("+list.get(position).getDeviceId()+")");
                }else{
                    intent.putExtra("client_device_id", list.get(position).getDeviceId());
                }
                intent.putExtra("state_record", list.get(position).getStateRecord());
                v.getContext().startActivity(intent);
            }
        });
//        SwitchMultiButton switchBlock = holder.itemView.findViewById(R.id.switch_lock);
//        switchBlock.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
//            @Override
//            public void onSwitch(int position, String tabText) {
//
//                listener.onRecyclerClick(holder.getAdapterPosition(), tabText, ((EditText)holder.itemView.findViewById(R.id.et_block_number)).getText(), ((EditText)holder.itemView.findViewById(R.id.et_nickname)).getText() );
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
