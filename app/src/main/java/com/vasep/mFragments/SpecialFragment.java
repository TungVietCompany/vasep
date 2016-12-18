package com.vasep.mFragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.vasep.activity.MainActivity;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterMenu;
import com.vasep.async.GetAllCategory;
import com.vasep.async.GetAllCategoryMenu;
import com.vasep.async.GetListArticle;
import com.vasep.async.GetListArticleNew;
import com.vasep.controller.Common;
import com.vasep.recyclerclick.RecyclerItemClickListener;
import com.vasep.R;

import butterknife.Bind;

/**
 * Created by Oclemmy on 5/10/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 * Fragment shown when crim navigation item is clicked.
 */
public class SpecialFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener,AdapterItem.OnLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener  {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //MaterialSearchView searchView;

    RecyclerView rView;
    AdapterHome adapter_home;
    GridLayoutManager gridLayoutManager;
    Toolbar toolbar;
    AHBottomNavigation bottomNavigation;

    //@Bind(R.id.screen1_image_top)
    ImageView screen1_image_top;

    //@Bind(R.id.screen1_category_top)
    TextView screen1_category_top;

    //@Bind(R.id.screen1_date_top)
    TextView screen1_date_top;

    //@Bind(R.id.screen1_title_top)
    TextView screen1_title_top;
    private SwipeRefreshLayout swipeRefresh;
    private AdapterItem mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.crime_fragment, container, false);

        /*khai bao*/
        screen1_image_top = (ImageView)rootView.findViewById(R.id.screen1_image_top);
        screen1_category_top = (TextView) rootView.findViewById(R.id.screen1_category_top);
        screen1_date_top = (TextView) rootView.findViewById(R.id.screen1_date_top);
        screen1_title_top = (TextView) rootView.findViewById(R.id.screen1_title_top);
        /*chèn dữ liệu vào recylerview*/
        /*chèn dữ liệu vào recylerview*/
        swipeRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        rView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterItem(getContext(),this);
        mAdapter.setGridLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(rView);
        swipeRefresh.setOnRefreshListener(this);

        /*khởi tạo toolbar*/
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) (getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        ((AppCompatActivity) (getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity) (getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true); // show or hide the default home button
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
                final String language = pref.getString("language", null);
                String language_type;
                if (adapterMenu.getCategories() != null) {
                    if (language == null || language.equals("vi")) {
                        AdapterMenu adapterMenu1 = new AdapterMenu(getContext(), adapterMenu.getCategories());
                        recyclerView.setAdapter(adapterMenu1);
                    }else{
                        AdapterMenu adapterMenu1 = new AdapterMenu(getContext(), adapterMenu.getCategories());
                        recyclerView.setAdapter(adapterMenu1);
                    }

                } else {
                    GetAllCategoryMenu getAllCategoryMenu = new GetAllCategoryMenu(getContext(), recyclerView, mAdapter, adapterMenu, 2);
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
                        try {
                            if (isChecked) {
                                editor.putString("language", "vi");
                                editor.commit();
                                try {
                                    MainActivity.getINSTANCE().setLanguage("vi");
                                    catalog_title.setText("DANH MỤC");
                                    language_title.setText("Ngôn ngữ");
                                    btn_login.setText("Đăng nhập");

                                } catch (Exception err) {
                                }


                            } else {
                                editor.putString("language", "en");
                                editor.commit();
                                try {
                                    MainActivity.getINSTANCE().setLanguage("en");
                                    catalog_title.setText("CATALOG");
                                    language_title.setText("Language");
                                    btn_login.setText("Sign in");
                                } catch (Exception err) {
                                }

                            }
                            dialog.dismiss();
                        }catch (Exception err){}

                    }
                });

                ImageView close_up = (ImageView) dialog.findViewById(R.id.close_up);
                close_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        /*bottom bar*/
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        bottomNavigation = (AHBottomNavigation) rootView.findViewById(R.id.myBottomNavigation_ID);
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

        this.createNavItems();
        loadData(0);

        ImageView search = (ImageView)rootView.findViewById(R.id.screen2_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder viewHolder = new ViewHolder(R.layout.dialog_search);
                showCompleteDialogSearch(viewHolder, Gravity.TOP,clickListenersearch,itemClickListenersearch,
                        dismissListenersearch,cancelListenersearch,false);
            }
        });

        return rootView;
    }

    private void createNavItems() {
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
        bottomNavigation.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 1) {
            callFragment(new NewsFragment());
        } else if (position == 2) {
            callFragment(new ReportFragment());
        }
    }

    public void callFragment(Fragment fragment) {
        FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.frame_main_all, fragment);
        transaction.commit();
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
                GetListArticleNew getListArticle = new GetListArticleNew(getContext(), Common.LOAD_TOP,Integer.parseInt(mAdapter.getArticle(start).getId()),1,mAdapter,rView,2,screen1_image_top,screen1_date_top,screen1_title_top,screen1_category_top);
                getListArticle.execute();
            }
        },1000);
    }

    private void loadData(int from) {
        GetListArticleNew getListArticle = new GetListArticleNew(getContext(),Common.LOAD_TOP,from,1,mAdapter,rView,1,screen1_image_top,screen1_date_top,screen1_title_top,screen1_category_top);
        getListArticle.execute();

    }

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

        /*GetAllCategory getAllCategory = new GetAllCategory(getContext(),rv_search,rView,mAdapter,dialog,0);
        getAllCategory.execute();*/

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
