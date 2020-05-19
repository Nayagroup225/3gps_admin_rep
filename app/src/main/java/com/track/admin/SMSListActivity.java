package com.track.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.track.admin.adapter.SMSRecyclerAdapter;
import com.track.admin.model.BaseRes;
import com.track.admin.model.SMSInfoRes;
import com.track.admin.preferences.ProgressHelper;
import com.track.admin.retrofit.ApiCall;
import com.track.admin.retrofit.IApiCallback;

import retrofit2.Response;


public class SMSListActivity extends AppCompatActivity implements IApiCallback, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mSmsList;
    SMSRecyclerAdapter mAdapter;
    TextView mNoResult;
    ImageView mRefreshBtn;
    ImageView ivBackArrow;

    SwipeRefreshLayout swipeRefreshLayout;
    private int PAGE_SIZE = 10, page = 1;
    private boolean isLoading, isLastPage;
    String mClientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_sms);

        ivBackArrow = findViewById(R.id.iv_back);
        mNoResult = findViewById(R.id.tv_no_notification);
        mSmsList = findViewById(R.id.recycler_sms);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mSmsList.setLayoutManager(manager);
        mAdapter = new SMSRecyclerAdapter();
        mSmsList.setAdapter(mAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        mRefreshBtn = findViewById(R.id.iv_get_sms);
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSmsState();
            }
        });

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mClientId = getIntent().getStringExtra("client_id");
        TextView title = findViewById(R.id.tv_title);
        title.setText(getIntent().getStringExtra("client_device_id"));

        setPageLimit(manager, mSmsList);
    }
    private void setPageLimit(final LinearLayoutManager manager, RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        page = page + 1;
                        getSMSList();
                    }
                }
            }
        });
        getSMSList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setSmsState() {
        ProgressHelper.showDialog(this);
        ApiCall.getInstance().setTrackSmsState(mClientId, 1, this);
    }

    protected void getSMSList() {
        ProgressHelper.showDialog(this);
        mSmsList.setVisibility(View.VISIBLE);
        mNoResult.setVisibility(View.INVISIBLE);
        isLoading = true;
        ApiCall.getInstance().smsList(mClientId, String.valueOf(page), this);
    }

    @Override
    public void onSuccess(String type, Response response) {
        isLoading = false;
        ProgressHelper.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        if (type.equals("sms_list")) {
            if (response.isSuccessful()) {
                if (((SMSInfoRes) response.body()).getErrorCode().equals("0")) {
                    if (page == 1)
                        mAdapter.clear();
                    mAdapter.addAllItem(((SMSInfoRes) response.body()).getData());
                    isLastPage = ((SMSInfoRes) response.body()).getData().size() != PAGE_SIZE;
                    setEmpty();
                } else {
                    mNoResult.setVisibility(View.VISIBLE);
                }
            } else {
                mNoResult.setVisibility(View.VISIBLE);
            }
        } else if (type.equals("state_sms")) {
            if (response.isSuccessful()) {
                if (((BaseRes) response.body()).getErrorCode().equals("0")) {
                    page = 1;
                    getSMSList();
                } else {
                    Toast.makeText(this, ((BaseRes) response.body()).getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setEmpty() {
        mNoResult.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onFailure(Object data) {
        isLoading = false;
        mNoResult.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Something wrong.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        page = 1;
        ApiCall.getInstance().smsList(mClientId, String.valueOf(page), this);
    }
}
