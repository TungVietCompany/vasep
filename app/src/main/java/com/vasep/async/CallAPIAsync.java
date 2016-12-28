package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.vasep.adapter.AdapterPayment;
import com.vasep.controller.AppotaBankAPI;
import com.vasep.controller.ConnectApp;
import com.vasep.models.Article;
import com.vasep.models.BankingAppotaAPI;
import com.vasep.models.Payment;
import com.vasep.models.ReportItem;
import com.vasep.notification.Information;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/12/2016.
 */

public class CallAPIAsync extends AsyncTask<Void,Void,BankingAppotaAPI> {

    Context context;
    ProgressDialog dialog;
    String amount,state,language,developer_trans_id,user_id,payment_id;
    ArrayList<ReportItem> list;
    int select_type;
    String buy_type;
    public CallAPIAsync(Context context, String developer_trans_id, String amount, String state, String language, ArrayList<ReportItem> list, String user_id, String payment_id, int select_type, String buy_type){
        this.context = context;
        this.amount=(int)Double.parseDouble(amount)+"";
        this.state=state;
        this.language=language;
        this.developer_trans_id=developer_trans_id;
        this.list= list;
        this.user_id=user_id;
        this.payment_id=payment_id;
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
    protected BankingAppotaAPI doInBackground(Void... params) {
        AppotaBankAPI appotaBankAPI = new AppotaBankAPI(context,language);
        return appotaBankAPI.CallAPI(select_type,developer_trans_id,amount,state);
    }

    @Override
    protected void onPostExecute(BankingAppotaAPI model) {
        try{
            if(model!=null){
                BankingAppotaAPI item=model;
                for (int i=0; i<list.size(); i++) {
                    if(buy_type.equals("online")){
                        buy_type="0";
                    }
                    else if(buy_type.equals("download")){
                        buy_type="1";
                    }
                    TransactionAsync transactionAsync = new TransactionAsync(context, Integer.parseInt(list.get(i).getId()), Integer.parseInt(user_id), Integer.parseInt(payment_id),
                            0, model.getError_code(), model.getMessage(), model.getData().getTransaction_id(), model.getData().getDeveloper_trans_id(),
                            model.getData().getAmount(), model.getData().getCurrency(), model.getData().getBank_options().get(0).getUrl(), model.getData().getBank_options().get(0).getBank(),Integer.parseInt(buy_type));
                    transactionAsync.execute();

                }
                String url = model.getData().getBank_options().get(0).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);

            }else{
                //Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
        dialog.dismiss();

    }


}


