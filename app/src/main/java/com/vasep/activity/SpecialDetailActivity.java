package com.vasep.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vasep.R;

import com.vasep.adapter.AdapterItem;
import com.vasep.async.AddView;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.vasep.models.Banner;
import com.vasep.models.ListBanner;


import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpecialDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private WebView webView;

    @Bind(R.id.screen2_image_top)
    ImageView screen2_image_top;

    @Bind(R.id.screen2_category_top)
    TextView screen2_category_top;

    @Bind(R.id.screen2_date_top)
    TextView screen2_date_top;

    @Bind(R.id.screen1_title_item)
    TextView screen1_title_item;
    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent i = getIntent();
         article = (Article)i.getSerializableExtra("article");

        AddView insert = new AddView(SpecialDetailActivity.this,article.getId());
        insert.execute();

        //Picasso.with(SpecialDetailActivity.this).load(article.getImage()).into(screen2_image_top);
        Glide.with(SpecialDetailActivity.this). load(article.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(screen2_image_top);
        screen2_category_top.setText(article.getCategory_name()+" | ");
        screen2_date_top.setText(ChangeDate.convertDate(article.getCreate_date()));
        screen1_title_item.setText(article.getTitle());

        toolbar = (Toolbar)findViewById(R.id.toolbar_newsdetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.btn_back1);
        actionBar.setDisplayUseLogoEnabled(true);

        ImageView new_image_banner=(ImageView) findViewById(R.id.new_image_banner);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String report = pref.getString("listBanner", "");
        final ArrayList<Banner> list = gson.fromJson(report, ListBanner.class) == null ? new ArrayList<Banner>() : gson.fromJson(report, ListBanner.class);
        Random r = new Random();
        final int i1 = r.nextInt(list.size());
        Picasso.with(SpecialDetailActivity.this).load(list.get(i1).getImage()).into(new_image_banner);

        new_image_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = list.get(i1).getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }catch (Exception err){

                }
            }
        });

         /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_newsdetail);

        webView = (WebView) findViewById(R.id.webview_newsdetail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(
                "",
                "<style>img{display: inline;height: 100%;max-width: 100%;}"
                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                        + article.getContent(), "text/html", "UTF-8",null);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_newsdetail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
