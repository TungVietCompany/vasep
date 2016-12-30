package com.vasep.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.vasep.R;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.async.BannerAsync;
import com.vasep.async.GetAllCategory;
import com.vasep.async.GetListArticleSearch;
import com.vasep.controller.Common;
import com.vasep.mFragments.NewsFragment;
import com.vasep.mFragments.ReportFragment;
import com.vasep.mFragments.SpecialFragment;
import com.vasep.models.Article;
import com.vasep.models.Category;

import butterknife.Bind;

import static com.vasep.R.id.swipeRefresh;

/**
 * Created by Administrator on 31/12/2016.
 */

public class CatalogActivity extends LocalizationActivity implements AdapterItem.OnLoadMoreListener
        , SwipeRefreshLayout.OnRefreshListener {

    ProgressBar progressBar;
    RecyclerView rView;
    AdapterHome adapter_home;
    GridLayoutManager gridLayoutManager;
    Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private AdapterItem mAdapter,mAdapterNew;
    boolean flag=false;
    Category category;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_fragment);

        Intent intent= getIntent();
        category = (Category) intent.getSerializableExtra("catagory");

        progressBar = (ProgressBar)findViewById(R.id.pBar);
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_catalog);
        rView = (RecyclerView) findViewById(R.id.recycler_catalog);
        GridLayoutManager mLayoutManager = new GridLayoutManager(CatalogActivity.this, 1);
        rView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterItem(CatalogActivity.this,CatalogActivity.this, this);
        mAdapterNew= new AdapterItem(CatalogActivity.this,CatalogActivity.this, this);
        mAdapter.setType(1);
        mAdapter.setGridLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(rView);
        swipeRefresh.setOnRefreshListener(this);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        final String languages = pref.getString("language", "vi");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_catalog);
        setSupportActionBar(toolbar);

        //toolbar.setLogo(R.drawable.back_btn);
        ImageView btn_back=(ImageView) findViewById(R.id.btn_back_catalog);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        TextView title_catalog=(TextView) findViewById(R.id.title_catalog);
        if(languages.equals("vi")) {
            title_catalog.setText(category.getName());
        }
        else{
            title_catalog.setText(category.getEng_name());
        }

          /*click vào nut home tren toolbar*/

        BannerAsync bannerAsync= new BannerAsync(CatalogActivity.this);
        bannerAsync.execute();

        if(!flag) {
            loadData(0);
            flag=true;
        }

        ImageView search = (ImageView) findViewById(R.id.catalog_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder viewHolder = new ViewHolder(R.layout.dialog_search);
                showCompleteDialogSearch(viewHolder, Gravity.TOP, clickListenersearch, itemClickListenersearch,
                        dismissListenersearch, cancelListenersearch, false);


            }
        });


        final ImageView img_language = (ImageView) findViewById(R.id.language_catalog);


        if (languages == null || languages.equals("vi")) {
            img_language.setImageDrawable(getResources().getDrawable(R.drawable.vi));
        }else{
            img_language.setImageDrawable(getResources().getDrawable(R.drawable.en));
        }
        img_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languagess = pref.getString("language", null);
                if (languagess == null || languagess.equals("vi")) {
                    editor.putString("language", "en");
                    editor.commit();
                    MainActivity.types=1;
                    MainActivity.getINSTANCE().setLanguage("en");

                }else{
                    editor.putString("language", "vi");
                    editor.commit();
                    MainActivity.types=1;
                    MainActivity.getINSTANCE().setLanguage("vi");

                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback
        String abc="xyz";
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                loadData(0);

            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mAdapter.setProgressMore(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setProgressMore(false);
                int start = mAdapter.getItemCount()*2;
                try {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                    String language = pref.getString("language", "vi");
                    mAdapter.setType(-1);
                    GetListArticleSearch getListArticleSearch = new GetListArticleSearch(CatalogActivity.this,progressBar, mAdapterNew,category.getId(), "", rView, mAdapter, 2, Common.LOAD_TOP, mAdapter.getArticle(start),-1, language, 0, 0, 0);
                    getListArticleSearch.execute();
                } catch (Exception err) {
                }
            }
        }, 1000);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        try {
            finish();
            startActivity(getIntent());
        }catch (Exception err){}

    }

    private void loadData(int from) {
        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            String language = pref.getString("language", "vi");
            String catalog = pref.getString("catalog", "");

            mAdapter.setType(-1);
            GetListArticleSearch getListArticleSearch = new GetListArticleSearch(CatalogActivity.this,CatalogActivity.this,progressBar,Common.LOAD_TOP, from, -1, mAdapter, rView, 1, null, null, null, null,null,null,null,new AdapterItem(CatalogActivity.this,CatalogActivity.this, this), language, category.getId(), "", 0, 0, 0);
            getListArticleSearch.execute();
        } catch (Exception err) {
            String errr = err.getMessage();
        }

    }
    public void LoadSearch(String title){
        SharedPreferences pref =getSharedPreferences("MyPref",MODE_PRIVATE);
        String language = pref.getString("language", "vi");
        GetListArticleSearch getListArticleSearch = new GetListArticleSearch(CatalogActivity.this,CatalogActivity.this,progressBar,Common.LOAD_TOP, 0, -1, mAdapter, rView, 1, null, null, null, null,null,null,null,new AdapterItem(CatalogActivity.this,CatalogActivity.this,this), language, category.getId(),title, 0, 0, 0);
        getListArticleSearch.execute();
    }

    private void showCompleteDialogSearch(Holder holder, int gravity,
                                          OnClickListener clickListener, OnItemClickListener itemClickListener,
                                          OnDismissListener dismissListener, OnCancelListener cancelListener,
                                          boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(CatalogActivity.this)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(R.color.bg_trans)
                .create();

        TextView title_catalog_search=(TextView) dialog.findViewById(R.id.titl_categorys);
        title_catalog_search.setVisibility(View.GONE);

        RecyclerView rv_search = (RecyclerView) dialog.findViewById(R.id.rv_search);
        rv_search.setVisibility(View.GONE);

        final TextView txt_search = (TextView)dialog.findViewById(R.id.screen10_btn_search);
        final TextView screen10_txt_search = (TextView)dialog.findViewById(R.id.screen10_txt_search);
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapter.setType(-1);
                LoadSearch(screen10_txt_search.getText().toString());
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                dialog.dismiss();
            }
        });


        //GetAllCategory getAllCategory = new GetAllCategory(CatalogActivity.this,CatalogActivity.this, rv_search, dialog, mAdapter, mAdapterNew, rView, 2, null, null, null, null, null, null, null);
        //getAllCategory.execute();
        dialog.show();


    }

    //search

    OnClickListener clickListenersearch = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {

        }
    };

    OnItemClickListener itemClickListenersearch = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

        }
    };

    OnDismissListener dismissListenersearch = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {

        }
    };

    OnCancelListener cancelListenersearch = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {

        }
    };
}
