package bd.gov.fenipaurashava.activity_user;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.AppointmentSpinnerItem;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapter.AppointmentSubjectSpinnerAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.common.NetworkCheck;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppointmentSubjectGET.AppointmentSubjecResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectGET.Datum;
import bd.gov.fenipaurashava.modelForDCAppointmentSavePOST.AppointmentSaveResponse;
import bd.gov.fenipaurashava.modelForSMSSendPOST.SMSSendResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentOfMayorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private LinearLayout selectedForDateIV, l1;
    private TextView dateTxt, text1;

    EditText subjectET, detailsET, nameET, addressET, referringET, mobileNoET;
    String subject, details, name, address, mobileNo, referring, date;
    private ApiInterface apiInterface;

    private Spinner appointmentSubjectSpinner;
    private ArrayList<AppointmentSpinnerItem> complainDivisionsSpinnerItems;
    private AppointmentSubjectSpinnerAdapter appointmentSubjectSpinnerAdapter;
    private ApiInterface apiService;
    private int subjectId;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_of_d_c);

        initField();

        fieldInit();

        fetchAppointmentSpinner();
    }


    private void fetchAppointmentSpinner() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getComplainOfDivisions(Common.APP_KEY).enqueue(new Callback<AppointmentSubjecResponse>() {
            @Override
            public void onResponse(Call<AppointmentSubjecResponse> call, Response<AppointmentSubjecResponse> response) {
                if (response.isSuccessful()) {

                    AppointmentSubjecResponse complainOfDivisionsResponse = response.body();
                    List<Datum> datas = complainOfDivisionsResponse.getData();
                    complainDivisionsSpinnerItems = new ArrayList<>();

                    for (int i = 0; i < datas.size(); i++) {
                        complainDivisionsSpinnerItems.add(new AppointmentSpinnerItem(datas.get(i).getName()));
                    }
                    appointmentSubjectSpinnerAdapter = new AppointmentSubjectSpinnerAdapter(AppointmentOfMayorActivity.this, complainDivisionsSpinnerItems);
                    appointmentSubjectSpinner.setAdapter(appointmentSubjectSpinnerAdapter);

                    appointmentSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            subjectId = datas.get(i).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<AppointmentSubjecResponse> call, Throwable t) {

            }
        });


    }

    private void initField() {
        selectedForDateIV = findViewById(R.id.selectedForDateIV);
        dateTxt = findViewById(R.id.dateTxt);
        text1 = findViewById(R.id.text1);
        l1 = findViewById(R.id.l1);

        selectedForDateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    public void backBtn(View view) {
        onBackPressed();
    }

//    private void setSpinner2() {
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.appointmentSubject, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        appointmentSubjectSpinner.setAdapter(adapter);
//        appointmentSubjectSpinner.setOnItemSelectedListener(AppointmentOfDCActivity.this);
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //  String date = month + "/" + dayOfMonth + "/" + year;
        int m = month + 1;
        date = year + "-" + m + "-" + dayOfMonth;
        dateTxt.setText(date);
    }


    //----------ruf-----------------
    private void fieldInit() {
        subjectET = findViewById(R.id.subjectET);
        detailsET = findViewById(R.id.detailsET);
        nameET = findViewById(R.id.nameET);
        addressET = findViewById(R.id.addressET);
        mobileNoET = findViewById(R.id.mobileNoET);
        referringET = findViewById(R.id.referringET);
        appointmentSubjectSpinner = findViewById(R.id.appointmentSubjectSpinner);
        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    private void createDCAppoinment() {
        // subject = subjectET.getText().toString().trim();
        details = detailsET.getText().toString().trim();
        name = nameET.getText().toString().trim();
        address = addressET.getText().toString().trim();
        mobileNo = mobileNoET.getText().toString().trim();
        referring = referringET.getText().toString().trim();

        if (details.isEmpty()) {
            detailsET.setError("required");
            detailsET.requestFocus();
        } else if (name.isEmpty()) {
            nameET.setError("required");
            nameET.requestFocus();
        } else if (address.isEmpty()) {
            addressET.setError("required");
            addressET.requestFocus();
        } else if (mobileNo.isEmpty()) {
            mobileNoET.setError("required");
            mobileNoET.requestFocus();
        } else if (referring.isEmpty()) {
            referringET.setError("required");
            referringET.requestFocus();
        } else {
            final ProgressDialog mDialog = new ProgressDialog(AppointmentOfMayorActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString(USER_ID, "");
            String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");
            int employeeId = 0;
            int e_id = Integer.parseInt(employee_id);
            Call<AppointmentSaveResponse> call = apiInterface.setAppointmentSave(Common.APP_KEY,
                    e_id, subjectId, name, mobileNo, date, referring, address, details);

            call.enqueue(new Callback<AppointmentSaveResponse>() {
                @Override
                public void onResponse(Call<AppointmentSaveResponse> call, Response<AppointmentSaveResponse> response) {

                    if (response.code() == 200) {
                        AppointmentSaveResponse meg = response.body();

                        detailsET.setText(null);
                        nameET.setText(null);
                        mobileNoET.setText(null);
                        addressET.setText(null);
                        referringET.setText(null);
                       // Toast.makeText(AppointmentOfMayorActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                        createSendSMS();
                        mDialog.dismiss();


                    } else if (response.code() == 203) {
                        Toast.makeText(AppointmentOfMayorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AppointmentOfMayorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AppointmentOfMayorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentSaveResponse> call, Throwable t) {

               //     Toast.makeText(AppointmentOfMayorActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });
        }
    }

    public void btnSubmit(View view) {

        if (NetworkCheck.isConnect(AppointmentOfMayorActivity.this)) {
            createDCAppoinment();
        } else {
            Toast.makeText(AppointmentOfMayorActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
        }

    }


    private void createSendSMS() {
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        String message = "এক জন ব্যক্তি 'মেয়র ফেনী পৌরসভা' অ্যাপর মাধমে আপনার সাক্ষাৎকারের জন্য আবেদন করেছে: \n"  + "ব্যক্তির নাম : " + name + "\n" + "মোবাইল নং:" + mobileNo + "\n" + "ঠিকানা: " + address+"\n\n"+"বিস্তারিত পেতে অ্যাপের এডমিন এ লগইন করুন।";
        Call<SMSSendResponse> call = apiInterface.setSMSSendResponse(Common.APP_KEY, 1, Common.SMS_NUMBER, message);

        call.enqueue(new Callback<SMSSendResponse>() {
            @Override
            public void onResponse(Call<SMSSendResponse> call, Response<SMSSendResponse> response) {

                if (response.code() == 200) {
                    SMSSendResponse meg = response.body();

                    //  Toast.makeText(ComplainActivity.this, "আপনার বার্তাটি পাঠানো হয়েছে", Toast.LENGTH_LONG).show();
                    // Toast.makeText(ComplainActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
// Toast.makeText(AppointmentOfMayorActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                    Toast.makeText(AppointmentOfMayorActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();

                } else if (response.code() == 203) {
                    Toast.makeText(AppointmentOfMayorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 401) {
                    Toast.makeText(AppointmentOfMayorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 422) {
                    Toast.makeText(AppointmentOfMayorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SMSSendResponse> call, Throwable t) {

                Toast.makeText(AppointmentOfMayorActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });


    }

}