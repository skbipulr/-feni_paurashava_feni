package bd.gov.fenipaurashava.activity_admin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.developer.kalert.KAlertDialog;
import com.squareup.picasso.Picasso;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.HomeActivity;
import bd.gov.fenipaurashava.activity_user.SetInformationFetchAllActivity;
import bd.gov.fenipaurashava.common.Common;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    LinearLayout aboutCV, appointmentCV, complainCV, dailyWorkCV, adminGonosunaniCV,
            setInfoCV, adminStuffCV, unoMessageCV, adminCV, adminAppointmentSubjectCV,
            adminComplainsCV, adminAppointmentFetchCV, adminComplainSubjectCV, adminStuffListCV,
            workScheduleCV, logoutLL;

    private String userName, userDesgination,picture;

    private CardView one_zero_nine_eight;
    private ImageView saveIV, updateIV, deleteIV, workScheduleSaveIV;

    private TextView userNameTV, userDesignationTV;
    private CircleImageView profileImageIV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(Common.USER_NAME,"");
        userDesgination = sharedpreferences.getString(Common.USER_DESIGNATION,"");
        picture = sharedpreferences.getString(Common.USER_PICTURE,"");

        userNameTV.setText(userName);
        userDesignationTV.setText(userDesgination);

        Picasso.get().load("http://fenimayor.digiins.gov.bd/district_app/public/employee/" +picture).placeholder(R.drawable.default_icon)
                .into(profileImageIV);

        one_zero_nine_eight = findViewById(R.id.one_zero_nine_eight);

        one_zero_nine_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton("1098");
            }
        });

        logoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new KAlertDialog(AdminActivity.this, KAlertDialog.WARNING_TYPE)
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
            }
        });
    }

    private void callButton(String mobileNumber) {
        if (mobileNumber.length() > 0) {
            if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) AdminActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + mobileNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }

    public void one_zeri_nine_zero(View view) {
        callButton("1090");
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


    private void init() {
        //adminGonosunaniCV = findViewById(R.id.adminGonoSunaniCV);
        setInfoCV = findViewById(R.id.setInfoCV);
        adminStuffCV = findViewById(R.id.adminStuffCV);
        adminAppointmentSubjectCV = findViewById(R.id.adminAppointmentSubjectCV);
        saveIV = findViewById(R.id.saveIV);
       // updateIV = findViewById(R.id.updateIV);
        deleteIV = findViewById(R.id.deleteIV);
        workScheduleSaveIV = findViewById(R.id.workScheduleSaveIV);
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

//        workScheduleSaveIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminActivity.this, WorkScheduleActivity.class));
//            }
//        });

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

//        updateIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminActivity.this, AppoimentFatchAllActivity.class));
//            }
//        });


        adminStuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminCurdEmployeeActivity.class));
            }
        });

//        saveIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminActivity.this, EditorActivity.class));
//            }
//        });

    }

    public void backBtn(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}