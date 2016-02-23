package com.zszdevelop.planman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseAdapter;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view_holder.ConsumeRecordViewHolder;

import java.util.List;


/**
 * Created by jimmy on 15/12/14.
 *
 */
public class ConsumeRecordAdapter extends BaseAdapter<ConsumeRecordInfo, ConsumeRecordViewHolder> {

    private List<ConsumeRecordInfo> lists;
    private Context context;
    public ConsumeRecordAdapter(Context context, int layoutResource, List<ConsumeRecordInfo> consumeRecordInfo) {
        super(context, layoutResource, consumeRecordInfo);
        this.context = context;
        this.lists = consumeRecordInfo;
    }

    @Override
    public ConsumeRecordViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_consume_record, parent, false);
        return new ConsumeRecordViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ConsumeRecordViewHolder holder, final int position) {
        ConsumeRecordInfo consumeRecordInfo = getItem(position);

        holder.tv_cr_time.setText(TimeUtil.timestampToData(consumeRecordInfo.getConsumeRecordTime()));
        holder.tv_consume_cc.setText(String.format("今日消耗%s", consumeRecordInfo.getConsumeCC()));
        holder.tv_consume_content.setText(consumeRecordInfo.getConsumeRecordContent());

    }

    public interface  OnItemClickListener {
        void OnItemClicked(ConsumeRecordInfo bean);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    };


}
