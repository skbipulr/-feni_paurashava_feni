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
import bd.gov.fenipaurashava.adapterForAPI.AppointmentFetchAllAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppointmentSubjectFetchAllGET.AppointmentSubjectFetchAllResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectFetchAllGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentSubjectListFetchActivity extends AppCompatActivity {


    private RecyclerView appointmentFetchAllRV;
    private AppointmentFetchAllAdapter appointmentFetchAllAdapter;
    private List<Datum> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    AppointmentFetchAllAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoiment_fatch_all);

        initSwipeLayout();
        loadDataFromAPI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppointmentSubjectListFetchActivity.this, AppointmentSubjectEditorActivity.class));
            }
        });
        listener = new AppointmentFetchAllAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(AppointmentSubjectListFetchActivity.this, AppointmentSubjectEditorActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("name", list.get(position).getName());
                startActivity(intent);
            }
        };


    }

    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getAppointmentSubjectFetchResponse(Common.APP_KEY).enqueue(new Callback<AppointmentSubjectFetchAllResponse>() {
            @Override
            public void onResponse(Call<AppointmentSubjectFetchAllResponse> call, Response<AppointmentSubjectFetchAllResponse> response) {
                if (response.code()==200) {
                    AppointmentSubjectFetchAllResponse fetchAllResponse = response.body();

                    assert fetchAllResponse != null;
                    list = fetchAllResponse.getData();
                    initFiled();
                    swipeRefreshLayout.setRefreshing(false);

                }
                else if (response.code() == 203) {
                    Toast.makeText(AppointmentSubjectListFetchActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<AppointmentSubjectFetchAllResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
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

    private void initFiled() {
        appointmentFetchAllRV = findViewById(R.id.appointmentFetchAllRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        appointmentFetchAllAdapter = new AppointmentFetchAllAdapter(this, list,listener);
        appointmentFetchAllRV.setLayoutManager(layoutManager);
        appointmentFetchAllRV.setAdapter(appointmentFetchAllAdapter);
        swipeRefreshLayout.setRefreshing(false);
        appointmentFetchAllAdapter.notifyDataSetChanged();
    }

    public void backBtn(View view) {
        onBackPressed();
    }
}