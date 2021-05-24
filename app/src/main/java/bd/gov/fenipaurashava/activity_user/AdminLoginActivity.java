package bd.gov.fenipaurashava.activity_user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminActivity;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelAdminLoginPOST.LoginResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity {

    private TextInputEditText edtUsername, edtPassword;
    private String username, password;
    private ApiInterface apiInterface;

    //SharedPreferences
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

    }

    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }


    private void createLogin() {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (username.isEmpty()) {
            edtUsername.setError("Please Enter Your Email");
            edtUsername.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPassword.setError("Please Enter Your Password.");
            edtPassword.requestFocus();
        } else {
            final ProgressDialog mDialog = new ProgressDialog(AdminLoginActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            Call<LoginResponse> call = apiInterface.setUserInfoForLogin(Common.APP_KEY, username, password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.code() == 200) {
                        LoginResponse meg = response.body();

                        int id = meg.getData().getId();

                        int employee_id = meg.getData().getEmployeeId();
                      //  Toast.makeText(AdminLoginActivity.this, "", Toast.LENGTH_SHORT).show();

                       // Toast.makeText(AdminLoginActivity.this, "user id: "+id+" employee id:"+employee_id, Toast.LENGTH_LONG).show();

                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(USER_ID, String.valueOf(id));
                        editor.putString(EMPLOYEE_ID, String.valueOf(employee_id));
                        editor.apply();

                        editor.putBoolean("login", true).apply();

                        sharedpreferences = getSharedPreferences(MyPREFERENCES,
                                Context.MODE_PRIVATE);
                        String user_ID = sharedpreferences.getString(USER_ID, "");

                        Common.USER_ID = user_ID;

                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                        Toast.makeText(AdminLoginActivity.this, "Congratulations!! Login successfully", Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                        Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                    } else if (response.code() == 203) {
                        Toast.makeText(AdminLoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AdminLoginActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AdminLoginActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(AdminLoginActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });
        }
    }

    public void btnSignIn(View view) {
        createLogin();
    }

    public void backBtn(View view) {
        onBackPressed();
        finish();
    }
}