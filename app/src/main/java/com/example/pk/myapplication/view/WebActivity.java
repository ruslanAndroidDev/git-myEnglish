package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.pk.myapplication.R;

public class WebActivity extends AppCompatActivity {
    public static final int WEBVIEW_SCALE = 200;
    String url;
    int scale;
    WebView webView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        webView.loadUrl(url);
        webView.setInitialScale(scale);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWebClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                String title = view.getTitle();
                int posit;
                if ((posit = title.indexOf("-")) != -1) {
                    String newTitle = title.substring(0, posit - 1);
                    toolbar.setTitle(newTitle);
                } else {
                    toolbar.setTitle(view.getTitle());
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
