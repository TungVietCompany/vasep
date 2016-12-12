package com.vasep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private boolean isGrid;

  public SimpleAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return 6;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;
    viewHolder = (ViewHolder) view.getTag();
    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
