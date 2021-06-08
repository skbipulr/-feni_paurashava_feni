package bd.gov.fenipaurashava.activity_user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminDashboardActivity;

public class UserDashboardActivity extends AppCompatActivity {

    LinearLayout aboutCV, appointmentCV, complainCV, dailyWorkCV, gonosunaniCV, setInfoCV, stuffCV, unoMessageCV, adminCV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    private final int REQUEST_CALL = 1;

    private FloatingActionButton ambulanceServiceFT,bloodBankFT;



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
                boolean login = sharedpreferences.getBoolean("login", false);
                boolean signIn = sharedpreferences.getBoolean("signIn", false);

                if (login) {
                    startActivity(new Intent(UserDashboardActivity.this, AdminDashboardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(UserDashboardActivity.this, AdminLoginActivity.class));
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

//                if (Common.USER_ID==null){
//                    startActivity(new Intent(HomeActivity.this, AdminLoginActivity.class));
//
//                }else {
//                    startActivity(new Intent(HomeActivity.this, AdminActivity.class));
//
//                }

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                boolean login = sharedpreferences.getBoolean("login", false);
                boolean signIn = sharedpreferences.getBoolean("signIn", false);

                if (login) {
                    startActivity(new Intent(UserDashboardActivity.this, AdminDashboardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(UserDashboardActivity.this, AdminLoginActivity.class));
                }

            }
        });


        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AboutJelaActivity.class));
            }
        });

        appointmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AppointmentOfMayorActivity.class));
            }
        });

        complainCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, ComplainActivity.class));
            }
        });

        dailyWorkCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, WorkScheduleUserActivity.class));
            }
        });


        setInfoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, SetInformationActivity.class));
            }
        });

        stuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, EmployeeActivity.class));
            }
        });

        unoMessageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, MayorMessageActivity.class));
            }
        });
    }



    private void callButton(String mobileNumber) {
        if (mobileNumber.length() > 0) {
            if (ContextCompat.checkSelfPermission(UserDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) UserDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + mobileNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }

    public void one_zeri_nine_zero(View view) {
        //callButton("1090");
        callDialogOpen();
    }

    public void one_six_zero(View view) {
        callButton("106");

    }

    public void one_zero_nine(View view) {
        callButton("109");
    }

    public void nine_nine_nine(View view) {
        callButton("999");
    }

    public void three_three_three(View view) {
        callButton("333");
    }

    public void service(View view) {
        String url = "http://fenipaurashava.gov.bd/";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    public void one_zero_nine_eight(View view) {
        callButton("1098");
    }


    private void callDialogOpen() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        CardView saveCV, cancelCV;
        View view = LayoutInflater.from(UserDashboardActivity.this).inflate(R.layout.ambulance_and_blood_bank_call_dialog, null);
        builder.setView(view);

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        bloodBankFT = view.findViewById(R.id.bloodBankFT);
        ambulanceServiceFT = view.findViewById(R.id.ambulanceServiceFT);
        cancelCV = view.findViewById(R.id.cancelCV);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        bloodBankFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton("01842307080");
            }
        });

        ambulanceServiceFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton("01842307080");
            }
        });


        cancelCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

}