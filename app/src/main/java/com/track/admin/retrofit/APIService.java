package com.track.admin.retrofit;

import com.track.admin.model.BaseRes;
import com.track.admin.model.NotificationInfoRes;
import com.track.admin.model.SMSInfoRes;
import com.track.admin.model.UserListRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface APIService {
    @FormUrlEncoded
    @POST("getTrackClientList")
    Call<UserListRes> getClientList(@Field("identity") String identify);

    @FormUrlEncoded
    @POST("setClientState")
    Call<BaseRes> setState(@Field("device_id") String deviceId,
                           @Field("state") String state,
                           @Field("block_number") String blockNumber,
                           @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("trackAdminLogin")
    Call<BaseRes> adminLogin(@Field("user_name") String adminId,
                                  @Field("password") String password,
                                  @Field("identity") String identify,
                             @Field("imei") String imei);

    @FormUrlEncoded
    @POST("getTrackNotification")
    Call<NotificationInfoRes> notificationList(@Field("page") String page);

    @FormUrlEncoded
    @POST("getSMSList")
    Call<SMSInfoRes> smsList(
            @Field("client_id") String clientId,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("getClientPoints")
    Call<BaseRes> getClientPoints(@Field("client_id") String clientId);

    @FormUrlEncoded
    @POST("setStateGps")
    Call<BaseRes> setTrackGpsState(@Field("client_id") String clientId,
                                   @Field("state_gps") int stateGps);

    @FormUrlEncoded
    @POST("setStateSms")
    Call<BaseRes> setTrackSmsState(@Field("client_id") String clientId,
                                   @Field("state_sms") int stateSms);

    @FormUrlEncoded
    @POST("setStateRecord")
    Call<BaseRes> setRecordState(@Field("client_id") String clientId,
                                   @Field("state_record") int stateRecord);

    @FormUrlEncoded
    @POST("getRecordedFile")
    Call<BaseRes> getRecordedFile(@Field("client_id") String clientId);
}