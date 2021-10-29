package bd.gov.fenipaurashava.activity_user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.LocationRequest;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.activity_admin.AdminDashboardActivity;
import bd.gov.fenipaurashava.databinding.ActivitySeeMoreApplicationBinding;
import bd.gov.fenipaurashava.departmentUser.activites.DepartmentListActivity;
import bd.gov.fenipaurashava.jonyAppModule.ApplyAndCertificateVerificationActivity;
import bd.gov.fenipaurashava.jonyAppModule.SeeMoreApplicationActivity;
import bd.gov.fenipaurashava.jonyAppModule.useractivity.ApplicationFormActivity;

public class UserDashboardActivity extends AppCompatActivity {


    FloatingActionButton aboutCV, appointmentCV, complainCV, dailyWorkCV,
            setInfoCV, stuffCV, unoMessageCV;

    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;

    LinearLayout adminCV, sonodJachaiLL;
    private final int REQUEST_CALL = 1;
    private FloatingActionButton ambulanceServiceFT, bloodBankFT;

    //for device location
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //for device location latitude and longitude
    private double latitude;
    private double longitude;
    //for auto complete search
    private String address;
    public static int REQUEST_CODE_FOR_LOCATION = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //   binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clickEvent();

        //==============for device location tracking====================
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = com.google.android.gms.location.LocationRequest.create()
                .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3000)
                .setFastestInterval(1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    //  Log.d("mylocation", String.valueOf(latitude));
                    try {
                        List<Address> addressList = new Geocoder(UserDashboardActivity.this)
                                .getFromLocation(latitude, longitude, 1);

                        address = addressList.get(0).getAddressLine(0);

                        RelativeLayout bankRL,pharmaciesRL,thanaRL,fireStationRL;



                        searchNearByPlace(latitude,longitude,findViewById(R.id.bankRL),"Banks",address);
                        searchNearByPlace(latitude,longitude,findViewById(R.id.pharmaciesRL),"Pharmacies",address);
                        searchNearByPlace(latitude,longitude,findViewById(R.id.thanaRL),"thana",address);
                        searchNearByPlace(latitude,longitude,findViewById(R.id.fireStationRL),"Fire+Service",address);
                        searchNearByPlace(latitude,longitude,findViewById(R.id.hospitalRL),"Hospitals",address);
                        searchNearByPlace(latitude,longitude,findViewById(R.id.atmBoothRL),"atm",address);

                        //  searchView.setText(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_FOR_LOCATION);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);


    }



    private void clickEvent() {


        urlAndTitlePass(findViewById(R.id.nagorikSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlBZTEpVd1RpOUJMMitNUklaUjVzeFE9PSIsInZhbHVlIjoiZTBvNGN0VENMM1VQMEpGbWN1YUMzUT09IiwibWFjIjoiYmFiOTJlNmMwYmJiOTdiNzMyMmE3YjM0MGUxMTE3MGE4ZmMxOGE3MDk2NGU4N2NiNTQzNDBjMDk2OTI3ZDJkYSJ9", "নাগরিক আবেদন");
        // urlAndTitlePass(findViewById(R.id.nagorikSonodLL),"https://www.google.com/maps/search/Restaurants/@23.4614226,91.1640978,15z/data=!3m1!4b1", "নাগরিক আবেদন");

        urlAndTitlePass(findViewById(R.id.tradLisenceLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6InR4ZTFvY0Y3ZFlrSnFFN2ZCTnFlRVE9PSIsInZhbHVlIjoiZEp1TjExXC9WQllcL0ZNUWJ5dUh1dkhBPT0iLCJtYWMiOiI1NzAwNjZhODA1NTA0MzM4NWQ4Y2M5YjY5MWUxZjI3NmU3MDdiMTc5YWNmYTNmZjliMzQ1Mjg3NDVmODg3NjJjIn0=", "ট্রেড লাইসেন্স আবেদন");


        urlAndTitlePass(findViewById(R.id.paribarikSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlQwcEhnMlQrRVdidWtZRnhHd3ROYXc9PSIsInZhbHVlIjoiTDQrelhpdGUxdzBjajNjVXRTWEdmdz09IiwibWFjIjoiYTA2NDQxM2EwODdkYjVhMGYxYjI2Y2RiOWVjYWM1OTEzZDk1NjQ2Nzc1ZjU4NDYwM2Q1NjljZWViNDllODA2YyJ9", "পারিবারিক সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.owarisSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IjNrWGQwR3gyV1BcL1AzQ21nYjN0WEF3PT0iLCJ2YWx1ZSI6ImQyTDg5MFRLWmlBZHVjNjBadEo4Y1E9PSIsIm1hYyI6ImNhNDYzMTUyZTk4MDQxYTgyODViY2M0MDVkYmFjYjE0ZjM3NTUxOGYzZDQyYjllZWY1MzIxZWJmYWRhZjExM2MifQ==", "ওয়ারিশ সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.charitrikSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IjE0N2dIelRxM1FyUGxFYkV6eURsZnc9PSIsInZhbHVlIjoiWDRrR0dBUHB1dCs0czlIQ1hGSzB2QT09IiwibWFjIjoiNWJlMjYyMGQ0ZDBkNDY0YTIxMmIzZTRjMjgzNmVmMzBkZjcwMjA3ZGE3YjJiZTEyNzIxYjY2NmFmNmFmNWI3NyJ9", "চারিত্রিক সনদের আবেদন");

        urlAndTitlePass(findViewById(R.id.obibahitoLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlNaTFF1SXR2eHBhVEkyaU5TUGpLN0E9PSIsInZhbHVlIjoiS1lLc1JlZzFkc3hobmpleStkRXdlUT09IiwibWFjIjoiY2Q5MzMxMGZiYmMxOGRhMGFlZDMzMThjYWU1MzA0ZGZjZjRkNmE0YWIyOTg5Yjg0NDMxNzYzOWJjZDNjMTI3NiJ9", "অবিবাহিত সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.bibahitoLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IllybWc2akRmYUVuNkU3UzFkVnkzeWc9PSIsInZhbHVlIjoiZ2xzTkxTMXlnWXBxcnFSbGs3U1MzZz09IiwibWFjIjoiNzU3M2RkODc0NzI3MmIyODc4N2IzNTEwZGE4MmQ4ZmY3NDgwMTFlYzc0MzJkZjhkNDU1MWRmNWE2MGY0ZmVlZCJ9", "বিবাহিত সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.protibondhiLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImFHb0lnZVdqZnF0Ris4bHpRdUZBS0E9PSIsInZhbHVlIjoiSzM5NFwvOVFORlZvc1dQNzFPT2YrXC9BPT0iLCJtYWMiOiIwOTcyNzVlZjdhNTMyMGRiMDIwN2ViZjZhMjI2NTI0ZjBiODUwMzNiNGVkNjU5MzVhM2RlYmY3Mjk3NmI5YTYzIn0=", "প্রতিবন্ধী সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.punobibahNahowaLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6Ing0QlwvaDlabjBaaThIb2VtUHpLQkZRPT0iLCJ2YWx1ZSI6IkVrbmhJVFZockVVRm5kRndBTUd5amc9PSIsIm1hYyI6ImNmY2NlYzY4NTYxMTFkNzg4ZmM4MzI0OTBhMDgxYWQ1MmRiOWUxMDM1ZjVhNjYyYTU2ODBmNWFmNzg4MTJkM2IifQ==", "পুনঃ বিবাহ না হওয়া সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.voterIdChangeLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlhLSzdRRGtuTHNEN0QwRFYwRTM0M0E9PSIsInZhbHVlIjoiRkFUUnlTcFlmd0d4QWtHckZPdzU3QT09IiwibWFjIjoiZjE5YWU5Y2ZlZDczNDc4NGEzZjgxNjRkMGRhMTMyZDAyYWQ0MDdjOWMxMjZkYTAwNzAzY2VkNmI1ZjFlNGY3MiJ9", "ভোটার আইডি স্থানান্তর সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.nodiVangonLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IkFnVVNUMFwvMFdVbHRRd3V6Wk85aVh3PT0iLCJ2YWx1ZSI6InZuVEVnN3N3TDROa2xGM05EWk4yOUE9PSIsIm1hYyI6ImQ5YWE1MDc4OTljNDU2ZDAxMmI0OWMxMTQ3OTI1N2Q2MGJhZGEzMmE5YmQ0NjRiNTQ2OWQxYmRkYTUwODA3M2QifQ==", "নদী ভাঙন সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.onApotiPotroLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImZnbzluRkF1Mm9QVzlRT1d6aG1NaWc9PSIsInZhbHVlIjoiV1c2V1RTaGJXSnB2ZE40aUYzR1FUQT09IiwibWFjIjoiOGNkMDJmZTg0NGYwMzc5NWJiNWU0NGEzZGQxOThkZGQyYjBmMWEwMWEyMWNkYjA3N2Q4NmI0YzYyN2Y2MzMxZCJ9", "অনাপত্তি পত্র আবেদন");

        urlAndTitlePass(findViewById(R.id.akoyNamePotayonLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImVrQWg2U2xrSVI4TTgxdEN3c1hwVGc9PSIsInZhbHVlIjoiT2hRK055bzJ2MUcreDg1NVZUaGtCUT09IiwibWFjIjoiZGI1MDljNzAwOTJiMGFmODc0NGRlMWJiZjg2ZDdmYzQwMGUxMDgzYTc3Njk3MzE2NTgzYzllZjBlMzM5MGE4NyJ9", "একই নামের প্রত্যয়ন আবেদন");

        urlAndTitlePass(findViewById(R.id.unimotiProtroLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImVrQWg2U2xrSVI4TTgxdEN3c1hwVGc9PSIsInZhbHVlIjoiT2hRK055bzJ2MUcreDg1NVZUaGtCUT09IiwibWFjIjoiZGI1MDljNzAwOTJiMGFmODc0NGRlMWJiZjg2ZDdmYzQwMGUxMDgzYTc3Njk3MzE2NTgzYzllZjBlMzM5MGE4NyJ9", "অনুমতি পত্রের আবেদন");

        urlAndTitlePass(findViewById(R.id.mitoSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6Im5yZVZBd0dPWVwvdm5FKys2WVV6ZVp3PT0iLCJ2YWx1ZSI6ImFFd3BqNTlOcFBoNVo2VVB3M2JuRGc9PSIsIm1hYyI6ImIzYTM3MDM5N2RlNzVlNTNjMjkyYjE3OTlmMjMxMzc4YmZjNTQ4ZDFhZDc2NWU1ZDcxMWJkMTE0MGY4MTNjMTEifQ==", "মৃত্যু সনদের আবেদন");


        urlAndTitlePass(findViewById(R.id.basikAyerPotayorLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IklMeW1aTlptSnBGbGFDcjhUZU4rREE9PSIsInZhbHVlIjoiN0QySU1jZnI5SWNnN0hhbTZjRW1wZz09IiwibWFjIjoiNGRiNWUzYTkyZjQ2N2M3OWZiNjM3NTVjYTAxODUzMDgxYjg1YjJmYTJhODliMzVlNzQ3ZjBjYjgwN2QyNWU1YiJ9", "বার্ষিক আয়ের প্রত্যয়ন আবেদন");


        urlAndTitlePass(findViewById(R.id.vumiHidSonodLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlZmdUJRaVhncDkrVWVxWGF1Q2lYeXc9PSIsInZhbHVlIjoiNSthQUFPR0xkd2xZeGJLR2RpR3NMUT09IiwibWFjIjoiMDA3MjNmODEwYWUyNjlkOTA3YjY2MDQ3ZDg5YTRkNTQzZDc1ZGM5YzRmMWZkNzcxNjM5ZGYyOWYwZDRhYmU0NyJ9", "ভূমিহিন সনদের আবেদন");

        urlAndTitlePass(findViewById(R.id.sonatondhomoLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6InJtWU8rQ01FNXE0aWQ3SFdRdkwwZEE9PSIsInZhbHVlIjoiam1UWnRcLzQ1c0NmSWlnbXYyTzdKRFE9PSIsIm1hYyI6IjY3NTlhYmI4Njc2OWUzOTYwNjM2YzI5ZmFjNzZmMTNiMjNkMzBmNjM3YmQzYzZjMmI5ZDQ1NWJmMWY5ZGZjNzYifQ==", "সনাতন ধর্ম অবলম্বী সনদের আবেদন");

        urlAndTitlePass(findViewById(R.id.potayonProtroLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6Ik1MWXlqZFdVaHJyWVFJT3h1dGpTWkE9PSIsInZhbHVlIjoiM3BUVmxPVTZIdUlxR1wvNjc3VE1Sanc9PSIsIm1hYyI6IjdlOGVkY2EyYmNhODUzNzg3MTUxMzA1NDlhZGI5ZDU2MjAxNzA3NWUzMzE4MzYxMTI0Y2YwN2Q4ZjE4NjYzMDAifQ==", "প্রত্যয়ন পত্র আবেদন");

        urlAndTitlePass(findViewById(R.id.holdingLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImJPS0hMMVo3TEFiUDJFYXF0NFVURHc9PSIsInZhbHVlIjoiZEJ4Y2luYzR6MHZ6ajZuTUxuZDB0QT09IiwibWFjIjoiYmE3N2FjZjFhMDMyNzUyYjdiYWVhY2E0N2RjMGJiYWVhYThlODgxNWUyOTBhZTE5Njc5ZjQ3YzMwNTdkOTc1ZCJ9", "নতুন হোল্ডিং আবেদন");

        urlAndTitlePass(findViewById(R.id.holdingNamejariLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6Im9JbUMyZlA0dnRMSVwvdzBmY3lMUnBRPT0iLCJ2YWx1ZSI6InczVFJcL1wvOTZ6VEkyRFwvc0M5dDlpcGc9PSIsIm1hYyI6IjczMzYzMWRhZWIxNDhlNDllNDJhODc3OGZkMjA1NWYxMmMyZTJkNTkwMWE1NTI1ZmNkNmU3NDkzNDRmM2EyNjUifQ==", "হোল্ডিং নামজারির আবেদন");

        urlAndTitlePass(findViewById(R.id.vumiBeboharBebostaponaLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IlRiRmlyVGs0ejJLanJJTStJcklQcHc9PSIsInZhbHVlIjoiT1Q0eVNzRGRuSzJ1MzNZS2tkYUhsZz09IiwibWFjIjoiYWZhYzI2ZjNmMzU2ODMyNGYzNzhlZDM0OWI3ODhmYTc0ZDA0M2RlOWRkNzU5N2UwYTA4OTkyMDE0MjIxNzFkOCJ9", "ভূমি ব্যবহার ছাড়পত্রের আবেদন");

        urlAndTitlePass(findViewById(R.id.posaPronilicenseLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6IkxMVFhoclpuWHFSbkowNmZueFVJOVE9PSIsInZhbHVlIjoiSFlQZXp0NEpwc2IyNUpkTXpoWVFldz09IiwibWFjIjoiYTU4Yzg3Mzk4NWNiNTFjZTViZTY5YjhhMzE0ZWM0NDgyOTdlMzNiZDIzMGVkMzU0M2U3MzI2MzczZGZmZmM1YiJ9", "পোষা প্রাণীর লাইসেন্সের আবেদন");

        urlAndTitlePass(findViewById(R.id.roadKhonoLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6ImhqWGdoZ2ZXSTVtOTdPWGlrMXY4elE9PSIsInZhbHVlIjoiR1QwUWtrSlJmMEhybGhuTXprN0xQZz09IiwibWFjIjoiNTFhZjQzODBjNGRhNGQ5MWUwMDdhN2FkYWJjNjhhOTliN2FhY2EzMTZjM2E3MTY0OGRmYzcwY2E1ZDUzYjRlNSJ9", "রাস্তা খননের অনুমতির আবেদন");

        urlAndTitlePass(findViewById(R.id.imaronLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6InZIdTlLc0R6bm5cL2g1WlZ5WlwvZzlHQT09IiwidmFsdWUiOiJRNzBcLzg5WGJPem1qMEJTdFwvVm1qdEE9PSIsIm1hYyI6IjMyYzBlODdhODVkZjIwNzFjM2U1ZTgxNWQ3MmEzMTk0YmMwZGUwZmYxOWY2ZGFhM2VkYTljNGQwOTczNGQ4MGIifQ==", "ইমারত নির্মাণ/সীমানা দেয়াল/বিবিধ নির্মাণ/পূণঃ নির্মাণ এবং পুকুর খনন/ভরাট/পাহাড় কর্তণ আবেদন পত্র");

        urlAndTitlePass(findViewById(R.id.premisesLL), "http://fenipaurashava.gov.bd/application/eyJpdiI6InBVeitKU1NXN1ZKWU11ZjdWVUF4U2c9PSIsInZhbHVlIjoiV3ZUN01aSzIwb0wwOWgxWk41SnJLdz09IiwibWFjIjoiNjU2YzU3MGNhZWFjMDQzOGQ3OWQ4ODExN2Q0ZjUwZDNmMjFjN2RkM2I0YmJjN2Q2ZDVmNWQwZjNhYjNhNWVmNyJ9", "প্রিমিসেস লাইসেন্স আবেদন");

    }

    public void urlAndTitlePass(LinearLayout layout, String url, String title) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }

//    private double latitude;
//    private double longitude;

    public void searchNearByPlace(double latitude,double longitude, RelativeLayout relativeLayout,String nearByPlaceName,String myLocation) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDashboardActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", "https://www.google.com/maps/search/"+nearByPlaceName+"/@"+latitude+","+longitude+",15z/data=!3m1!4b1");
                intent.putExtra("title", myLocation);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.admin_menu:
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                boolean login = sharedpreferences.getBoolean("login", false);
                boolean signIn = sharedpreferences.getBoolean("signIn", false);

                if (login) {
                    startActivity(new Intent(UserDashboardActivity.this, AdminDashboardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(UserDashboardActivity.this, AdminLoginActivity.class));
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        aboutCV = findViewById(R.id.aboutCV);
        appointmentCV = findViewById(R.id.appointmentCV);
        complainCV = findViewById(R.id.complainCV);
        dailyWorkCV = findViewById(R.id.dailtWorkCV);
        setInfoCV = findViewById(R.id.setInfoCV);
        stuffCV = findViewById(R.id.stuffCV);
        unoMessageCV = findViewById(R.id.unoMessageCV);
        adminCV = findViewById(R.id.adminCV);
        sonodJachaiLL = findViewById(R.id.sonodJachaiLL);

        adminCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                boolean login = sharedpreferences.getBoolean("login", false);
                boolean signIn = sharedpreferences.getBoolean("signIn", false);

                if (login) {
                    startActivity(new Intent(UserDashboardActivity.this, AdminDashboardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(UserDashboardActivity.this, AdminLoginActivity.class));
                }

            }
        });


        aboutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AboutJelaActivity.class));
            }
        });

        appointmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AppointmentOfMayorActivity.class));
            }
        });

        complainCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, ComplainActivity.class));
            }
        });

        dailyWorkCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, WorkScheduleUserActivity.class));
            }
        });


        setInfoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, SetInformationActivity.class));
            }
        });

        stuffCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, DepartmentListActivity.class));
            }
        });

        unoMessageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, MayorMessageActivity.class));
            }
        });

        sonodJachaiLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboardActivity.this, ApplyAndCertificateVerificationActivity.class));

            }
        });
    }


    private void callButton(String mobileNumber) {
        if (mobileNumber.length() > 0) {
            if (ContextCompat.checkSelfPermission(UserDashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) UserDashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dail = "tel:" + mobileNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
            }
        }
    }

    public void one_zeri_nine_zero(View view) {
        //callButton("1090");
        callDialogOpen();
    }

    public void one_six_zero(View view) {
        callButton("106");

    }

    public void one_zero_nine(View view) {
        callButton("109");
    }

    public void nine_nine_nine(View view) {
        callButton("999");
    }

//    public void three_three_three(View view) {
//        callButton("333");
//    }

    public void service(View view) {

        startActivity(new Intent(UserDashboardActivity.this, SeeMoreApplicationActivity.class));
//        String url = "http://fenipaurashava.gov.bd/";
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    public void one_zero_nine_eight(View view) {
        callButton("1098");
    }


    private void callDialogOpen() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        CardView saveCV, cancelCV;
        View view = LayoutInflater.from(UserDashboardActivity.this).inflate(R.layout.ambulance_and_blood_bank_call_dialog, null);
        builder.setView(view);

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

        bloodBankFT = view.findViewById(R.id.bloodBankFT);
        ambulanceServiceFT = view.findViewById(R.id.ambulanceServiceFT);
        cancelCV = view.findViewById(R.id.cancelCV);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        bloodBankFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton("01842307080");
            }
        });

        ambulanceServiceFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton("01842307080");
            }
        });


        cancelCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

}