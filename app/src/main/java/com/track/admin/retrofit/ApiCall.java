package com.track.admin.retrofit;

import com.track.admin.model.BaseRes;
import com.track.admin.model.NotificationInfoRes;
import com.track.admin.model.SMSInfoRes;
import com.track.admin.model.UserListRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static APIService service;

    public static ApiCall getInstance() {
        if (service == null) {
            service = RestClient.getClient();
        }
        return new ApiCall();
    }

    public void getClientList(String identity, final IApiCallback<UserListRes> iApiCallback) {
        Call<UserListRes> call = service.getClientList(identity);
        call.enqueue(new Callback<UserListRes>() {
            @Override
            public void onResponse(Call<UserListRes> call, Response<UserListRes> response) {
                iApiCallback.onSuccess("list", response);
            }

            @Override
            public void onFailure(Call<UserListRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void setState(String deviceId, String state, String blockNumber, String nickname, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.setState(deviceId, state, blockNumber, nickname);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("set_state", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void adminLogin(String deviceId, String phoneNumber, String identity, String imei, IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.adminLogin(deviceId, phoneNumber, imei, identity);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("check", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());
            }
        });
    }

    public void notificationList(String page, final IApiCallback<NotificationInfoRes> iApiCallback) {
        Call<NotificationInfoRes> call = service.notificationList(page);
        call.enqueue(new Callback<NotificationInfoRes>() {
            @Override
            public void onResponse(Call<NotificationInfoRes> call, Response<NotificationInfoRes> response) {
                iApiCallback.onSuccess("notification_list", response);
            }

            @Override
            public void onFailure(Call<NotificationInfoRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void smsList(String clientId, String page, final IApiCallback<SMSInfoRes> iApiCallback) {
        Call<SMSInfoRes> call = service.smsList(clientId, page);
        call.enqueue(new Callback<SMSInfoRes>() {
            @Override
            public void onResponse(Call<SMSInfoRes> call, Response<SMSInfoRes> response) {
                iApiCallback.onSuccess("sms_list", response);
            }

            @Override
            public void onFailure(Call<SMSInfoRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void getClientPoints(String clientId, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.getClientPoints(clientId);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("points", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void setTrackGpsState(String clientId, int stateGps, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.setTrackGpsState(clientId, stateGps);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("state_gps", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void setTrackSmsState(String clientId, int stateSms, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.setTrackSmsState(clientId, stateSms);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("state_sms", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void setRecordState(String clientId, int stateRecord, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.setRecordState(clientId, stateRecord);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("state_record", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void getRecordedFile(String clientId, final IApiCallback<BaseRes> iApiCallback) {
        Call<BaseRes> call = service.getRecordedFile(clientId);
        call.enqueue(new Callback<BaseRes>() {
            @Override
            public void onResponse(Call<BaseRes> call, Response<BaseRes> response) {
                iApiCallback.onSuccess("recorded_file", response);
            }

            @Override
            public void onFailure(Call<BaseRes> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }


}