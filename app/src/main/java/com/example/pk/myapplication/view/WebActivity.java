package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.pk.myapplication.R;

public class WebActivity extends AppCompatActivity {
    public static final int WEBVIEW_SCALE = 200;
    com.vk.sdk.WebView webView;
    String url;
    int scale;
    TextView webActivity_toolbar_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        scale = intent.getIntExtra("scale", WEBVIEW_SCALE);

        webView = (com.vk.sdk.WebView) findViewById(R.id.webView);
        setWebClient();
        webView.loadUrl(url);
        webView.setInitialScale(scale);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        webActivity_toolbar_tv = (TextView) findViewById(R.id.webActivity_toolbar_tv);
        webView = (com.vk.sdk.WebView) findViewById(R.id.webView);
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
                    webActivity_toolbar_tv.setText(newTitle);
                } else {
                    webActivity_toolbar_tv.setText(view.getTitle());
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
