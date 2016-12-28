package com.vasep.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.models.Category;
import com.vasep.models.ReportItem;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 27/12/2016.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ExploreHoder> {
    private Context mContext;
    private List<ReportItem> categories;
    int type_language;

    public AdapterCart(Context c,List<ReportItem> categories) {
        mContext = c;
        this.categories = categories;

    }

    @Override
    public ExploreHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_cart, null);
        ExploreHoder rcv = new ExploreHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(AdapterCart.ExploreHoder holder, final int position) {
        Picasso.with(mContext).load(categories.get(position).getUrl()).into(holder.image_report_cart);
        holder.txt_title_report_cart.setText(categories.get(position).getTitle());
        holder.txt_unitprice_cart.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(categories.get(position).getMoney_order()))+" vnđ");
        holder.txt_discount_cart.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(categories.get(position).getMoney_discount()))+" vnđ");
        holder.txt_total_cart.setText(new DecimalFormat("#,###.#").format(Double.parseDouble(categories.get(position).getMoney_total()))+" vnđ");

        holder.btn_remove_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.remove(position);
                notifyDataSetChanged();
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
        try {
            return categories.size();
        }catch (Exception err){
            return 0;
        }
    }

    public class ExploreHoder extends RecyclerView.ViewHolder{
        ImageView image_report_cart,btn_remove_cart;
        TextView txt_title_report_cart,txt_unitprice_cart,txt_discount_cart,txt_total_cart;

        public ExploreHoder(View itemView) {
            super(itemView);
            image_report_cart=(ImageView) itemView.findViewById(R.id.image_report_cart);
            btn_remove_cart=(ImageView) itemView.findViewById(R.id.btn_remove_cart);
            txt_title_report_cart=(TextView) itemView.findViewById(R.id.txt_title_report_cart);
            txt_unitprice_cart=(TextView) itemView.findViewById(R.id.txt_unitprice_cart);
            txt_discount_cart=(TextView) itemView.findViewById(R.id.txt_discount_cart);
            txt_total_cart=(TextView) itemView.findViewById(R.id.txt_total_cart);

        }
    }
}