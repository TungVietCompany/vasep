package com.vasep.adapter;

/**
 * Created by Administrator on 22/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.async.CallAPIAsync;
import com.vasep.async.TransactionAsync;
import com.vasep.controller.AppotaBankAPI;
import com.vasep.models.Article;
import com.vasep.models.BankingAppotaAPI;
import com.vasep.models.Category;
import com.vasep.models.Payment;

import java.text.DecimalFormat;
import java.util.List;


public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ExploreHoder> {
    private Context mContext;
    private List<Payment> payments;
    int type_language;
    Article article;
    int select_type;
    public AdapterPayment(Context c, List<Payment> payments, Article article,int select_type) {
        mContext = c;
        this.payments = payments;
        this.article = article;
        this.select_type=select_type;
    }

    @Override
    public ExploreHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_payment, null);
        ExploreHoder rcv = new ExploreHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ExploreHoder holder, int position) {
        final int index = position;
        // set giá tri ở đây
        if (position == payments.size() - 1) {
            holder.view_line.setVisibility(View.INVISIBLE);
        }
        SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("MyPref", mContext.MODE_PRIVATE);
        final String user_id = pref.getString("user_id", "");
        final String language = pref.getString("language", "vi");
        if (language.equals("vi")) {
            holder.txt_type.setText(payments.get(position).getName());
        } else {
            holder.txt_type.setText(payments.get(position).getEng_name());
        }
        holder.txt_price.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(payments.get(position).getPrice())) + " vnđ");

        holder.dialog_allpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    CallAPIAsync callAPIAsync = new CallAPIAsync(mContext, (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()).replaceAll(" ", "").replace(":", ""), payments.get(index).getPrice(), user_id + "_" + article.getId(), language, article, user_id, payments.get(index).getId(),select_type);
                    callAPIAsync.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ExploreHoder extends RecyclerView.ViewHolder {

        TextView txt_type;
        TextView txt_price;
        View view_line;
        LinearLayout dialog_allpayment;

        public ExploreHoder(View itemView) {
            super(itemView);
            txt_type = (TextView) itemView.findViewById(R.id.txt_title);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            view_line = (View) itemView.findViewById(R.id.view_line);
            dialog_allpayment = (LinearLayout) itemView.findViewById(R.id.dialog_allpayment);

        }
    }
}