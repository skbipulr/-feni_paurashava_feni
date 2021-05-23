package bd.gov.fenipaurashava.activity_user;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.WorkScheduleFetchAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.WorkScheduleFetchResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyWorkActivity extends AppCompatActivity {

    private RecyclerView scheduleRV;
    private WorkScheduleFetchAdapter scheduleFetchAdapter;
    private List<Datum> scheduleList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_work);
        initSwipeLayout();
        loadDataFromAPI();
    }

    private void loadDataFromAPI() {

        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = simpleDateFormat.format(calendar.getTime());

        apiService.getWorkScheduleFetchResponse(Common.APP_KEY, Common.ADMIN_EMP_ID,datetime,"2025-05-24").enqueue(new Callback<WorkScheduleFetchResponse>() {
            @Override
            public void onResponse(Call<WorkScheduleFetchResponse> call, Response<WorkScheduleFetchResponse> response) {
                if (response.code()==200) {
                    WorkScheduleFetchResponse fetchResponse = response.body();

                    scheduleList = fetchResponse.getData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(DailyWorkActivity.this, ""+scheduleList.size(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();

                    if (scheduleList.size()==0){
                        Toast.makeText(DailyWorkActivity.this, "No more schedule for today.", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (response.code() == 203) {
                    Toast.makeText(DailyWorkActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<WorkScheduleFetchResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initSwipeLayout() {
        //view
        swipeRefreshLayout = findViewById(R.id.scheduleSwipeLayout);
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
        scheduleRV = findViewById(R.id.scheduleRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scheduleFetchAdapter = new WorkScheduleFetchAdapter(this, scheduleList);
        scheduleRV.setLayoutManager(layoutManager);
        scheduleRV.setAdapter(scheduleFetchAdapter);
        swipeRefreshLayout.setRefreshing(false);
        scheduleFetchAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}