package bd.gov.fenipaurashava.activity_admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForAppoinmentSubjectSavePOST.AppointmentSubjectSaveResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectDeletePOST.AppointmentSubjectDeleteResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectUpdatePOST.AppointmentSubjectUpdateResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {


    private EditText mName;
    private String name;
    private int id;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiService;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mName = findViewById(R.id.name);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");

        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle("Edit");
            mName.setText(name);

        } else {
           getSupportActionBar().setTitle("Add");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){
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
                imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save
                if (id == 0) {
                    if (TextUtils.isEmpty(mName.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    else {
                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);
                        readMode();
                    }
                } else {
                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);
                    readMode();
                }
                return true;
            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorActivity.this);
                dialog.setMessage("Delete this item?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(id);
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

    private void postData(final String key) {
        String name = mName.getText().toString().trim();

        if (name.isEmpty()) {
            mName.setError("required");
            mName.requestFocus();
        } else {

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString(USER_ID,"");
            String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

            int use_id = Integer.parseInt(user_id);
            int em_id = Integer.parseInt(employee_id);

            if (use_id ==Common.ADMIN_USER_ID){
                final ProgressDialog mDialog = new ProgressDialog(EditorActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

                Call<AppointmentSubjectSaveResponse> call = apiService.setAppointmentSubjectSaveResponse(Common.APP_KEY, user_id, name);

                call.enqueue(new Callback<AppointmentSubjectSaveResponse>() {
                    @Override
                    public void onResponse(Call<AppointmentSubjectSaveResponse> call, Response<AppointmentSubjectSaveResponse> response) {
                        if (response.code() == 200) {
                            AppointmentSubjectSaveResponse serverResponse = response.body();
                            Toast.makeText(EditorActivity.this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditorActivity.this, AppoimentFatchAllActivity.class));
                            mDialog.dismiss();
                        } else if (response.code() == 203) {
                            Toast.makeText(EditorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 401) {
                            Toast.makeText(EditorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 422) {
                            Toast.makeText(EditorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<AppointmentSubjectSaveResponse> call, Throwable t) {
                        mDialog.dismiss();
                    }
                });

            }

        }
    }

    private void updateData(final String key, final int id) {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID,"");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id==Common.ADMIN_USER_ID){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.show();
            readMode();
            String name = mName.getText().toString().trim();

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            Call<AppointmentSubjectUpdateResponse> call = apiService.setAppointmentSubjectUpdateResponse(Common.APP_KEY, id,user_id, name);

            call.enqueue(new Callback<AppointmentSubjectUpdateResponse>() {
                @Override
                public void onResponse(Call<AppointmentSubjectUpdateResponse> call, Response<AppointmentSubjectUpdateResponse> response) {

                    progressDialog.dismiss();

                    Log.i(EditorActivity.class.getSimpleName(), response.toString());

                    if (response.code()==200){
                        Toast.makeText(EditorActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditorActivity.this, AppoimentFatchAllActivity.class));
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentSubjectUpdateResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }



    }

    private void deleteData( final int id) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID,"");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id==Common.ADMIN_USER_ID){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
            readMode();
            //delete operation
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.setAppointmentSubjectDeleteResponse(Common.APP_KEY, id, user_id).enqueue(new Callback<AppointmentSubjectDeleteResponse>() {
                @Override
                public void onResponse(Call<AppointmentSubjectDeleteResponse> call, Response<AppointmentSubjectDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(EditorActivity.this, "" + response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditorActivity.this, AppoimentFatchAllActivity.class));
                    }else {
                        Toast.makeText(EditorActivity.this, "problem", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentSubjectDeleteResponse> call, Throwable t) {
                    Toast.makeText(EditorActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

            progressDialog.dismiss();
        }else {
            Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("RestrictedApi")
    void readMode(){
        mName.setFocusableInTouchMode(false);
        mName.setFocusable(false);
    }

    private void editMode()
    {
        mName.setFocusableInTouchMode(true);
    }

}
