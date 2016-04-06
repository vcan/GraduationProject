package com.zszdevelop.planman.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ShengZhong on 2016/3/9.
 */
public class BodyDataViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_head_suggest_bmi)
   public TextView tvHeadSuggestBmi;
    @Bind(R.id.tv_head_suggest_bmi_value)
    public  TextView tvHeadSuggestBmiValue;
    @Bind(R.id.tv_suggest_weight_scope)
    public  TextView tvSuggestWeightScope;
    @Bind(R.id.tv_head_suggest_weight_scope_value)
    public TextView tvHeadSuggestWeightScopeValue;
    @Bind(R.id.ll_head_suggest_weight_scope)
    public LinearLayout llHeadSuggestWeightScope;
    @Bind(R.id.tv_head_suggest_weight)
    public TextView tvHeadSuggestWeight;
    @Bind(R.id.tv_head_suggest_weight_value)
    public  TextView tvHeadSuggestWeightValue;
    @Bind(R.id.ll_head_suggest_weight)
    public LinearLayout llHeadSuggestWeight;
    @Bind(R.id.tv_head_suggest_ree)
    public  TextView tvHeadSuggestRee;
    @Bind(R.id.tv_head_suggest_ree_value)
    public  TextView tvHeadSuggestReeValue;
    @Bind(R.id.ll_head_suggest_ree)
    public  LinearLayout llHeadSuggestRee;
    @Bind(R.id.tv_head_suggest_heart_rate)
    public  TextView tvHeadSuggestHeartRate;
    @Bind(R.id.tv_head_suggest_heart_rate_value)
    public  TextView tvHeadSuggestHeartRateValue;
    @Bind(R.id.ll_head_suggest_heart_rate)
    public  LinearLayout llHeadSuggestHeartRate;

    public BodyDataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
