package bd.gov.fenipaurashava.activity_admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.developer.kalert.KAlertDialog;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.HomeActivity;
import bd.gov.fenipaurashava.activity_user.PublicHeadingActivity;
import bd.gov.fenipaurashava.activity_user.SetInformationFetchAllActivity;
import bd.gov.fenipaurashava.activity_user.WorkScheduleActivity;

public class AdminActivity extends AppCompatActivity {
    CardView aboutCV, appointmentCV, complainCV, dailyWorkCV, adminGonosunaniCV,
            setInfoCV, adminStuffCV, unoMessageCV, adminCV,adminAppointmentSubjectCV,
            adminComplainsCV,adminAppointmentFetchCV,adminComplainSubjectCV,adminStuffListCV,
            workScheduleCV;

    private ImageView saveIV, updateIV, deleteIV,workScheduleSaveIV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout_menu:

                new KAlertDialog(this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Are you sure want to logout?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                //delete operation
                                sharedpreferences = AdminActivity.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();
                                finish();
                                Intent intent = new Intent(AdminActivity.this, HomeActivity.class);
                                startActivity(intent);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("No")
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        adminGonosunaniCV = findViewById(R.id.adminGonoSunaniCV);
        setInfoCV = findViewById(R.id.setInfoCV);
        adminStuffCV = findViewById(R.id.adminStuffCV);
        adminAppointmentSubjectCV = findViewById(R.id.adminAppointmentSubjectCV);
        saveIV = findViewById(R.id.saveIV);
        updateIV = findViewById(R.id.updateIV);
        deleteIV = findViewById(R.id.deleteIV);
        workScheduleSaveIV = findViewById(R.id.workScheduleSaveIV);
        adminComplainsCV = findViewById(R.id.adminComplainsCV);
        adminAppointmentFetchCV = findViewById(R.id.adminAppointmentFetchCV);
        adminComplainSubjectCV = findViewById(R.id.adminComplainSubjectCV);
        adminStuffListCV = findViewById(R.id.adminStuffListCV);
        workScheduleCV = findViewById(R.id.workScheduleCV);

        adminStuffListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminEmployeeActivityList.class));
            }
        });

        workScheduleCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, WorkScheduleShowActivity.class));
            }
        });


        adminComplainSubjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ComplainFeatchActivity.class));
            }
        });

        adminAppointmentFetchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AppointmentFeatchActivity.class));
            }
        });


        setInfoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, SetInformationFetchAllActivity.class));
            }
        });

        workScheduleSaveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, WorkScheduleActivity.class));
            }
        });

        adminComplainsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ComplainFetchAllActivity.class));
            }
        });


        adminAppointmentSubjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AppoimentFatchAllActivity.class));
            }
        });

        updateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AppoimentFatchAllActivity.class));
            }
        });

        adminGonosunaniCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, PublicHeadingActivity.class));
            }
        });

        adminStuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminCurdEmployeeActivity.class));
            }
        });

        saveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, EditorActivity.class));
            }
        });

    }

    public void backBtn(View view) {
       startActivity(new Intent(this, HomeActivity.class));
    }
}