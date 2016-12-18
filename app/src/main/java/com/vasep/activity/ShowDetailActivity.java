package com.vasep.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.vasep.R;
import com.vasep.models.Article;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class ShowDetailActivity extends AppCompatActivity implements DownloadFile.Listener {

    RemotePDFViewPager remotePDFViewPager;
    Button btnDownload;
    PDFPagerAdapter adapter;
    LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        root = (LinearLayout) findViewById(R.id.remote_pdf_root);
        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");

//        InsertView insert = new InsertView(ShowDetailActivity.this,article.getId());
//        insert.execute();

        if (Build.VERSION.SDK_INT >= 21) {
            remotePDFViewPager = new RemotePDFViewPager(ShowDetailActivity.this, article.getReport(), this);
            remotePDFViewPager.setId(R.id.pdfViewPager);
        } else {
            WebView webview = (WebView) findViewById(R.id.pdfView);
            webview.getSettings().setJavaScriptEnabled(true);
            String pdf = "http://103.237.147.54/Webtin/public/templates/upload/report_full/1481397968.pdf";
            webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf+"&overridemobile=true");
            WebSettings webSettings = webview.getSettings();
            //webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }


    public static void open(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public void showDownloadButton() {

    }

    public void hideDownloadButton() {

    }

    public void updateLayout() {
        root.removeAllViewsInLayout();

        root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
        showDownloadButton();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        showDownloadButton();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}
