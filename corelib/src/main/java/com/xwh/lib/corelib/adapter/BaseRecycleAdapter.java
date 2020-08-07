package com.xwh.lib.corelib.adapter;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bodyfast.zerofasting.fastic.corelib.R;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mObjects = new ArrayList<>();
    private Context context;
    private int[] views;
    int needLoadingMore = 0; //0 关闭  1 加载中   2 加载结束
    String loadingMoreMessage = "正在努力加载";
    String loadingMoreFinishMessage = "没有更多内容";

    public void setRecycleLinearMangaer(RecyclerView recycleView) {
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recycleView.setLayoutManager(layout);
    }

    public void setRecycleGridLayoutMangaer(RecyclerView recycleView, int num) {
        LinearLayoutManager layout = new GridLayoutManager(context, num);
        recycleView.setLayoutManager(layout);
    }

    public BaseRecycleAdapter(Context context, List<T> objects, @LayoutRes int... layout) {
        mObjects = objects;
        this.context = context;
        this.views = layout;
    }


    public void openLoadMore() {
        needLoadingMore = 1;
        notifyDataSetChanged();
    }


    public void setLoadingMoreMessage(String loadingMoreMessage, String loadingMoreFinishMessage) {
        this.loadingMoreMessage = loadingMoreMessage;
        this.loadingMoreFinishMessage = loadingMoreFinishMessage;
    }

    public void closeLoadingMore() {
        needLoadingMore = 0;
    }

    public void finishLoadingMore() {
        needLoadingMore = 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        if (itemViewType == -1) {
            return new LoadingMoreViewHolder(getLoadingView());
        } else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(getItemView(itemViewType), null, true));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (mObjects.size() > 10 && mObjects.size() - i < 3) {
            loadingMore();
        }
        if (mObjects.size() > i) {
            bindView((MyViewHolder) viewHolder, mObjects.get(i), i);
        } else {
            if (viewHolder instanceof BaseRecycleAdapter.LoadingMoreViewHolder) {
                LoadingMoreViewHolder moreViewHolder = (LoadingMoreViewHolder) viewHolder;
                if (needLoadingMore == 1) {
                    moreViewHolder.loadMore.setText(loadingMoreMessage);
                    moreViewHolder.loadMore.setStreamState(true);
                } else if (needLoadingMore == 2) {
                    moreViewHolder.loadMore.setText(loadingMoreFinishMessage);
                    moreViewHolder.loadMore.setStreamState(false);
                } else {
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (needLoadingMore != 0) {
            if (position >= mObjects.size()) {
                return -1;
            }

        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (needLoadingMore != 0 && mObjects.size() > 10) {
            return mObjects.size() + 1;
        }

        return mObjects == null ? 0 : mObjects.size();
    }

    public void addData(T data) {
        mObjects.add(data);
        notifyItemChanged(mObjects.size());
    }

    public void addData(int index, T data) {
        mObjects.add(index, data);
        notifyItemChanged(index);
    }

    public void clearAll() {
        mObjects.clear();
        notifyDataSetChanged();
    }

    public void addDataAll(List<T> data) {
        mObjects.addAll(data);
        notifyItemChanged(mObjects.size() - data.size() - 1, mObjects.size());
    }

    public void setData(List<T> data) {
        mObjects.clear();
        mObjects.addAll(data);
        notifyDataSetChanged();
    }

    public void addDataAll(int index, List<T> data) {
        mObjects.addAll(index, data);
        notifyItemChanged(mObjects.size() - data.size() - 1, mObjects.size());
    }

    public int getItemView(int position) {
        if (views.length > position) {
            return views[position];
        } else
            return views[0];
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public View find(@IdRes int id) {
            return itemView.findViewById(id);
        }
    }

    class LoadingMoreViewHolder extends RecyclerView.ViewHolder {
        MyTextView loadMore;

        public LoadingMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            loadMore = itemView.findViewById(R.id.text);
        }
    }

    public View getLoadingView() {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        MyTextView loadMore = new MyTextView(context);
        loadMore.setId(R.id.text);
        LinearLayout.LayoutParams www = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadMore.setLayoutParams(www);
        loadMore.setText(loadingMoreMessage);
        loadMore.setPadding(10, 20, 30, 20);
        loadMore.setTextColor(context.getResources().getColor(R.color.text_black));
        loadMore.setTextSize(12);
        linearLayout.addView(loadMore);
        return linearLayout;
    }

    public abstract void bindView(MyViewHolder viewHolder, T data, int postion);


    public void loadingMore() {

    }

}
