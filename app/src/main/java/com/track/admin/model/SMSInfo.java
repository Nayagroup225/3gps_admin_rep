package com.track.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMSInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("read_state")
    @Expose
    private int readState;
    @SerializedName("type")
    @Expose
    private int type;

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public int getReadState() {
        return readState;
    }

    public int getType() {
        return type;
    }
}
