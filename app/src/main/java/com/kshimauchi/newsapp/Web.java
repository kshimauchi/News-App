package com.kshimauchi.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by kshim on 7/11/2017.
 */

public class Web extends AppCompatActivity
{
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url = "http://www.google.com";

        Intent browserIntent = getIntent();
        if(browserIntent.hasExtra(MainActivity.URLKEY)) url = browserIntent.getStringExtra(MainActivity.URLKEY);
        WebView viewWeb = (WebView)findViewById(R.id.viewWeb);
        viewWeb.loadUrl(url);
    }
}
