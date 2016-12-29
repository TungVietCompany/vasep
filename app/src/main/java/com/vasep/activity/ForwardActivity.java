package com.vasep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.vasep.R;
import com.vasep.async.GetArticleAsync;

/**
 * Created by Administrator on 29/12/2016.
 */

public class ForwardActivity extends LocalizationActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //MaterialSearchView searchView;
    public static MainActivity INSTANCE;
    public int type;
    public static int types;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String id_type = getIntent().getExtras().getString("id_type");
            if (id_type != null) {
                String[] arr = id_type.split("_");
                GetArticleAsync getArticleAsync = new GetArticleAsync(ForwardActivity.this, arr[0], arr[1]);
                getArticleAsync.execute();
            } else {
            /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id_type",id);
            editor.commit();*/
            }
        }catch (Exception err){

        }

    }
    @Override
    public void onNewIntent(Intent intent){
        try {
            String id_type = getIntent().getExtras().getString("id_type");
            if (id_type != null) {
                String[] arr = id_type.split("_");
                GetArticleAsync getArticleAsync = new GetArticleAsync(ForwardActivity.this, arr[0], arr[1]);
                getArticleAsync.execute();
            } else {
            /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id_type",id);
            editor.commit();*/
            }
        }catch (Exception err){

        }
    }
}
