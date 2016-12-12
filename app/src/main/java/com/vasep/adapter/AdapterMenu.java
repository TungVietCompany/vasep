package com.vasep.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasep.R;


/**
 * Created by Administrator on 26/08/2016.
 */
public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ExploreHoder> {
    private Context mContext;

    public AdapterMenu(Context c) {
        mContext = c;
    }

    @Override
    public ExploreHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recylerview_dialog, null);
        ExploreHoder rcv = new ExploreHoder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ExploreHoder holder, int position) {

    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class ExploreHoder extends RecyclerView.ViewHolder{
        public ExploreHoder(View itemView) {
            super(itemView);

        }
    }
}