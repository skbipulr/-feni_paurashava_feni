package bd.gov.fenipaurashava.activity_user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView stuffImageIV;
    TextView nameTV, phoneNumberTV, designationTV, emailTV, bcsBatchTV, dateTV;

    Datum employee = null;
    private ImageView facebookIV, twitterIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initField();

        Intent i = getIntent();
        employee = (Datum) i.getSerializableExtra("employee");

        Picasso.get().load("http://fenimayor.digiins.gov.bd/district_app/public/employee/" + employee.getPicture())
                .into(stuffImageIV);
        nameTV.setText(employee.getName());
        phoneNumberTV.setText(employee.getMobileNo());
        designationTV.setText(employee.getDesignation());
        emailTV.setText(employee.getEmail());
        bcsBatchTV.setText(employee.getBcsBatch());
        dateTV.setText((String) employee.getJoiningDate());

    }

    private void initField() {
        stuffImageIV = findViewById(R.id.stuffImageIV);
        nameTV = findViewById(R.id.profile_nameTV);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
        designationTV = findViewById(R.id.designationTV);
        emailTV = findViewById(R.id.emailTV);
        bcsBatchTV = findViewById(R.id.bcsbatchTV);
        dateTV = findViewById(R.id.dateTV);
    }

    public void backBtn(View view) {
        onBackPressed();
    }

    public void clickWhatsapp(View view) {
       /* Uri uri = Uri.parse("01304568660");
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        intent.setPackage("com.whatsapp");
        startActivity(intent);*/

       /* Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);*/

        openTab("https://chat.whatsapp.com/");

    }

    public void openTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(ProfileActivity.this, Uri.parse(url));
    }

    public void facebookClick(View view) {

        String facebook = (String) employee.getFbId();

        if (facebook.isEmpty()) {
            Toast.makeText(this, "This link is not available.", Toast.LENGTH_SHORT).show();

        } else {
            openTab((String) employee.getFbId());

        }

    }

    public void twitterClick(View view) {
        String twitter = (String) employee.getTweeterId();

        if (twitter.isEmpty()) {
            Toast.makeText(this, "This link is not available.", Toast.LENGTH_SHORT).show();
        } else {
            openTab((String) employee.getTweeterId());
        }
    }
}