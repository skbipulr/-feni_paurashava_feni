package bd.gov.fenipaurashava.jonyAppModule;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import bd.gov.fenipaurashava.R;
import bd.gov.fenipaurashava.databinding.ActivitySeeMoreApplicationBinding;
import bd.gov.fenipaurashava.jonyAppModule.useractivity.ApplicationFormActivity;

public class SeeMoreApplicationActivity extends AppCompatActivity {

    ActivitySeeMoreApplicationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_application);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_more_application);
        statusBarColorChange();

        clickEvent();
    }

    private void clickEvent() {

        binding.nagorikSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeMoreApplicationActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", "http://fenipaurashava.gov.bd/application/eyJpdiI6IlBZTEpVd1RpOUJMMitNUklaUjVzeFE9PSIsInZhbHVlIjoiZTBvNGN0VENMM1VQMEpGbWN1YUMzUT09IiwibWFjIjoiYmFiOTJlNmMwYmJiOTdiNzMyMmE3YjM0MGUxMTE3MGE4ZmMxOGE3MDk2NGU4N2NiNTQzNDBjMDk2OTI3ZDJkYSJ9");
                intent.putExtra("title", "নাগরিক আবেদন");
                startActivity(intent);
            }
        });
        binding.tradLisenceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeMoreApplicationActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", "http://fenipaurashava.gov.bd/application/eyJpdiI6InR4ZTFvY0Y3ZFlrSnFFN2ZCTnFlRVE9PSIsInZhbHVlIjoiZEp1TjExXC9WQllcL0ZNUWJ5dUh1dkhBPT0iLCJtYWMiOiI1NzAwNjZhODA1NTA0MzM4NWQ4Y2M5YjY5MWUxZjI3NmU3MDdiMTc5YWNmYTNmZjliMzQ1Mjg3NDVmODg3NjJjIn0=");
                intent.putExtra("title", "ট্রেড লাইসেন্স আবেদন");
                startActivity(intent);
            }
        });
        binding.paribarikSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeMoreApplicationActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", "http://fenipaurashava.gov.bd/application/eyJpdiI6IlQwcEhnMlQrRVdidWtZRnhHd3ROYXc9PSIsInZhbHVlIjoiTDQrelhpdGUxdzBjajNjVXRTWEdmdz09IiwibWFjIjoiYTA2NDQxM2EwODdkYjVhMGYxYjI2Y2RiOWVjYWM1OTEzZDk1NjQ2Nzc1ZjU4NDYwM2Q1NjljZWViNDllODA2YyJ9");
                intent.putExtra("title", "পারিবারিক সনদের আবেদন");
                startActivity(intent);
            }
        });
        binding.owarisSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeeMoreApplicationActivity.this, ApplicationFormActivity.class);
                intent.putExtra("url", "http://fenipaurashava.gov.bd/application/eyJpdiI6IjNrWGQwR3gyV1BcL1AzQ21nYjN0WEF3PT0iLCJ2YWx1ZSI6ImQyTDg5MFRLWmlBZHVjNjBadEo4Y1E9PSIsIm1hYyI6ImNhNDYzMTUyZTk4MDQxYTgyODViY2M0MDVkYmFjYjE0ZjM3NTUxOGYzZDQyYjllZWY1MzIxZWJmYWRhZjExM2MifQ==");
                intent.putExtra("title", "ওয়ারিশ সনদের আবেদন");
                startActivity(intent);
            }
        });


        binding.charitrikSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IjE0N2dIelRxM1FyUGxFYkV6eURsZnc9PSIsInZhbHVlIjoiWDRrR0dBUHB1dCs0czlIQ1hGSzB2QT09IiwibWFjIjoiNWJlMjYyMGQ0ZDBkNDY0YTIxMmIzZTRjMjgzNmVmMzBkZjcwMjA3ZGE3YjJiZTEyNzIxYjY2NmFmNmFmNWI3NyJ9", "চারিত্রিক সনদের আবেদন");
            }
        });
        binding.obibahitoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IlNaTFF1SXR2eHBhVEkyaU5TUGpLN0E9PSIsInZhbHVlIjoiS1lLc1JlZzFkc3hobmpleStkRXdlUT09IiwibWFjIjoiY2Q5MzMxMGZiYmMxOGRhMGFlZDMzMThjYWU1MzA0ZGZjZjRkNmE0YWIyOTg5Yjg0NDMxNzYzOWJjZDNjMTI3NiJ9", "অবিবাহিত সনদের আবেদন");
            }
        });
        binding.bibahitoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IllybWc2akRmYUVuNkU3UzFkVnkzeWc9PSIsInZhbHVlIjoiZ2xzTkxTMXlnWXBxcnFSbGs3U1MzZz09IiwibWFjIjoiNzU3M2RkODc0NzI3MmIyODc4N2IzNTEwZGE4MmQ4ZmY3NDgwMTFlYzc0MzJkZjhkNDU1MWRmNWE2MGY0ZmVlZCJ9", "বিবাহিত সনদের আবেদন");
            }
        });
        binding.protibondhiLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImFHb0lnZVdqZnF0Ris4bHpRdUZBS0E9PSIsInZhbHVlIjoiSzM5NFwvOVFORlZvc1dQNzFPT2YrXC9BPT0iLCJtYWMiOiIwOTcyNzVlZjdhNTMyMGRiMDIwN2ViZjZhMjI2NTI0ZjBiODUwMzNiNGVkNjU5MzVhM2RlYmY3Mjk3NmI5YTYzIn0=", "প্রতিবন্ধী সনদের আবেদন");
            }
        });
        binding.punobibahNahowaLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6Ing0QlwvaDlabjBaaThIb2VtUHpLQkZRPT0iLCJ2YWx1ZSI6IkVrbmhJVFZockVVRm5kRndBTUd5amc9PSIsIm1hYyI6ImNmY2NlYzY4NTYxMTFkNzg4ZmM4MzI0OTBhMDgxYWQ1MmRiOWUxMDM1ZjVhNjYyYTU2ODBmNWFmNzg4MTJkM2IifQ==", "পুনঃ বিবাহ না হওয়া সনদের আবেদন");
            }
        });

        //-------------
        binding.voterIdChangeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IlhLSzdRRGtuTHNEN0QwRFYwRTM0M0E9PSIsInZhbHVlIjoiRkFUUnlTcFlmd0d4QWtHckZPdzU3QT09IiwibWFjIjoiZjE5YWU5Y2ZlZDczNDc4NGEzZjgxNjRkMGRhMTMyZDAyYWQ0MDdjOWMxMjZkYTAwNzAzY2VkNmI1ZjFlNGY3MiJ9", "ভোটার আইডি স্থানান্তর সনদের আবেদন");
            }
        });

        binding.nodiVangonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IkFnVVNUMFwvMFdVbHRRd3V6Wk85aVh3PT0iLCJ2YWx1ZSI6InZuVEVnN3N3TDROa2xGM05EWk4yOUE9PSIsIm1hYyI6ImQ5YWE1MDc4OTljNDU2ZDAxMmI0OWMxMTQ3OTI1N2Q2MGJhZGEzMmE5YmQ0NjRiNTQ2OWQxYmRkYTUwODA3M2QifQ==", "নদী ভাঙন সনদের আবেদন");
            }
        });

        binding.onApotiPotroLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImZnbzluRkF1Mm9QVzlRT1d6aG1NaWc9PSIsInZhbHVlIjoiV1c2V1RTaGJXSnB2ZE40aUYzR1FUQT09IiwibWFjIjoiOGNkMDJmZTg0NGYwMzc5NWJiNWU0NGEzZGQxOThkZGQyYjBmMWEwMWEyMWNkYjA3N2Q4NmI0YzYyN2Y2MzMxZCJ9", "অনাপত্তি পত্র আবেদন");
            }
        });

        binding.akoyNamePotayonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImVrQWg2U2xrSVI4TTgxdEN3c1hwVGc9PSIsInZhbHVlIjoiT2hRK055bzJ2MUcreDg1NVZUaGtCUT09IiwibWFjIjoiZGI1MDljNzAwOTJiMGFmODc0NGRlMWJiZjg2ZDdmYzQwMGUxMDgzYTc3Njk3MzE2NTgzYzllZjBlMzM5MGE4NyJ9", "একই নামের প্রত্যয়ন আবেদন");
            }
        });

        binding.unimotiProtroLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImVrQWg2U2xrSVI4TTgxdEN3c1hwVGc9PSIsInZhbHVlIjoiT2hRK055bzJ2MUcreDg1NVZUaGtCUT09IiwibWFjIjoiZGI1MDljNzAwOTJiMGFmODc0NGRlMWJiZjg2ZDdmYzQwMGUxMDgzYTc3Njk3MzE2NTgzYzllZjBlMzM5MGE4NyJ9", "অনুমতি পত্রের আবেদন");
            }
        });

        binding.mitoSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6Im5yZVZBd0dPWVwvdm5FKys2WVV6ZVp3PT0iLCJ2YWx1ZSI6ImFFd3BqNTlOcFBoNVo2VVB3M2JuRGc9PSIsIm1hYyI6ImIzYTM3MDM5N2RlNzVlNTNjMjkyYjE3OTlmMjMxMzc4YmZjNTQ4ZDFhZDc2NWU1ZDcxMWJkMTE0MGY4MTNjMTEifQ==", "মৃত্যু সনদের আবেদন");
            }
        });

        binding.basikAyerPotayorLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IklMeW1aTlptSnBGbGFDcjhUZU4rREE9PSIsInZhbHVlIjoiN0QySU1jZnI5SWNnN0hhbTZjRW1wZz09IiwibWFjIjoiNGRiNWUzYTkyZjQ2N2M3OWZiNjM3NTVjYTAxODUzMDgxYjg1YjJmYTJhODliMzVlNzQ3ZjBjYjgwN2QyNWU1YiJ9", "বার্ষিক আয়ের প্রত্যয়ন আবেদন");
            }
        });

        binding.vumiHidSonodLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IlZmdUJRaVhncDkrVWVxWGF1Q2lYeXc9PSIsInZhbHVlIjoiNSthQUFPR0xkd2xZeGJLR2RpR3NMUT09IiwibWFjIjoiMDA3MjNmODEwYWUyNjlkOTA3YjY2MDQ3ZDg5YTRkNTQzZDc1ZGM5YzRmMWZkNzcxNjM5ZGYyOWYwZDRhYmU0NyJ9", "ভূমিহিন সনদের আবেদন");
            }
        });

        binding.sonatondhomoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6InJtWU8rQ01FNXE0aWQ3SFdRdkwwZEE9PSIsInZhbHVlIjoiam1UWnRcLzQ1c0NmSWlnbXYyTzdKRFE9PSIsIm1hYyI6IjY3NTlhYmI4Njc2OWUzOTYwNjM2YzI5ZmFjNzZmMTNiMjNkMzBmNjM3YmQzYzZjMmI5ZDQ1NWJmMWY5ZGZjNzYifQ==", "সনাতন ধর্ম অবলম্বী সনদের আবেদন");
            }
        });

        binding.potayonProtroLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6Ik1MWXlqZFdVaHJyWVFJT3h1dGpTWkE9PSIsInZhbHVlIjoiM3BUVmxPVTZIdUlxR1wvNjc3VE1Sanc9PSIsIm1hYyI6IjdlOGVkY2EyYmNhODUzNzg3MTUxMzA1NDlhZGI5ZDU2MjAxNzA3NWUzMzE4MzYxMTI0Y2YwN2Q4ZjE4NjYzMDAifQ==", "প্রত্যয়ন পত্র আবেদন");
            }
        });

        binding.holdingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImJPS0hMMVo3TEFiUDJFYXF0NFVURHc9PSIsInZhbHVlIjoiZEJ4Y2luYzR6MHZ6ajZuTUxuZDB0QT09IiwibWFjIjoiYmE3N2FjZjFhMDMyNzUyYjdiYWVhY2E0N2RjMGJiYWVhYThlODgxNWUyOTBhZTE5Njc5ZjQ3YzMwNTdkOTc1ZCJ9","নতুন হোল্ডিং আবেদন");
            }
        });

        binding.holdingNamejariLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6Im9JbUMyZlA0dnRMSVwvdzBmY3lMUnBRPT0iLCJ2YWx1ZSI6InczVFJcL1wvOTZ6VEkyRFwvc0M5dDlpcGc9PSIsIm1hYyI6IjczMzYzMWRhZWIxNDhlNDllNDJhODc3OGZkMjA1NWYxMmMyZTJkNTkwMWE1NTI1ZmNkNmU3NDkzNDRmM2EyNjUifQ==","হোল্ডিং নামজারির আবেদন");
            }
        });


        binding.vumiBeboharBebostaponaLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6IlRiRmlyVGs0ejJLanJJTStJcklQcHc9PSIsInZhbHVlIjoiT1Q0eVNzRGRuSzJ1MzNZS2tkYUhsZz09IiwibWFjIjoiYWZhYzI2ZjNmMzU2ODMyNGYzNzhlZDM0OWI3ODhmYTc0ZDA0M2RlOWRkNzU5N2UwYTA4OTkyMDE0MjIxNzFkOCJ9","ভূমি ব্যবহার ছাড়পত্রের আবেদন");
            }
        });

        binding.posaPronilicenseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("eyJpdiI6IkxMVFhoclpuWHFSbkowNmZueFVJOVE9PSIsInZhbHVlIjoiSFlQZXp0NEpwc2IyNUpkTXpoWVFldz09IiwibWFjIjoiYTU4Yzg3Mzk4NWNiNTFjZTViZTY5YjhhMzE0ZWM0NDgyOTdlMzNiZDIzMGVkMzU0M2U3MzI2MzczZGZmZmM1YiJ9","পোষা প্রাণীর লাইসেন্সের আবেদন");
            }
        });

        binding.roadKhonoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6ImhqWGdoZ2ZXSTVtOTdPWGlrMXY4elE9PSIsInZhbHVlIjoiR1QwUWtrSlJmMEhybGhuTXprN0xQZz09IiwibWFjIjoiNTFhZjQzODBjNGRhNGQ5MWUwMDdhN2FkYWJjNjhhOTliN2FhY2EzMTZjM2E3MTY0OGRmYzcwY2E1ZDUzYjRlNSJ9","রাস্তা খননের অনুমতির আবেদন");
            }
        });

        binding.imaronLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6InZIdTlLc0R6bm5cL2g1WlZ5WlwvZzlHQT09IiwidmFsdWUiOiJRNzBcLzg5WGJPem1qMEJTdFwvVm1qdEE9PSIsIm1hYyI6IjMyYzBlODdhODVkZjIwNzFjM2U1ZTgxNWQ3MmEzMTk0YmMwZGUwZmYxOWY2ZGFhM2VkYTljNGQwOTczNGQ4MGIifQ==","ইমারত নির্মাণ/সীমানা দেয়াল/বিবিধ নির্মাণ/পূণঃ নির্মাণ এবং পুকুর খনন/ভরাট/পাহাড় কর্তণ আবেদন পত্র");
            }
        });

        binding.premisesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlAndTitlePass("http://fenipaurashava.gov.bd/application/eyJpdiI6InBVeitKU1NXN1ZKWU11ZjdWVUF4U2c9PSIsInZhbHVlIjoiV3ZUN01aSzIwb0wwOWgxWk41SnJLdz09IiwibWFjIjoiNjU2YzU3MGNhZWFjMDQzOGQ3OWQ4ODExN2Q0ZjUwZDNmMjFjN2RkM2I0YmJjN2Q2ZDVmNWQwZjNhYjNhNWVmNyJ9","প্রিমিসেস লাইসেন্স আবেদন");
            }
        });
    }

    private void statusBarColorChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.sattusbar_color));
        }
    }

    public void backBtn(View view) {
        onBackPressed();
        finish();
    }


    public void urlAndTitlePass(String url, String title) {
        Intent intent = new Intent(SeeMoreApplicationActivity.this, ApplicationFormActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

}