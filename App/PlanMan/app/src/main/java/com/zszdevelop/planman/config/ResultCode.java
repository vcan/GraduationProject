package com.zszdevelop.planman.config;

/**
 * Created by zhangshengzhong on 16/2/22.
 */
public class ResultCode {
    // 体重
    public static final int WEIGHT_CODE = 1;

    // 胸围
    public static final int CHEST_CODE = 2;

    // 腰围
    public static final int LOIN_CODE = 3;

    // 左臂围
    public static final int LEFT_ARM_CODE = 4;

    // 右臂围
    public static final int RIGHT_ARM_CODE = 5;

    // 腰围
    public static final int SHOULDER_CODE = 6;

    // 首页
    public static final int FIRST_PAGE_CODE = 1;

    // 吃
    public static final int FOOD_CODE= 1;

    // 运动
    public static final int SPORTS_CODE = 2;

    // 修改成功
    public static final int MODIFY_SUCCESS = 1;

     // 修改描述
    public static final int MODIFY_DESCRIBE = 10001;

    // 活 动 内 容 活动系数
    // 卧床（全天） 1.2
    // 轻活动生活模式（多坐或缓步） 1.3
    // 一般活动度 1.5~1.75
    // 活动量大的生活模式（重工作者） 2.0

    // 轻活动类型
    public static float lIGHT_ACTION_CODE = (float)1.3;

    // 轻活动类型
    public static float COMMON_ACTION_CODE = (float)1.6;

    // 轻活动类型
    public static float HARD_ACTION_CODE = (float)2.0;
}
