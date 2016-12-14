package com.vasep.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;

import java.util.ArrayList;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<Article> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private GridLayoutManager mGridLayoutManager;

    public boolean isMoreLoading() {
        return isMoreLoading;
    }

    private boolean isMoreLoading= false;
    private int visibleThreshold = 2;
    private Context context;

    int lastVisibleItem, visibleItemCount, totalItemCount;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public AdapterItem(Context context, OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        itemList = new ArrayList<>();
        this.context = context;

    }

    public List<Article> getList(){
        return  itemList;
    }

    public void setGridLayoutManager(GridLayoutManager linearLayoutManager) {
        this.mGridLayoutManager = linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView) {
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mGridLayoutManager.getItemCount();
                lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();

                if (!isMoreLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new NewsHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dapter_recyclerview, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    public void addAll(List<Article> lst) {
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public Article getArticle(int count){
        Article article = itemList.get(count-1);
        return article;
    }

    public void addItemMore(List<Article> lst) {
        itemList.addAll(lst);
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHoder) {
            Article article = (Article) itemList.get(position);
            Picasso.with(context).load(article.getImage()).into(((NewsHoder) holder).imageView);
            ((NewsHoder) holder).txt_screen1_title.setText(article.getTitle());
            ((NewsHoder) holder).txt_screen1_category.setText(article.getCategory_name());
            ((NewsHoder) holder).txt_screen1_date.setText(ChangeDate.convertDate(article.getCreate_date()));

        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            itemList.remove(itemList.size() - 1);
            notifyItemRemoved(itemList.size());
        }
    }

    static class NewsHoder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_screen1_title;
        TextView txt_screen1_category;
        TextView txt_screen1_date;

        public NewsHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.screen1_image_item);
            txt_screen1_title = (TextView) itemView.findViewById(R.id.screen1_title_item);
            txt_screen1_category = (TextView) itemView.findViewById(R.id.screen_category_item);
            txt_screen1_date = (TextView) itemView.findViewById(R.id.screen_date_item);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }
}