package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vasep.R;
import com.vasep.adapter.AdapterMenu;
import com.vasep.adapter.AdapterPayment;
import com.vasep.controller.ConnectApp;
import com.vasep.models.Article;
import com.vasep.models.Payment;
import com.vasep.models.ReportItem;
import com.vasep.notification.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 22/12/2016.
 */

public class GetPaymentAsync extends AsyncTask<Void,Void,ArrayList<Payment>> {

    Context context;
    ProgressDialog dialog;
    RecyclerView recyclerView;
    AdapterPayment adapterHome;
    ArrayList<ReportItem> lst;
    int select_type;
    String buy_type;
    public GetPaymentAsync(Context context, RecyclerView recyclerView, AdapterPayment adapterHome, ArrayList<ReportItem> list,int select_type, String buy_type){
        this.context = context;
        this.recyclerView=recyclerView;
        this.adapterHome=adapterHome;
        this.lst= list;
        this.select_type=select_type;
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
    protected ArrayList<Payment> doInBackground(Void... params) {
        ConnectApp contactController = new ConnectApp(context);
        return contactController.GetPayment();
    }

    @Override
    protected void onPostExecute(ArrayList<Payment> list) {
        try{
            if(list.size()>0){
                double price=0;
                for (int i=0; i<lst.size(); i++){
                    price=price+ Double.parseDouble(lst.get(i).getMoney_total());
                }
                list.get(0).setPrice(price+"");
                adapterHome= new AdapterPayment(context,list,lst,select_type,buy_type);
                recyclerView.setAdapter(adapterHome);

            }else{
                //Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}

