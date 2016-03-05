package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.bean.FigureType;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.ToastUtil;
import com.zszdevelop.planman.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_choose_type)
    TextView tvChooseType;
    @Bind(R.id.tv_choose_goal_value)
    TextView tvChooseGoalValue;
    @Bind(R.id.tv_choose_current_value)
    TextView tvChooseCurrentValue;
    @Bind(R.id.tv_choose_goal_time)
    TextView tvChooseGoalTime;
    @Bind(R.id.tv_choose_current_time)
    TextView tvChooseCurrentTime;
    @Bind(R.id.tv_choose_describe)
    TextView tvChooseDescribe;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private OptionsPickerView pvOptionsType;

    private boolean isGoalTime = true;
    private boolean isGoalValue = true;

    private ArrayList<Integer> options1Items = new ArrayList<>();
    private ArrayList<FigureType> options1ItemTypes = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> options2Items = new ArrayList<>();

    private GoalInfo goalInfo = new GoalInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        initView();
        initlistener();
        fillData();
    }


    private void initView() {
        initToolbar();
        initOptionFigure();
        initOptionTime();
        initOptionPlanType();
    }


    private void initlistener() {

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                float value = Float.parseFloat(String.format("%s.%s", options1Items.get(options1), options2Items.get(options1).get(option2)));
                if (isGoalValue) {
                    goalInfo.setStopGoal(value);
                    tvChooseGoalValue.setText(String.valueOf(value) + "kg");
                } else {
                    goalInfo.setStartGoal(value);
                    tvChooseCurrentValue.setText(String.valueOf(value) + "kg");
                }

                ToastUtil.showToast("目标完成");
            }
        });
        //点击弹出选项选择器
        tvChooseGoalValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isGoalValue = true;
                pvOptions.show();
            }
        });

        //点击弹出选项选择器
        tvChooseCurrentValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isGoalValue = false;
                pvOptions.show();
            }
        });


        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (isGoalTime) {
                    if (date.getTime() < System.currentTimeMillis() + 1000 * 60 * 10) {
                        ToastUtil.showToast("目标时间不能小于当前时间");
                        return;
                    }
                    goalInfo.setStopTime(String.valueOf(date.getTime()));
                    tvChooseGoalTime.setText(TimeUtil.getTime(date));

                } else {

                    goalInfo.setStartTime(String.valueOf(date.getTime()));
                    tvChooseCurrentTime.setText(TimeUtil.getTime(date));

                }
            }
        });

        //弹出时间选择器
        tvChooseGoalTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isGoalTime = true;
                pvTime.show();
            }
        });

         //弹出时间选择器
        tvChooseCurrentTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isGoalTime = false;
                pvTime.show();
            }
        });

        pvOptionsType.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                FigureType figureType = options1ItemTypes.get(options1);
                int type = figureType.getFigureType();
                goalInfo.setGoalType(type);
                tvChooseType.setText(figureType.getPickerViewText());
                ToastUtil.showToast("选择类型完成");
            }
        });

        tvChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptionsType.show();
            }
        });

        tvChooseDescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void fillData() {
    }


    private void initOptionFigure() {
        //选项选择器
        pvOptions = new OptionsPickerView(PlanActivity.this);
        for (int i = 0; i < 300; i++) {
            options1Items.add(i);
        }

        ArrayList<Integer> optionsItemsNumber = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            optionsItemsNumber.add(i);
        }
        for (int i = 0; i < 300; i++) {
            options2Items.add(optionsItemsNumber);
        }


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
//        pvOptions.setLabels(".", "cm/kg");
        pvOptions.setTitle("目标值");
        pvOptions.setCyclic(true, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮

        pvOptions.setSelectOptions(50, 0);

    }


    private void initOptionPlanType() {
        //选项选择器
        pvOptionsType = new OptionsPickerView(PlanActivity.this);
        options1ItemTypes.add(new FigureType(ResultCode.SHOULDER_CODE,"肩宽"));
        options1ItemTypes.add(new FigureType(ResultCode.LOIN_CODE,"腰围"));
        options1ItemTypes.add(new FigureType(ResultCode.WEIGHT_CODE,"体重"));
        options1ItemTypes.add(new FigureType(ResultCode.CHEST_CODE,"胸围"));
        options1ItemTypes.add(new FigureType(ResultCode.LEFT_ARM_CODE,"左臂围"));
        options1ItemTypes.add(new FigureType(ResultCode.RIGHT_ARM_CODE,"右臂围"));


        //三级联动效果
        pvOptionsType.setPicker(options1ItemTypes);
        pvOptionsType.setTitle("目标类型");
        pvOptionsType.setCyclic(false);
        pvOptionsType.setSelectOptions(2);

    }


    private void initOptionTime() {
        //时间选择器
//            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime = new TimePickerView(PlanActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
    }


    private void initToolbar() {
        toolbar.setTitle("新增计划");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Snackbar.make(MainActivity.this, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
                        intent = new Intent(PlanActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_plan:
                        intent = new Intent(PlanActivity.this, PlanActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_record_figure:
                        intent = new Intent(PlanActivity.this, RecordFigureActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_search_food:
                        intent = new Intent(PlanActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.FOOD_CODE);
                        startActivity(intent);
                        break;

                    case R.id.navigation_search_sport:
                        intent = new Intent(PlanActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
}
