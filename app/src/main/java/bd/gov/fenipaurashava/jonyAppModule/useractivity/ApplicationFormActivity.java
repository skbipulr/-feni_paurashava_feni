package bd.gov.fenipaurashava.jonyAppModule.useractivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bd.gov.fenipaurashava.R;

public class ApplicationFormActivity extends AppCompatActivity {
    private WebView webview;
    private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagorik_application);

        webview = findViewById(R.id.webviewid);
        titleTV = findViewById(R.id.titleTV);
        webview.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        titleTV.setText(title);
        webview.loadUrl(url);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        statusBarColorChange();
    }

    private void statusBarColorChange() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.sattusbar_color));
        }
    }

    public void back(View view) {
        onBackPressed();
        finish();
    }
}
