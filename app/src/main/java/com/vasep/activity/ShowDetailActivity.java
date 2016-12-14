package com.vasep.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.vasep.R;
import com.vasep.models.Article;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class ShowDetailActivity extends AppCompatActivity{
    PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;
    LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        root = (LinearLayout) findViewById(R.id.remote_pdf_root);
        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");

        /*WebView mWebView = (WebView) findViewById(R.id.webview_showdetail);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(article.getReport());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);*/

        WebView webview = (WebView) findViewById(R.id.webview_showdetail);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = article.getReport();
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

    }
}
