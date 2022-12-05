package com.digitalInnovation.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PdfReaderActivity extends AppCompatActivity {
    WebView pdfView;
    String URL ;
    String fileName;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        Intent intent = getIntent();
        fileName = intent.getStringExtra("studentResume");
        Log.e( "onCreate: ",fileName );

        URL ="http://ims.ditests.com/uploads/"+fileName;

        pdfView = findViewById(R.id.pdfView);

        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.getSettings().setDisplayZoomControls(false);
        pdfView.getSettings().setBuiltInZoomControls(true);
        pdfView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
               // pdfView.loadUrl("javascript:(function(){" + "document.querySelector('[role=\"toolbar\"]').remove();})()");
            }
        });

        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url="+URL);


    }
}