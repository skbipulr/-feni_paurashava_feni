package bd.gov.fenipaurashava.activity_user;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import bd.gov.fenipaurashava.ComplainDivisionsSpinnerItem;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapter.ComplainDivisionsSpinnerAdapter;
import bd.gov.fenipaurashava.adapter.ComplainDivisionsSpinnerAdapter2;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppointmentSubjectGET.AppointmentSubjecResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectGET.Datum;
import bd.gov.fenipaurashava.modelForDCAppointmentSavePOST.AppointmentSaveResponse;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppinmentOfOtherStuffActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{

    private LinearLayout selectedForDateIV, l1;
    private TextView dateTxt, text1;

    EditText subjectET, detailsET, nameET, addressET, referringET, mobileNoET;
    String subject, details, name, address, mobileNo, referring, date;
    private ApiInterface apiInterface;

    private Spinner appointmentSubjectSpinner,employeeSpinner;
    private ArrayList<ComplainDivisionsSpinnerItem> complainDivisionsSpinnerItems;
    private ComplainDivisionsSpinnerAdapter complainDivisionsSpinnerAdapter;

    private ComplainDivisionsSpinnerAdapter2 employeeItemsAdapter;
    private ArrayList<bd.gov.fenipaurashava.modelForEmployeeGET.Datum> employeeItems;

    private ApiInterface apiService;
    private int subjectId,employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinment_of_other_stuff);

        initField();

        fieldInit();

        fetchComplainOfDivisions();
        fetchComplainOfDivisions2();
    }

    private void fetchComplainOfDivisions() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getComplainOfDivisions(Common.APP_KEY).enqueue(new Callback<AppointmentSubjecResponse>() {
            @Override
            public void onResponse(Call<AppointmentSubjecResponse> call, Response<AppointmentSubjecResponse> response) {
                if (response.isSuccessful()) {

                    AppointmentSubjecResponse complainOfDivisionsResponse = response.body();
                    List<Datum> datas = complainOfDivisionsResponse.getData();
                    complainDivisionsSpinnerItems = new ArrayList<>();

                    for (int i = 0; i < datas.size(); i++) {

                        complainDivisionsSpinnerItems.add(new ComplainDivisionsSpinnerItem(datas.get(i).getName()));

                    }

                    complainDivisionsSpinnerAdapter = new ComplainDivisionsSpinnerAdapter(AppinmentOfOtherStuffActivity.this, complainDivisionsSpinnerItems);

                    appointmentSubjectSpinner.setAdapter(complainDivisionsSpinnerAdapter);

                    appointmentSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            String place = adapterView.getItemAtPosition(i).toString();

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


    private void fetchComplainOfDivisions2() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getEmployeeResponse(Common.APP_KEY).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.isSuccessful()) {

                    EmployeeResponse employeeResponse = response.body();
                    List<bd.gov.fenipaurashava.modelForEmployeeGET.Datum> datas = employeeResponse.getData();
                    employeeItems = new ArrayList<>();

                    for (int i = 0; i < datas.size(); i++) {

                        employeeItems.add(new bd.gov.fenipaurashava.modelForEmployeeGET.Datum(datas.get(i).getName()));

                    }

                    employeeItemsAdapter = new ComplainDivisionsSpinnerAdapter2(AppinmentOfOtherStuffActivity.this, employeeItems);

                    employeeSpinner.setAdapter(employeeItemsAdapter);

                    employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            String place = adapterView.getItemAtPosition(i).toString();

                            employee_id = datas.get(i).getId();

                          //  Toast.makeText(AppinmentOfOtherStuffActivity.this, "employee_id: "+employee_id, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {

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
        int m  = month+1;
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
        employeeSpinner = findViewById(R.id.employeeSpinner);
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
        }  else if (referring.isEmpty()) {
            referringET.setError("required");
            referringET.requestFocus();
        }
        else {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            int employeeId=0;
            //   int id = Integer.parseInt(Common.USER_ID);
            Call<AppointmentSaveResponse> call = apiInterface.setAppointmentSave("A1b1C2d32564kjhkjadu",
                    employee_id,subjectId,name,mobileNo,date,referring,address,details);

            call.enqueue(new Callback<AppointmentSaveResponse>() {
                @Override
                public void onResponse(Call<AppointmentSaveResponse> call, Response<AppointmentSaveResponse> response) {

                    if (response.code() == 200) {
                        AppointmentSaveResponse meg = response.body();

                        // subjectET.setText(null);
                        detailsET.setText(null);
                        nameET.setText(null);
                        mobileNoET.setText(null);
                        addressET.setText(null);
                        referringET.setText(null);
                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                        Toast.makeText(AppinmentOfOtherStuffActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                        mDialog.dismiss();


                    } else if (response.code() == 203) {
                        Toast.makeText(AppinmentOfOtherStuffActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AppinmentOfOtherStuffActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AppinmentOfOtherStuffActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentSaveResponse> call, Throwable t) {

                    Toast.makeText(AppinmentOfOtherStuffActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });
        }
    }

    public void btnSubmit(View view) {
        createDCAppoinment();
    }

}