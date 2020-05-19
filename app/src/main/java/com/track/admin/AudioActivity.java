package com.track.admin;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.track.admin.model.BaseRes;
import com.track.admin.preferences.ProgressHelper;
import com.track.admin.retrofit.ApiCall;
import com.track.admin.retrofit.IApiCallback;
import com.track.admin.retrofit.RestClient;

import lib.kingja.switchbutton.SwitchMultiButton;
import retrofit2.Response;

public class AudioActivity extends AppCompatActivity implements IApiCallback<BaseRes> {
    TextView tvTitle, tvAudioFile;
    ImageView ivBackArrow;
//    SwitchMultiButton mRecordSwitch;
    ImageView ivDownload, ivRecord;
    LinearLayout liInRecording, liDownAndRecord, liDownFile;

    String mClientDeviceId = "";
    String mClientId = "";
    String mDownloadUrl = RestClient.BASE_URL+"downloadAudioFile?";
    int mStateRecord;

    static int PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tvTitle = findViewById(R.id.tv_title);
        ivBackArrow = findViewById(R.id.iv_back);
        tvAudioFile = findViewById(R.id.tv_audio_file);
        ivDownload = findViewById(R.id.iv_audio_down);
        ivRecord = findViewById(R.id.iv_audio_record);
        liInRecording = findViewById(R.id.li_in_recording);
        liDownFile = findViewById(R.id.li_audio_file);
        liDownAndRecord = findViewById(R.id.li_audio_down_record);

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mClientDeviceId = getIntent().getStringExtra("client_device_id");
        mClientId = getIntent().getStringExtra("client_id");
        mDownloadUrl+="client_id="+mClientId;
        tvTitle.setText(mClientDeviceId);

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDownloadUrl == null || mDownloadUrl.equals("") || tvAudioFile.getText().toString().equals("")){
                    Toast.makeText(AudioActivity.this, "녹음파일을 다운받을수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_REQUEST_CODE);
                    } else {
                        downLoadAudioFile();
                    }
                } else {
                    downLoadAudioFile();
                }
            }
        });

        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liDownFile.setVisibility(View.GONE);
                liInRecording.setVisibility(View.GONE);
                startRecord();
            }
        });

        getRecordedFile();

    }

    void startRecord() {
        ProgressHelper.dismiss();
        ProgressHelper.showDialog(this);
        ApiCall.getInstance().setRecordState(mClientId, 1, this);
    }

    void getRecordedFile() {
        ProgressHelper.showDialog(this);
        ApiCall.getInstance().getRecordedFile(mClientId, this);
    }

    void downLoadAudioFile() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDownloadUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download...");
        request.setDescription("Downloading Audio File.");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, tvAudioFile.getText().toString());
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        mDownloadUrl = "";
//
//        ProgressHelper.showDialog(this);
//        ApiCall.getInstance().getRecordedFile(mClientId, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                downLoadAudioFile();
            }
        }
    }

    void setDownloadView(){
       liDownFile.setVisibility(View.VISIBLE);
       liDownAndRecord.setVisibility(View.VISIBLE);
        AdminActivity.mAdapter.getItem(getIntent().getIntExtra("index", 0)).setStateRecord(0);
    }

    void setViewByState(String recordState){
        if(recordState.equals("1")){// in recording now
            liInRecording.setVisibility(View.VISIBLE);
            liDownAndRecord.setVisibility(View.GONE);
            AdminActivity.mAdapter.getItem(getIntent().getIntExtra("index", 0)).setStateRecord(1);
        }else{ // first record
            liDownAndRecord.setVisibility(View.VISIBLE);
            AdminActivity.mAdapter.getItem(getIntent().getIntExtra("index", 0)).setStateRecord(0);
        }
    }

    @Override
    public void onSuccess(String type, Response<BaseRes> response) {
        ProgressHelper.dismiss();
        if (type.equals("recorded_file")) {
            Response<BaseRes> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    tvAudioFile.setText(res.body().getErrorMsg());
                    setDownloadView();
                } else if(res.body().getErrorCode().equals("2")){
                    setViewByState(res.body().getErrorMsg());
                }else showToast(res.body().getErrorMsg());
            } else showToast("Something Wrong");
        } else if (type.equals("state_record")) {
            Response<BaseRes> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    AdminActivity.mAdapter.getItem(getIntent().getIntExtra("index", 0)).setStateRecord(Integer.valueOf(res.body().getErrorMsg()));
                    setViewByState("1");
                } else showToast(res.body().getErrorMsg());
            } else showToast("Something Wrong");
        }

    }

    @Override
    public void onFailure(Object data) {
        ProgressHelper.dismiss();
        showToast("Network Wrong");
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
