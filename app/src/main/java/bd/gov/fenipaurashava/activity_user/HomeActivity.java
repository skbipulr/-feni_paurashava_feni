package bd.gov.fenipaurashava.activity_user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminActivity;
import bd.gov.fenipaurashava.common.Common;

public class HomeActivity extends AppCompatActivity {

    CardView aboutCV, appointmentCV, complainCV, dailyWorkCV, gonosunaniCV, setInfoCV, stuffCV, unoMessageCV, adminCV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.admin_menu:
//                if (Common.USER_ID==null){
//                    startActivity(new Intent(HomeActivity.this,AdminLoginActivity.class));
//                }else {
//                    startActivity(new Intent(HomeActivity.this,AdminActivity.class));
//                }
//                startActivity(new Intent(HomeActivity.this, AdminActivity.class));
//                return true;

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                boolean login = sharedpreferences.getBoolean("login",false);
                boolean signIn = sharedpreferences.getBoolean("signIn",false);

                if (login) {
                    startActivity(new Intent(HomeActivity.this, AdminActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(HomeActivity.this,AdminLoginActivity.class));
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        aboutCV = findViewById(R.id.aboutCV);
        appointmentCV = findViewById(R.id.appointmentCV);
        complainCV = findViewById(R.id.complainCV);
        dailyWorkCV = findViewById(R.id.dailtWorkCV);
        gonosunaniCV = findViewById(R.id.gonoSunaniCV);
        setInfoCV = findViewById(R.id.setInfoCV);
        stuffCV = findViewById(R.id.stuffCV);
        unoMessageCV = findViewById(R.id.unoMessageCV);
        adminCV = findViewById(R.id.adminCV);


        adminCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.USER_ID==null){
                    startActivity(new Intent(HomeActivity.this, AdminLoginActivity.class));

                }else {
                    startActivity(new Intent(HomeActivity.this, AdminActivity.class));

                }

            }
        });




        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AboutJelaActivity.class));
            }
        });

        appointmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AppointmentOfDCActivity.class));
            }
        });

        complainCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ComplainActivity.class));
            }
        });

        dailyWorkCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DailyWorkActivity.class));
            }
        });

        gonosunaniCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(HomeActivity.this, PublicHeadingSaveActivity.class));
                startActivity(new Intent(HomeActivity.this, PublicHeadingSaveActivity.class));
            }
        });

        setInfoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SetInformationActivity.class));
            }
        });

        stuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EmployeeActivity.class));
            }
        });

        unoMessageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, bd.gov.fenipaurashava.activity_user.UnoMessageActivity.class));
            }
        });


    }


}