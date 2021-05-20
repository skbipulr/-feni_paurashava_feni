package bd.gov.fenipaurashava.activity_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.modelForSetInformationFetchGET.Datum;
import com.squareup.picasso.Picasso;


public class SetInformationDetailsActivity extends AppCompatActivity {

    TextView nameTV,mobileTV,addressTV,titleTV,descriptionTV;
    private ImageView imageViewIV;

    private Datum dataClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information_details);

        Intent i = getIntent();
        dataClass = (Datum) i.getSerializableExtra("data");

        fieldInit();

        nameTV.setText(dataClass.getName());
        mobileTV.setText(dataClass.getMobileNo());
        addressTV.setText(dataClass.getAddress());
        titleTV.setText(dataClass.getSubject());
        descriptionTV.setText(dataClass.getDescription());

        String uri = "http://apis.digiins.gov.bd/district_app/public/information/"+dataClass.getPicture();

        Picasso.get().load(uri).placeholder(R.drawable.placeholder).into(imageViewIV);


    }

    private void fieldInit() {
        nameTV = findViewById(R.id.nameTV);
        mobileTV = findViewById(R.id.mobileTV);
        addressTV = findViewById(R.id.addresTV);
        titleTV = findViewById(R.id.titleTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        imageViewIV = findViewById(R.id.imageViewIV);

    }

    public void backBtn(View view) {
        onBackPressed();
    }
}