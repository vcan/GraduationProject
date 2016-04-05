package com.zszdevelop.planman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.activity.RecordFigureActivity;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class PlanChangeFragment extends BaseFragment{

    @Bind(R.id.lcv_plan_change)
    LineChartView lcvPlanChange;

    ArrayList<GoalRecordInfo> goalRecordInfos;
    @Bind(R.id.fab_plan_change)
    FloatingActionButton fabPlanChange;

    private LineChartData lineData;
    private int goalRecordType;
    private String chartsName;


    public static PlanChangeFragment newInstanceFragment(ArrayList<GoalRecordInfo> goalRecordInfos,int goalRecordType) {
        PlanChangeFragment fragment = new PlanChangeFragment();
        fragment.goalRecordInfos = goalRecordInfos;
        fragment.goalRecordType = goalRecordType;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_plan_change;
    }

    @Override
    protected void onBindFragment(View view) {
        initChart();
        initListener();

    }

    private void initListener() {
        fabPlanChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordFigureActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    public void refreshFragment(ArrayList<GoalRecordInfo> data) {
        goalRecordInfos = data;
        generateInitialLineData();
    }

    private void initChart() {
        generateInitialLineData();
//        generateLineData(ChartUtils.COLOR_GREEN, 0);

    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData() {


        float heightMax = 0;
        float heightMin = 0;
        switch (goalRecordType){
            case ResultCode.WEIGHT_CODE:
               heightMax = 150;
                heightMin = 20;
                chartsName = "体重";
                break;
            case ResultCode.CHEST_CODE:
                heightMax = 100;
                heightMin = 30;
                chartsName = "胸围";
                break;
            case ResultCode.LOIN_CODE:
                heightMax = 100;
                heightMin = 30;
                chartsName = "腰围";
                break;
            case ResultCode.LEFT_ARM_CODE:
                heightMax = 60;
                heightMin = 15;
                chartsName = "左臂围";
                break;
            case ResultCode.RIGHT_ARM_CODE:
                chartsName = "右臂围";
                heightMax = 60;
                heightMin = 15;
                break;
            case ResultCode.SHOULDER_CODE:
                chartsName = "肩宽";
                heightMax = 100;
                heightMin = 15;
                break;

        }


        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < goalRecordInfos.size(); ++i) {
            values.add(new PointValue(i, goalRecordInfos.get(i).getGoalRecordData()).setLabel(String.valueOf(goalRecordInfos.get(i).getGoalRecordData())));
            axisValues.add(new AxisValue(i).setLabel(TimeUtil.timestampToData(goalRecordInfos.get(i).getGoalRecordTime())));

//            values.add(new PointValue(4, 0.5f).setLabel("Good Enough".toCharArray()));
        }

        Line line = new Line(values);
        line.setHasLabels(true);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        // For build-up animation you have to disable viewport recalculation.
        lcvPlanChange.setViewportCalculationEnabled(false);
        // And set initial max viewport and current viewport- remember to set viewports after data.

        Viewport v = new Viewport(0, heightMax, 6, heightMin);
        lcvPlanChange.setMaximumViewport(v);
        lcvPlanChange.setCurrentViewport(v);
        lcvPlanChange.setZoomType(ZoomType.HORIZONTAL);


        Axis distanceAxis = new Axis();
        distanceAxis.setName(chartsName);
        distanceAxis.setTextColor(ChartUtils.COLOR_ORANGE);
        distanceAxis.setMaxLabelChars(4);
        distanceAxis.setValues(axisValues);


//        distanceAxis.setFormatter(new SimpleAxisValueFormatter().setAppendedText("km".toCharArray()));
        distanceAxis.setHasLines(true);
        distanceAxis.setHasTiltedLabels(true);
        lineData.setAxisXBottom(distanceAxis);

        lcvPlanChange.setLineChartData(lineData);
        

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
