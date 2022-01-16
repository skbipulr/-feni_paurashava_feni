package bd.gov.fenipaurashava.activity_user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.EmployeeAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {

    private RecyclerView employeeRV;
    private EmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    String id,departmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        departmentName = intent.getStringExtra("departmentName");
        TextView designationTV = findViewById(R.id.designationTV);
        designationTV.setText(departmentName);

        // initEmployee();
        initSwipeLayout();
        loadDataFromAPI();

    }


    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getDepartmentWiseEmployeeResponse(Common.APP_KEY, id).enqueue(new Callback<EmployeeResponse>() {

            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.code() == 200) {
                    EmployeeResponse employeeResponse = response.body();

                    employeeList = employeeResponse.getEmployeeData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);
                    //  Toast.makeText(EmployeeActivity.this, ""+employeeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 203) {
                    Toast.makeText(EmployeeActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(EmployeeActivity.this, "Please wait for a movement...", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

            }
        });
    }

    private void initSwipeLayout() {
        //view
        swipeRefreshLayout = findViewById(R.id.employeeSwipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataFromAPI();
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadDataFromAPI();
                swipeRefreshLayout.setRefreshing(true);
            }

        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void initEmployee() {
        employeeRV = findViewById(R.id.employeeRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        employeeAdapter = new EmployeeAdapter(this, employeeList);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
        swipeRefreshLayout.setRefreshing(false);
        employeeAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}