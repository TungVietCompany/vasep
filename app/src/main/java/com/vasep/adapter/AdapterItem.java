package com.vasep.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.vasep.R;
import com.vasep.async.BannerAsync;
import com.vasep.controller.ChangeDate;
import com.vasep.models.Article;

import java.util.ArrayList;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_BANNER = 0;

    private ArrayList<Article> itemList;
    private ArrayList<String> urlList;
    private OnLoadMoreListener onLoadMoreListener;
    private GridLayoutManager mGridLayoutManager;

    public boolean isMoreLoading() {
        return isMoreLoading;
    }

    private boolean isMoreLoading = false;
    private int visibleThreshold =2;
    private Context context;
    private int type = 1;
    int lastVisibleItem, visibleItemCount, totalItemCount;
    int page = 0;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public AdapterItem(Context context, OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        itemList = new ArrayList<>();

        this.context = context;


    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Article> getList() {
        return itemList;
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
        return position % 4 != 0 ? VIEW_ITEM : VIEW_BANNER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //if (viewType == VIEW_ITEM) {
            return new NewsHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dapter_recyclerview, parent, false));
        //} else {
            //return new BannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_banner, parent, false));
        //}

    }

    public void addAll(List<Article> lst) {
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void clearData() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public int getArticle(int count) {
        if (count > 0) {
            try {
                if (count < itemList.size()) {
                    int index = Integer.parseInt(itemList.get(count).getId());
                    return index;
                } else {
                    int index = Integer.parseInt(itemList.get(count - 1).getId());
                    return index;
                }
            }catch (Exception err){
                int index = Integer.parseInt(itemList.get(count - 2).getId());
                return index;
            }
        } else {
            return 0;
        }

    }

    public void addItemMore(List<Article> lst) {
        itemList.addAll(lst);
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHoder) {
            try {
                ((NewsHoder) holder).card2.setVisibility(View.INVISIBLE);
                Article article = itemList.get((position) * 2);

                Glide.with(context).load(article.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(((NewsHoder) holder).imageView);
                ((NewsHoder) holder).txt_screen1_title.setText(article.getTitle());
                ((NewsHoder) holder).txt_screen1_category.setText(article.getCategory_name());
                ((NewsHoder) holder).txt_screen1_date.setText(ChangeDate.convertDate(article.getCreate_date()));
                if (Integer.parseInt(article.getPrice()) > 0) {
                    if (article.getIs_lock().equals("1")) {
                        ((NewsHoder) holder).txt_lock.setVisibility(View.VISIBLE);
                        ((NewsHoder) holder).img_lock.setVisibility(View.VISIBLE);
                    }
                }
                if((position) * 2+1<itemList.size()) {
                    ((NewsHoder) holder).card2.setVisibility(View.VISIBLE);
                    Article article2 = itemList.get((position) * 2+1);

                    Glide.with(context).load(article2.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(((NewsHoder) holder).imageView2);
                    ((NewsHoder) holder).txt_screen1_title2.setText(article2.getTitle());
                    ((NewsHoder) holder).txt_screen1_category2.setText(article2.getCategory_name());
                    ((NewsHoder) holder).txt_screen1_date2.setText(ChangeDate.convertDate(article2.getCreate_date()));
                    if (Integer.parseInt(article2.getPrice()) > 0) {
                        if (article2.getIs_lock().equals("1")) {
                            ((NewsHoder) holder).txt_lock2.setVisibility(View.VISIBLE);
                            ((NewsHoder) holder).img_lock2.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    ((NewsHoder) holder).card2.setVisibility(View.INVISIBLE);
                }

                if(position!=0 && (position)%2==0){

                    ((NewsHoder) holder).layout_banner.setVisibility(View.VISIBLE);
                    BannerAsync bannerAsync= new BannerAsync(context,((NewsHoder) holder).image_banner,page);
                    bannerAsync.execute();

                    page++;
                }


            } catch (Exception err) {
                String exx= err.getMessage();
            }

        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public int getItemCount() {

        if (itemList.size() % 2 == 0) {
            return itemList.size() / 2;
        } else {
            return itemList.size() / 2 + 1;
        }

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
        ImageView imageView,image_banner;
        TextView txt_screen1_title;
        TextView txt_screen1_category;
        TextView txt_screen1_date;

        TextView txt_lock;
        ImageView img_lock;

        ImageView imageView2;
        TextView txt_screen1_title2;
        TextView txt_screen1_category2;
        TextView txt_screen1_date2;

        TextView txt_lock2;
        ImageView img_lock2;

        CardView card2;
        LinearLayout layout_banner;

        public NewsHoder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.screen1_image_item);
            txt_screen1_title = (TextView) itemView.findViewById(R.id.screen1_title_item);
            txt_screen1_category = (TextView) itemView.findViewById(R.id.screen_category_item);
            txt_screen1_date = (TextView) itemView.findViewById(R.id.screen_date_item);

            txt_lock = (TextView) itemView.findViewById(R.id.txt_lock);
            img_lock = (ImageView) itemView.findViewById(R.id.img_lock);

            imageView2 = (ImageView) itemView.findViewById(R.id.screen1_image_item2);
            txt_screen1_title2 = (TextView) itemView.findViewById(R.id.screen1_title_item2);
            txt_screen1_category2 = (TextView) itemView.findViewById(R.id.screen_category_item2);
            txt_screen1_date2 = (TextView) itemView.findViewById(R.id.screen_date_item2);

            txt_lock2 = (TextView) itemView.findViewById(R.id.txt_lock2);
            img_lock2 = (ImageView) itemView.findViewById(R.id.img_lock2);

            card2=(CardView) itemView.findViewById(R.id.card2);

            layout_banner=(LinearLayout) itemView.findViewById(R.id.layout_banner);
            image_banner=(ImageView) itemView.findViewById(R.id.image_banner);
        }
    }

    static class BannerHolder extends RecyclerView.ViewHolder {
        ImageView image_banner;

        public BannerHolder(View itemView) {
            super(itemView);
            image_banner = (ImageView) itemView.findViewById(R.id.image_banner);

        }
    }

//    static class ProgressViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar pBar;
//
//        public ProgressViewHolder(View v) {
//            super(v);
//            pBar = (ProgressBar) v.findViewById(R.id.pBar);
//        }
//    }
}