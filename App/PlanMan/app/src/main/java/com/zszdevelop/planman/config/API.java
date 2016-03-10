package com.zszdevelop.planman.config;


/**
 * Created by ShengZhong on 2015/12/16.
 */
public class API {


    public static final String SERVER_URI = "http://172.25.114.12:8080/PlanManServer/%s";
    public static final String SERVER_DEBUG_URI = "http://172.27.35.4:8080/PlanManServer/%s";

    public static final String SERVER_IMAGE_URL = "http://play.mengtta.com/";
    public static final String DEBUG_SERVER_IMAGE_URL = "http://192.168.0.183/";

    /*一：注册手机uuid*/
   public static final String REGISTER_UUID_URI ="UuidServlet";

   /*二：登陆*/
   public static final String LOGIN_URI ="LoginServlet";

    /*四：首页数据*/
   public static final String MAIN_URI ="HomeServlet?userId=%d";

    /*八：新增一条目标记录*/
   public static final String INSTER_GOAL_URI="GoalInfoServlet";

    /*九：获取训练计划信息*/
   public static final String GET_GOAL_PLAN_URI="GoalInfoServlet?userId=%d";

   /*十三：获取消耗纪录*/
   public static final String SUBMIT_CONSUME_RECORD_URI ="ConsumeRecordServlet?";

    /*十三：获取消耗纪录*/
   public static final String CONSUME_RECORD_URI ="ConsumeRecordServlet?userId=%d";

    /*十八：第一条身材记录*/
   public static final String FIRST_RECORD_FIGUTR_URI ="FirstGoalRecordServlet?userId=%d&goalRecordType=%d";

    /*十九：提交身材记录*/
   public static final String SUBMIT_FIGUTR_URI ="GoalRecordServlet";

    /*二十：修改计划完成度*/
   public static final String MODIFY_GOAL_COMPLETE_URI ="ModifyGoalStutusServlet";




}
