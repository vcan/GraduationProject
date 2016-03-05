package com.zszdevelop.planman.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.http.ToastUtil;
import com.zszdevelop.planman.utils.RiseNumberTextView;
import com.zszdevelop.planman.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class FigureFragment extends BaseFragment {


    @Bind(R.id.tv_figure_title)
    TextView tvFigureTitle;

    @Bind(R.id.tv_figure_time)
    TextView tvFigureTime;
    @Bind(R.id.btn_figure_complete)
    Button btnFigureComplete;
    @Bind(R.id.rntv_figure_number)
    RiseNumberTextView rntvFigureNumber;
    @Bind(R.id.tv_figure_old_time)
    TextView tvFigureOldTime;


    private ArrayList<Integer> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> options2Items = new ArrayList<>();

    private int type;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private float newFigure;
    private long newFigureTime =System.currentTimeMillis();

    public static FigureFragment newInstanceFragment(int type) {
        FigureFragment fragment = new FigureFragment();
        fragment.type = type;
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_figure;
    }

    @Override
    protected void onBindFragment(View view) {

        initView();
        initListener();
        fillData();

    }

    private void initView() {

        initOptionTime();
        initOptionFigure();


    }

    private void initOptionFigure() {
        //选项选择器
        pvOptions = new OptionsPickerView(getActivity());
        for (int i = 0;i< 300 ;i++){
            options1Items.add(i);
        }



        ArrayList<Integer> optionsItemsNumber = new ArrayList<>();
        for (int i = 0; i < 100 ; i++) {
            optionsItemsNumber.add(i);
        }
        for (int i =0;i<300;i++){
            options2Items.add(optionsItemsNumber);
        }


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        if (type == ResultCode.WEIGHT_CODE){
            pvOptions.setLabels(".", "kg");
        }else {
            pvOptions.setLabels(".", "cm");
        }
        pvOptions.setTitle("添加记录");
        pvOptions.setCyclic(true, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
//        String format = String.format("%.2f", newFigure);
//        String[] split = format.split(".");
//        pvOptions.setSelectOptions(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        pvOptions.setSelectOptions(50, 0);

    }

    private void initOptionTime() {
        //时间选择器
//            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
    }

    private void initListener() {

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                newFigure = Float.parseFloat(String.format("%s.%s", options1Items.get(options1), options2Items.get(options1).get(option2)));
                //返回的分别是三个级别的选中位置
                rntvFigureNumber.withNumber(newFigure)
                        .setDuration(3000)
                        .start();
            }
        });
        //点击弹出选项选择器
        rntvFigureNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });


        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                newFigureTime = date.getTime();
                if (date.getTime()> System.currentTimeMillis()){
                    ToastUtil.showToast("您选择的不能超过当前时间");
                    return;
                }

                tvFigureTime.setText(TimeUtil.getTime(date));
            }
        });

        //弹出时间选择器
        tvFigureTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });


        // 添加记录
        btnFigureComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFigure();


            }
        });
    }


    private void fillData() {

        tvFigureTime.setText(TimeUtil.getTime(new Date()));

        String url = String.format(API.FIRST_RECORD_FIGUTR_URI, Helper.getUserId(), type);
        HttpRequest.get(url, new HttpRequestListener() {
                    @Override
                    public void onSuccess(String json) {
                        Gson gson = new Gson();
                        GoalRecordInfo goalRecordInfo = gson.fromJson(json, GoalRecordInfo.class);

                        String typeStr = "体重";
                        int goalRecordType = goalRecordInfo.getGoalRecordType();
                        if (goalRecordType == ResultCode.CHEST_CODE) {
                            typeStr = "胸围";
                        } else if (goalRecordType == ResultCode.LOIN_CODE) {
                            typeStr = "腰围";
                        } else if (goalRecordType == ResultCode.LEFT_ARM_CODE) {
                            typeStr = "左臂围";
                        } else if (goalRecordType == ResultCode.RIGHT_ARM_CODE) {
                            typeStr = "右臂围";
                        } else if (goalRecordType == ResultCode.SHOULDER_CODE) {
                            typeStr = "肩宽";
                        }
                        tvFigureTitle.setText(String.format("您最近一次记录%s", typeStr));

                        String goalRecordTime = goalRecordInfo.getGoalRecordTime();
                        if (!TextUtils.isEmpty(goalRecordTime)) {
                            tvFigureOldTime.setText(String.format("上次记录时间%s", TimeUtil.timestampToData(Long.parseLong(goalRecordTime))));

                        } else {
                            tvFigureOldTime.setText("您暂无此记录");
                        }

                        newFigure = goalRecordInfo.getGoalRecordData();
                        rntvFigureNumber.withNumber(newFigure)
                                .setDuration(3000)
                                .start();

                    }
                }

        );

}

    private void submitFigure() {
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",String.valueOf(Helper.getUserId()));
        map.put("authToken",Helper.getToken());
        map.put("goalRecordTime",String.valueOf(newFigureTime));
        map.put("goalRecordType",String.valueOf(type));
        map.put("goalRecordData",String.valueOf(newFigure));

        HttpRequest.post(API.SUBMIT_FIGUTR_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                ToastUtil.showToast("记录添加成功");
//               int modifyStatus = JsonUtil.getIntJsonValueByKey(json, "modifyStatus");
//                if (modifyStatus == ResultCode.MODIFY_SUCCESS){
//
//                }
            }
        });
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
