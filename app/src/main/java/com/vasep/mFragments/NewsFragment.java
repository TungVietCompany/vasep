package com.vasep.mFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.vasep.activity.CatalogActivity;
import com.vasep.activity.ChangePasswordActivity;
import com.vasep.activity.MainActivity;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.activity.SignInActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
import com.vasep.adapter.AdapterMenu;
import com.vasep.async.BannerAsync;
import com.vasep.async.GetAllCategory;
import com.vasep.async.GetAllCategoryMenu;
import com.vasep.async.GetListArticle;
import com.vasep.async.GetListArticleNew;

import com.vasep.async.NotiAsync;
import com.vasep.async.GetListArticleSearch;
import com.vasep.controller.Common;
import com.vasep.models.Article;
import com.vasep.models.Banner;
import com.vasep.models.Category;
import com.vasep.recyclerclick.RecyclerItemClickListener;
import com.vasep.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Oclemmy on 5/10/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 * Fragment shown when crim navigation item is clicked.
 */
public class NewsFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener, AdapterItem.OnLoadMoreListener
        , SwipeRefreshLayout.OnRefreshListener {

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

    //@Bind(R.id.screen1_tops)
    RelativeLayout screen1_tops,relative_crime;
    AppBarLayout appBarLayout;

    private SwipeRefreshLayout swipeRefresh;
    private AdapterItem mAdapter,mAdapterNew;
    View rootView;

    public ProgressBar pBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.crime_fragment, container, false);

        pBar = (ProgressBar)rootView.findViewById(R.id.pBar);
        /*khai bao*/
        screen1_tops=(RelativeLayout) rootView.findViewById(R.id.screen1_tops);
        relative_crime=(RelativeLayout) rootView.findViewById(R.id.relative_crime);


        screen1_image_top = (ImageView) rootView.findViewById(R.id.screen1_image_top);
        screen1_category_top = (TextView) rootView.findViewById(R.id.screen1_category_top);
        screen1_date_top = (TextView) rootView.findViewById(R.id.screen1_date_top);
        screen1_title_top = (TextView) rootView.findViewById(R.id.screen1_title_top);
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        /*chèn dữ liệu vào recylerview*/
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterItem(getContext(),getActivity(), this);
        mAdapterNew= new AdapterItem(getContext(),getActivity(), this);
        mAdapter.setGridLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(rView);
        swipeRefresh.setOnRefreshListener(this);

        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        /*khơi tạo xxx*/
        appBarLayout=(AppBarLayout) rootView.findViewById(R.id.appBarLayout);
        /*khởi tạo toolbar*/
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) (getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle("Material Search");

        ((AppCompatActivity) (getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity) (getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true); // show or hide the default home button
        actionBar.setLogo(R.mipmap.icon_menu);

        BannerAsync bannerAsync= new BannerAsync(getContext());
        bannerAsync.execute();

        /*bottom bar*/
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        bottomNavigation = (AHBottomNavigation) rootView.findViewById(R.id.myBottomNavigation_ID);
        ViewTreeObserver viewTreeObserver = bottomNavigation.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomNavigation.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = bottomNavigation.getMeasuredWidth();
                int height = bottomNavigation.getMeasuredHeight();
                swipeRefresh.setPadding(0, 0, 0, height);
            }
        });
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
        loadData(0);

        ImageView search = (ImageView) rootView.findViewById(R.id.screen2_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder viewHolder = new ViewHolder(R.layout.dialog_search);
                showCompleteDialogSearch(viewHolder, Gravity.TOP, clickListenersearch, itemClickListenersearch,
                        dismissListenersearch, cancelListenersearch, false);
            }
        });

        final ImageView img_language = (ImageView) rootView.findViewById(R.id.language);
        final String languages = pref.getString("language", null);

        if (languages == null || languages.equals("vi")) {
            img_language.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.vi));
        }else{
            img_language.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.en));
        }
        img_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languagess = pref.getString("language", null);
                if (languagess == null || languagess.equals("vi")) {
                    editor.putString("language", "en");
                    editor.commit();
                    MainActivity.types=2;
                    MainActivity.getINSTANCE().setLanguage("en");

                }else{
                    editor.putString("language", "vi");
                    editor.commit();
                    MainActivity.types=2;
                    MainActivity.getINSTANCE().setLanguage("vi");
                }
            }
        });



        return rootView;
    }

    private void createNavItems() {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem = new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_Highlight), R.mipmap.noibat);
        AHBottomNavigationItem dramaItem = new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_News), R.mipmap.tintuc);
        AHBottomNavigationItem docstem = new AHBottomNavigationItem(getActivity().getResources().getString(R.string.bt_Report), R.mipmap.baocao);

        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);
        //set properties
        bottomNavigation.setDefaultBackgroundColor(getActivity().getResources().getColor(R.color.background_toolbarsss));
        bottomNavigation.setAccentColor(Color.parseColor("#fefffa"));
        bottomNavigation.setInactiveColor(R.color.bottom_inactiveColor);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        //bottomNavigation.setForceTint(true);
        //bottomNavigation.setColored(true);

        //set current item
        bottomNavigation.setCurrentItem(1);

        View view = toolbar.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogmenu);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.bg_menu)));
                final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recylerview_menu);
                GridLayoutManager gridview = new GridLayoutManager(getContext(), 3);
                recyclerView.setLayoutManager(gridview);
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
                final SharedPreferences.Editor editor = pref.edit();
                final String language = pref.getString("language", null);
                final AdapterMenu adapterMenu;
                if (language == null || language.equals("vi")) {

                    adapterMenu = new AdapterMenu(getContext(), null);
                } else {
                    adapterMenu = new AdapterMenu(getContext(), null);

                }
                final TextView catalog_title = (TextView) dialog.findViewById(R.id.calatoge_menu);
                //catalog_title.setText(R.string.catalog);

                final TextView language_title = (TextView) dialog.findViewById(R.id.language_title);
                language_title.setText(R.string.language);

                final TextView btn_login = (TextView) dialog.findViewById(R.id.btn_login);
                btn_login.setText(R.string.login);
                if (adapterMenu.getCategories() != null) {
                    AdapterMenu adapterMenu1 = new AdapterMenu(getContext(), adapterMenu.getCategories());
                    recyclerView.setAdapter(adapterMenu1);

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
                                    List<Category> list = adapterMenu.getCategories();
                                    for (int i = 0; i < list.size(); i++) {
                                        list.get(i).setLanguage_type(0);
                                    }
                                    MainActivity.types=2;
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
                                    List<Category> list = adapterMenu.getCategories();
                                    for (int i = 0; i < list.size(); i++) {
                                        list.get(i).setLanguage_type(1);
                                    }
                                    MainActivity.types=2;
                                    MainActivity.getINSTANCE().setLanguage("en");
                                    catalog_title.setText("CATALOG");
                                    language_title.setText("Language");
                                    btn_login.setText("Sign in");


                                } catch (Exception err) {
                                }

                            }
                            dialog.dismiss();
                        } catch (Exception err) {
                        }

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


                final List<Article> list1 = new ArrayList<Article>();
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent= new Intent(getActivity(), CatalogActivity.class);
                                intent.putExtra("catagory",adapterMenu.getCategories().get(position));
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }));
                TextView btn_changePass= (TextView) dialog.findViewById(R.id.btn_changePass);
                final String user_id = pref.getString("user_id", "");
                if(user_id.equals("")){
                    btn_login.setText(R.string.login);
                    btn_changePass.setVisibility(View.GONE);
                }else{
                    btn_login.setText(R.string.log_out);
                    btn_changePass.setVisibility(View.VISIBLE);
                }
                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), SignInActivity.class);
                        getActivity().startActivityForResult(intent,1);
                        if(!user_id.equals("")) {
                            editor.putString("user_id","");
                            editor.putString("pass","");
                            editor.commit();
                        }
                    }
                });


                btn_changePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                        getContext().startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            callFragment(new SpecialFragment());
        } else if (position == 2) {
            callFragment(new ReportFragment());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback
        String abc="xyz";
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
                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    String language = pref.getString("language", null);
                    String catalog = pref.getString("catalog", null);
                    if (null == catalog) {
                        catalog = "";
                    }
                    if (null == language) {
                        language = "vi";
                    }
                    mAdapter.setType(0);
                    GetListArticleSearch getListArticleSearch = new GetListArticleSearch(getContext(),pBar,mAdapterNew, catalog, "", rView, mAdapter, 2, Common.LOAD_TOP, mAdapter.getArticle(start), 0, language, 0, 0, 0);
                    getListArticleSearch.execute();
                } catch (Exception err) {
                    String exx= err.getMessage();
                }
            }
        }, 1000);
    }

    private void loadData(int from) {
        try {
            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
            String language = pref.getString("language", null);
            String catalog = pref.getString("catalog", null);
            if (null == catalog) {
                catalog = "";
            }
            if (null == language) {
                language = "vi";
            }
            mAdapter.setType(0);
            GetListArticleSearch getListArticleSearch = new GetListArticleSearch(getActivity(),getContext(),pBar, Common.LOAD_TOP, from, 0, mAdapter, rView, 1, screen1_image_top, screen1_date_top, screen1_title_top, screen1_category_top, screen1_tops,relative_crime,appBarLayout,new AdapterItem(getContext(),getActivity(),this), language, catalog, "", 0, 0, 0);
            getListArticleSearch.execute();
        } catch (Exception err) {

        }
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
                .setMargin(0, toolbar.getHeight(), 0, 0)
                .create();

        RecyclerView rv_search = (RecyclerView) dialog.findViewById(R.id.rv_search);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_search.setLayoutManager(gridLayoutManager);

        GetAllCategory getAllCategory = new GetAllCategory(getContext(),getActivity(),rv_search,dialog,mAdapter,mAdapterNew,rView,0,screen1_image_top, screen1_date_top, screen1_title_top, screen1_category_top,screen1_tops,relative_crime,appBarLayout);
        getAllCategory.execute();

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
