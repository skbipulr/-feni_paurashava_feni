package bd.gov.fenipaurashava.activity_user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;

import bd.gov.fenipaurashava.ComplainSpinnerItem;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapter.ComplainSubjectSpinnerAdapter;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForComplainSavePOST.ComplainSaveResponse;
import bd.gov.fenipaurashava.modelForComplainSubjectFetchGET.ComplainSubjectFetchResponse;
import bd.gov.fenipaurashava.modelForComplainSubjectFetchGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainActivity extends AppCompatActivity {

    private ImageView getImageIV;
    private LinearLayout selectedIV;
    private int subjectId;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    public static final String USER_ID = "USER_ID";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";

    EditText subjectET, complainDetailsET, nameOneET, addressOneET, nameTwoET, addressTwoET, phoneNumberET;
    String subject, complainDetails, nameOne, addressOne, nameTwo, addressTwo, phoneNumber;
    private ApiInterface apiInterface;
    private Spinner complainOfDivisionsSpinner;
    private String employee_id;

    private ArrayList<ComplainSpinnerItem> complainDivisionsSpinnerItems;
    private ComplainSubjectSpinnerAdapter complainSubjectSpinnerAdapter;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedpreferences.getString(USER_ID, "");
        employee_id = sharedpreferences.getString(EMPLOYEE_ID, "");

        initField();
        fetchComplainOfDivisions();
    }

    private void fetchComplainOfDivisions() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getComplainSubjectFetchResponse(Common.APP_KEY).enqueue(new Callback<ComplainSubjectFetchResponse>() {
            @Override
            public void onResponse(Call<ComplainSubjectFetchResponse> call, Response<ComplainSubjectFetchResponse> response) {
                if (response.isSuccessful()) {

                    ComplainSubjectFetchResponse complainOfDivisionsResponse = response.body();
                    List<Datum> datas = complainOfDivisionsResponse.getData();
                    complainDivisionsSpinnerItems = new ArrayList<>();

                    for (int i = 0; i < datas.size(); i++) {
                        subjectId = datas.get(i).getId();
                        complainDivisionsSpinnerItems.add(new ComplainSpinnerItem(datas.get(i).getName()));
                    }

                    complainSubjectSpinnerAdapter = new ComplainSubjectSpinnerAdapter(ComplainActivity.this, complainDivisionsSpinnerItems);
                    complainOfDivisionsSpinner.setAdapter(complainSubjectSpinnerAdapter);

                    complainOfDivisionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ComplainSubjectFetchResponse> call, Throwable t) {

            }
        });

    }

    private void initField() {

        selectedIV = findViewById(R.id.selectedIV);
        getImageIV = findViewById(R.id.getImageIV);
        complainOfDivisionsSpinner = findViewById(R.id.complainOfDivisionsSpinner);
        complainDetailsET = findViewById(R.id.complainDetailsET);
        nameOneET = findViewById(R.id.nameOneET);
        addressOneET = findViewById(R.id.addressOneET);
        nameTwoET = findViewById(R.id.nameTwoET);
        addressTwoET = findViewById(R.id.addressTwoET);
        phoneNumberET = findViewById(R.id.phoneNumberET);

        selectedIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImage();
            }
        });
    }

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
            if (uri != null) {
                getImageIV.setImageURI(uri);
                getImageIV.setVisibility(View.VISIBLE);
            } else {
                getImageIV.setVisibility(View.GONE);
            }
        }
    }

    public void backBtn(View view) {
        onBackPressed();
    }

    private void createPublicHearing() {
        complainDetails = complainDetailsET.getText().toString().trim();
        nameOne = nameOneET.getText().toString().trim();
        addressOne = addressOneET.getText().toString().trim();
        nameTwo = nameTwoET.getText().toString().trim();
        addressTwo = addressTwoET.getText().toString().trim();
        phoneNumber = phoneNumberET.getText().toString().trim();

        if (complainDetails.isEmpty()) {
            complainDetailsET.setError("required");
            complainDetailsET.requestFocus();

        } else if (nameOne.isEmpty()) {
            nameOneET.setError("required");
            nameOneET.requestFocus();
        } else if (addressOne.isEmpty()) {
            addressOneET.setError("required");
            addressOneET.requestFocus();
        } else if (phoneNumber.isEmpty()) {
            phoneNumberET.setError("required");
            phoneNumberET.requestFocus();
        } else if(nameTwo.isEmpty()){
            nameTwoET.setError("required");
            nameTwoET.requestFocus();
        }else if(addressTwo.isEmpty()){
            addressTwoET.setError("required");
            addressTwoET.requestFocus();
        }
        else {

            final ProgressDialog mDialog = new ProgressDialog(ComplainActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
            //   int id = Integer.parseInt(Common.USER_ID);
            Call<ComplainSaveResponse> call = apiInterface.setComplain(Common.APP_KEY, employee_id, subjectId, nameOne
                    , nameTwo, phoneNumber, complainDetails, addressOne,
                    addressTwo, "");

            call.enqueue(new Callback<ComplainSaveResponse>() {
                @Override
                public void onResponse(Call<ComplainSaveResponse> call, Response<ComplainSaveResponse> response) {

                    if (response.isSuccessful() && response.body()!=null) {
                        ComplainSaveResponse meg = response.body();

                        Toast.makeText(ComplainActivity.this, "কংগ্রাচুলেশন, আপনার অভিযোগটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                        nameOneET.setText("");
                        nameTwoET.setText("");
                        addressOneET.setText("");
                        addressTwoET.setText("");
                        phoneNumberET.setText("");
                        complainDetailsET.setText("");

                    } else if (response.code() == 203) {
                        Toast.makeText(ComplainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(ComplainActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(ComplainActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ComplainSaveResponse> call, Throwable t) {

                    Toast.makeText(ComplainActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });

        }


//       if (complainDetails.isEmpty()) {
//            complainDetailsET.setError("required");
//            complainDetailsET.requestFocus();
//        } else if (nameTwo.isEmpty()) {
//            nameOneET.setError("required");
//            nameOneET.requestFocus();
//        }
//        else if (addressTwo.isEmpty()) {
//            addressTwoET.setError("required");
//            addressTwoET.requestFocus();
//        }
//
//        else {
//
//        }
    }

    public void btnSubmit(View view) {
        createPublicHearing();
    }

}
