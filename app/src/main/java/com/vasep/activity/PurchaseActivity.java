package com.vasep.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.adapter.AdapterMenu;
import com.vasep.adapter.AdapterPayment;
import com.vasep.async.GetPaymentAsync;
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

    @Bind(R.id.btn_visa)
    LinearLayout btn_visa;

    @Bind(R.id.btn_bank)
    LinearLayout btn_bank;

    @Bind(R.id.btn_pays)
    CardView btn_pays;

    int select_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);
        Intent i = getIntent();
        final Article article = (Article) i.getSerializableExtra("article");


        Picasso.with(PurchaseActivity.this).load(article.getImage()).into(screen6_image);
        screen6_title.setText(article.getTitle());
        screen6_price.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(article.getPrice())) + " vnđ");
        screen6_discount.setText(new DecimalFormat("#,###.#").format(Double.parseDouble((Float.parseFloat(article.getPrice()) * Float.parseFloat(article.getDiscount())) / 100 + "")) + " vnđ");
        float sum_money = Float.parseFloat(article.getPrice()) - (Float.parseFloat(article.getPrice()) * Float.parseFloat(article.getDiscount())) / 100;
        screen6_summoney.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(sum_money + "")) + " vnđ");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_purchase);
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
                Intent intent = new Intent(PurchaseActivity.this, ContactActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
                finish();
            }
        });

         /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseActivity.this, ReportDetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
                finish();
            }
        });

        btn_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bank.setBackground(getResources().getDrawable(R.drawable.customborderpay));
                btn_visa.setBackground(getResources().getDrawable(R.drawable.customnoborderpay));
                select_type = 1;
            }
        });

        btn_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bank.setBackground(getResources().getDrawable(R.drawable.customnoborderpay));
                btn_visa.setBackground(getResources().getDrawable(R.drawable.customborderpay));
                select_type = 2;
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        String user_id = pref.getString("user_id", "");

            btn_pays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(select_type!=0){
                        final Dialog dialog = new Dialog(PurchaseActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_list_payment);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_menu)));
                        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_payment);
                        LinearLayoutManager gridview= new LinearLayoutManager(PurchaseActivity.this);
                        recyclerView.setLayoutManager(gridview);

                        AdapterPayment adapterMenu = new AdapterPayment(PurchaseActivity.this, null,article,0);


                        GetPaymentAsync getPaymentAsync = new GetPaymentAsync(PurchaseActivity.this, recyclerView, adapterMenu, article,select_type);
                        getPaymentAsync.execute();

                        dialog.show();

                        ImageView img_close_dialog_payment= (ImageView) dialog.findViewById(R.id.img_close_dialog_payment);
                        img_close_dialog_payment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                Intent intent = getIntent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });


                    }else{
                        Toast.makeText(PurchaseActivity.this,"Chọn phương thức thanh toán",Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            finish();

        }
    }

}
