package com.zszdevelop.planman.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy on 15/12/14.
 */
public abstract class BaseAdapter<T extends BaseBean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private List<T> data;
    private int layout;
    private Context context;

    public BaseAdapter(Context context, int layoutResource, List<T> data) {
        this.context = context;
        this.layout = layoutResource;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        onBindItemViewHolder((VH) holder, position);
    }

    public abstract void onBindItemViewHolder(final VH holder, int position);

    /**
     * 直接根据下标获取当前Item的数据对象
     *
     * @param position Item的位置s
     * @return 对象存在则返回对象实体，否则返回null
     */
    public T getItem(int position) {

        if (data.size() > 0) {
            return data.get(position);
        } else {
            return null;
        }
    }

    private int getBaseItemCount() {
        return data.size()+1;
    }

    /**
     * 如果有出现加载更多的Item，则需要把Adapter的数量+1，否则无法显示出来。
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return getBaseItemCount();
    }

    public void appendData(List<T> list) {
        if (list == null) {
            return;
        }
        data.addAll(list);
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }



}
