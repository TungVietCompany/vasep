package com.vasep.mFragments;

import android.app.Dialog;
import android.content.Intent;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.vasep.async.GetListArticle;
import com.vasep.async.GetListArticleNew;
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

public class ReportFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener
        ,SwipeRefreshLayout.OnRefreshListener{
    AHBottomNavigation bottomNavigation;

    RecyclerView rView;
    AdapterHome adapter_home;

    Holder holder;
    Toolbar toolbar;

    private AdapterItem mAdapter;
    private ArrayList<Article> itemList;
    private SwipeRefreshLayout swipeRefresh;

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_fragment, container, false);
        rView = (RecyclerView)rootView.findViewById(R.id.recycler_report);

        swipeRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        rView.setLayoutManager(mLayoutManager);
        swipeRefresh.setOnRefreshListener(this);

        GetListArticle getListArticle = new GetListArticle(getContext(), 6, 0,2, rView);
        getListArticle.execute();

        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar_report);

        ((AppCompatActivity)(getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity)(getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity)(getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.icon_menu);
        actionBar.setDisplayUseLogoEnabled(true);

        /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogmenu);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recylerview_menu);
                GridLayoutManager gridview = new GridLayoutManager(getContext(), 3);
                recyclerView.setLayoutManager(gridview);

                AdapterMenu adapter = new AdapterMenu(getContext());
                recyclerView.setAdapter(adapter);

                dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
                dialog.show();

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
        this.createNavItems();
        return rootView;
    }


    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem=new AHBottomNavigationItem("Nổi bật",R.drawable.noibat);
        AHBottomNavigationItem dramaItem=new AHBottomNavigationItem("Tin tức",R.drawable.tintuc);
        AHBottomNavigationItem docstem=new AHBottomNavigationItem("Báo cáo",R.drawable.baocao);

        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#0c4ca3"));
        bottomNavigation.setAccentColor(Color.parseColor("#fefffa"));
        //set current item
        bottomNavigation.setCurrentItem(2);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_report, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_search_report:
                showCompleteDialog(holder,Gravity.TOP,clickListener,itemClickListener,dismissListener,cancelListener,false);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            CardView card_report = (CardView)view.findViewById(R.id.card_report);
            card_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"hu",Toast.LENGTH_LONG).show();
                }
            });
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

        final String marketing[]={"EU","Quốc tế","Hàn Quốc"};
        ListView lv=(ListView)dialog.findViewById(R.id.lv_maketing);
        //ArrayAdapter<String>adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, marketing);
        AdapterLvMaketing adapter = new AdapterLvMaketing(getContext(),marketing);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        dialog.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);


            }
        },2000);
    }
}
