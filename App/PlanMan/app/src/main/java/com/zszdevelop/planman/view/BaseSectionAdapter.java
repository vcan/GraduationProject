package com.zszdevelop.planman.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.base.BaseBean;
import com.zszdevelop.planman.utils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jimmy on 15/12/29.
 */
public abstract class BaseSectionAdapter<T extends BaseBean> extends RecyclerView.Adapter {

    private final static int ITEM_TYPE_HEAD = Integer.MIN_VALUE;
    private final static int ITEM_ADAPTER_OFFSET = 1;

    private Context context;
    private int layoutRes;
    private List<T> data;

    public BaseSectionAdapter(Context context, int layoutRes, List<T> data) {
        this.context = context;
        this.layoutRes = layoutRes;
        this.data = data;
    }

    public T getItem(int position) {
        if (data.size() <= 0) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEAD) {
            return onCreateHeadViewHolder(parent, ITEM_TYPE_HEAD);
        } else {
            return onCreateItemViewHolder(parent, viewType - ITEM_ADAPTER_OFFSET);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();

        if (type == ITEM_TYPE_HEAD && position == 0) {
            //head的相关逻辑写在这里
            LogUtils.e("处理头部的逻辑");
            onBindHeadView(holder, position);
        } else {
            //这个是处理常规ITEM的操作
            onBindItemView(holder, position - (hasHeader() ? 1 : 0) );
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 & hasHeader()) {
            return ITEM_TYPE_HEAD;
        } else {
            return super.getItemViewType(position) + ITEM_ADAPTER_OFFSET;
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = getBasicItemCount();
        if (hasHeader()) {
            itemCount += 1;
        }

        return itemCount;
    }

    public void appendData(List<T> list) {
        if (list == null) {
            return;
        }
        data.addAll(list);
    }

    public void clear() {
        LogUtils.e(data.size()+"前");
        data.clear();
        LogUtils.e(data.size() + "后");
        notifyDataSetChanged();
    }


    private int getBasicItemCount() {
        return data.size();
    }

    public abstract boolean hasHeader();


    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    public abstract RecyclerView.ViewHolder onCreateHeadViewHolder(ViewGroup parent, int viewType);


    public abstract void onBindItemView(RecyclerView.ViewHolder holder, int position);

    public abstract void onBindHeadView(RecyclerView.ViewHolder holder, int position);

    protected class OtherViewHolder extends RecyclerView.ViewHolder {


        public OtherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
