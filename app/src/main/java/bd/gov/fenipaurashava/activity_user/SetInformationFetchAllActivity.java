package bd.gov.fenipaurashava.activity_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.SetInformationAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForSetInformationFetchGET.Datum;
import bd.gov.fenipaurashava.modelForSetInformationFetchGET.InformationGetResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetInformationFetchAllActivity extends AppCompatActivity {

    private RecyclerView publicHearingRV;
    private SetInformationAdapter publicHeadingAdapter;
    private List<Datum> publicHearingList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information_fetch_all);

        initSwipeLayout();
        loadDataFromAPI();
    }



    private void loadDataFromAPI() {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID,"");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

       // Toast.makeText(this, "user id "+use_id, Toast.LENGTH_SHORT).show();

        if (use_id==Common.ADMIN_USER_ID){

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            apiService.getInformationGetResponse(Common.APP_KEY,use_id,0).enqueue(new Callback<InformationGetResponse>() {
                @Override
                public void onResponse(Call<InformationGetResponse> call, Response<InformationGetResponse> response) {

                    InformationGetResponse publicHearingResponse = response.body();
                    if (response.code()==200) {

                        publicHearingList = publicHearingResponse.getData();
                        init();
                        swipeRefreshLayout.setRefreshing(false);
                        // Toast.makeText(SetInformationFetchAllActivity.this, ""+publicHearingList.get(0).getName(), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.code() == 203) {
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }else if (response.code() == 401){
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }else if (response.code() == 422){
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<InformationGetResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        }else {
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            apiService.getInformationGetResponse(Common.APP_KEY,use_id,em_id).enqueue(new Callback<InformationGetResponse>() {
                @Override
                public void onResponse(Call<InformationGetResponse> call, Response<InformationGetResponse> response) {

                    InformationGetResponse publicHearingResponse = response.body();
                    if (response.code()==200) {

                        publicHearingList = publicHearingResponse.getData();
                        init();
                        swipeRefreshLayout.setRefreshing(false);
                        // Toast.makeText(SetInformationFetchAllActivity.this, ""+publicHearingList.get(0).getName(), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.code() == 203) {
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }else if (response.code() == 401){
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }else if (response.code() == 422){
                        Toast.makeText(SetInformationFetchAllActivity.this, publicHearingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<InformationGetResponse> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public void backBtn(View view) {
        onBackPressed();
    }


    private void init() {
        publicHearingRV = findViewById(R.id.publicHearingRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        publicHeadingAdapter = new SetInformationAdapter(this, publicHearingList);
        publicHearingRV.setLayoutManager(layoutManager);
        publicHearingRV.setAdapter(publicHeadingAdapter);
        swipeRefreshLayout.setRefreshing(false);
        publicHeadingAdapter.notifyDataSetChanged();

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
                //  initSwipeLayout();
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

}