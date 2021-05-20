package bd.gov.fenipaurashava.activity_user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.interfaces.ApiInterface;
import bd.gov.fenipaurashava.modelForPublicHearingDeletePOST.SetPublicHearingDeleteResponse;
import bd.gov.fenipaurashava.modelPublicHeadingGET.Datum;
import bd.gov.fenipaurashava.webApi.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicHearingEditorActivity extends AppCompatActivity {


    private EditText nameET,mobileNoET,addressET,titleET,descriptionET;



    private Datum dataClass = null;
    private String name,mobileNo,address,title,description;
    private int id;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_hearing_public);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nameET = findViewById(R.id.nameET);
        mobileNoET = findViewById(R.id.mobileET);
        addressET = findViewById(R.id.addressET);
        titleET = findViewById(R.id.titleET);
        descriptionET = findViewById(R.id.descriptionET);

//        Intent intent = getIntent();
//        id = intent.getIntExtra("id", 0);
//        name = intent.getStringExtra("name");

        Intent i = getIntent();
        dataClass = (Datum) i.getSerializableExtra("allData");

        name = dataClass.getName();
        mobileNo = dataClass.getMobileNo();
        address = dataClass.getAddress();
        id = dataClass.getId();
        title = dataClass.getSubject();
        description = dataClass.getDescription();


        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle("Edit");
            nameET.setText(name);
            mobileNoET.setText(mobileNo);
            addressET.setText(address);
            titleET.setText(title);
            descriptionET.setText(description);

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

            case R.id.menu_save:

                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(PublicHearingEditorActivity.this);
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



    private void deleteData( final int id) {

        if (id == Common.ADMIN_USER_ID){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Deleting...");
            progressDialog.show();
            readMode();
            //delete operation
            apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

            apiService.setPublicHearingDeleteResponse(Common.APP_KEY, id, "1").enqueue(new Callback<SetPublicHearingDeleteResponse>() {
                @Override
                public void onResponse(Call<SetPublicHearingDeleteResponse> call, Response<SetPublicHearingDeleteResponse> response) {

                    if (response.code() == 200) {
                        Toast.makeText(PublicHearingEditorActivity.this, "" + response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PublicHearingEditorActivity.this, "problem", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<SetPublicHearingDeleteResponse> call, Throwable t) {

                    Toast.makeText(PublicHearingEditorActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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

        nameET.setFocusableInTouchMode(false);
        mobileNoET.setFocusableInTouchMode(false);
        addressET.setFocusableInTouchMode(false);
        titleET.setFocusableInTouchMode(false);
        descriptionET.setFocusableInTouchMode(false);
        nameET.setFocusable(false);
        mobileNoET.setFocusable(false);
        addressET.setFocusable(false);
        titleET.setFocusable(false);
        descriptionET.setFocusable(false);

    }

//    private void editMode(){
//        mName.setFocusableInTouchMode(true);
//    }

}
