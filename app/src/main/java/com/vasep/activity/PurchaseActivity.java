package com.vasep.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.controller.money;
import com.vasep.models.Article;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PurchaseActivity extends AppCompatActivity {

    @Bind(R.id.screen6_image)
    ImageView screen6_image;

    @Bind(R.id.screen6_title)
    TextView screen6_title;

    @Bind(R.id.screen6_price)
    TextView screen6_price;

    @Bind(R.id.screen6_discount)
    TextView screen6_discount;

    @Bind(R.id.screen6_summoney)
    TextView screen6_summoney;

    @Bind(R.id.screen6_contact)
    CardView screen6_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);
        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");

        Picasso.with(PurchaseActivity.this).load(article.getImage()).into(screen6_image);
        screen6_title.setText(article.getTitle());
        screen6_price.setText( new DecimalFormat("#,###.#").format(Double.parseDouble(article.getPrice()))+" vnđ");
        screen6_discount.setText(new DecimalFormat("#,###.#").format(Double.parseDouble((Float.parseFloat(article.getPrice())*Float.parseFloat(article.getDiscount()))/100+"")) +" vnđ");
        float sum_money = Float.parseFloat(article.getPrice())-(Float.parseFloat(article.getPrice())*Float.parseFloat(article.getDiscount()))/100;
        screen6_summoney.setText( new DecimalFormat("#,###.#").format(Double.parseDouble(sum_money+"")) +" vnđ");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_purchase);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.btn_back);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.purchase));
        actionBar.setIcon(R.drawable.btn_back1);


        screen6_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseActivity.this,ContactActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
                finish();
            }
        });

         /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseActivity.this,ReportDetailActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
                finish();
            }
        });

    }
}
