package bd.gov.fenipaurashava.activity_user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import bd.gov.fenipaurashava.ComplainDivisionsSpinnerItem3;
import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.adapter.ComplainDivisionsSpinnerAdapter3;
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
    private FloatingActionButton floating_action_button,floating_action;

    String complainType;

    EditText subjectET, complainDetailsET, nameOneET, addressOneET, nameTwoET, addressTwoET, phoneNumberET,complainTypeET;
    String subject, complainDetails, nameOne, addressOne, nameTwo, addressTwo, phoneNumber;
    private ApiInterface apiInterface;
    private Spinner complainOfDivisionsSpinner;
    private LinearLayout complainSpeenerLL;

    private ArrayList<ComplainDivisionsSpinnerItem3> complainDivisionsSpinnerItems;
    private ComplainDivisionsSpinnerAdapter3 complainDivisionsSpinnerAdapter;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
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
                       complainType =  datas.get(i).getName();
                        complainDivisionsSpinnerItems.add(new bd.gov.fenipaurashava.ComplainDivisionsSpinnerItem3(datas.get(i).getName()));

                    }

                    //Toast.makeText(ComplainActivity.this, "dadadad"+ datas.get(i).getId(), Toast.LENGTH_SHORT).show();
                    complainDivisionsSpinnerAdapter = new ComplainDivisionsSpinnerAdapter3(ComplainActivity.this, complainDivisionsSpinnerItems);
                    complainOfDivisionsSpinner.setAdapter(complainDivisionsSpinnerAdapter);

                    complainOfDivisionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //fetchDistricts(i + 1);
                            String place = adapterView.getItemAtPosition(i).toString();
//                            fetchComplainOfTypes(i + 1);
//                            complainOfDivisions = i + 1;
                            // Toast.makeText(ComplainActivity.this, "position: "+place, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(ComplainActivity.this, "dadadad" + datas.get(i).getId(), Toast.LENGTH_SHORT).show();

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
        complainTypeET = findViewById(R.id.complainTypeET);

        complainSpeenerLL = findViewById(R.id.complainSpeenerLL);
        floating_action_button = findViewById(R.id.floating_action_button);
        floating_action = findViewById(R.id.floating_action);

        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complainTypeET.setVisibility(View.VISIBLE);
                complainSpeenerLL.setVisibility(View.GONE);
                floating_action_button.setVisibility(View.GONE);
                floating_action.setVisibility(View.VISIBLE);
            }
        });

        floating_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complainTypeET.setVisibility(View.GONE);
                complainSpeenerLL.setVisibility(View.VISIBLE);
                floating_action_button.setVisibility(View.VISIBLE);
                floating_action.setVisibility(View.GONE);
            }
        });

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
        complainType = complainTypeET.getText().toString().trim();

        if (complainDetails.isEmpty()) {
            complainDetailsET.setError("required");
            complainDetailsET.requestFocus();

        } else if (nameOne.isEmpty()) {
            nameOneET.setError("required");
            nameOneET.requestFocus();
        }
        else if (addressOne.isEmpty()) {
            addressOneET.setError("required");
            addressOneET.requestFocus();
        }
        else if (phoneNumber.isEmpty()) {
            phoneNumberET.setError("required");
            phoneNumberET.requestFocus();
        }else {

            final ProgressDialog mDialog = new ProgressDialog(ComplainActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();
            apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
            //   int id = Integer.parseInt(Common.USER_ID);
            Call<ComplainSaveResponse> call = apiInterface.setComplain(Common.APP_KEY, 221212121, complainType, nameOne
                    , nameTwo, phoneNumber, complainDetails, addressOne,
                    addressTwo, "");

            call.enqueue(new Callback<ComplainSaveResponse>() {
                @Override
                public void onResponse(Call<ComplainSaveResponse> call, Response<ComplainSaveResponse> response) {

                    if (response.code() == 200) {
                        ComplainSaveResponse meg = response.body();

                       // Toast.makeText(ComplainActivity.this, "subject Id: "+subjectId, Toast.LENGTH_SHORT).show();

                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                        Toast.makeText(ComplainActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
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
