package bd.gov.fenipaurashava.activity_user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
   // private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;


    private Calendar calendar;
    private int year, month, fromDay, toDay, hour, minute;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    String userFromDate, userToDate, nFromDate, nToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_work);

        //initFilData();
       // initSwipeLayout();
       // loadDataFromAPI();
        initEmployee();

        loadData();

    }

    private void loadData() {
        scheduleList.add(new Datum(R.drawable.daily_3,"রোজাদারদের  মাঝে ইফতার বিতরণ করার কিছু সময়।","18-04-2021","",""));
        scheduleList.add(new Datum(R.drawable.daily_2,"ফেনী পৌরসভা বিনামূল্যে মাস্ক বিতরণ করার কিছু সময়","14-04-2021","",""));
        scheduleList.add(new Datum(R.drawable.daily_1,"আজ করোনা সংক্রমণ মোকাবেলায় সচেতনতা বৃদ্ধির লক্ষ্যে ফেনী পৌরসভার ১৮ টি ওয়ার্ডের মসজিদ-মন্দির সহ সাধারণ মানুষের মাঝে বিনামূল্যে মাস্ক বিতরণ করার উদ্যোগ নিয়েছে ফেনী পৌরসভা৷ আজ মঙ্গলবার বিকেলে ট্রাংক রোডে শহীদ মিনারের সামনে  বিনামূল্যে মাস্ক বিতরণ কার্যক্রম উদ্বোধন করার কিছু সময়","07-04-2021","","আজ করোনা সংক্রমণ মোকাবেলায় সচেতনতা বৃদ্ধির লক্ষ্যে ফেনী পৌরসভার ১৮ টি ওয়ার্ডের মসজিদ-মন্দির সহ সাধারণ মানুষের মাঝে বিনামূল্যে মাস্ক বিতরণ করার উদ্যোগ নিয়েছে ফেনী পৌরসভা৷ আজ মঙ্গলবার বিকেলে ট্রাংক রোডে শহীদ মিনারের সামনে  বিনামূল্যে মাস্ক বিতরণ কার্যক্রম উদ্বোধন করার কিছু সময়"));
    }

    private void loadDataFromAPI() {

        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = simpleDateFormat.format(calendar.getTime());

//        Toast.makeText(this, "From Date "+fromDate , Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "To Date "+userToDate , Toast.LENGTH_SHORT).show();

        apiService.getWorkScheduleFetchResponse(Common.APP_KEY, Common.ADMIN_EMP_ID, fromDate, userToDate).enqueue(new Callback<WorkScheduleFetchResponse>() {
            @Override
            public void onResponse(Call<WorkScheduleFetchResponse> call, Response<WorkScheduleFetchResponse> response) {
                if (response.code() == 200) {
                    WorkScheduleFetchResponse fetchResponse = response.body();

                    scheduleList = fetchResponse.getData();
                    //initEmployee();
                   // swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(DailyWorkActivity.this, ""+scheduleList.size(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();

                    if (scheduleList.size() == 0) {
                        Toast.makeText(DailyWorkActivity.this, "No more schedule for today.", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 203) {
                    Toast.makeText(DailyWorkActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                   // swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<WorkScheduleFetchResponse> call, Throwable t) {
               // swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    private void initSwipeLayout() {
//        //view
//        swipeRefreshLayout = findViewById(R.id.scheduleSwipeLayout);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadDataFromAPI();
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });
//
//
//        //Default, load for first time
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                loadDataFromAPI();
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
//
//    }

    private void initEmployee() {
        scheduleRV = findViewById(R.id.scheduleRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scheduleFetchAdapter = new WorkScheduleFetchAdapter(this, scheduleList);
        scheduleRV.setLayoutManager(layoutManager);
        scheduleRV.setAdapter(scheduleFetchAdapter);
       // swipeRefreshLayout.setRefreshing(false);
        scheduleFetchAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }


    private void initFilData() {

        //date picker part
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        int indexMonth = month + 1;
        fromDay = 1;
        toDay = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        int tod = toDay+2;

        //toDate
        userToDate = year+1 + "/" + indexMonth + "/" + tod;

        Log.d("ok",userToDate);
       // Toast.makeText(this, "To Date: "+userToDate, Toast.LENGTH_SHORT).show();

    }
}