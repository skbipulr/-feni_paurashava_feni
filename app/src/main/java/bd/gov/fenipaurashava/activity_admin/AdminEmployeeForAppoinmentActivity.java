package bd.gov.fenipaurashava.activity_admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.AdminEmployeeAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppoinmentReferringPOST.AppointmentReferringResponse;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEmployeeForAppoinmentActivity extends AppCompatActivity implements AdminEmployeeAdapter.OnEmployeeItemClickListener {

    private RecyclerView employeeRV;
    private AdminEmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    bd.gov.fenipaurashava.modelForAppointmentFetchGET.Datum dataClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee_for_appoinment);

        // initEmployee();
        initSwipeLayout();
        loadDataFromAPI();

        Intent i = getIntent();
        dataClass = (bd.gov.fenipaurashava.modelForAppointmentFetchGET.Datum) i.getSerializableExtra("allInfo");

        assert dataClass != null;
      //  Toast.makeText(this, "" + dataClass.getId(), Toast.LENGTH_SHORT).show();

    }


    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getEmployeeResponse(Common.APP_KEY).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.code() == 200) {
                    EmployeeResponse employeeResponse = response.body();
                    employeeList = employeeResponse.getEmployeeData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 203) {
                    Toast.makeText(AdminEmployeeForAppoinmentActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.d("error",t.getMessage());
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

    private void initEmployee() {
        employeeRV = findViewById(R.id.employeeRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        employeeAdapter = new AdminEmployeeAdapter(this, employeeList);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
        swipeRefreshLayout.setRefreshing(false);
        employeeAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }

    @Override
    public void onEmployeeItemClick(Datum employee) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you referring this appointment?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createRefer(employee);
                dialog.dismiss();

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void createRefer(Datum employee) {

       int employeeId = Integer.parseInt(employee.getId());
       int itemId = dataClass.getId();

        final ProgressDialog mDialog = new ProgressDialog(AdminEmployeeForAppoinmentActivity.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();
        Call<AppointmentReferringResponse> call  = apiService.setAppointmentReferringResponse("A1b1C2d32564kjhkjadu",1,itemId,employeeId);

        call.enqueue(new Callback<AppointmentReferringResponse>() {
            @Override
            public void onResponse(Call<AppointmentReferringResponse> call, Response<AppointmentReferringResponse> response) {

                AppointmentReferringResponse referringResponse = response.body();

                if (response.code() ==200){
                    referringResponse.getMessage();
                    Toast.makeText(AdminEmployeeForAppoinmentActivity.this, ""+referringResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
                else if (response.code() == 203) {
                    Toast.makeText(AdminEmployeeForAppoinmentActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 401) {
                    Toast.makeText(AdminEmployeeForAppoinmentActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 422) {
                    Toast.makeText(AdminEmployeeForAppoinmentActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<AppointmentReferringResponse> call, Throwable t) {
                Toast.makeText(AdminEmployeeForAppoinmentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }
}