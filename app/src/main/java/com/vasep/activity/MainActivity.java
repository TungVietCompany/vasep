package com.vasep.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.async.GetAllCategory;
import com.vasep.async.GetArticleAsync;
import com.vasep.async.NotiAsync;
import com.vasep.mFragments.NewsFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.vasep.R;
import com.vasep.mFragments.ReportFragment;
import com.vasep.mFragments.SpecialFragment;
import com.vasep.models.Article;
import com.vasep.models.Expire;


public class MainActivity extends LocalizationActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //MaterialSearchView searchView;
    public static MainActivity INSTANCE;
    public int type;
    public static int types;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = MainActivity.this.getSharedPreferences("MyPref", MainActivity.this.MODE_PRIVATE);
        try {
            //firebase
            String session_id = FirebaseInstanceId.getInstance().getToken().toString();
            final SharedPreferences.Editor editor = pref.edit();
            if (pref.getString("firebase_id", null) == null) {
                editor.putString("firebase_id", session_id);
                editor.commit();
                NotiAsync notiAsync = new NotiAsync(MainActivity.this, session_id, "android");
                notiAsync.execute();

            }
            //end
        } catch (Exception er) {

        }

        INSTANCE = this;
        Intent i = getIntent();
        type = i.getIntExtra("type", 0);


        onNewIntent(getIntent());



        Log.d("sa", type + "");
        if (type == 0) {
            try {
                if (types == 1) {
                    callFragment(new SpecialFragment());
                } else if (types == 2) {
                    callFragment(new NewsFragment());
                } else if (types == 3) {
                    callFragment(new ReportFragment());
                } else {
                    callFragment(new SpecialFragment());
                }
            } catch (Exception err) {
                callFragment(new SpecialFragment());
            }

        } else if (type == 3) {
            type = 0;
            callFragment(new ReportFragment());
        } else if (type == 2) {
            type = 0;
            callFragment(new NewsFragment());
        } else if (type == 1) {
            callFragment(new SpecialFragment());
            type = 0;
        }
        String language = pref.getString("language", null);
        if (language != null) {
            setLanguage(language);
        } else {
            setLanguage("vi");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            finish();
            startActivity(getIntent());
        }
    }
    @Override
    public void onNewIntent(Intent intent){
        try {
            String id_type = getIntent().getStringExtra("id_type");
            if (id_type != null) {
                String[] arr = id_type.split("_");
                GetArticleAsync getArticleAsync = new GetArticleAsync(MainActivity.this, arr[0], arr[1]);
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

    public void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.frame_main_all, fragment);
        transaction.commit();
    }

    public static int getType() {
        return types;
    }

    public static void setType(int type) {
        MainActivity.types = types;
    }

    public static MainActivity getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(MainActivity INSTANCE) {
        MainActivity.INSTANCE = INSTANCE;
    }


}
