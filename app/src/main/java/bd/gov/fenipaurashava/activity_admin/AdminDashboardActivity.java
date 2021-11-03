package bd.gov.fenipaurashava.activity_admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import com.developer.kalert.KAlertDialog;
import com.squareup.picasso.Picasso;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.UserDashboardActivity;
import bd.gov.fenipaurashava.activity_user.SetInformationListFetchActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.common.NetworkCheck;
import bd.gov.fenipaurashava.departmentAdmin.activites.DepartmentAdminActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDashboardActivity extends AppCompatActivity {


    CardView setInfoCV, adminStuffCV, adminAppointmentSubjectCV,
    adminComplainsCV, adminAppointmentFetchCV, adminComplainSubjectCV, adminStuffListCV,
    workScheduleCV, serviceCountCV;
    LinearLayout logoutLL;

    private String userName, userDesignation, picture;
    private TextView userNameTV, userDesignationTV;
    private CircleImageView profileImageIV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
        getDataFromSharePreference();
        clickEvent();

        Log.i("url", Common.IMAGE_BASE_URL + picture);

    }

    private void clickEvent() {
        logoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new KAlertDialog(AdminDashboardActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Are you sure want to logout?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                //delete operation
                                sharedpreferences = AdminDashboardActivity.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();
                                finish();
                                Intent intent = new Intent(AdminDashboardActivity.this, UserDashboardActivity.class);
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
            }
        });
    }

    private void getDataFromSharePreference() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(Common.USER_NAME, "");
        userDesignation = sharedpreferences.getString(Common.USER_DESIGNATION, "");
        picture = sharedpreferences.getString(Common.USER_PICTURE, "");
        userNameTV.setText(userName);
        userDesignationTV.setText(userDesignation);
        Picasso.get().load(Common.IMAGE_BASE_URL + picture).placeholder(R.drawable.default_icon)
                .into(profileImageIV);

        Log.d("url", Common.IMAGE_BASE_URL + picture);

    }


    public void service(View view) {
        String url = "http://fenipaurashava.gov.bd/";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


    private void init() {
        setInfoCV = findViewById(R.id.setInfoCV);
        adminStuffCV = findViewById(R.id.adminStuffCV);
        adminAppointmentSubjectCV = findViewById(R.id.adminAppointmentSubjectCV);
        adminComplainsCV = findViewById(R.id.adminComplainsCV);
        adminAppointmentFetchCV = findViewById(R.id.adminAppointmentFetchCV);
        adminComplainSubjectCV = findViewById(R.id.adminComplainSubjectCV);
        adminStuffListCV = findViewById(R.id.adminDepartmentCV);
        workScheduleCV = findViewById(R.id.workScheduleCV);
        logoutLL = findViewById(R.id.logoutLL);
        serviceCountCV = findViewById(R.id.serviceCountCV);

        userNameTV = findViewById(R.id.userNameTV);
        userDesignationTV = findViewById(R.id.userDesignationTV);
        profileImageIV = findViewById(R.id.profileImageIV);

        serviceCountCV.setOnClickListener(view -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, CitizenServiceCountActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

        adminStuffListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                    startActivity(new Intent(AdminDashboardActivity.this, DepartmentAdminActivity.class));
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        workScheduleCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, WorkScheduleShowActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

        adminComplainSubjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                    startActivity(new Intent(AdminDashboardActivity.this, ComplainSubjectListFetchActivity.class));
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adminAppointmentFetchCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, AppointmentListFetchActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

        setInfoCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, SetInformationListFetchActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

        adminComplainsCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, ComplainListFetchActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


        adminAppointmentSubjectCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, AppointmentSubjectListFetchActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

        adminStuffCV.setOnClickListener(v -> {
            if (NetworkCheck.isConnect(AdminDashboardActivity.this)) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminCurdEmployeeActivity.class));
            } else {
                Toast.makeText(AdminDashboardActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void backBtn(View view) {
        startActivity(new Intent(this, UserDashboardActivity.class));
    }
}