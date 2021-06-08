package bd.gov.fenipaurashava.activity_admin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.developer.kalert.KAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.UserDashboardActivity;
import bd.gov.fenipaurashava.activity_user.SetInformationListFetchActivity;
import bd.gov.fenipaurashava.common.Common;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDashboardActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    LinearLayout
            setInfoCV, adminStuffCV, adminAppointmentSubjectCV,
            adminComplainsCV, adminAppointmentFetchCV, adminComplainSubjectCV, adminStuffListCV,
            workScheduleCV, logoutLL;

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
        Picasso.get().load("http://fenimayor.digiins.gov.bd/district_app/public/employee/" + picture).placeholder(R.drawable.default_icon)
                .into(profileImageIV);
    }

    private void callButton(String mobileNumber) {
        if (mobileNumber.length() > 0) {
            if (ContextCompat.checkSelfPermission(AdminDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) AdminDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + mobileNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }

    //=============================call start==================================
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

    public void one_zero_nine_eight(View view) {
        callButton("1098");
    }

    public void one_zeri_nine_zero(View view) {
        callDialogOpen();
    }
    //=============================call start==================================

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
        adminStuffListCV = findViewById(R.id.adminStuffListCV);
        workScheduleCV = findViewById(R.id.workScheduleCV);
        logoutLL = findViewById(R.id.logoutLL);

        userNameTV = findViewById(R.id.userNameTV);
        userDesignationTV = findViewById(R.id.userDesignationTV);
        profileImageIV = findViewById(R.id.profileImageIV);

        adminStuffListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminEmployeeActivityList.class));
            }
        });

        workScheduleCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, WorkScheduleShowActivity.class));
            }
        });

        adminComplainSubjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ComplainSubjectListFetchActivity.class));
            }
        });

        adminAppointmentFetchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AppointmentListFetchActivity.class));
            }
        });

        setInfoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, SetInformationListFetchActivity.class));
            }
        });

        adminComplainsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ComplainListFetchActivity.class));
            }
        });


        adminAppointmentSubjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AppointmentSubjectListFetchActivity.class));
            }
        });

        adminStuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, AdminCurdEmployeeActivity.class));
            }
        });

    }

    //================================calling dialog start============================
    private void callDialogOpen() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        CardView cancelCV;
        View view = LayoutInflater.from(AdminDashboardActivity.this).inflate(R.layout.ambulance_and_blood_bank_call_dialog, null);
        builder.setView(view);

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        FloatingActionButton bloodBankFT = view.findViewById(R.id.bloodBankFT);
        FloatingActionButton ambulanceServiceFT = view.findViewById(R.id.ambulanceServiceFT);
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
    //================================calling dialog end============================

    public void backBtn(View view) {
        startActivity(new Intent(this, UserDashboardActivity.class));
    }
}