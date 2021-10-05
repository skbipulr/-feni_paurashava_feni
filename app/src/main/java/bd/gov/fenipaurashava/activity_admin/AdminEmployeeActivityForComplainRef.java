package bd.gov.fenipaurashava.activity_admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import bd.gov.fenipaurashava.modelForComplainReferringPOST.ComplainReferringResponse;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEmployeeActivityForComplainRef extends AppCompatActivity implements AdminEmployeeAdapter.OnEmployeeItemClickListener {

    private RecyclerView employeeRV;
    private AdminEmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    bd.gov.fenipaurashava.modelForComplainFechAllGET.Datum dataClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee_for_complain_ref);

        // initEmployee();
        initSwipeLayout();
        loadDataFromAPI();

        Intent i = getIntent();
        dataClass = (bd.gov.fenipaurashava.modelForComplainFechAllGET.Datum) i.getSerializableExtra("allInfo");

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
                    Toast.makeText(AdminEmployeeActivityForComplainRef.this, "server problem", Toast.LENGTH_SHORT).show();
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
        dialog.setMessage("Are you referring this complain?");
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
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        int employeeId = Integer.parseInt(employee.getId());
        int itemId = dataClass.getId();

        final ProgressDialog mDialog = new ProgressDialog(AdminEmployeeActivityForComplainRef.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();
        Call<ComplainReferringResponse> call = apiService.setComplainReferringResponse("A1b1C2d32564kjhkjadu", use_id, itemId, employeeId);

        call.enqueue(new Callback<ComplainReferringResponse>() {
            @Override
            public void onResponse(Call<ComplainReferringResponse> call, Response<ComplainReferringResponse> response) {

                ComplainReferringResponse referringResponse = response.body();

                if (response.code() == 200) {
                    referringResponse.getMessage();
                    Toast.makeText(AdminEmployeeActivityForComplainRef.this, "" + referringResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 203) {
                    Toast.makeText(AdminEmployeeActivityForComplainRef.this, "Fail", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 401) {
                    Toast.makeText(AdminEmployeeActivityForComplainRef.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 422) {
                    Toast.makeText(AdminEmployeeActivityForComplainRef.this, "Validation Error", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ComplainReferringResponse> call, Throwable t) {
                //Toast.makeText(AdminEmployeeActivityForComplainRef.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("error",t.getMessage());
                mDialog.dismiss();
            }
        });

    }
}