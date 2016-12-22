package com.vasep.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.vasep.R;

import com.vasep.async.CheckExpireAsync;
import com.vasep.controller.ArticleController;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReportDetailActivity extends AppCompatActivity {

    @Bind(R.id.screen4_image_item)
    ImageView screen4_image_item;

    @Bind(R.id.screen4_title_item)
    TextView screen4_title_item;

    @Bind(R.id.screen4_category_top)
    TextView screen4_category_top;

    @Bind(R.id.screen4_date_top)
    TextView screen4_date_top;

    @Bind(R.id.screen4_money_item)
    TextView screen4_money_item;

    @Bind(R.id.webview_reportdetail)
    WebView webview_reportdetail;

    @Bind(R.id.screen4_book)
    CardView screen4_book;

    @Bind(R.id.screen4_read)
    CardView screen4_read;

    @Bind(R.id.btn_preview)
    TextView btn_review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        ButterKnife.bind(this);

        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");


        Glide.with(ReportDetailActivity.this). load(article.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(screen4_image_item);
        screen4_category_top.setText(article.getCategory_name()+" | ");
        screen4_date_top.setText(ChangeDate.convertDate(article.getCreate_date()));
        screen4_title_item.setText(article.getTitle());
        screen4_money_item.setText( new DecimalFormat("#,###.##").format(Double.parseDouble(article.getPrice())) +" vnđ");
        webview_reportdetail.getSettings().setJavaScriptEnabled(true);
        webview_reportdetail.loadDataWithBaseURL(
                "",
                "<style>img{display: inline;height: auto;max-width: 100%;}"
                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                        + article.getContent(), "text/html", "UTF-8", "");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        String user_id = pref.getString("user_id", "");

        CheckExpireAsync checkExpireAsync = new CheckExpireAsync(ReportDetailActivity.this, user_id, article.getId(), screen4_read, screen4_book, article, btn_review);
        checkExpireAsync.execute();


        /*screen4_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ReportDetailActivity.this, ShowDetailActivity.class);
                intent1.putExtra("article", article);
                intent1.putExtra("key_view", 1);
                startActivity(intent1);
            }
        });

        screen4_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ReportDetailActivity.this, PurchaseActivity.class);
                intent1.putExtra("article", article);
                startActivity(intent1);
            }
        });*/

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_reportdetail);
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
                Intent intent = new Intent(ReportDetailActivity.this,MainActivity.class);
                intent.putExtra("type",3);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_report);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
