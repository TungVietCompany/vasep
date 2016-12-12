package com.vasep.mFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.vasep.activity.NewsDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterMenu;
import com.vasep.async.GetListArticle;
import com.vasep.recyclerclick.RecyclerItemClickListener;
import com.vasep.R;

import butterknife.Bind;

/**
 * Created by Oclemmy on 5/10/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 * Fragment shown when crim navigation item is clicked.
 */
public class NewsFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener {

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
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setLayoutManager(gridLayoutManager);
        GetListArticle getListArticle = new GetListArticle(getContext(), 2, 0,0, rView,screen1_image_top,
                screen1_date_top,screen1_category_top,screen1_title_top);
        getListArticle.execute();


        /*khởi tạo toolbar*/
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) (getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity) (getActivity())).getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        ((AppCompatActivity) (getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity) (getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true); // show or hide the default home button
        actionBar.setLogo(R.drawable.filter_icon);
        actionBar.setDisplayUseLogoEnabled(true);

        /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogmenu);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recylerview_menu);
                GridLayoutManager gridview = new GridLayoutManager(getContext(), 3);
                recyclerView.setLayoutManager(gridview);

                AdapterMenu adapter = new AdapterMenu(getContext());
                recyclerView.setAdapter(adapter);

                dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
                dialog.show();

            }
        });

        /*bottom bar*/
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        bottomNavigation = (AHBottomNavigation) rootView.findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
        return rootView;
    }

    private void createNavItems() {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem = new AHBottomNavigationItem("Nổi bật", R.drawable.noibat);
        AHBottomNavigationItem dramaItem = new AHBottomNavigationItem("Tin tức", R.drawable.tintuc);
        AHBottomNavigationItem docstem = new AHBottomNavigationItem("Báo cáo", R.drawable.baocao);

        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#0c4ca3"));
        bottomNavigation.setAccentColor(Color.parseColor("#fefffa"));
        //set current item
        bottomNavigation.setCurrentItem(1);

    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            callFragment(new SpecialFragment());
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
}
