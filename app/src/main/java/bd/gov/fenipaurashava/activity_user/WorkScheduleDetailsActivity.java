package bd.gov.fenipaurashava.activity_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.Datum;


public class WorkScheduleDetailsActivity extends AppCompatActivity {


    TextView subjectTitleTV,placeTV,scheduleDateTV,descriptionTV;

    Datum dataClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_schedule_details);

        subjectTitleTV = findViewById(R.id.subjectTitleTV);
        placeTV = findViewById(R.id.placeTV);
        scheduleDateTV = findViewById(R.id.scheduleDateTV);
        descriptionTV = findViewById(R.id.descriptionTV);

        Intent i = getIntent();
        dataClass = (Datum) i.getSerializableExtra("data");

        scheduleDateTV.setText(dataClass.getScheduleDate());
        placeTV.setText(dataClass.getPlace());
        subjectTitleTV.setText(dataClass.getSubject());
        descriptionTV.setText(dataClass.getDetails());
    }

    public void backBtn(View view) {
        onBackPressed();
    }
}