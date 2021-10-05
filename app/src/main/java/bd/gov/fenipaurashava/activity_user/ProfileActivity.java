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
import bd.gov.fenipaurashava.common.Common;
import bd.gov.fenipaurashava.modelForEmployeeGET.Datum;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView stuffImageIV;
    TextView nameTV, phoneNumberTV, electionAreaTV, designationTV, emailTV, bcsBatchTV, dateTV,qualificationTV,addressTV;

    Datum employee = null;
    private ImageView facebookIV, twitterIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initField();

        Intent i = getIntent();
        employee = (Datum) i.getSerializableExtra("employee");

        Picasso.get().load(Common.IMAGE_BASE_URL + employee.getPhoto()).placeholder(R.drawable.default_icon)
                .into(stuffImageIV);
        nameTV.setText(employee.getName());
        phoneNumberTV.setText(employee.getMobile());
        designationTV.setText(employee.getDesignationName());
        emailTV.setText(String.valueOf(employee.getEmail()));
        addressTV.setText(String.valueOf(employee.getAddress()));
        qualificationTV.setText(String.valueOf(employee.getQualification()));
        dateTV.setText((String) employee.getJoinDate());
        electionAreaTV.setText((String) employee.getElectionArea());

    }

    private void initField() {
        stuffImageIV = findViewById(R.id.stuffImageIV);
        nameTV = findViewById(R.id.profile_nameTV);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
        designationTV = findViewById(R.id.designationTV);
        emailTV = findViewById(R.id.emailTV);
        bcsBatchTV = findViewById(R.id.bcsbatchTV);
        dateTV = findViewById(R.id.dateTV);
        addressTV = findViewById(R.id.addressTV);
        qualificationTV = findViewById(R.id.qualificationTV);
        electionAreaTV = findViewById(R.id.electionAreaTV);
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

//    public void facebookClick(View view) {
//
//        String facebook = (String) employee.getFbId();
//
//        if (facebook.isEmpty()) {
//            Toast.makeText(this, "This link is not available.", Toast.LENGTH_SHORT).show();
//
//        } else {
//            openTab((String) employee.getFbId());
//
//        }
//
//    }
//
//    public void twitterClick(View view) {
//        String twitter = (String) employee.getTweeterId();
//
//        if (twitter.isEmpty()) {
//            Toast.makeText(this, "This link is not available.", Toast.LENGTH_SHORT).show();
//        } else {
//            openTab((String) employee.getTweeterId());
//        }
//    }
}