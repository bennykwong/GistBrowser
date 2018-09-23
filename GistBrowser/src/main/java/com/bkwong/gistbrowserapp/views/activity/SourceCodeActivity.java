package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.File;

public class SourceCodeActivity extends BaseActivity{

    WebView sourceCodeWebView;
    TextView fileTitle;
    File data;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getIntent().getExtras().getParcelable("fileData");
        setContentView(R.layout.source_code_webview_layout);

        fileTitle = findViewById(R.id.source_code_fileName);
        fileTitle.setText(data.getFileName());

        sourceCodeWebView = (WebView) findViewById(R.id.source_code_webview);
        sourceCodeWebView.getSettings().setJavaScriptEnabled(true);
        sourceCodeWebView.getSettings().setUseWideViewPort(true);
        sourceCodeWebView.loadUrl(data.getFileURL());

    }
}
