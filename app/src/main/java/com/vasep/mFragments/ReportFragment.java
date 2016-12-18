package com.vasep.mFragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.vasep.activity.MainActivity;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterLvMaketing;
import com.vasep.adapter.AdapterMenu;
import com.vasep.adapter.AdapterRecylerSearch;
import com.vasep.async.GetAllCategory;
import com.vasep.async.GetAllCategoryMenu;
import com.vasep.async.GetAllMarket;
import com.vasep.async.GetAllProduct;
import com.vasep.async.GetAllType;
import com.vasep.async.GetListArticle;
import com.vasep.async.GetListArticleNew;
import com.vasep.async.GetListArticleSearch;
import com.vasep.controller.Common;
import com.vasep.controller.CustomNumberPicker;
import com.vasep.models.Article;
import com.vasep.recyclerclick.RecyclerItemClickListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.vasep.R;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener,AdapterItem.OnLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener{
    AHBottomNavigation bottomNavigation;

    RecyclerView rView;

    Holder holder;
    Toolbar toolbar;

    private AdapterItem mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    View rootView;

    String language_type = "0";
    private static List<Article> list = new ArrayList<>();

    private boolean issearch = false;
    static String category_id,textsearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_fragment, container, false);
        rView = (RecyclerView)rootView.findViewById(R.id.recycler_report);
        setHasOptionsMenu(true);
        swipeRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        rView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterItem(getContext(),this);
        mAdapter.setGridLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(rView);
        swipeRefresh.setOnRefreshListener(this);

        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar_report);

        ((AppCompatActivity)(getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity)(getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity)(getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.icon_menu);
        actionBar.setDisplayUseLogoEnabled(true);

        /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogmenu);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.bg_menu)));
                final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recylerview_menu);
                GridLayoutManager gridview = new GridLayoutManager(getContext(), 3);
                recyclerView.setLayoutManager(gridview);
                final AdapterMenu adapterMenu = new AdapterMenu(getContext(), null);

                final TextView catalog_title= (TextView) dialog.findViewById(R.id.calatoge_menu);
                catalog_title.setText(R.string.catalog);

                final TextView language_title= (TextView) dialog.findViewById(R.id.language_title);
                language_title.setText(R.string.language);

                final TextView btn_login= (TextView) dialog.findViewById(R.id.btn_login);
                btn_login.setText(R.string.login);
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                String language = pref.getString("language", null);
                if(adapterMenu.getCategories() != null){
                    if (language == null || language.equals("vi")) {
                        AdapterMenu adapterMenu1 = new AdapterMenu(getContext(), adapterMenu.getCategories());
                        recyclerView.setAdapter(adapterMenu1);
                    }else{
                        AdapterMenu adapterMenu1 = new AdapterMenu(getContext(), adapterMenu.getCategories());
                        recyclerView.setAdapter(adapterMenu1);
                    }
                }else {
                    GetAllCategoryMenu getAllCategoryMenu = new GetAllCategoryMenu(getContext(), recyclerView,mAdapter, adapterMenu, 2);
                    getAllCategoryMenu.execute();
                }

                final Switch swtich = (Switch) dialog.findViewById(R.id.switch_menu);
                if (language == null || language.equals("vi")) {
                    swtich.setChecked(true);
                } else {
                    swtich.setChecked(false);
                }

                swtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            editor.putString("language", "vi");
                            editor.commit();
                            try {
                                MainActivity.getINSTANCE().setLanguage("vi");
                                catalog_title.setText("DANH MỤC");
                                language_title.setText("Ngôn ngữ");
                                btn_login.setText("Đăng nhập");

                            } catch (Exception err) {}


                        } else {
                            editor.putString("language", "en");
                            editor.commit();
                            try {
                                MainActivity.getINSTANCE().setLanguage("en");
                                catalog_title.setText("CATALOG");
                                language_title.setText("Language");
                                btn_login.setText("Sign in");
                            } catch (Exception err) {}

                        }
                        dialog.dismiss();
                    }
                });
                ImageView close_up=(ImageView) dialog.findViewById(R.id.close_up);
                close_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        ImageView search = (ImageView)rootView.findViewById(R.id.screen3_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder viewHolder = new ViewHolder(R.layout.dialog_search);

                showCompleteDialogSearch(viewHolder,Gravity.TOP,clickListenersearch,itemClickListenersearch,
                        dismissListenersearch,cancelListenersearch,false);
            }
        });

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        holder = new ViewHolder(R.layout.dialog_report);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompleteDialog(holder,Gravity.BOTTOM,clickListener,itemClickListener,dismissListener,cancelListener,false);
            }
        });

        //toolbar bottom
        bottomNavigation= (AHBottomNavigation) rootView.findViewById(R.id.myBottomNavigation_report);
        bottomNavigation.setOnTabSelectedListener(this);
        ViewTreeObserver viewTreeObserver = bottomNavigation.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomNavigation.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = bottomNavigation.getMeasuredWidth();
                int height = bottomNavigation.getMeasuredHeight();
                swipeRefresh.setPadding(0,0,0,height);
            }
        });
        bottomNavigation.setCurrentItem(2);
        this.createNavItems();
        loadData(0);
        return rootView;
    }


    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem=new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_Highlight),R.mipmap.noibat);
        AHBottomNavigationItem dramaItem=new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_News),R.mipmap.tintuc);
        AHBottomNavigationItem docstem=new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_Report),R.mipmap.baocao);

        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);

        //set properties
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setDefaultBackgroundColor(getActivity().getResources().getColor(R.color.background_toolbarsss));
        bottomNavigation.setAccentColor(Color.parseColor("#fefffa"));
        bottomNavigation.setInactiveColor(R.color.bottom_inactiveColor);
        //set current item
        bottomNavigation.setCurrentItem(2);

    }


    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position==0)
        {
            callFragment(new SpecialFragment());
        }else  if (position==1)
        {
            callFragment(new NewsFragment());
        }

    }

    public void callFragment(Fragment fragment) {
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.frame_main_all, fragment);
        transaction.commit();
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


    //end
    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
        }
    };

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

        }
    };

    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {

        }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {

        }
    };

    private void showCompleteDialogSearch(Holder holder, int gravity,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(android.R.color.transparent)
                .setMargin(0,toolbar.getHeight(),0,0)
                .create();

        RecyclerView rv_search = (RecyclerView)dialog.findViewById(R.id.rv_search);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        rv_search.setLayoutManager(gridLayoutManager);

        //GetAllCategory getAllCategory = new GetAllCategory(getContext(),issearch,category_id,textsearch,rv_search,rView,mAdapter,dialog,2);
        //getAllCategory.execute();

        dialog.show();
    }


    private void showCompleteDialog(Holder holder, int gravity,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(android.R.color.transparent)
                .setMargin(0,toolbar.getHeight(),0,0)
                .create();

        CustomNumberPicker lv=(CustomNumberPicker) dialog.findViewById(R.id.lv_maketing);
        GetAllMarket getAllMarket = new GetAllMarket(getContext(),lv);
        getAllMarket.execute();

        lv.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker

            }
        });


        CustomNumberPicker lv_product=(CustomNumberPicker)dialog.findViewById(R.id.lv_product);
        GetAllProduct getAllProduct = new GetAllProduct(getContext(),lv_product);
        getAllProduct.execute();

        lv_product.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker

            }
        });

        GetAllType getAllType = new GetAllType(getContext(),dialog);
        getAllType.execute();

        dialog.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                loadData(0);

            }
        },1000);
    }
    @Override
    public void onLoadMore() {
        mAdapter.setProgressMore(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setProgressMore(false);
                int start = mAdapter.getItemCount();
                if(mAdapter.getList().size() != 0 && !issearch){
                    GetListArticleNew getListArticle = new GetListArticleNew(getContext(),rView,mAdapter,2, Common.LOAD_TOP,Integer.parseInt(mAdapter.getArticle(start).getId()),2);
                    getListArticle.execute();
                }
                if(issearch){
                    GetListArticleSearch getListArticle = new GetListArticleSearch(getContext(),category_id,textsearch,rView,mAdapter,2, Common.LOAD_TOP,Integer.parseInt(mAdapter.getArticle(start).getId()),2);
                    getListArticle.execute();
                }

            }
        },1000);
    }

    private void loadData(int from) {
        GetListArticleNew getListArticle = new GetListArticleNew(getContext(),rView,mAdapter,1, Common.LOAD_TOP,from,2);
        getListArticle.execute();

    }
}
