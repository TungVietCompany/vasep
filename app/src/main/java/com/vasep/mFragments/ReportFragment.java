package com.vasep.mFragments;

import android.content.Intent;
import android.graphics.Color;
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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.vasep.activity.ReportDetailActivity;
import com.vasep.adapter.AdapterHome;
import com.vasep.adapter.AdapterItem;
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

public class ReportFragment extends Fragment implements AHBottomNavigation.OnTabSelectedListener,AdapterItem.OnLoadMoreListener
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

        itemList = new ArrayList<Article>();
        swipeRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefresh);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_report);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterItem(getContext(),this);
        mAdapter.setGridLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(this);

        /*rView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(),ReportDetailActivity.class);
                        startActivity(intent);
                    }
                }));
*/

        toolbar = (Toolbar)rootView.findViewById(R.id.tool_bar);
        ((AppCompatActivity)(getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity)(getActivity())).getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        ((AppCompatActivity)(getActivity())).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionBar = ((AppCompatActivity)(getActivity())).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        holder = new ViewHolder(R.layout.dialog_report);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), R.style.DialogSlideAnim));
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.getWindow().setGravity(Gravity.BOTTOM);
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                dialog.setContentView(R.layout.dialog_report);
//                dialog.show();
                showCompleteDialog(holder,Gravity.BOTTOM,clickListener,itemClickListener,dismissListener,cancelListener,false);
            }
        });

        //toolbar bottom
        bottomNavigation= (AHBottomNavigation) rootView.findViewById(R.id.myBottomNavigation_report);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
        loadData(0);
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
        },2000);
    }

    @Override
    public void onLoadMore() {
        mAdapter.setProgressMore(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setProgressMore(false);
                int start = mAdapter.getItemCount();
                int id = Integer.parseInt(mAdapter.getArticle(start).getId());
                GetListArticleNew getListArticle = new GetListArticleNew(getContext(),mAdapter,2, 4,Integer.parseInt(mAdapter.getArticle(start).getId()),2);
                getListArticle.execute();
                mAdapter.setMoreLoading(false);
            }
        },2000);
    }

    private void loadData(int from) {
        GetListArticleNew getListArticle = new GetListArticleNew(getContext(),mAdapter,1, 4,from,2);
        getListArticle.execute();

    }
}
