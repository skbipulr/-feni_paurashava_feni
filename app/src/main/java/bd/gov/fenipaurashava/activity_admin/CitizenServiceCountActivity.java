package bd.gov.fenipaurashava.activity_admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.databinding.ActivityCitizenServiceCountBinding;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelCitizenServiceCount.CitizenServiceGet;
import bd.gov.fenipaurashava.modelCitizenServiceCount.Data;
import bd.gov.fenipaurashava.modelCitizenServiceCount.Market;
import bd.gov.fenipaurashava.modelCitizenServiceCount.OthersSonod;
import bd.gov.fenipaurashava.modelCitizenServiceCount.Trade;
import bd.gov.fenipaurashava.webApi.RetrofitClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitizenServiceCountActivity extends AppCompatActivity {

    ActivityCitizenServiceCountBinding binding;
    private List<Data> serviceList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    private Market market;
    private OthersSonod othersSonod;
    private Trade trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_service_count);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_citizen_service_count);


        initSwipeLayout();
        loadDataFromAPI();


    }

    private void loadDataFromAPI() {
        apiService = RetrofitClient2.getRetrofit().create(ApiInterface.class);

        apiService.getServiceCount().enqueue(new Callback<CitizenServiceGet>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CitizenServiceGet> call, Response<CitizenServiceGet> response) {

                CitizenServiceGet res = response.body();
                if (response.code() == 200) {
                    market = res.getData().getMarket();
                    othersSonod = res.getData().getOthersSonod();
                    trade = res.getData().getTrade();

                    // ===========trad license========start====
                    binding.halTodayManyTV.setText(trade.getCurrentAmount().getToday() + " Tk.");
                    binding.halTotalManyTV.setText(trade.getCurrentAmount().getTotal() + " Tk.");
                    binding.bokeyaToDayManyTV.setText(trade.getDueAmount().getToday() + " Tk.");
                    binding.bokeyaTotalManyTV.setText(trade.getDueAmount().getTotal() + " Tk.");
                    binding.todayTradCertificateTV.setText(trade.getCertificate().getToday() + " টি");
                    binding.totalTradCertificateTV.setText(trade.getCertificate().getTotal() + " টি");

                    double todayTotalTrad1 = Double.parseDouble(trade.getCurrentAmount().getToday());
                    double todayTotalTrad2 = Double.parseDouble(trade.getDueAmount().getToday());
                    binding.totalTodayManyTV.setText(String.valueOf(todayTotalTrad1 + todayTotalTrad2) + " Tk.");
                    double totalTotalTrad1 = Double.parseDouble(trade.getCurrentAmount().getTotal());
                    double totalTotalTrad2 = Double.parseDouble(trade.getDueAmount().getTotal());
                    binding.totalTotalManyTV.setText(String.valueOf(totalTotalTrad1 + totalTotalTrad2) + " Tk.");
                    // ===========trad license========end====

                    // ===========market management========start====
                    binding.bazarHalTodayManyTV.setText(market.getCurrentAmount().getToday() + " Tk.");
                    binding.bazarHalTotalManyTV.setText(market.getCurrentAmount().getTotal() + " Tk.");
                    binding.bazarBokeyaToDayManyTV.setText(market.getDueAmount().getToday() + " Tk.");
                    binding.bazarBokeyaTotalManyTV.setText(market.getDueAmount().getTotal() + " Tk.");


                    int bazarTodayTotalTrad1 = market.getCurrentAmount().getToday();
                    int bazarTodayTotalTrad2 = market.getDueAmount().getToday();
                    binding.bazarTotalTodayManyTV.setText(String.valueOf(bazarTodayTotalTrad1 + bazarTodayTotalTrad2) + " Tk.");
                    double bazarTotalTotalTrad1 = Double.parseDouble(market.getCurrentAmount().getTotal());
                    double bazarTotalTotalTrad2 = market.getDueAmount().getTotal();
                    binding.bazarTotalTotalManyTV.setText(String.valueOf(bazarTotalTotalTrad1 + bazarTotalTotalTrad2) + " Tk.");
                    // ===========market management========end====


                    //===other certificate========start===
                    binding.otherHalTodayManyTV.setText(othersSonod.getCurrentAmount().getToday() + " Tk.");
                    binding.OtherHalTotalManyTV.setText(othersSonod.getCurrentAmount().getTotal() + " Tk.");

                    binding.todayOtherTV.setText(othersSonod.getCertificate().getToday() + " টি");
                    binding.totalOtherTV.setText(othersSonod.getCertificate().getTotal() + " টি");
                    //===other certificate========end===

                    swipeRefreshLayout.setRefreshing(false);
                } else if (response.code() == 203) {
                    Toast.makeText(CitizenServiceCountActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(CitizenServiceCountActivity.this, "server problem" + response.message(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CitizenServiceGet> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("error",t.getMessage());
                //Toast.makeText(CitizenServiceCountActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void backBtn(View view) {
        onBackPressed();
    }
}