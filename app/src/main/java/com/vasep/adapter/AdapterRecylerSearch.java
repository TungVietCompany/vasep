package com.vasep.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vasep.R;
import com.vasep.models.Category;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class AdapterRecylerSearch extends RecyclerView.Adapter<AdapterRecylerSearch.SearchHoder>{

    private Context context;
    private List<Category> categories;
    public AdapterRecylerSearch(Context context,List<Category> categories){
        this.context = context;
        this.categories = categories;
    }
    @Override
    public SearchHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_search, null);
        AdapterRecylerSearch.SearchHoder rcv = new AdapterRecylerSearch.SearchHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchHoder holder, int position) {
        holder.screen10_txt_category.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class SearchHoder extends RecyclerView.ViewHolder{

        TextView screen10_txt_category;
        ImageView screen10_image_category;

        public SearchHoder(View itemView) {
            super(itemView);
            screen10_txt_category = (TextView)itemView.findViewById(R.id.screen10_txt_category);
            screen10_image_category = (ImageView)itemView.findViewById(R.id.screen10_image_category);
        }
    }
}
