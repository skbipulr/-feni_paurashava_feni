package bd.gov.fenipaurashava.activity_admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_user.WorkScheduleActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.common.RangeTimePickerDialog;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForWorkScheduleDeletePOST.WorkScheduleDeleteResponse;
import bd.gov.fenipaurashava.modelForWorkScheduleUpdatePOST.WorkScheduleUpdateResponse;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkScheduleEditDeleteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    LinearLayout dateLL, timeLL;
    private TextView dateTV;
    private ApiInterface apiService;
    private EditText workSubjectET, workPlaceET, workDetailsET;
    private String workSubject, workPlace, workDetails, date;

    private int hour1;
    private int minute1;
    private Datum schedule = null;
    private Menu action;

    private int order, itemId;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_schedule_edit_delete);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dateLL = findViewById(R.id.dateLL);
        dateTV = findViewById(R.id.dateTV);
        workSubjectET = findViewById(R.id.workSubjectET);
        workPlaceET = findViewById(R.id.workPlaceET);
        workDetailsET = findViewById(R.id.workDetailsET);

        dateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        //intent
        Intent i = getIntent();
        schedule = (Datum) i.getSerializableExtra("schedule");

        assert schedule != null;
        itemId = schedule.getId();
        workSubject = schedule.getSubject();
        workPlace = schedule.getPlace();
        date = schedule.getScheduleDate();
        workDetails = schedule.getDetails();

        setDataFromIntentExtra();

    }


    //---------------------
    private void setDataFromIntentExtra() {

        if (itemId != 0) {
            readMode();
            getSupportActionBar().setTitle("Edit");
            dateTV.setText(date);
            workDetailsET.setText(workDetails);
            workPlaceET.setText(workPlace);
            workSubjectET.setText(workSubject);

        } else {
            getSupportActionBar().setTitle("Add");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor2, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (itemId == 0) {
            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit
                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(workDetailsET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(workPlaceET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(workSubjectET, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //update
                updateData(itemId);
                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(true);
                readMode();

                return true;
            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(WorkScheduleEditDeleteActivity.this);
                dialog.setMessage("Delete this item?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(itemId);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void postData(final String key) {
//        String name = nameET.getText().toString().trim();
//
//        if (name.isEmpty()) {
//            nameET.setError("required");
//            nameET.requestFocus();
//        } else {
//            final ProgressDialog mDialog = new ProgressDialog(WorkScheduleEditDeleteActivity.this);
//            mDialog.setMessage("Please waiting...");
//            mDialog.show();
//
//            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
//
//            Call<AppointmentSubjectSaveResponse> call = apiService.setAppointmentSubjectSaveResponse(Common.APP_KEY, "1", name);
//
//            call.enqueue(new Callback<AppointmentSubjectSaveResponse>() {
//                @Override
//                public void onResponse(Call<AppointmentSubjectSaveResponse> call, Response<AppointmentSubjectSaveResponse> response) {
//                    if (response.code() == 200) {
//                        AppointmentSubjectSaveResponse serverResponse = response.body();
//                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        mDialog.dismiss();
//                    } else if (response.code() == 203) {
//                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    } else if (response.code() == 401) {
//                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    } else if (response.code() == 422) {
//                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<AppointmentSubjectSaveResponse> call, Throwable t) {
//
//                    mDialog.dismiss();
//                }
//            });
//
//        }
//    }

    private void updateData(int id_t) {

        workSubject = workSubjectET.getText().toString().trim();
        workPlace = workPlaceET.getText().toString().trim();
        workDetails = workDetailsET.getText().toString().trim();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id == Common.ADMIN_USER_ID && em_id == Common.ADMIN_EMP_ID) {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.show();
            readMode();


            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);


            Call<WorkScheduleUpdateResponse> call = apiService.setWorkScheduleUpdateResponse(Common.APP_KEY, id_t, String.valueOf(Common.ADMIN_EMP_ID), user_id, workSubject, date, workPlace, workDetails);

            call.enqueue(new Callback<WorkScheduleUpdateResponse>() {
                @Override
                public void onResponse(Call<WorkScheduleUpdateResponse> call, Response<WorkScheduleUpdateResponse> response) {

                    progressDialog.dismiss();

                    if (response.code() == 200) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(WorkScheduleEditDeleteActivity.this, WorkScheduleShowActivity.class));

                    } else if (response.code() == 203) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<WorkScheduleUpdateResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(WorkScheduleEditDeleteActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }


    }

    private void deleteData(int id_t) {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id == Common.ADMIN_USER_ID) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
            readMode();
            //delete operation
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.setWorkScheduleDeleteResponse(Common.APP_KEY, id_t, user_id).enqueue(new Callback<WorkScheduleDeleteResponse>() {
                @Override
                public void onResponse(Call<WorkScheduleDeleteResponse> call, Response<WorkScheduleDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "" + response.body().getMessage() + "", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(WorkScheduleEditDeleteActivity.this, WorkScheduleShowActivity.class));

                    } else if (response.code() == 203) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(WorkScheduleEditDeleteActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<WorkScheduleDeleteResponse> call, Throwable t) {

                    Toast.makeText(WorkScheduleEditDeleteActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            progressDialog.dismiss();
        } else {
            Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("RestrictedApi")
    void readMode() {

        workSubjectET.setFocusableInTouchMode(false);
        workSubjectET.setFocusable(false);

        workPlaceET.setFocusableInTouchMode(false);
        workPlaceET.setFocusable(false);

        workDetailsET.setFocusableInTouchMode(false);
        workDetailsET.setFocusable(false);


    }

    private void editMode() {
        workSubjectET.setFocusableInTouchMode(true);
        workPlaceET.setFocusableInTouchMode(true);
        workDetailsET.setFocusableInTouchMode(true);
    }

    //----------------------


    //----------date taken-----------start-------------
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, WorkScheduleEditDeleteActivity.this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date = year + "-" + month + "-" + dayOfMonth;
        dateTV.setText(date);

    }
    //----------date taken-----------end-------------


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