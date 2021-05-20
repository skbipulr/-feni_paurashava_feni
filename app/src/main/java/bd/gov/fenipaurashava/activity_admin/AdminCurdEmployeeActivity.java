package bd.gov.fenipaurashava.activity_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.AdminCurdEmployeeAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCurdEmployeeActivity extends AppCompatActivity {

    private RecyclerView employeeRV;
    private AdminCurdEmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    private AdminCurdEmployeeAdapter.RecyclerViewCurdClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_curd_employee);

        // initEmployee();
        initSwipeLayout();
        loadDataFromAPI();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCurdEmployeeActivity.this, EmployeeSaveActivity.class));
            }
        });


        listener = new AdminCurdEmployeeAdapter.RecyclerViewCurdClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Datum employee =  employeeList.get(position);

                Intent intent = new Intent(AdminCurdEmployeeActivity.this, AdminCurdEditorActivity.class);
                intent.putExtra("employee", employee);
                startActivity(intent);
            }
        };
    }


    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getEmployeeResponse(Common.APP_KEY).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.code() == 200) {
                    EmployeeResponse employeeResponse = response.body();
                    employeeList = employeeResponse.getData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 203) {
                    Toast.makeText(AdminCurdEmployeeActivity.this, "server problem", Toast.LENGTH_SHORT).show();
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
        employeeAdapter = new AdminCurdEmployeeAdapter(this, employeeList,listener);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
        swipeRefreshLayout.setRefreshing(false);
        employeeAdapter.notifyDataSetChanged();
    }

    public void backBtn(View view) {
        onBackPressed();
    }


}