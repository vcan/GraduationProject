package com.zszdevelop.planman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseAdapter;
import com.zszdevelop.planman.bean.ShowType;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.view_holder.ShowTypeViewHolder;

import java.util.List;


/**
 * Created by jimmy on 15/12/14.
 *
 */
public class ShowTypeAdapter extends BaseAdapter<ShowType, ShowTypeViewHolder> {

    private List<ShowType> lists;
    private Context context;
    public ShowTypeAdapter(Context context, int layoutResource, List<ShowType> showTypes) {
        super(context, layoutResource, showTypes);
        this.context = context;
        this.lists = showTypes;
    }

    @Override
    public ShowTypeViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_type, parent, false);
        return new ShowTypeViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ShowTypeViewHolder holder, final int position) {
        ShowType item = getItem(position);
        holder.btn_select_type.setText(item.getShowTypeStr());
//        holder.fg_small.
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("andaole ");
                itemClickListener.OnItemClicked(getItem(position));
            }
        });

    }

    public interface  OnItemClickListener {
        void OnItemClicked(ShowType bean);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    };


}
