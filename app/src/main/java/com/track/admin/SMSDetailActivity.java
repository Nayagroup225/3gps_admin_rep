package com.track.admin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.track.admin.model.BaseRes;
import com.track.admin.preferences.ProgressHelper;
import com.track.admin.retrofit.ApiCall;
import com.track.admin.retrofit.IApiCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;
import retrofit2.Response;

public class SMSDetailActivity extends AppCompatActivity {
    ImageView mReadStateImg, mTypeImg, ivBackImage;
    String mBody = "", mDate, mAddress = "";
    int mType, mReadState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);
        mReadStateImg = findViewById(R.id.iv_read_state);
        mTypeImg = findViewById(R.id.iv_type);
        mType = getIntent().getIntExtra("type", 0);
        mReadState = getIntent().getIntExtra("read_state", 0);
        mBody = getIntent().getStringExtra("body");
        mAddress = getIntent().getStringExtra("address");
        mDate = getIntent().getStringExtra("date");
        TextView tvAddress = findViewById(R.id.tv_address);
        TextView tvDate = findViewById(R.id.tv_date);
        TextView tvBody = findViewById(R.id.tv_body);
        if(mType != 1){
            mTypeImg.setImageResource(R.drawable.icon_send);
        }
        if(mReadState == 0){
            mReadStateImg.setImageResource(R.drawable.icon_unread);
        }
        tvAddress.setText(mAddress);
        tvDate.setText(mDate);
        tvBody.setText(mBody);
        ivBackImage = findViewById(R.id.iv_back);
        ivBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
