package com.vasep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vasep.R;
import com.vasep.models.Market;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class AdapterLvMaketing extends BaseAdapter {
    private Context mContext;
    List<Market> list;


    public AdapterLvMaketing(Context c, List<Market> list) {
        mContext = c;
        this.list=list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    //-----------
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_lv_maketing, null);

        TextView textView = (TextView)convertView.findViewById(R.id.txt_adapterlv_maket);
        textView.setText(list.get(position).getName());

        return convertView;
    }

}

