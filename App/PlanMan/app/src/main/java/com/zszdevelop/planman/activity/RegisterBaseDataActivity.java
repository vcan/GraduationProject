package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.HelperRegister;
import com.zszdevelop.planman.bean.RegisterData;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterBaseDataActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_register_describe)
    TextView tvRegisterDescribe;
    @Bind(R.id.rb_register_male)
    RadioButton rbRegisterMale;
    @Bind(R.id.rb_register_female)
    RadioButton rbRegisterFemale;
    @Bind(R.id.tv_register_sex)
    LinearLayout tvRegisterSex;
    @Bind(R.id.tv_register_birthday)
    TextView tvRegisterBirthday;
    @Bind(R.id.ll_register_birthday)
    LinearLayout llRegisterBirthday;
    @Bind(R.id.tv_register_high)
    TextView tvRegisterHigh;
    @Bind(R.id.ll_register_high)
    LinearLayout llRegisterHigh;
    @Bind(R.id.tv_register_weight)
    TextView tvRegisterWeight;
    @Bind(R.id.ll_register_weight)
    LinearLayout llRegisterWeight;
    @Bind(R.id.btn_register_base_next)
    Button btnRegisterBaseNext;
    @Bind(R.id.rg_sex)
    RadioGroup rgSex;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private ArrayList<Integer> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> options2Items = new ArrayList<>();
    private ArrayList<Integer> options1ItemsHigh = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> options2ItemsHigh = new ArrayList<>();
    boolean isHigh = true;
    private RegisterData registerData;
    private OptionsPickerView pvOptionsHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_base_data);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }


    private void initView() {
        registerData = new RegisterData();

        initToolbar();
        initOptionTime();
        initOptionFigure();
        initOptionHigh();
    }


    private void initListener() {

        // 弹出生日选项
        tvRegisterBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });

        // 弹出身高选项
        tvRegisterHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptionsHigh.setLabels(".", "cm");
                pvOptionsHigh.setTitle("设置身高");
                isHigh = true;
                pvOptionsHigh.show();
            }
        });

        // 弹出体重选项
        tvRegisterWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.setLabels(".", "kg");
                pvOptions.setTitle("设置体重");
                isHigh = false;
                pvOptions.show();
            }
        });

        // 体重回调
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                float value = Float.parseFloat(String.format("%s.%s", options1Items.get(options1), options2Items.get(options1).get(option2)));

                    registerData.setGoalRecordData(value);
                    registerData.setGoalRecordType(ResultCode.WEIGHT_CODE);
                    tvRegisterWeight.setText(String.valueOf(value) + "kg");


            }
        });

        // 身高回调
        pvOptionsHigh.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                float value = Float.parseFloat(String.format("%s.%s", options1ItemsHigh.get(options1), options2ItemsHigh.get(options1).get(option2)));

                    registerData.setHigh(value);
                    tvRegisterHigh.setText(String.valueOf(value) + "cm");

            }
        });


        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_register_male:
                        registerData.setSex(UserConfig.MAN);
                        break;
                    case R.id.rb_register_female:
                        registerData.setSex(UserConfig.WOMAN);
                        break;
                }
            }
        });



        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                registerData.setBirthday(date.getTime());
                tvRegisterBirthday.setText(TimeUtil.getTime(date));
            }
        });

        btnRegisterBaseNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();

            }
        });

    }


    private void verifyData() {
        String birthday = tvRegisterBirthday.getText().toString().trim();
        String high = tvRegisterHigh.getText().toString().trim();
        String weight = tvRegisterWeight.getText().toString().trim();

        if (TextUtils.isEmpty(birthday)) {
            tvRegisterBirthday.setText("还没有填写生日");
            return;
        }
        if (TextUtils.isEmpty(high)) {
            tvRegisterHigh.setText("还没有填写身高");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            tvRegisterWeight.setText("还没有填写体重");
            return;
        }
        submitData();
    }


    private void fillData() {
        // 默认男的选中
        registerData.setSex(UserConfig.MAN);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人数据");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void submitData() {

        HelperRegister helper = HelperRegister.getInstance();
        helper.setRegisterData(registerData);
        Intent intent = new Intent(RegisterBaseDataActivity.this, RegisterActionActivity.class);
        startActivity(intent);

    }

    private void initOptionTime() {
        //时间选择器
//            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime = new TimePickerView(RegisterBaseDataActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        GregorianCalendar gcd = new GregorianCalendar(1993,8,7);
        pvTime.setTime( gcd.getTime());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
    }


    private void initOptionFigure() {
        //选项选择器
        pvOptions = new OptionsPickerView(RegisterBaseDataActivity.this);
        for (int i = 0; i < 100; i++) {
            options1Items.add(i+30);
        }

        ArrayList<Integer> optionsItemsNumber = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            optionsItemsNumber.add(i);
        }
        for (int i = 0; i < 100; i++) {
            options2Items.add(optionsItemsNumber);
        }

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        pvOptions.setCyclic(true, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮

        pvOptions.setSelectOptions(30, 0);

    }

    private void initOptionHigh() {
        //选项选择器
        pvOptionsHigh = new OptionsPickerView(RegisterBaseDataActivity.this);
        for (int i = 0; i < 150; i++) {
            options1ItemsHigh.add(i+100);
        }

        ArrayList<Integer> optionsItemsNumber = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            optionsItemsNumber.add(i);
        }
        for (int i = 0; i < 150; i++) {
            options2ItemsHigh.add(optionsItemsNumber);
        }

        //三级联动效果
        pvOptionsHigh.setPicker(options1ItemsHigh, options2ItemsHigh, true);
        pvOptionsHigh.setCyclic(true, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮

        pvOptionsHigh.setSelectOptions(75, 0);

    }

}
