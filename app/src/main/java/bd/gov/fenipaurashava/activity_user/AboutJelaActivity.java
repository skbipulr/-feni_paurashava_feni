package bd.gov.fenipaurashava.activity_user;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;

public class AboutJelaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_jela);
    }

    public void backBtn(View view) {
        onBackPressed();
    }
}