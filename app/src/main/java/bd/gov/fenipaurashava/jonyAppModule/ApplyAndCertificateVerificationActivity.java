package bd.gov.fenipaurashava.jonyAppModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.databinding.DataBindingUtil;


import java.util.ArrayList;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.databinding.ActivityApplyAndCertificateVerificationBinding;
import es.dmoral.toasty.Toasty;


public class ApplyAndCertificateVerificationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityApplyAndCertificateVerificationBinding binding;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    private String user_id = "35";
    private String sonodNo = "20191914095000005";
    RadioButton radioButton;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_and_certificate_verification);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_and_certificate_verification);

        binding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyAndCertificateVerificationActivity.this, Scanner.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });


        //===========spinner start==============
        binding.spinner.setOnItemSelectedListener(ApplyAndCertificateVerificationActivity.this);
        // Spinner Drop down elements
        List<String> landType = new ArrayList<String>();
        landType.add("নাগরিক সনদ");//1
        landType.add("মৃত্যু সনদ");//2
        landType.add("অবিবাহিত সনদ");//3
        landType.add("পুনঃবিবাহ না হওয়া সনদ");//4
        landType.add("একই নামের প্রত্যয়ন");//5
        landType.add("সনাতন ধর্ম অবলম্বি সনদ");//6
        landType.add("প্রত্যয়ন");//7
        landType.add("নদী ভাঙনের সনদ");//8
        landType.add("চারিত্রিক সনদ");//9
        landType.add("ভূমিহীন সনদ");//10
        landType.add("বার্ষিক আয়ের সনদ");//11
        landType.add("প্রকৃত বাক ও শ্রবণ প্রতিবন্ধি সনদ");//12
        landType.add("অনুমতি সনদ");//13
        landType.add("ভোটার আইডি স্থানান্তর সনদ");//14
        landType.add("অনাপত্তি প্রত্র");//15
        landType.add("রাস্তা খনন");//16
        landType.add("ওয়ারিশ সনদ");//17
        landType.add("পারিবারিক সনদ");//18
        landType.add("ট্রেড লাইসেন্স সনদ");//19
        landType.add("বিবাহিত সনদ");//20
        landType.add("সাইনবোর্ড কর");//21
        landType.add("সার চার্জ");//22
        landType.add("বকেয়া");//23

        //======পৌরসভা========
        landType.add("প্রিমিসেস");//90
        landType.add("পোষা প্রাণীর সনদ");//--91
        landType.add("নতুন হোল্ডিং সনদ");//--92
        landType.add("নতুন হোল্ডিং নামজারী সনদ");//--93
        landType.add("রাস্তা খননের অনুমতি সনদ");//--94
        landType.add("ইমারত নির্মাণ অনুমতি সনদ");//--95
        landType.add("ভূমি ব্যবহার ছাড়পত্রের সনদ");//--96
        landType.add("উৎসেকর");//--97
        landType.add("পূর্বোক্ত হোল্ডিং কর");//--98
        landType.add("হোল্ডিং কর");//--99

        //======সমিতি===========
        landType.add("সমিতি আয়");//--106
        landType.add("সমিতি ব্যয়");//--107


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ApplyAndCertificateVerificationActivity.this, android.R.layout.simple_spinner_item, landType);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        binding.spinner.setAdapter(dataAdapter);
        //===========spinner end==============

    }

    public void checkButton(View v) {
        int radioId = binding.radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = String.valueOf(2131362053);
                String id = String.valueOf(radioButton.getId());

                sonodNo = binding.sonodNoET.getText().toString().trim();
                if (sonodNo.isEmpty()) {
                    Toasty.error(ApplyAndCertificateVerificationActivity.this, "Field is required", Toast.LENGTH_SHORT, true).show();
                } else {
                   // http://fenipaurashava.gov.bd/api/verify/certificate/5654564/1/21565456401000167
                    String url = "http://fenipaurashava.gov.bd/api/verify/certificate/5654564/" + pos + "/" + sonodNo;
                    openTab(url);
                }


            }
        });

    }

    //open web tab
    public void openTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(ApplyAndCertificateVerificationActivity.this, Uri.parse(url));
    }

    public void login(View view) {
        sonodNo = binding.sonodNoET.getText().toString().trim();
        if (sonodNo.isEmpty()) {
            Toast.makeText(this, "Field is required", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://fenipaurashava.gov.bd/api/verify/certificate/5654564/" + pos + "/" + sonodNo;
            Log.e("pos",String.valueOf(pos));
            openTab(url);
        }

    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        // overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        pos = position + 1;

        if (pos == 24) {
            pos = 90;
        } else if (pos == 25) {
            pos = 91;
        } else if (pos == 26) {
            pos = 92;
        } else if (pos == 27) {
            pos = 93;
        } else if (pos == 28) {
            pos = 94;
        } else if (pos == 29) {
            pos = 95;
        } else if (pos == 30) {
            pos = 96;
        } else if (pos == 31) {
            pos = 97;
        } else if (pos == 32) {
            pos = 98;
        } else if (pos == 33) {
            pos = 99;
        } else if (pos == 34) {
            pos = 106;
        } else if (pos == 35) {
            pos = 107;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }
}