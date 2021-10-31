package bd.gov.fenipaurashava.activity_user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import bd.gov.fenipaurashava.common.NetworkCheck;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForSMSSendPOST.SMSSendResponse;
import bd.gov.fenipaurashava.modelForSetInformationPOST.SetInformationSaveResponse;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetInformationActivity extends AppCompatActivity {

    private ImageView getImageIV;
    private LinearLayout selectedIV;

    private EditText subjectET, descriptionET, nameET, addressET, mobileNoET;
    TextView pictureTextTV;
    String subject, details, name, address, mobileNo;
    private ApiInterface apiInterface;

    private File uploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information);

        initField();

        fieldInit();
    }

    private void initField() {
        selectedIV = findViewById(R.id.selectedIV);
        getImageIV = findViewById(R.id.getImageIV);


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

    public void backBtn(View view) {
        onBackPressed();
    }


    //----------ruf-----------------
    private void fieldInit() {
        subjectET = findViewById(R.id.subjectET);
        descriptionET = findViewById(R.id.descriptionET);
        nameET = findViewById(R.id.nameET);
        addressET = findViewById(R.id.addressET);
        mobileNoET = findViewById(R.id.mobileET);
        pictureTextTV = findViewById(R.id.pictureTextTV);
        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }

    private void setInformation() {
        subject = subjectET.getText().toString().trim();
        details = descriptionET.getText().toString().trim();
        name = nameET.getText().toString().trim();
        address = addressET.getText().toString().trim();
        mobileNo = mobileNoET.getText().toString().trim();

        if (subject.isEmpty()) {
            subjectET.setError("required");
            subjectET.requestFocus();
            return;
        } else if (details.isEmpty()) {
            descriptionET.setError("required");
            descriptionET.requestFocus();
        } else if (name.isEmpty()) {
            nameET.setError("required");
            nameET.requestFocus();
        } else if (address.isEmpty()) {
            addressET.setError("required");
            addressET.requestFocus();
        } else if (mobileNo.isEmpty()) {
            mobileNoET.setError("required");
            mobileNoET.requestFocus();
        } else {

            RequestBody _name = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody _subject = RequestBody.create(MediaType.parse("text/plain"), subject);
            RequestBody _details = RequestBody.create(MediaType.parse("text/plain"), details);
            RequestBody _address = RequestBody.create(MediaType.parse("text/plain"), address);
            RequestBody _mobileNo = RequestBody.create(MediaType.parse("text/plain"), mobileNo);

            MultipartBody.Part _uploadFile = prepareFilePart("picture", uploadFile);

            final ProgressDialog mDialog = new ProgressDialog(SetInformationActivity.this);
            mDialog.setMessage("Please waiting...");
            mDialog.show();

            //   int id = Integer.parseInt(Common.USER_ID);
            Call<SetInformationSaveResponse> call = apiInterface.setInformationSaveResponse("A1b1C2d32564kjhkjadu",
                    _name, _subject, _address, _mobileNo, _details, _uploadFile);

            call.enqueue(new Callback<SetInformationSaveResponse>() {
                @Override
                public void onResponse(Call<SetInformationSaveResponse> call, Response<SetInformationSaveResponse> response) {

                    if (response.code() == 200) {
                        SetInformationSaveResponse meg = response.body();

                        subjectET.setText(null);
                        descriptionET.setText(null);
                        nameET.setText(null);
                        mobileNoET.setText(null);
                        addressET.setText(null);
                        pictureTextTV.setText(null);
                        // Toast.makeText(SignInActivity.this, ""+userAssessToken, Toast.LENGTH_LONG).show();
                      //  Toast.makeText(SetInformationActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                        createSendSMS();
                        mDialog.dismiss();


                    } else if (response.code() == 203) {
                        Toast.makeText(SetInformationActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 401) {
                        Toast.makeText(SetInformationActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else if (response.code() == 422) {
                        Toast.makeText(SetInformationActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<SetInformationSaveResponse> call, Throwable t) {

                    Toast.makeText(SetInformationActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            });
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

    public void btnSubmit(View view) {
        if (NetworkCheck.isConnect(SetInformationActivity.this)) {
            setInformation();
        } else {
            Toast.makeText(SetInformationActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }


    private void createSendSMS() {
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        String message = "এক জন ব্যক্তি 'মেয়র ফেনী পৌরসভা' অ্যাপর মাধমে একটি তথ্য দিয়েছে \n তথ্যটি হলো :\n" + subject + "\n" + "তথ্য দাতার নাম : " + name + "\n" + "মোবাইল নং:" + mobileNo + "\n" + "ঠিকানা: " + address+"\n\n"+"তথ্যের বিস্তারিত পেতে অ্যাপের এডমিন এ লগইন করুন।";

        Call<SMSSendResponse> call = apiInterface.setSMSSendResponse(Common.APP_KEY, 1, Common.SMS_NUMBER, message);

        call.enqueue(new Callback<SMSSendResponse>() {
            @Override
            public void onResponse(Call<SMSSendResponse> call, Response<SMSSendResponse> response) {

                if (response.code() == 200) {
                    SMSSendResponse meg = response.body();
                    Toast.makeText(SetInformationActivity.this, "কংগ্রাচুলেশন, আপনার তথ্যটি জমা হয়েছে", Toast.LENGTH_LONG).show();
                    mDialog.dismiss();


                } else if (response.code() == 203) {
                    Toast.makeText(SetInformationActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 401) {
                    Toast.makeText(SetInformationActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                } else if (response.code() == 422) {
                    Toast.makeText(SetInformationActivity.this, "Validation Error", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SMSSendResponse> call, Throwable t) {

               // Toast.makeText(SetInformationActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });


    }

}