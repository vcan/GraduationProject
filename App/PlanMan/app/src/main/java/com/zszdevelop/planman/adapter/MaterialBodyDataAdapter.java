package com.zszdevelop.planman.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view_holder.BodyDataViewHolder;
import com.zszdevelop.planman.view_holder.ConsumeRecordViewHolder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/12.
 */
public class MaterialBodyDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;


    private Context context;
    private List<ConsumeRecordInfo> lists;
    private BodyData bodyData;
    private int layoutRes;
    private int headLayout;


    private float standardWeight;
    private float bmi;
    private int intakeCC;

    public MaterialBodyDataAdapter(Activity context, int headLayout, int layoutRes, List<ConsumeRecordInfo> data) {

        this.headLayout = headLayout;
        this.layoutRes = layoutRes;
        this.context = context;
        this.lists = data;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {

        return lists.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(context).inflate(headLayout, parent, false);
                return new BodyDataViewHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
                return new ConsumeRecordViewHolder(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                BodyDataViewHolder headHolder= (BodyDataViewHolder) itemHolder;
                if (bodyData == null){
                    return;
                }
                standardWeight = bodyData.getStandardWeight();
                bmi = bodyData.getBmi();
                intakeCC = bodyData.getIntakeCC();
                float maxHeart = bodyData.getMaxHeart();
//        中低强度的运动应该达到人最大心率(最大心率=220-实际年龄)的60%~75%


                headHolder.tvHeadSuggestBmiValue.setText(String.format("%.2f", bmi));
                headHolder.tvHeadSuggestReeValue.setText(String.format("%s大卡", intakeCC));
                headHolder.tvHeadSuggestWeightValue.setText(String.format("%.2fKG", standardWeight));
                headHolder.tvHeadSuggestWeightScopeValue.setText(String.format("%.2f~%.2fKG", standardWeight * 0.9, standardWeight * 1.1));
                headHolder.tvHeadSuggestHeartRateValue.setText(String.format("%s 次/分钟到 %s 次/分钟", maxHeart * 0.6, maxHeart * 0.75));

                break;
            case TYPE_CELL:
                ConsumeRecordViewHolder holderItem = (ConsumeRecordViewHolder) itemHolder;

                // 通过getItem 取得数据
                ConsumeRecordInfo item = getItem(position);
                holderItem.tv_cr_time.setText(TimeUtil.timestampToData(item.getConsumeRecordTime()));
                LogUtils.e(TimeUtil.timestampToData(item.getConsumeRecordTime()) + ">>>>" + item.getConsumeRecordTime());
                holderItem.tv_consume_cc.setText(String.format("摄入:%s大卡", item.getConsumeCC()));

                Gson gson = new Gson();
                Type listType = new TypeToken<List<SlidingDeckModel>>() {
                }.getType();
                List<SlidingDeckModel> modelList = gson.fromJson(item.getConsumeRecordContent(), listType);

                holderItem.ll_consume_content.removeAllViews();
                for (int i = 0; i < modelList.size(); i++) {
                    TextView textView = new TextView(context);
                    textView.setText(String.format("%s : %s 大卡", modelList.get(i).getSlidingName(), modelList.get(i).getTotalCC()));
                    holderItem.ll_consume_content.addView(textView);
                }
                break;
        }
    }

    public ConsumeRecordInfo getItem(int position) {

        return lists.get(position - 1);
    }


    public void setHeaderData(BodyData bodyData) {
        if (bodyData == null) {
            return;
        }
        this.bodyData = bodyData;
        notifyDataSetChanged();

    }
}
