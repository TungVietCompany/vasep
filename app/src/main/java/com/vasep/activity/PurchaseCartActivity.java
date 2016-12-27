package com.vasep.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.vasep.adapter.AdapterCart;
import com.vasep.adapter.AdapterPayment;
import com.vasep.async.GetPaymentAsync;
import com.vasep.models.Article;
import com.vasep.models.ReportItem;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 27/12/2016.
 */

public class PurchaseCartActivity extends AppCompatActivity {

    @Bind(R.id.cart_total)
    TextView cart_total;

    @Bind(R.id.cart_discount)
    TextView cart_discount;

    @Bind(R.id.cart_summoney)
    TextView cart_summoney;

    @Bind(R.id.btn_visa_cart)
    LinearLayout btn_visa_cart;

    @Bind(R.id.btn_bank_cart)
    LinearLayout btn_bank_cart;

    @Bind(R.id.btn_pays_cart)
    CardView btn_pays_cart;

    @Bind(R.id.contact_cart)
    CardView contact_cart;

    @Bind(R.id.recylerview_list_cart)
    RecyclerView recylerview_list_cart;

    int select_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        Intent i = getIntent();
        List<ReportItem> reportItems = (List<ReportItem>) i.getSerializableExtra("list_cart");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview_list_cart);
        LinearLayoutManager gridview= new LinearLayoutManager(PurchaseCartActivity.this);
        recyclerView.setLayoutManager(gridview);
        AdapterCart adapterCart= new AdapterCart(PurchaseCartActivity.this,reportItems);
        recyclerView.setAdapter(adapterCart);

        /*screen6_price.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(article.getPrice())) + " vnđ");
        screen6_discount.setText(new DecimalFormat("#,###.#").format(Double.parseDouble((Float.parseFloat(article.getPrice()) * Float.parseFloat(article.getDiscount())) / 100 + "")) + " vnđ");
        float sum_money = Float.parseFloat(article.getPrice()) - (Float.parseFloat(article.getPrice()) * Float.parseFloat(article.getDiscount())) / 100;
        screen6_summoney.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(sum_money + "")) + " vnđ");*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_purchase);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.btn_back);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.purchase));
        actionBar.setIcon(R.drawable.btn_back1);


        contact_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseCartActivity.this, ContactActivity.class);
                startActivity(intent);
                finish();
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

        btn_bank_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bank_cart.setBackground(getResources().getDrawable(R.drawable.customborderpay));
                btn_visa_cart.setBackground(getResources().getDrawable(R.drawable.customnoborderpay));
                select_type = 1;
            }
        });

        btn_visa_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bank_cart.setBackground(getResources().getDrawable(R.drawable.customnoborderpay));
                btn_visa_cart.setBackground(getResources().getDrawable(R.drawable.customborderpay));
                select_type = 2;
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        String user_id = pref.getString("user_id", "");

        btn_pays_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select_type!=0){
                    final Dialog dialog = new Dialog(PurchaseCartActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_list_payment);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_menu)));
                    final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_payment);
                    LinearLayoutManager gridview= new LinearLayoutManager(PurchaseCartActivity.this);
                    recyclerView.setLayoutManager(gridview);

                    /*AdapterPayment adapterMenu = new AdapterPayment(PurchaseCartActivity.this, null,article,0);


                    GetPaymentAsync getPaymentAsync = new GetPaymentAsync(PurchaseCartActivity.this, recyclerView, adapterMenu, article,select_type);
                    getPaymentAsync.execute();*/

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
                    Toast.makeText(PurchaseCartActivity.this,"Chọn phương thức thanh toán",Toast.LENGTH_SHORT).show();
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
