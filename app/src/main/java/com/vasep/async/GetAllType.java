package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.vasep.R;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.controller.CategoryController;
import com.vasep.controller.TypeController;
import com.vasep.models.Category;
import com.vasep.models.Type;
import com.vasep.notification.Information;

import java.util.List;

/**
 * Created by thuyetpham94 on 13/12/2016.
 */

public class GetAllType extends AsyncTask<Void, Void, List<Type>> {

    Context context;
    DialogPlus dialogPlus;

    public GetAllType(Context context, DialogPlus dialogPlus) {
        this.context = context;
        this.dialogPlus = dialogPlus;

    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected List<Type> doInBackground(Void... params) {
        TypeController categoryController = new TypeController();
        return categoryController.getAllType();
    }

    @Override
    protected void onPostExecute(final List<Type> type) {
        try {
            if (type.size() > 0) {

                for (int i = 0; i < type.size(); i++) {
                    type.get(i).setCheck(false);
                }
                final ImageView image_rp1=(ImageView) dialogPlus.findViewById(R.id.image_rp1);
                final ImageView image_rp2=(ImageView) dialogPlus.findViewById(R.id.image_rp2);
                final ImageView image_rp3=(ImageView) dialogPlus.findViewById(R.id.image_rp3);

                final TextView screen10_txt_report1 = (TextView) dialogPlus.findViewById(R.id.screen10_txt_report1);
                final TextView screen10_txt_report2 = (TextView) dialogPlus.findViewById(R.id.screen10_txt_report2);
                final TextView screen10_txt_report3 = (TextView) dialogPlus.findViewById(R.id.screen10_txt_report3);

                final TextView screen10_number_report1 = (TextView) dialogPlus.findViewById(R.id.screen10_number_report1);
                final TextView screen10_number_report2 = (TextView) dialogPlus.findViewById(R.id.screen10_number_report2);
                final TextView screen10_number_report3 = (TextView) dialogPlus.findViewById(R.id.screen10_number_report3);

                final LinearLayout linearLayout1 = (LinearLayout) dialogPlus.findViewById(R.id.screen10_report1);
                final LinearLayout linearLayout2 = (LinearLayout) dialogPlus.findViewById(R.id.screen10_report2);
                final LinearLayout linearLayout3 = (LinearLayout) dialogPlus.findViewById(R.id.screen10_report3);

                screen10_txt_report1.setText(context.getResources().getString(R.string.report_type1));
                screen10_number_report1.setText(type.get(0).getNumber_report() + " " + context.getResources().getString(R.string.bt_Report));

                screen10_txt_report2.setText(context.getResources().getString(R.string.report_type2));
                screen10_number_report2.setText(type.get(1).getNumber_report() + " " + context.getResources().getString(R.string.bt_Report));

                screen10_txt_report3.setText(context.getResources().getString(R.string.report_type3));
                screen10_number_report3.setText(type.get(2).getNumber_report() + " " + context.getResources().getString(R.string.bt_Report));

                SharedPreferences pref = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                final int type_report = pref.getInt("type_report", 0);
                if(type_report==0||type_report==1){
                    editor.putInt("type_report", 1);
                    editor.commit();
                    linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                    linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                    linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                    image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                    image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                    image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                    screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.black));

                    screen10_number_report1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                }else if(type_report==2){
                    editor.putInt("type_report", 2);
                    editor.commit();
                    linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                    linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                    linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                    image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                    image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                    image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                    screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.black));

                    screen10_number_report2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                }else{
                    editor.putInt("type_report", 3);
                    editor.commit();
                    linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                    linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                    linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                    image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                    image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                    image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                    screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.black));

                    screen10_number_report3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                }

                image_rp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("type_report", 1);
                        editor.commit();
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));

                    }
                });
                image_rp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editor.putInt("type_report", 2);
                        editor.commit();
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));

                    }
                });
                image_rp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editor.putInt("type_report", 3);
                        editor.commit();
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));

                    }
                });

                screen10_txt_report1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("type_report", 1);
                        editor.commit();
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    }
                });

                screen10_txt_report2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("type_report", 2);
                        editor.commit();
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                    }
                });

                screen10_txt_report3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("type_report", 3);
                        editor.commit();
                        linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                        linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        image_rp3.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter_active));
                        image_rp2.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));
                        image_rp1.setImageDrawable(context.getResources().getDrawable(R.drawable.baocao_filter));

                        screen10_txt_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_txt_report3.setTextColor(context.getResources().getColor(R.color.black));

                        screen10_number_report3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        screen10_number_report2.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));
                        screen10_number_report1.setTextColor(context.getResources().getColor(R.color.screen5_color_boder));

                    }
                });

            } else {
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
