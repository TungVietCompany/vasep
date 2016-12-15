package com.vasep.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class GetAllType extends AsyncTask<Void,Void,List<Type>>{

    Context context;
    DialogPlus dialogPlus;
    public GetAllType(Context context,DialogPlus dialogPlus){
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
        try{
            if(type.size() > 0){

                for(int i = 0;i<type.size() ;i++){
                    type.get(i).setCheck(false);
                }

                TextView screen10_txt_report1 = (TextView)dialogPlus.findViewById(R.id.screen10_txt_report1);
                TextView screen10_txt_report2 = (TextView)dialogPlus.findViewById(R.id.screen10_txt_report2);
                TextView screen10_txt_report3 = (TextView)dialogPlus.findViewById(R.id.screen10_txt_report3);

                TextView screen10_number_report1 = (TextView)dialogPlus.findViewById(R.id.screen10_number_report1);
                TextView screen10_number_report2 = (TextView)dialogPlus.findViewById(R.id.screen10_number_report2);
                TextView screen10_number_report3 = (TextView)dialogPlus.findViewById(R.id.screen10_number_report3);

                final LinearLayout linearLayout1 = (LinearLayout)dialogPlus.findViewById(R.id.screen10_report1);
                final LinearLayout linearLayout2 = (LinearLayout)dialogPlus.findViewById(R.id.screen10_report2);
                final LinearLayout linearLayout3 = (LinearLayout)dialogPlus.findViewById(R.id.screen10_report3);

                screen10_txt_report1.setText(type.get(0).getName());
                screen10_number_report1.setText(type.get(0).getNumber_report()+ " báo cáo");

                screen10_txt_report2.setText(type.get(1).getName());
                screen10_number_report2.setText(type.get(1).getNumber_report()+ " báo cáo");

                screen10_txt_report3.setText(type.get(2).getName());
                screen10_number_report3.setText(type.get(2).getNumber_report()+ " báo cáo");

                linearLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.get(0).isCheck()){
                            linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));

                        }else {
                            linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                            linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                            linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        }
                    }
                });
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.get(1).isCheck()){
                            linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        }else {
                            linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                            linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                            linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        }
                    }
                });
                linearLayout3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.get(2).isCheck()){
                            linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        }else {
                            linearLayout3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborderpay));
                            linearLayout1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                            linearLayout2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customnoborderpay));
                        }
                    }
                });
            }else{
                Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, Information.no_data, Toast.LENGTH_SHORT).show();
        }

    }
}
