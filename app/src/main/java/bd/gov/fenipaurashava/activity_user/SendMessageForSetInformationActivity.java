package bd.gov.fenipaurashava.activity_user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForSMSSendPOST.SMSSendResponse;
import bd.gov.fenipaurashava.modelForSetInformationFetchGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageForSetInformationActivity extends AppCompatActivity {

    private EditText  messageET;
    private String phoneNo, message;
    String phoneNumber;
    private ApiInterface apiInterface;
    private Button btnSent;

    TextView phoneNoET;

    Datum dataClass=null;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_set_send_message);

        fieldInit();

        Intent i = getIntent();
        dataClass = (Datum) i.getSerializableExtra("info");
        assert dataClass != null;
        phoneNumber = dataClass.getMobileNo();

        phoneNoET.setText(phoneNumber);

       // Toast.makeText(this, "" + dataClass.getMobileNo(), Toast.LENGTH_SHORT).show();


    }

    private void fieldInit() {
        phoneNoET = findViewById(R.id.mobileNoET);
        messageET = findViewById(R.id.messageET);
        btnSent = findViewById(R.id.btnSent);
        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSendSMS();
            }
        });
    }

    public void btnSent(View view) {

    }

    public void backBtn(View view) {
        onBackPressed();
    }



    private void createSendSMS() {

        message = messageET.getText().toString().trim();
        if (message.isEmpty()) {
            messageET.setError("required");
            messageET.requestFocus();
        }else {

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString(USER_ID,"");
            String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

            int use_id = Integer.parseInt(user_id);
            int em_id = Integer.parseInt(employee_id);



            final ProgressDialog mDialog = new ProgressDialog(SendMessageForSetInformationActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            if(user_id.equals(Common.ADMIN_USER_ID)){
                Call<SMSSendResponse> call = apiInterface.setSMSSendResponse("A1b1C2d32564kjhkjadu", use_id,phoneNumber,message);

                call.enqueue(new Callback<SMSSendResponse>() {
                    @Override
                    public void onResponse(Call<SMSSendResponse> call, Response<SMSSendResponse> response) {

                        if (response.code() == 200) {
                            SMSSendResponse meg = response.body();


                            messageET.setText(null);

                            // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                            Toast.makeText(SendMessageForSetInformationActivity.this, "আপনার বার্তাটি পাঠানো হয়েছে", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();


                        } else if (response.code() == 203) {
                            Toast.makeText(SendMessageForSetInformationActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 401) {
                            Toast.makeText(SendMessageForSetInformationActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 422) {
                            Toast.makeText(SendMessageForSetInformationActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SMSSendResponse> call, Throwable t) {

                        Toast.makeText(SendMessageForSetInformationActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });

            }else {
                Toast.makeText(this, "You are not authorized person", Toast.LENGTH_SHORT).show();
            }


        }

    }


}