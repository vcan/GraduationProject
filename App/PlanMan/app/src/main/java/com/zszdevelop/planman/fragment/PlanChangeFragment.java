package com.zszdevelop.planman.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.bean.GoalRecordInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class PlanChangeFragment extends BaseFragment {

    @Bind(R.id.lcv_plan_change)
    LineChartView lcvPlanChange;

    ArrayList<GoalRecordInfo> goalRecordInfos;

    private LineChartData lineData;
    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};




    public static PlanChangeFragment newInstanceFragment(ArrayList<GoalRecordInfo> goalRecordInfos) {
        PlanChangeFragment fragment = new PlanChangeFragment();
        fragment.goalRecordInfos = goalRecordInfos;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_plan_change;
    }

    @Override
    protected void onBindFragment(View view) {
        initChart();

    }

    public void refreshFragment(ArrayList<GoalRecordInfo> data){
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
        int numValues = 7;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < goalRecordInfos.size(); ++i) {
            values.add(new PointValue(i, goalRecordInfos.get(i).getGoalRecordData()));
            axisValues.add(new AxisValue(i).setLabel(goalRecordInfos.get(i).getGoalRecordTime()));
        }
//        for (int i = 0; i < numValues; ++i) {
//            values.add(new PointValue(i, 0));
//            axisValues.add(new AxisValue(i).setLabel(days[i]));
//        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        lcvPlanChange.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        lcvPlanChange.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 110, 6, 0);
        lcvPlanChange.setMaximumViewport(v);
        lcvPlanChange.setCurrentViewport(v);

        lcvPlanChange.setZoomType(ZoomType.HORIZONTAL);
    }


    private void generateLineData(int color, float range) {
        // Cancel last animation if not finished.
        lcvPlanChange.cancelDataAnimation();

        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color);
//        for (PointValue value : line.getValues()) {
//            // Change target only for Y value.
//            value.setTarget(value.getX(), (float) Math.random() * range);
//        }

//        for ()
        // Start new data animation with 300ms duration;
        lcvPlanChange.startDataAnimation(300);
    }


    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected() {

            generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
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
