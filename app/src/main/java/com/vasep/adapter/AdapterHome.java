package com.vasep.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;
import com.squareup.picasso.Picasso;
import com.vasep.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 26/08/2016.
 */
public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ExploreHoder> {
    private Context mContext;
    List<Article> articleList = new ArrayList<>();

    public AdapterHome(Context c, List<Article> articleList) {
        mContext = c;
        this.articleList = articleList;
    }

    @Override
    public ExploreHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dapter_recyclerview, null);
        ExploreHoder rcv = new ExploreHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ExploreHoder holder, int position) {
        Picasso.with(mContext).load(articleList.get(position).getImage()).into(holder.imageView);
        holder.txt_screen1_title.setText(articleList.get(position).getTitle());
        holder.txt_screen1_category.setText(articleList.get(position).getCategory_name());
        holder.txt_screen1_date.setText(ChangeDate.convertDate(articleList.get(position).getCreate_date()));
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemCount() {
        if (articleList.size() > 0) {
            return articleList.size();
        } else {
            return 0;
        }
    }

    public class ExploreHoder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txt_screen1_title;
        TextView txt_screen1_category;
        TextView txt_screen1_date;

        public ExploreHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.screen1_image_item);
            txt_screen1_title = (TextView) itemView.findViewById(R.id.screen1_title_item);
            txt_screen1_category = (TextView) itemView.findViewById(R.id.screen_category_item);
            txt_screen1_date = (TextView) itemView.findViewById(R.id.screen_date_item);
        }
    }
}