package com.zszdevelop.planman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.bean.Food;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseAdapter;
import com.zszdevelop.planman.view_holder.SearchViewHolder;

import java.util.List;


/**
 * Created by jimmy on 15/12/14.
 *
 */
public class SearchAdapter extends BaseAdapter<Food, SearchViewHolder> {

    private List<Food> lists;
    private Context context;
    public SearchAdapter(Context context, int layoutResource, List<Food> lists) {
        super(context, layoutResource, lists);
        this.context = context;
        this.lists = lists;
    }

    @Override
    public SearchViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(SearchViewHolder holder, final int position) {
        Food food = getItem(position);
        if (food == null){
            return;
        }

       holder.tv_calory_cc.setText(String.valueOf(food.getCalory()));
        holder.tv_search_title.setText(food.getName());

    }

    public interface  OnItemClickListener {
        void OnItemClicked(Food bean);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    };


}
