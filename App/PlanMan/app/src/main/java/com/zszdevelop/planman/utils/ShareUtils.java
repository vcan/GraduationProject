package com.zszdevelop.planman.utils;

import android.content.Context;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseApplication;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by jimmy on 16/1/14.
 */
public class ShareUtils {

    public static void shareToSocial() {

        Context context = BaseApplication.getApplication();
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("型男计划,一款树立减肥信心 的 App");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("通过制定计划明确目标.记录身材变化,查看变化趋势树立信心.了解自己身体状态,通过控制摄入支出平衡以达到以达到科学减肥.有了型男计划,减肥从此 so easy");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("型男计划是个好应用");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(context);



    }
}

