package bd.gov.fenipaurashava.activity_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.WorkScheduleActivity;
import bd.gov.fenipaurashava.adapterForAPI.WorkScheduleFetchAdminAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.WorkScheduleFetchResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkScheduleShowActivity extends AppCompatActivity {


    private RecyclerView scheduleRV;
    private WorkScheduleFetchAdminAdapter scheduleFetchAdapter;
    private List<Datum> scheduleList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;
    WorkScheduleFetchAdminAdapter.RecyclerViewCurdClickListener listener;

    private int count = 0;

    private TextView fromDateTv, toDateTv;
    private ImageView fromDateBtn, toDateBtn;
    private LinearLayout fromDataLL, toDataLL;

    String userFromDate, userToDate, nFromDate, nToDate;

    private Calendar calendar;
    private int year, month, fromDay, toDay, hour, minute;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_schedule_show);

        init();
        initSwipeLayout();
        try {
            loadDataFromAPI();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            initFilData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkScheduleShowActivity.this, WorkScheduleActivity.class));
            }
        });


        listener = new WorkScheduleFetchAdminAdapter.RecyclerViewCurdClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Datum data = scheduleList.get(position);
                Intent intent = new Intent(WorkScheduleShowActivity.this, WorkScheduleEditDeleteActivity.class);
                intent.putExtra("schedule", data);
                startActivity(intent);
            }
        };

    }

    private void init() {

        fromDateBtn = findViewById(R.id.fromDateCalenderBtn);
        toDateBtn = findViewById(R.id.toDateCalenderBtn);

        fromDateTv = findViewById(R.id.viewFromDateTV);
        toDateTv = findViewById(R.id.viewToDateTV);
        fromDataLL = findViewById(R.id.fromDataLL);
        toDataLL = findViewById(R.id.toDataLL);

    }


    private void loadDataFromAPI() throws ParseException {

        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = simpleDateFormat.format(calendar.getTime());

        apiService.getWorkScheduleFetchResponse(Common.APP_KEY, Common.ADMIN_EMP_ID, userFromDate, userToDate).enqueue(new Callback<WorkScheduleFetchResponse>() {
            @Override
            public void onResponse(Call<WorkScheduleFetchResponse> call, Response<WorkScheduleFetchResponse> response) {
                if (response.code() == 200) {
                    WorkScheduleFetchResponse fetchResponse = response.body();

                    scheduleList = fetchResponse.getData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);

                } else if (response.code() == 203) {
                    Toast.makeText(WorkScheduleShowActivity.this, "server problem", Toast.LENGTH_SHORT).show();
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
                try {
                    loadDataFromAPI();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    loadDataFromAPI();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(true);
            }

        });

    }

    private void initEmployee() {
        scheduleRV = findViewById(R.id.scheduleRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scheduleFetchAdapter = new WorkScheduleFetchAdminAdapter(this, scheduleList, listener);
        scheduleRV.setLayoutManager(layoutManager);
        scheduleRV.setAdapter(scheduleFetchAdapter);
        swipeRefreshLayout.setRefreshing(false);
        scheduleFetchAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }

    private void initFilData() throws ParseException {

        //date picker part
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        int indexMonth = month - 10;
        fromDay = 1;
        toDay = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);


        //fromDate
        userFromDate = year + "/" + (month + 1) + "/" + toDay;
        fromDateTv.setText(userFromDate);

        final DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int fromDay) {
                calendar.set(year, month, fromDay);
                userFromDate = dateFormat.format(calendar.getTime());
                fromDateTv.setText(userFromDate);

                if (count > 0) {                     //set listener to pull modyifiying data when second time data set
                    try {
                        loadDataFromAPI();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        fromDataLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set Date as begining of the month
                DatePickerDialog datePickerDialog = new DatePickerDialog(WorkScheduleShowActivity.this, fromDateListener, year, month, fromDay);
                datePickerDialog.show();
            }
        });

        //toDate
        userToDate = year + 1 + "/" + (month + 1) + "/" + toDay;
        toDateTv.setText(userToDate);

        final DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int toDay) {
                calendar.set(year, month, toDay + 1);
                userToDate = dateFormat.format(calendar.getTime());
                toDateTv.setText(userToDate);

                if (count > 0) {                     //set listener to pull modyifiying data when second time data set
                    try {
                        loadDataFromAPI();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        toDataLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set Date as begining of the month
                DatePickerDialog datePickerDialog = new DatePickerDialog(WorkScheduleShowActivity.this, toDateListener, year, month, toDay);
                datePickerDialog.show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadDataFromAPI();
            initEmployee();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            loadDataFromAPI();
            initEmployee();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            loadDataFromAPI();
            initEmployee();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}