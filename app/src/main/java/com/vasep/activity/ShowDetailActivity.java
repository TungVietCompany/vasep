package com.vasep.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.vasep.R;
import com.vasep.async.AddView;
import com.vasep.models.Article;
import com.vasep.notification.Information;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class ShowDetailActivity extends AppCompatActivity implements DownloadFile.Listener {

    RemotePDFViewPager remotePDFViewPager;
    Button btnDownload;
    PDFPagerAdapter adapter;
    LinearLayout root;

    //private ProgressBar progress;
    WebView webview;

    ProgressDialog dialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        dialog = new ProgressDialog(this);

        root = (LinearLayout) findViewById(R.id.remote_pdf_root);
        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");
        int key_view= i.getExtras().getInt("key_view");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_showdetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.btn_back1);
        actionBar.setDisplayUseLogoEnabled(true);

         /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailActivity.this,ReportDetailActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
                finish();
            }
        });

        AddView insert = new AddView(ShowDetailActivity.this,article.getId());
        insert.execute();

//        InsertView insert = new InsertView(ShowDetailActivity.this,article.getId());
//        insert.execute();

        if (Build.VERSION.SDK_INT >= 21) {
            if(key_view==1){
                remotePDFViewPager = new RemotePDFViewPager(ShowDetailActivity.this, article.getContent(), this);
                remotePDFViewPager.setId(R.id.pdfViewPager);
            }else{
                remotePDFViewPager = new RemotePDFViewPager(ShowDetailActivity.this, article.getReport(), this);
                remotePDFViewPager.setId(R.id.pdfViewPager);
            }

        } else {
            webview = (WebView) findViewById(R.id.pdfView);
            webview.getSettings().setJavaScriptEnabled(true);
            //progress.setProgress(0);
            //String pdf = "http://103.237.147.54/Webtin/public/templates/upload/report_full/1481397968.pdf";
            String pdf="";
            // demo
            if(key_view==1){
                pdf=article.getContent();
            }else{
                pdf=article.getReport();
            }
            webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf+"&overridemobile=true");
            WebSettings webSettings = webview.getSettings();
            //webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
            webview.setWebViewClient(new MyWebViewClient());
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
            //progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            dialog.setMessage(Information.loading);
            dialog.show();
            //progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
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
