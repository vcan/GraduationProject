package com.zszdevelop.planman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.bean.BodyData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class SuggestFragment extends BaseFragment {

    @Bind(R.id.tv_suggest_title)
    TextView tvSuggestTitle;
    @Bind(R.id.tv_suggest_bmi)
    TextView tvSuggestBmi;
    @Bind(R.id.tv_suggest_bmi_value)
    TextView tvSuggestBmiValue;
    @Bind(R.id.tv_suggest_bmi_describe)
    TextView tvSuggestBmiDescribe;
    @Bind(R.id.tv_suggest_weight_scope)
    TextView tvSuggestWeightScope;
    @Bind(R.id.tv_suggest_weight_scope_value)
    TextView tvSuggestWeightScopeValue;
    @Bind(R.id.ll_suggest_weight_scope)
    LinearLayout llSuggestWeightScope;
    @Bind(R.id.tv_suggest_weight)
    TextView tvSuggestWeight;
    @Bind(R.id.tv_suggest_weight_value)
    TextView tvSuggestWeightValue;
    @Bind(R.id.ll_suggest_weight)
    LinearLayout llSuggestWeight;
    @Bind(R.id.tv_suggest_ree)
    TextView tvSuggestRee;
    @Bind(R.id.tv_suggest_ree_value)
    TextView tvSuggestReeValue;
    @Bind(R.id.ll_suggest_ree)
    LinearLayout llSuggestRee;
    @Bind(R.id.tv_suggest_ree_describe)
    TextView tvSuggestReeDescribe;
    @Bind(R.id.tv_suggest_heart_rate)
    TextView tvSuggestHeartRate;
    @Bind(R.id.tv_suggest_heart_rate_value)
    TextView tvSuggestHeartRateValue;
    @Bind(R.id.ll_suggest_heart_rate)
    LinearLayout llSuggestHeartRate;
    @Bind(R.id.tv_suggest_heart_rate_describe)
    TextView tvSuggestHeartRateDescribe;

    private BodyData bodyData;
    private float standardWeight;
    private float bmi;
    private int intakeCC;


    public static SuggestFragment newInstanceFragment(BodyData bodyData) {
        SuggestFragment fragment = new SuggestFragment();
        fragment.bodyData = bodyData;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_suggest;
    }

    @Override
    protected void onBindFragment(View view) {
        fillData();
    }



    private void fillData() {

        standardWeight = bodyData.getStandardWeight();
        bmi = bodyData.getBmi();
        intakeCC = bodyData.getIntakeCC();
        float maxHeart = bodyData.getMaxHeart();
//        中低强度的运动应该达到人最大心率(最大心率=220-实际年龄)的60%~75%

        tvSuggestBmiValue.setText(String.valueOf(bmi));
        tvSuggestReeValue.setText(String.format("%s大卡", intakeCC));
        tvSuggestWeightValue.setText(String.format("%.2fKG", standardWeight));
        tvSuggestWeightScopeValue.setText(String.format("%.2f~%.2fKG", standardWeight * 0.9, standardWeight * 1.1));
        tvSuggestHeartRateValue.setText(String.format("%s 次/分钟到 %s 次/分钟", maxHeart * 0.6, maxHeart * 0.75));
    }





    @Override
    protected void lazyLoad() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
