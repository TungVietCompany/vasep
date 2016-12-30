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
import android.text.format.DateFormat;
import android.view.MotionEvent;
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
import com.vasep.async.CallAPIAsync;
import com.vasep.async.GetPaymentAsync;
import com.vasep.models.Article;
import com.vasep.models.ReportItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
        final ArrayList<ReportItem> reportItems = (ArrayList<ReportItem>) i.getSerializableExtra("listReportPays");
        final String buy_type= i.getStringExtra("buy_type");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview_list_cart);
        LinearLayoutManager gridview= new LinearLayoutManager(PurchaseCartActivity.this);
        recyclerView.setLayoutManager(gridview);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        AdapterCart adapterCart= new AdapterCart(PurchaseCartActivity.this,reportItems);
        recyclerView.setAdapter(adapterCart);

        double total_UnitPrice=0;
        double total_Discount=0;
        double total=0;
        for(int j=0; j<reportItems.size(); j++){
            total_UnitPrice=total_UnitPrice+Double.parseDouble(reportItems.get(j).getMoney_order());
            //total_Discount=total_Discount+Double.parseDouble(reportItems.get(j).getMoney_discount());

        }
        total= total_UnitPrice-total_Discount;

        cart_total.setText(new DecimalFormat("#,###.#").format(total_UnitPrice) + " vnđ");
        cart_discount.setText(new DecimalFormat("#,###.#").format(total_Discount) + " vnđ");
        cart_summoney.setText(new DecimalFormat("#,###.#").format(total) + " vnđ");

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

                    double price=0;
                    for (int i=0; i<reportItems.size(); i++){
                        price=price+ Double.parseDouble(reportItems.get(i).getMoney_total());
                    }

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                    final String user_id = pref.getString("user_id", "");
                    final String language = pref.getString("language", "vi");
                    try {
                        String state=user_id+ "_";
                        for(int i=0; i< reportItems.size(); i++){
                            if(i==reportItems.size()-1){
                                state=state+ reportItems.get(i).getId();
                            }else{
                                state=state+ reportItems.get(i).getId()+"_";
                            }
                        }

                        CallAPIAsync callAPIAsync = new CallAPIAsync(PurchaseCartActivity.this, (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll(" ", "").replace(":", "").replace("-",""), price+"", state, language, reportItems, user_id, "1",select_type,buy_type);
                        callAPIAsync.execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    /*final Dialog dialog = new Dialog(PurchaseCartActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_list_payment);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_menu)));
                    final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_payment);
                    LinearLayoutManager gridview= new LinearLayoutManager(PurchaseCartActivity.this);
                    recyclerView.setLayoutManager(gridview);

                    AdapterPayment adapterMenu = new AdapterPayment(PurchaseCartActivity.this, null,reportItems,0,buy_type);


                    GetPaymentAsync getPaymentAsync = new GetPaymentAsync(PurchaseCartActivity.this, recyclerView, adapterMenu, reportItems,select_type,buy_type);
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
*/

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
