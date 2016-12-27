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
                        if(user_id.equals("")){
                            Intent intent=new Intent(activity, SignInActivity.class);
                            activity.startActivityForResult(intent,1);
                        }else {
                            /*Intent intent1 = new Intent(context, PurchaseActivity.class);
                            intent1.putExtra("article", article);
                            activity.startActivityForResult(intent1, 1);*/

                            final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_add_cart);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.bg_menu)));
                            final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.listView_item_cart);
                            LinearLayoutManager gridview= new LinearLayoutManager(activity);
                            recyclerView.setLayoutManager(gridview);

                            SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                            final SharedPreferences.Editor editor = pref.edit();
                            String report = pref.getString("listReport", "");

                            Gson gson = new Gson();
                            final ArrayList<ReportItem> reportItems = gson.fromJson(report, ListReportItem.class);

                            AdapterCart adapterCart= new AdapterCart(context,reportItems);
                            recyclerView.setAdapter(adapterCart);

                            TextView btn_pays_dialog= (TextView) dialog.findViewById(R.id.btn_pays_dialog);
                            TextView btn_continue_dialog= (TextView) dialog.findViewById(R.id.btn_continue_dialog);

                            btn_continue_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ReportItem reportItem= new ReportItem();
                                    reportItem.setId(article.getId());
                                    reportItem.setUrl(article.getImage());
                                    reportItem.setTitle(article.getTitle());
                                    reportItem.setMoney_discount(article.getDiscount());
                                    reportItem.setMoney_order(article.getPrice());
                                    reportItem.setMoney_total(Integer.parseInt(article.getPrice())-Integer.parseInt(article.getDiscount())+"");
                                    reportItems.add(reportItem);


                                    Gson gson = new Gson();
                                    String json = gson.toJson(reportItems);
                                    editor.putString("listReport", json);
                                    editor.commit();
                                }
                            });
                        }
                    }
                });
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}
