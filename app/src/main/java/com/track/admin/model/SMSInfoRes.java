package com.track.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SMSInfoRes extends BaseRes {

    @SerializedName("data")
    @Expose
    private List<SMSInfo> data = null;


    public List<SMSInfo> getData() {
        return data;
    }

    public void setData(List<SMSInfo> data) {
        this.data = data;
    }
}
