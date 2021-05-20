package bd.gov.fenipaurashava.activity_user;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.EmployeeAdapter;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {

    private RecyclerView employeeRV;
    private EmployeeAdapter employeeAdapter;
    private List<Datum> employeeList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        initEmployee();
       // initSwipeLayout();
        //loadDataFromAPI();
        loadData();

    }

    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getEmployeeResponse("A1b1C2d32564kjhkjadu").enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.code()==200) {
                    EmployeeResponse employeeResponse = response.body();

                    employeeList = employeeResponse.getData();
                    initEmployee();
                    swipeRefreshLayout.setRefreshing(false);
                    //Toast.makeText(EmployeeActivity.this, ""+employeeList.size(), Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == 203) {
                    Toast.makeText(EmployeeActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

            }
        });
    }


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
        employeeAdapter = new EmployeeAdapter(this, employeeList);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
        //swipeRefreshLayout.setRefreshing(false);
        employeeAdapter.notifyDataSetChanged();

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}