package bd.gov.fenipaurashava.activity_admin;

import android.content.Context;
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
import bd.gov.fenipaurashava.adapterForAPI.ComplainFetchAllAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForComplainFechAllGET.ComplainResponse;
import bd.gov.fenipaurashava.modelForComplainFechAllGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainFetchAllActivity extends AppCompatActivity {

    private RecyclerView complainsRV;
    private ComplainFetchAllAdapter complainFetchAllAdapter;
    private List<Datum> complainList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;


    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_fetch_all);

        initSwipeLayout();
        loadDataFromAPI();
    }

    private void loadDataFromAPI() {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID,"");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

        // Toast.makeText(this, "user "+user_id+" employee"+employee_id, Toast.LENGTH_SHORT).show();

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        Log.d("user id",user_id);

        if (use_id==Common.ADMIN_USER_ID){
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            apiService.getComplainResponse(Common.APP_KEY,0, use_id).enqueue(new Callback<ComplainResponse>() {
                @Override
                public void onResponse(Call<ComplainResponse> call, Response<ComplainResponse> response) {

                    if (response.isSuccessful()) {

                        ComplainResponse complainResponse = response.body();

                        complainList = complainResponse.getData();
                        initComplains();
                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        Toast.makeText(ComplainFetchAllActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ComplainResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else {
             apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            apiService.getComplainResponse(Common.APP_KEY,em_id, use_id).enqueue(new Callback<ComplainResponse>() {
                @Override
                public void onResponse(Call<ComplainResponse> call, Response<ComplainResponse> response) {

                    if (response.isSuccessful()) {

                        ComplainResponse complainResponse = response.body();

                        complainList = complainResponse.getData();
                        initComplains();
                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        Toast.makeText(ComplainFetchAllActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ComplainResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }

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

    private void initComplains() {
        complainsRV = findViewById(R.id.complainsRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        complainFetchAllAdapter = new ComplainFetchAllAdapter(this, complainList);
        complainsRV.setLayoutManager(layoutManager);
        complainsRV.setAdapter(complainFetchAllAdapter);
        swipeRefreshLayout.setRefreshing(false);
        complainFetchAllAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}