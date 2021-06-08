package bd.gov.fenipaurashava.activity_admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelEmployeeDeletePOST.EmployeeDeleteResponse;
import bd.gov.fenipaurashava.modelEmployeeUpdatePOST.EmployeeUpdateResponse;
import bd.gov.fenipaurashava.modelForAppoinmentSubjectSavePOST.AppointmentSubjectSaveResponse;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCurdEditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private EditText mName;
    private int id;

    private CircleImageView getImageIV;
    private FloatingActionButton selectedIV;


    private ApiInterface apiInterface;

    private LinearLayout selectedForDateIV, l1;
    private EditText nameET, emailET, mobileNoET, designationET, bcsBatchET,
            userNameET, passwordET, orderET, facebookIdET, tweeterIdET;
    private TextView pictureTextTV, dateTxt;

    private File uploadFile, file;
    private String name, email, mobileNo, designation, bcsBatch, userName,
            password, facebookId, tweeterId, dataAndTime, picture;

    private int order, itemId;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";


    String date;


    private Menu action;
    private Bitmap bitmap;

    private Datum employee = null;

    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_curd_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        selectedForDateIV = findViewById(R.id.selectedForDateIV);
        selectedIV = findViewById(R.id.selectedIV);
        getImageIV = findViewById(R.id.getImageIV);
        dateTxt = findViewById(R.id.dateTxt);

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


        selectedIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImage();
            }
        });

        selectedForDateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        Intent i = getIntent();
        employee = (Datum) i.getSerializableExtra("employee");
//password, order,facebookId,tweeterId,dataAndTime
        assert employee != null;
        itemId = employee.getId();
        name = employee.getName();
        email = employee.getEmail();
        mobileNo = employee.getMobileNo();
        designation = employee.getDesignation();
        bcsBatch = employee.getBcsBatch();
        userName = (String) employee.getUsername();
        password = passwordET.getText().toString().trim();
        order = employee.getOrderNo();

        facebookId = (String) employee.getFbId();
        tweeterId = (String) employee.getTweeterId();
        dataAndTime = (String) employee.getJoiningDate();
        picture = employee.getPicture();

        setDataFromIntentExtra();
    }


    private void setDataFromIntentExtra() {

        if (itemId != 0) {
            readMode();
            getSupportActionBar().setTitle("Edit");
            nameET.setText(name);
            emailET.setText(email);
            mobileNoET.setText(mobileNo);
            designationET.setText(designation);
            bcsBatchET.setText(bcsBatch);
            userNameET.setText(userName);
            passwordET.setText(password);
            orderET.setText(String.valueOf(order));
            facebookIdET.setText(facebookId);
            tweeterIdET.setText(tweeterId);
            dateTxt.setText(dataAndTime);

            Picasso.get().load("http://fenimayor.digiins.gov.bd/district_app/public/employee/" + employee.getPicture())
                    .placeholder(R.drawable.placeholder).into(getImageIV);
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
                imm.showSoftInput(nameET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(emailET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(mobileNoET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(designationET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(bcsBatchET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(userNameET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(passwordET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(orderET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(facebookIdET, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(tweeterIdET, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save
                if (itemId == 0) {
                    if (TextUtils.isEmpty(nameET.getText().toString())) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {
                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);
                        readMode();
                    }
                } else {
                    updateData(itemId);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);
                    readMode();
                }
                return true;
            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminCurdEditorActivity.this);
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

    private void postData(final String key) {
        String name = nameET.getText().toString().trim();

        if (name.isEmpty()) {
            nameET.setError("required");
            nameET.requestFocus();
        } else {
            final ProgressDialog mDialog = new ProgressDialog(AdminCurdEditorActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            Call<AppointmentSubjectSaveResponse> call = apiService.setAppointmentSubjectSaveResponse(Common.APP_KEY, "1", name);

            call.enqueue(new Callback<AppointmentSubjectSaveResponse>() {
                @Override
                public void onResponse(Call<AppointmentSubjectSaveResponse> call, Response<AppointmentSubjectSaveResponse> response) {
                    if (response.code() == 200) {
                        AppointmentSubjectSaveResponse serverResponse = response.body();
                        Toast.makeText(AdminCurdEditorActivity.this, "" + serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    } else if (response.code() == 203) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
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

    private void updateData(int id_t) {
        name = nameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        mobileNo = mobileNoET.getText().toString().trim();
        designation = designationET.getText().toString().trim();
        bcsBatch = bcsBatchET.getText().toString().trim();
        String userName = userNameET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        String orderString = orderET.getText().toString().trim();

        String datee = dateTxt.getText().toString().trim();

        facebookId = facebookIdET.getText().toString().trim();
        tweeterId = tweeterIdET.getText().toString().trim();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        String employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id == Common.ADMIN_USER_ID && em_id == Common.ADMIN_EMP_ID) {

            String e_id, f_id, t_id;
            if (file != null || employee_id != null || facebookId != null || tweeterId != null) {
                file = uploadFile;
                e_id = employee_id;
                f_id = facebookId;
                t_id = tweeterId;

            } else {
                file = null;
                e_id = "";
                f_id = "";
                t_id = "";
            }

            RequestBody _userId = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody _employee_id = RequestBody.create(MediaType.parse("text/plain"), employee_id);
            RequestBody _name = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody _email = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody _mobileNo = RequestBody.create(MediaType.parse("text/plain"), mobileNo);
            RequestBody _designation = RequestBody.create(MediaType.parse("text/plain"), designation);
            RequestBody _bcsBatch = RequestBody.create(MediaType.parse("text/plain"), bcsBatch);
            RequestBody _dateAndTime = RequestBody.create(MediaType.parse("text/plain"), datee);
            RequestBody _userName = RequestBody.create(MediaType.parse("text/plain"), userName);
            RequestBody _password = RequestBody.create(MediaType.parse("text/plain"), password);
            RequestBody _order = RequestBody.create(MediaType.parse("text/plain"), orderString);
            RequestBody _facebookId = RequestBody.create(MediaType.parse("text/plain"), facebookId);
            RequestBody _tweeterId = RequestBody.create(MediaType.parse("text/plain"), tweeterId);

            MultipartBody.Part _uploadFile = prepareFilePart("picture", uploadFile);


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating...");
            progressDialog.show();
            readMode();


            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
            apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);

            Call<EmployeeUpdateResponse> call = apiInterface.setEmployeeUpdateUpdateResponse("A1b1C2d32564kjhkjadu", id_t, _userId, _name
                    , _employee_id, _designation, _mobileNo, _email, _uploadFile, _bcsBatch, _dateAndTime, _facebookId, _tweeterId
                    , _order, _userName, _password);


            call.enqueue(new Callback<EmployeeUpdateResponse>() {
                @Override
                public void onResponse(Call<EmployeeUpdateResponse> call, Response<EmployeeUpdateResponse> response) {

                    progressDialog.dismiss();

                    if (response.code() == 200) {
                        Toast.makeText(AdminCurdEditorActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 203) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeUpdateResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminCurdEditorActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        // Toast.makeText(this, "user "+user_id+" employee"+employee_id, Toast.LENGTH_SHORT).show();
        int use_id = Integer.parseInt(user_id);
        int em_id = Integer.parseInt(employee_id);

        if (use_id == Common.ADMIN_USER_ID) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
            readMode();
            //delete operation
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.employeeDeleteResponse(Common.APP_KEY, id_t, user_id).enqueue(new Callback<EmployeeDeleteResponse>() {
                @Override
                public void onResponse(Call<EmployeeDeleteResponse> call, Response<EmployeeDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(AdminCurdEditorActivity.this, "" + response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 203) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(AdminCurdEditorActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeDeleteResponse> call, Throwable t) {

                    Toast.makeText(AdminCurdEditorActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        nameET.setFocusableInTouchMode(false);
        nameET.setFocusable(false);

        mobileNoET.setFocusableInTouchMode(false);
        mobileNoET.setFocusable(false);

        emailET.setFocusableInTouchMode(false);
        emailET.setFocusable(false);

        designationET.setFocusableInTouchMode(false);
        designationET.setFocusable(false);

        bcsBatchET.setFocusableInTouchMode(false);
        bcsBatchET.setFocusable(false);

        userNameET.setFocusableInTouchMode(false);
        userNameET.setFocusable(false);

        passwordET.setFocusableInTouchMode(false);
        passwordET.setFocusable(false);

        orderET.setFocusableInTouchMode(false);
        orderET.setFocusable(false);

        facebookIdET.setFocusableInTouchMode(false);
        facebookIdET.setFocusable(false);

        tweeterIdET.setFocusableInTouchMode(false);
        tweeterIdET.setFocusable(false);

        selectedIV.setVisibility(View.INVISIBLE);

    }

    private void editMode() {
        nameET.setFocusableInTouchMode(true);
        mobileNoET.setFocusableInTouchMode(true);
        emailET.setFocusableInTouchMode(true);
        designationET.setFocusableInTouchMode(true);
        bcsBatchET.setFocusableInTouchMode(true);
        userNameET.setFocusableInTouchMode(true);
        passwordET.setFocusableInTouchMode(true);
        orderET.setFocusableInTouchMode(true);
        facebookIdET.setFocusableInTouchMode(true);
        tweeterIdET.setFocusableInTouchMode(true);

        selectedIV.setVisibility(View.VISIBLE);

    }


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
            // pictureTextTV.setText(uploadFile.getName());
            if (uri != null) {
                getImageIV.setImageURI(uri);
                getImageIV.setVisibility(View.VISIBLE);
            }
        }
    }
    //----------image taken-----------end-------------

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
