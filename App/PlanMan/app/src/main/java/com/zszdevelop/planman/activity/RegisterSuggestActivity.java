package com.zszdevelop.planman.activity;

import android.os.Bundle;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.HelperRegister;
import com.zszdevelop.planman.bean.ComsumeCCInfo;
import com.zszdevelop.planman.bean.RegisterData;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.utils.TimeUtil;

public class RegisterSuggestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_suggest);

        initView();
        initListener();
        fillData();
    }

    private void initView() {



    }

    private void initListener() {


    }

    private void fillData() {

        RegisterData registerData = HelperRegister.getInstance().getRegisterData();
        float goalRecordWeight = registerData.getGoalRecordData();
        float high = registerData.getHigh();

        // 活 动 内 容 活动系数
        // 卧床（全天） 1.2
        // 轻活动生活模式（多坐或缓步） 1.3
        // 一般活动度 1.5~1.75
        // 活动量大的生活模式（重工作者） 2.0
        float actionType = registerData.getActionType();

        // 男性静态能量消耗值 男性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) ＋ 5
        // 女性静态能量消耗值 女性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) - 161
        // 其每天所需的热量 = REE × 活动系数 = 1294 × 1.5 = 1941(大卡)
        int intakeCC = 1500;
        float consumeREE = 1500;
        try {
            String birthdayStr = String.valueOf(registerData.getBirthday());
            if (registerData.getSex() == UserConfig.MAN) {// 男性
                consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)- (5 * TimeUtil.BirthDayToAge(birthdayStr)) + 5);
            } else {// 女性
                consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high)- (5 * TimeUtil.BirthDayToAge(birthdayStr)) - 161);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        intakeCC = (int) (consumeREE * actionType);

        // 需要消耗的热量
        // 		一旦热量累积到达7700大卡，你的体重就会增加一公斤
        // 		怎么样的减肥速度最健康、最有效率呢？答案是每周减0.5-1公斤。
        //  	新手：每周瘦0.5公斤，7000/2/7 = 550 每天要多消耗550大卡
        int consumeCC = (int) (intakeCC +550);


        ComsumeCCInfo comsumeCCInfo = new ComsumeCCInfo();
        float highM = high/100;
        comsumeCCInfo.setBMI(goalRecordWeight / (highM * highM));
        comsumeCCInfo.setConsumeCC(consumeCC);
        comsumeCCInfo.setIntakeCC(intakeCC);
        comsumeCCInfo.setConsumeREE((int)consumeREE);

    }
}
