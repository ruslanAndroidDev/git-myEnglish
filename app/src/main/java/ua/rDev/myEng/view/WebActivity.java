package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ua.rDev.myEng.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class WebActivity extends AppCompatActivity {
    public static final int WEBVIEW_SCALE = 200;
    String url;
    int scale;
    WebView webView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        String color = preferences.getString("color", "1");
        if (color.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme2);
        }
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();

        toolbar = (Toolbar) findViewById(R.id.webActivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        url = intent.getStringExtra("url");
        scale = intent.getIntExtra("scale", WEBVIEW_SCALE);

        webView = (WebView) findViewById(R.id.webView);
        setWebClient();
        webView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 10MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

        if (!isNetworkAvailable()) { // loading offline
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.loadUrl(url);
        webView.setInitialScale(scale);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        webView = (WebView) findViewById(R.id.webView);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWebClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                toolbar.setTitle(view.getTitle());
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
        });
    }

    private void close() {
        Intent intent;
        if (url.equals(getString(R.string.verbs_url))) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, TenseActivity.class);
        }
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            close();
        }
    }
}
