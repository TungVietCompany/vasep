package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.vasep.adapter.AdapterPayment;
import com.vasep.controller.ConnectApp;
import com.vasep.models.Article;
import com.vasep.models.Payment;
import com.vasep.notification.Information;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/12/2016.
 */



public class TransactionAsync extends AsyncTask<Void,Void,Boolean> {

    Context context;
    ProgressDialog dialog;
    RecyclerView recyclerView;
    AdapterPayment adapterHome;
    Article article;
    int article_id,user_id, payment_id, status, error_code;
    String message, transaction_id, developer_trans_id, amount, currency, url, bank;
    int buy_type;
    public TransactionAsync(Context context, int article_id,int user_id,int payment_id,int status,int error_code,String message,String transaction_id,String developer_trans_id,String amount,String currency,String url,String bank, int buy_type){
        this.context = context;
        this.article_id=article_id;
        this.user_id=user_id;
        this.payment_id=payment_id;
        this.status=status;
        this.error_code=error_code;

        this.message=message;
        this.transaction_id=transaction_id;
        this.developer_trans_id=developer_trans_id;
        this.amount=amount;
        this.currency=currency;
        this.url=url;
        this.bank=bank;
        this.buy_type=buy_type;
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
        ConnectApp contactController = new ConnectApp(context);
        return contactController.InserTransaction(article_id, user_id, payment_id, status, error_code, message, transaction_id, developer_trans_id, amount, currency, url,bank,buy_type);
    }

    @Override
    protected void onPostExecute(Boolean flag) {
        try{
            if(flag){
                String ahihi="";

            }else{
                //Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}

