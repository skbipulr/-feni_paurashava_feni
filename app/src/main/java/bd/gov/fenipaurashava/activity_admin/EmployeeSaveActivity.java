package bd.gov.fenipaurashava.activity_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForEmployeeSavePOST.EmployeeSaveResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeSaveActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ImageView getImageIV;
    private LinearLayout selectedIV;

    private ApiInterface apiInterface;

    private LinearLayout selectedForDateIV, l1;
    private EditText nameET, emailET, mobileNoET, designationET, bcsBatchET,
            userNameET, passwordET, orderET,facebookIdET,tweeterIdET;
    private TextView pictureTextTV, dateTxt;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    private File uploadFile;
    private String name, email, mobileNo, designation, bcsBatch, userName, password, order,facebookId,tweeterId,dataAndTime;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_save);

        fieldInit();
        initField();

        selectedForDateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void fieldInit() {
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        mobileNoET = findViewById(R.id.mobileNoET);
        designationET = findViewById(R.id.designationET);
        bcsBatchET = findViewById(R.id.bcsBatchET);
        userNameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);
        orderET = findViewById(R.id.orderET);
        facebookIdET = findViewById(R.id.facebookIdET);
        tweeterIdET = findViewById(R.id.tweeterIdET);
        orderET = findViewById(R.id.orderET);
        pictureTextTV = findViewById(R.id.pictureTextTV);
        dateTxt = findViewById(R.id.dateTxt);

        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    private void initField() {
        selectedIV = findViewById(R.id.selectedIV);
        getImageIV = findViewById(R.id.getImageIV);
        selectedForDateIV = findViewById(R.id.selectedForDateIV);

        selectedIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImage();
            }
        });
    }


    //----------image taken-----------start-------------
    private void takeImage() {
        ImagePicker.Companion.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path = uri.getPath();
            uploadFile = new File(path);
            pictureTextTV.setText(uploadFile.getName());
            if (uri != null) {
                getImageIV.setImageURI(uri);
                getImageIV.setVisibility(View.VISIBLE);
            } else {
                getImageIV.setVisibility(View.GONE);
            }
        }
    }
    //----------image taken-----------end-------------


    //----------date taken-----------start-------------
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

        date = year + "-" + month + "-" + dayOfMonth;
        dateTxt.setText(date);

    }
    //----------date taken-----------end-------------


    public void backBtn(View view) {
        onBackPressed();
    }

    public void btnSubmit(View view) {
        createEmployee();
    }

    private void createEmployee() {
        name = nameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        mobileNo = mobileNoET.getText().toString().trim();
        designation = designationET.getText().toString().trim();
        bcsBatch = bcsBatchET.getText().toString().trim();
        userName = userNameET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        order = orderET.getText().toString().trim();
        facebookId = facebookIdET.getText().toString().trim();
        tweeterId = tweeterIdET.getText().toString().trim();

        if (name.isEmpty()) {
            nameET.setError("required");
            nameET.requestFocus();
        } else if (email.isEmpty()) {
            emailET.setError("required");
            emailET.requestFocus();
        } else if (mobileNo.isEmpty()) {
            mobileNoET.setError("required");
            mobileNoET.requestFocus();
        }
        else if (designation.isEmpty()) {
            designationET.setError("required");
            designationET.requestFocus();
        }
//        else if (bcsBatch.isEmpty()) {
//            bcsBatchET.setError("required");
//            bcsBatchET.requestFocus();
//        }
        else if (userName.isEmpty()) {
            userNameET.setError("required");
            userNameET.requestFocus();
        }
        else if (password.isEmpty()) {
            passwordET.setError("required");
            passwordET.requestFocus();
        }
        else if (order.isEmpty()) {
            orderET.setError("required");
            orderET.requestFocus();
        }else {

//            String user_id = "1";
//            String employee_id = "19023652145";

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String user_id = sharedpreferences.getString(USER_ID,"");
            String employee_id = sharedpreferences.getString(EMPLOYEE_ID,"");

            int use_id = Integer.parseInt(user_id);
            int em_id = Integer.parseInt(employee_id);

//            Toast.makeText(this, "user_id: "+user_id+" em_id "+em_id, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Common_id: "+Common.ADMIN_USER_ID+" Common_em_id "+Common.ADMIN_EMP_ID, Toast.LENGTH_SHORT).show();

            if (use_id== Common.ADMIN_USER_ID && em_id==Common.ADMIN_EMP_ID){
                RequestBody _userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
                RequestBody _employee_id = RequestBody.create(MediaType.parse("text/plain"), employee_id);
                RequestBody _name = RequestBody.create(MediaType.parse("text/plain"), name);
                RequestBody _email = RequestBody.create(MediaType.parse("text/plain"), email);
                RequestBody _mobileNo = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
                RequestBody _designation = RequestBody.create(MediaType.parse("text/plain"), designation);
                RequestBody _bcsBatch = RequestBody.create(MediaType.parse("text/plain"), "11");
                RequestBody _dateAndTime = RequestBody.create(MediaType.parse("text/plain"), date);
                RequestBody _userName = RequestBody.create(MediaType.parse("text/plain"), userName);
                RequestBody _password = RequestBody.create(MediaType.parse("text/plain"), password);
                RequestBody _order = RequestBody.create(MediaType.parse("text/plain"), order);
                RequestBody _facebookId = RequestBody.create(MediaType.parse("text/plain"), facebookId);
                RequestBody _tweeterId = RequestBody.create(MediaType.parse("text/plain"), tweeterId);

                MultipartBody.Part _uploadFile = prepareFilePart("picture", uploadFile);

                final ProgressDialog mDialog = new ProgressDialog(EmployeeSaveActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                Call<EmployeeSaveResponse> call = apiInterface.employeeSaveResponse("A1b1C2d32564kjhkjadu",_userId,_name
                        ,_employee_id,_designation,_mobileNo,_email,_uploadFile,_bcsBatch,_dateAndTime,_facebookId,_tweeterId
                        ,_order,_userName,_password);

                call.enqueue(new Callback<EmployeeSaveResponse>() {
                    @Override
                    public void onResponse(Call<EmployeeSaveResponse> call, Response<EmployeeSaveResponse> response) {
                        if (response.code() == 200) {
                            EmployeeSaveResponse meg = response.body();

                            Toast.makeText(EmployeeSaveActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();


                        } else if (response.code() == 203) {
                            Toast.makeText(EmployeeSaveActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 401) {
                            Toast.makeText(EmployeeSaveActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else if (response.code() == 422) {
                            Toast.makeText(EmployeeSaveActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeSaveResponse> call, Throwable t) {
                        Toast.makeText(EmployeeSaveActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });

            }else {
                Toast.makeText(this, "Your are not authorize person", Toast.LENGTH_SHORT).show();
            }



        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, File file) {
        if (file != null) {
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getMimeType(Uri.fromFile(file))), file
                    );
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            return MultipartBody.Part.createFormData(partName, "", attachmentEmpty);
        }
    }

    private String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }


}