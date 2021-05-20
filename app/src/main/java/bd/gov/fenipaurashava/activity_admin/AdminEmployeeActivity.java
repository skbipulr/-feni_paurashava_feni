package bd.gov.fenipaurashava.activity_admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.AdminEmployeeAdapter;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;

import java.util.ArrayList;
import java.util.List;

public class AdminEmployeeActivity extends AppCompatActivity {

    private RecyclerView employeeRV;
    private AdminEmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    bd.gov.fenipaurashava.modelPublicHeadingGET.Datum dataClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee);

         initEmployee();

         loadData();
//        initSwipeLayout();
      //  loadDataFromAPI();

//        Intent i = getIntent();
//        dataClass = (bd.gov.apnarjelaprosasok.modelPublicHeadingGET.Datum) i.getSerializableExtra("allInfo");

    }

//
//    private void loadDataFromAPI() {
//        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
//
//        apiService.getEmployeeResponse(Common.APP_KEY).enqueue(new Callback<EmployeeResponse>() {
//            @Override
//            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
//                if (response.code() == 200) {
//                    EmployeeResponse employeeResponse = response.body();
//                    employeeList = employeeResponse.getData();
//                    initEmployee();
//                    swipeRefreshLayout.setRefreshing(false);
//                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();
//                } else if (response.code() == 203) {
//                    Toast.makeText(AdminEmployeeActivity.this, "server problem", Toast.LENGTH_SHORT).show();
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void initSwipeLayout() {
//        //view
//        swipeRefreshLayout = findViewById(R.id.employeeSwipeLayout);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadDataFromAPI();
//                swipeRefreshLayout.setRefreshing(false);
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
//    }


    private void  loadData(){
        employeeList.add(new Datum("নজরুল ইসলাম স্বপন মিয়াজী","মেয়র","01718899334","nojurul@gmail.com",R.drawable.processed));
        employeeList.add(new Datum("সৈয়দ আবুজার গিফারী","সচিব","01716-152301","abujargifry@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","017*******","demo@gmail.com",R.drawable.default_icon));

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

//    @Override
//    public void onEmployeeItemClick(Datum employee) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setMessage("Are you referring this public heading?");
//        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                createRefer(employee);
//                dialog.dismiss();
//
//            }
//        });
//        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//    private void createRefer(Datum employee) {
//
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        String user_id = sharedpreferences.getString(USER_ID,"");
//        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");
//
//        int use_id = Integer.parseInt(user_id);
//        int em_id = Integer.parseInt(employee_id);
//
//
//            int employeeId =  employee.getId();
//            int itemId = dataClass.getId();
//
//            final ProgressDialog mDialog = new ProgressDialog(AdminEmployeeActivity.this);
//            mDialog.setMessage("Please waiting...");
//            mDialog.show();
//            Call<PublicHearingReferringResponse> call  = apiService.setPublicHearingReferringResponse(Common.APP_KEY,use_id,itemId,employeeId);
//
//            call.enqueue(new Callback<PublicHearingReferringResponse>() {
//                @Override
//                public void onResponse(Call<PublicHearingReferringResponse> call, Response<PublicHearingReferringResponse> response) {
//
//                    PublicHearingReferringResponse referringResponse = response.body();
//
//                    if (response.code() ==200){
//                        referringResponse.getMessage();
//                        Toast.makeText(AdminEmployeeActivity.this, ""+referringResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    }
//                    else if (response.code() == 203) {
//                        Toast.makeText(AdminEmployeeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    } else if (response.code() == 401) {
//                        Toast.makeText(AdminEmployeeActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    } else if (response.code() == 422) {
//                        Toast.makeText(AdminEmployeeActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<PublicHearingReferringResponse> call, Throwable t) {
//                    Toast.makeText(AdminEmployeeActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    mDialog.dismiss();
//                }
//            });
//
//    }
}