package bd.gov.fenipaurashava.activity_admin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.EmployeeAdapterList;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;

import java.util.ArrayList;
import java.util.List;

public class AdminEmployeeActivityList extends AppCompatActivity {

    private RecyclerView employeeRV;
    private EmployeeAdapterList employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        initEmployee();
        loadData();
//        initSwipeLayout();
//        loadDataFromAPI();

    }
//
//    private void loadDataFromAPI() {
//        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
//
//        apiService.getEmployeeResponse("A1b1C2d32564kjhkjadu").enqueue(new Callback<EmployeeResponse>() {
//            @Override
//            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
//                if (response.code()==200) {
//                    EmployeeResponse employeeResponse = response.body();
//
//                    employeeList = employeeResponse.getData();
//                    initEmployee();
//                    swipeRefreshLayout.setRefreshing(false);
//                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();
//                }
//                else if (response.code() == 203) {
//                    Toast.makeText(AdminEmployeeActivityList.this, "server problem", Toast.LENGTH_SHORT).show();
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
//               loadDataFromAPI();
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
//
//        });
//
//    }

    private void  loadData(){
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
        employeeList.add(new Datum("Demo name","Demo Designation","০১৭৩৩৩৫৪৯০০","demo@mopa.gov.bd",R.drawable.default_icon));
    }
//    private void  loadData(){
//
//        employeeList.add(new Datum("মোহাম্মদ কামরুল হাসান","জেলা প্রশাসক ও জেলা ম্যাজিস্ট্রেট","০১৭৩৩৩৫৪৯০০","dccomilla@mopa.gov.bd",R.drawable.uno_2));
//        employeeList.add(new Datum("মোহাম্মদ শওকত ওসমান","উপ-পরিচালক, স্থানীয় সরকার, কুমিল্লা",
//                "০১৭৩৩৩৫৪৯০১","ddlgcomilla1@gmail.com",
//                R.drawable.soukot));
//        employeeList.add(new Datum("মোঃ মাঈন উদ্দিন","অতিরিক্ত জেলা প্রশাসক (রাজস্ব)",
//                "০১৭৩৩৩৫৪৯০৫","adcrcomilla1@gmail.com",R.drawable.moim));
//        employeeList.add(new Datum("মোহাম্মদ শাহাদাত হোসেন",
//                "অতিরিক্ত জেলা প্রশাসক (শিক্ষা ও আইসিটি)","০১৭৩৩-৩৫৪৯০৪",
//                "adceictcomilla@gmail.com",R.drawable.sahadad));
//        employeeList.add(new Datum("মোহাম্মদ শাহাদাত হোসেন",
//                "অতিরিক্ত জেলাপ্রশাসক (সার্বিক)","০১৭৩৩৩৫৪৯০২",
//                "adcgcomilla@gmail.com",R.drawable.sahadad));
//        employeeList.add(new Datum("মোঃ আরিফুল ইসলাম সরদার","অতিরিক্ত জেলা ম্যাজিস্ট্রেট",
//                "01733334312","tadcictchittagong@gmail.com",R.drawable.ariful_islam));
//        employeeList.add(new Datum("অরুপ রতন সিংহ","সহকারী কমিশনার (শরণার্থী প্রত্যাবাসন কেন্দ্র, কক্সবাজারে সংযুক্ত)",
//                "০১৭২২২২৬৩৩৩","aruoratan.sust@gmail.com",R.drawable.roton));
//        employeeList.add(new Datum("বাবলু সূত্রধর","সহকারী কমিশনার (শরণার্থী প্রত্যাবাসন কেন্দ্র, কক্সবাজারে সংযুক্ত)","০১৭১০৫৮৬৫৪১","raihan17567@gmail.com",R.drawable.bolo));
//
//    }

    private void initEmployee() {
        employeeRV = findViewById(R.id.employeeRV);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        employeeAdapter = new EmployeeAdapterList(this, employeeList);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
        employeeAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}