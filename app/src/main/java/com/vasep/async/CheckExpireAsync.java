package com.vasep.async;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vasep.R;
import com.vasep.activity.PurchaseActivity;
import com.vasep.activity.PurchaseCartActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.ShowDetailActivity;
import com.vasep.activity.SignInActivity;
import com.vasep.adapter.AdapterCart;
import com.vasep.adapter.AdapterMenu;
import com.vasep.controller.ConnectApp;
import com.vasep.controller.ContactController;
import com.vasep.models.Article;
import com.vasep.models.ListReportItem;
import com.vasep.models.ReportItem;
import com.vasep.notification.Information;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 23/12/2016.
 */

public class CheckExpireAsync extends AsyncTask<Void,Void,Boolean> {

    Context context;
    ProgressDialog dialog;
    String user_id,report_id;
    CardView review,buy;
    Article article;
    TextView btn_review;
    Activity activity;
    public CheckExpireAsync(Activity mActivity,Context context, String user_id, String report_id, CardView review, CardView buy, Article article,TextView btn_review){
        this.context = context;
        this.user_id = user_id;
        this.report_id = report_id;
        this.review=review;
        this.buy=buy;
        this.article=article;
        this.btn_review=btn_review;
        this.activity=mActivity;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(Information.loading);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ConnectApp connectApp = new ConnectApp(context);
        if(user_id.equals("")){
            return false;
        }else {
            return connectApp.CheckUserExpire(Integer.parseInt(user_id), Integer.parseInt(report_id));
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        try{
            if(aBoolean == true){
                btn_review.setText(context.getResources().getString(R.string.view_report));
                buy.setVisibility(View.INVISIBLE);

                review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context,ShowDetailActivity.class);
                        intent1.putExtra("article",article);
                        intent1.putExtra("key_view", 2);
                        context.startActivity(intent1);
                    }
                });
            }else{
                buy.setVisibility(View.VISIBLE);
                review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(context,ShowDetailActivity.class);
                        intent1.putExtra("article",article);
                        intent1.putExtra("key_view", 1);
                        context.startActivity(intent1);
                    }
                });

                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences pref = context.getSharedPreferences("MyPref",context.MODE_PRIVATE);
                        String user_id = pref.getString("user_id", "");

                        if(user_id.equals("")){
                            Intent intent=new Intent(activity, SignInActivity.class);
                            activity.startActivityForResult(intent,1);
                        }else {
                            final Dialog dialogs = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogs.setContentView(R.layout.dialog_buy_type);
                            dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.bg_menu)));
                            TextView price_view_online=(TextView) dialogs.findViewById(R.id.price_view_online);
                            price_view_online.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(article.getPrice_online())) + " vnđ");
                            TextView price_view_download=(TextView) dialogs.findViewById(R.id.price_view_download);
                            price_view_download.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(article.getPrice_download())) + " vnđ");

                            TextView btn_view_online=(TextView) dialogs.findViewById(R.id.btn_view_online);
                            TextView btn_view_download=(TextView) dialogs.findViewById(R.id.btn_view_download);

                            ImageView imageView=(ImageView) dialogs.findViewById(R.id.img_close_dialog);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogs.dismiss();
                                }
                            });

                            btn_view_online.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogs.dismiss();
                                    ReportItem reportItem= new ReportItem();
                                    reportItem.setId(article.getId());
                                    reportItem.setUrl(article.getImage());
                                    reportItem.setTitle(article.getTitle());

                                    double discount_new=Double.parseDouble("0");

                                    reportItem.setMoney_discount(discount_new+"");
                                    reportItem.setMoney_order(article.getPrice_online());
                                    reportItem.setMoney_total((Double.parseDouble(article.getPrice_online())-discount_new)+"");

                                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String report = pref.getString("listReport", "");
                                    final ArrayList<ReportItem> reportItems = gson.fromJson(report, ListReportItem.class)==null?new ArrayList<ReportItem>():gson.fromJson(report, ListReportItem.class) ;

                                    boolean flag=false;
                                    for (int i=0; i< reportItems.size(); i++) {
                                        if (reportItems.get(i).getId().equals(reportItem.getId())) {
                                            flag=true;
                                        }
                                    }
                                    if(reportItems.size()==0|| !flag){
                                        reportItems.add(reportItem);
                                        String json = gson.toJson(reportItems);
                                        editor.putString("listReport", json);
                                        editor.commit();
                                    }

                                    final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.dialog_add_cart);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.bg_menu)));
                                    final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_item_cart);
                                    LinearLayoutManager gridview= new LinearLayoutManager(activity);
                                    recyclerView.setLayoutManager(gridview);

                                    AdapterCart adapterCart= new AdapterCart(context,reportItems);
                                    recyclerView.setAdapter(adapterCart);



                                    TextView btn_pays_dialog= (TextView) dialog.findViewById(R.id.btn_pays_dialog);
                                    TextView btn_continue_dialog= (TextView) dialog.findViewById(R.id.btn_continue_dialog);

                                    btn_continue_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    btn_pays_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            editor.putString("listReport", "");
                                            editor.commit();
                                            dialog.dismiss();

                                            Intent intent1 = new Intent(context, PurchaseCartActivity.class);
                                            intent1.putExtra("listReportPays", reportItems);
                                            intent1.putExtra("buy_type", "online");
                                            activity.startActivityForResult(intent1, 1);
                                        }
                                    });

                                    ImageView btn_close=(ImageView) dialog.findViewById(R.id.btn_close);
                                    btn_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                }
                            });

                            btn_view_download.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogs.dismiss();
                                    ReportItem reportItem= new ReportItem();
                                    reportItem.setId(article.getId());
                                    reportItem.setUrl(article.getImage());
                                    reportItem.setTitle(article.getTitle());

                                    double discount_new=Double.parseDouble("0");

                                    reportItem.setMoney_discount(discount_new+"");
                                    reportItem.setMoney_order(article.getPrice_download());
                                    reportItem.setMoney_total((Double.parseDouble(article.getPrice_download())-discount_new)+"");

                                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String report = pref.getString("listReport", "");
                                    final ArrayList<ReportItem> reportItems = gson.fromJson(report, ListReportItem.class)==null?new ArrayList<ReportItem>():gson.fromJson(report, ListReportItem.class) ;

                                    boolean flag=false;
                                    for (int i=0; i< reportItems.size(); i++) {
                                        if (reportItems.get(i).getId().equals(reportItem.getId())) {
                                            flag=true;
                                        }
                                    }
                                    if(reportItems.size()==0|| !flag){
                                        reportItems.add(reportItem);
                                        String json = gson.toJson(reportItems);
                                        editor.putString("listReport", json);
                                        editor.commit();
                                    }

                                    final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.dialog_add_cart);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.bg_menu)));
                                    final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_item_cart);
                                    LinearLayoutManager gridview= new LinearLayoutManager(activity);
                                    recyclerView.setLayoutManager(gridview);

                                    AdapterCart adapterCart= new AdapterCart(context,reportItems);
                                    recyclerView.setAdapter(adapterCart);



                                    TextView btn_pays_dialog= (TextView) dialog.findViewById(R.id.btn_pays_dialog);
                                    TextView btn_continue_dialog= (TextView) dialog.findViewById(R.id.btn_continue_dialog);

                                    btn_continue_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    btn_pays_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            editor.putString("listReport", "");
                                            editor.commit();
                                            dialog.dismiss();

                                            Intent intent1 = new Intent(context, PurchaseCartActivity.class);
                                            intent1.putExtra("listReportPays", reportItems);
                                            intent1.putExtra("buy_type", "download");
                                            activity.startActivityForResult(intent1, 1);
                                        }
                                    });

                                    ImageView btn_close=(ImageView) dialog.findViewById(R.id.btn_close);
                                    btn_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                }
                            });

                            /*Intent intent1 = new Intent(context, PurchaseActivity.class);
                            intent1.putExtra("article", article);
                            activity.startActivityForResult(intent1, 1);*/

                            dialogs.show();
                        }
                    }
                });
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}
