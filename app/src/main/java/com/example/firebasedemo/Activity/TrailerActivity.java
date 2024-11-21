package com.example.firebasedemo.Activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasedemo.R;

public class TrailerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Activate JavaScript

        // URL YouTube
        // String videoKey = intent.intent.getStringExtra("key");
        String videoKey = "QJ8E9R70csY";
        String videoUrl = "https://www.youtube.com/embed/" + videoKey;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(videoUrl);
    }
}
