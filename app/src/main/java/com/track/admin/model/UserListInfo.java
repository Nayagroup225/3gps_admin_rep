package com.track.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListInfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("state_gps")
    @Expose
    private int stateGps;

    @SerializedName("state_sms")
    @Expose
    private int stateSms;

    @SerializedName("state_record")
    @Expose
    private int stateRecord;

    @SerializedName("nick_name")
    @Expose
    private String nickName;

    public String getId() {
        return id;
    }

    public String getDeviceId(){
        return deviceId;
    }

    public int getStateGps() {
        return stateGps;
    }

    public int getStateRecord() {
        return stateRecord;
    }

    public int getStateSms() {
        return stateSms;
    }

    public void setStateGps(int stateGps) {
        this.stateGps = stateGps;
    }

    public void setStateSms(int stateSms) {
        this.stateSms = stateSms;
    }

    public void setStateRecord(int stateRecord) {
        this.stateRecord = stateRecord;
    }

    public String getNickName() { return nickName; }

}
