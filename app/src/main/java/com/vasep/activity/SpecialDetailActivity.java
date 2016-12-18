package com.vasep.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent i = getIntent();
        Article article = (Article)i.getSerializableExtra("article");
        Picasso.with(SpecialDetailActivity.this).load(article.getImage()).into(screen2_image_top);
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

         /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialDetailActivity.this,MainActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                finish();
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_newsdetail);

        webView = (WebView) findViewById(R.id.webview_newsdetail);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.loadDataWithBaseURL(
                "",
                "<style>img{display: inline;height: auto;max-width: 100%;}"
                        + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                        + article.getContent(), "text/html", "UTF-8", "");

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
