package com.track.admin;

import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.track.admin.model.BaseRes;
import com.track.admin.preferences.AppSharedPreference;
import com.track.admin.preferences.ProgressHelper;
import com.track.admin.retrofit.ApiCall;
import com.track.admin.retrofit.IApiCallback;

import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, IApiCallback<BaseRes> {
    TextView mTitle;
    ImageView mBackArrow, mRefresh, mChangeMap;
    SwitchMultiButton mTrackSwitch;

    String mClientDeviceId = "";
    String mClientId = "";
    int mStateGps;
    int mMapType = 1;

    ArrayList<LatLng> mPositionsArray;

    private GoogleMap googleMap;
    SupportMapFragment mMapFragment;
    Polyline mPolyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTitle = findViewById(R.id.tv_title);
        mBackArrow = findViewById(R.id.iv_back);
        mTrackSwitch = findViewById(R.id.switch_track);
        mRefresh = findViewById(R.id.iv_refresh);
        mChangeMap = findViewById(R.id.iv_change_map);

        mChangeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapType++;
                mMapType %= 5;
                if(mMapType == 0){
                    mMapType = 1;
                }
                googleMap.setMapType(mMapType);
                googleMap.clear();
                int size = mPositionsArray.size();
                LatLng userPosition = mPositionsArray.get(size-1);

                int height = 100;
                int width = 100;
                Bitmap startMarker = Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_point_start)).getBitmap(), width, height, false);
                Bitmap endMarker = Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_point_end)).getBitmap(), width, height, false);

                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(startMarker)).position(userPosition).title("First Position"));
                if(size>=2) {
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(endMarker)).position(mPositionsArray.get(0)).title("Second Position"));
                }

                PolylineOptions polylineOptions = new PolylineOptions();
                for (LatLng point : mPositionsArray) {
                    polylineOptions.add(point);
                }
                polylineOptions.width(5.0f).color(Color.RED);
                googleMap.addPolyline(polylineOptions);
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPositionsArray.clear();
//                googleMap.clear();
                getClientPoints();
            }
        });

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity.super.onBackPressed();
            }
        });

        mClientDeviceId = getIntent().getStringExtra("client_device_id");
        mClientId = getIntent().getStringExtra("client_id");
        mTitle.setText(mClientDeviceId);
        mStateGps = getIntent().getIntExtra("state_gps", 0);
        if (mStateGps == 1) {
            mTrackSwitch.setSelectedTab(0);
        } else {
            mTrackSwitch.setSelectedTab(1);
        }

        mTrackSwitch.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                setTrackGpsState(position);
            }
        });

        mPositionsArray = new ArrayList<LatLng>();
        getClientPoints();
        mMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.setMapType(mMapType);
    }

    void drawMap(){
        googleMap.clear();
        // and move the map's camera to the same location.
        int size = mPositionsArray.size();
        LatLng userPosition = mPositionsArray.get(size-1);

        int height = 100;
        int width = 100;
        Bitmap startMarker = Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_point_start)).getBitmap(), width, height, false);
        Bitmap endMarker = Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_point_end)).getBitmap(), width, height, false);

        this.googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(startMarker)).position(userPosition).title("First Position"));
        if(size>=2) {
            this.googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(endMarker)).position(mPositionsArray.get(0)).title("Second Position"));
        }
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 18.0f));

        PolylineOptions polylineOptions = new PolylineOptions();
        for (LatLng point : mPositionsArray) {
            polylineOptions.add(point);
        }
        polylineOptions.width(5.0f).color(Color.RED);
        this.googleMap.addPolyline(polylineOptions);
    }

    void setTrackGpsState(int state) {
        ProgressHelper.dismiss();
        ProgressHelper.showDialog(this);
        if (state == 0) {
            state = 1;
        } else {
            state = 0;
        }
        ApiCall.getInstance().setTrackGpsState(mClientId, state, this);
    }

    void getClientPoints() {
        ProgressHelper.showDialog(this);
        ApiCall.getInstance().getClientPoints(mClientId, this);
    }

    @Override
    public void onSuccess(String type, Response<BaseRes> response) {
        ProgressHelper.dismiss();
        if (type.equals("points")) {
            Response<BaseRes> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    if(res.body().getErrorMsg().equals("")) return;
                    String[] points = res.body().getErrorMsg().split(",");
                    for (int i = 0; i < points.length; i += 2) {
                        mPositionsArray.add(new LatLng(Double.valueOf(points[i]), Double.valueOf(points[i + 1])));
                    }
                    drawMap();
                } else showToast(res.body().getErrorMsg());
            } else showToast("Something Wrong");
        }else if (type.equals("state_gps")) {
            Response<BaseRes> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    AdminActivity.mAdapter.getItem(getIntent().getIntExtra("index",0)).setStateGps(Integer.valueOf(res.body().getErrorMsg()));
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
