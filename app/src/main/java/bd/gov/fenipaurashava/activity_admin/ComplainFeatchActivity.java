package bd.gov.fenipaurashava.activity_admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapterForAPI.ComplainSubjectFetchAllAdapter;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForComplainSubjectFetchGET.ComplainSubjectFetchResponse;
import bd.gov.fenipaurashava.modelForComplainSubjectFetchGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainFeatchActivity extends AppCompatActivity {


    private RecyclerView appointmentFetchAllRV;
    private ComplainSubjectFetchAllAdapter complainSubjectFetchAllAdapter;
    private List<Datum> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiService;

    ComplainSubjectFetchAllAdapter.RecyclerViewComplainSubjectClickListener listener;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_featch);

        initSwipeLayout();
        loadDataFromAPI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplainFeatchActivity.this, ComplainSubjectEditorActivity.class));
            }
        });
        listener = (view, position) -> {
            Intent intent = new Intent(ComplainFeatchActivity.this, EditorActivity.class);
            intent.putExtra("id", list.get(position).getId());
            intent.putExtra("name", list.get(position).getName());
            startActivity(intent);
        };

    }

    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getComplainSubjectFetchResponse("A1b1C2d32564kjhkjadu").enqueue(new Callback<ComplainSubjectFetchResponse>() {
            @Override
            public void onResponse(Call<ComplainSubjectFetchResponse> call, Response<ComplainSubjectFetchResponse> response) {
                if (response.code()==200) {
                    ComplainSubjectFetchResponse fetchAllResponse = response.body();

                    assert fetchAllResponse != null;
                    list = fetchAllResponse.getData();
                    initFiled();
                    swipeRefreshLayout.setRefreshing(false);

                }
                else if (response.code() == 203) {
                    Toast.makeText(ComplainFeatchActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ComplainSubjectFetchResponse> call, Throwable t) {
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
        complainSubjectFetchAllAdapter = new ComplainSubjectFetchAllAdapter(this, list,listener);
        appointmentFetchAllRV.setLayoutManager(layoutManager);
        appointmentFetchAllRV.setAdapter(complainSubjectFetchAllAdapter);
        swipeRefreshLayout.setRefreshing(false);
        complainSubjectFetchAllAdapter.notifyDataSetChanged();
    }

    public void backBtn(View view) {
        onBackPressed();
    }
}