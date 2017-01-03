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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 26/08/2016.
 */
public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ExploreHoder> {
    private Context mContext;
    private List<Category> categories;
    int type_language;

    public AdapterMenu(Context c,List<Category> categories) {
        mContext = c;
        this.categories = categories;

    }
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getType_language() {
        return type_language;
    }

    public void setType_language(int type_language) {
        this.type_language = type_language;
    }

    @Override
    public ExploreHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recylerview_dialog, null);
        ExploreHoder rcv = new ExploreHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ExploreHoder holder, int position) {

        Picasso.with(mContext).load(categories.get(position).getImage()).into(holder.img_menu);
        if(0== categories.get(position).getLanguage_type()) {
            holder.textView.setText(categories.get(position).getName());
        }else{
            holder.textView.setText(categories.get(position).getEng_name());
        }

    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ExploreHoder extends RecyclerView.ViewHolder{
        ImageView img_menu;
        TextView textView;
        CardView cardView;
        public ExploreHoder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.txt_menu);
            img_menu = (ImageView)itemView.findViewById(R.id.img_menu);
            cardView=(CardView) itemView.findViewById(R.id.card);
            int xxx= Resources.getSystem().getDisplayMetrics().widthPixels;
            img_menu.setMaxHeight((xxx-60)/3);
            cardView.setMinimumHeight((xxx-60)/3);
            

        }
    }
}