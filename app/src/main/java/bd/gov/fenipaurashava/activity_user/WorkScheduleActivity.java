package bd.gov.fenipaurashava.activity_user;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminDashboardActivity;
import bd.gov.fenipaurashava.activity_admin.WorkScheduleShowActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.common.RangeTimePickerDialog;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForWorkSchedulePOST.WorkScheduleSaveResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    LinearLayout dateLL, timeLL;
    private TextView dateTV;
    private ApiInterface apiService;
    private EditText workSubjectET, workPlaceET, workDetailsET;
    private String workSubject, workPlace, workDetails, date;

    private int hour1;
    private int minute1;


    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_schedule);

        initField();

        dateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker();
            }
        });

    }

    private void initField() {
        dateLL = findViewById(R.id.dateLL);
        dateTV = findViewById(R.id.dateTV);
        workSubjectET = findViewById(R.id.workSubjectET);
        workPlaceET = findViewById(R.id.workPlaceET);
        workDetailsET = findViewById(R.id.workDetailsET);

    }

    public void backBtn(View view) {
        onBackPressed();
        startActivity(new Intent(WorkScheduleActivity.this, AdminDashboardActivity.class));
        finish();
    }


    private void datePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int m = month + 1;
                date = year + "-" + m + "-" + dayOfMonth;
                dateTV.setText(date);
                timePicker(date);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void timePicker(final String date) {
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                hour1 = hourOfDay;
                minute1 = minutes;
                updateTime(hour1, minute1);

            }
        }, hour, minute, false);
        // timePickerDialog.setMin(hour + 1, minute);
        timePickerDialog.show();
    }

    public void btnSubmit(View view) {
        createWorkSchedule();
    }

    private void createWorkSchedule() {

        workSubject = workSubjectET.getText().toString().trim();
        workDetails = workDetailsET.getText().toString().trim();
        workPlace = workPlaceET.getText().toString().trim();
        date = dateTV.getText().toString().trim();

        if (workSubject.isEmpty()) {
            workSubjectET.setError("required");
            workSubjectET.requestFocus();

        } else if (workDetails.isEmpty()) {
            workDetailsET.setError("required");
            workDetailsET.requestFocus();
        } else if (workPlace.isEmpty()) {
            workPlaceET.setError("required");
            workPlaceET.requestFocus();
        } else if (date.isEmpty()) {
            dateTV.setError("required");
            dateTV.requestFocus();
        } else {
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            final ProgressDialog mDialog = new ProgressDialog(WorkScheduleActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String u_id = sharedpreferences.getString(USER_ID, "");
            String e_id = sharedpreferences.getString(EMPLOYEE_ID, "");

            int user_id = Integer.parseInt(u_id);
            int employee_id = Integer.parseInt(e_id);

            if (user_id == Common.ADMIN_USER_ID && employee_id == Common.ADMIN_EMP_ID) {

                Call<WorkScheduleSaveResponse> call = apiService.setWorkScheduleSaveResponse(Common.APP_KEY, u_id, e_id, workSubject, date, workPlace, workDetails);

                call.enqueue(new Callback<WorkScheduleSaveResponse>() {
                    @Override
                    public void onResponse(Call<WorkScheduleSaveResponse> call, Response<WorkScheduleSaveResponse> response) {
                        if (response.code() == 200) {
                            WorkScheduleSaveResponse meg = response.body();
                            Toast.makeText(WorkScheduleActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();

                            workDetailsET.setText("");
                            workPlaceET.setText("");
                            workSubjectET.setText("");
                            dateTV.setText("");

                            startActivity(new Intent(WorkScheduleActivity.this, WorkScheduleShowActivity.class));

                        } else if (response.code() == 203) {
                            Toast.makeText(WorkScheduleActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 401) {
                            Toast.makeText(WorkScheduleActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 422) {
                            Toast.makeText(WorkScheduleActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkScheduleSaveResponse> call, Throwable t) {

                    }
                });

            } else {
                Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
            }


        }

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //  String date = month + "/" + dayOfMonth + "/" + year;
//        date = year + "-" + month + "-" + dayOfMonth;
//        dateTV.setText(date);
    }


    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        String dateTime = date + "  " + aTime;

        dateTV.setText(dateTime);
    }
}