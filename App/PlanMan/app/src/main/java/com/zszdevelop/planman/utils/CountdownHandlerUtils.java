package com.zszdevelop.planman.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.zszdevelop.planman.config.Config;


/**
 * Created by ShengZhong on 2016/2/19.
 */
public class CountdownHandlerUtils {

    private static final int WHAT_RECODE = 111;
    private  static final int WHAT_TIME = 110;
    private static int code_total_time = Config.CODE_TIME;

    // 防止 Handler 内存泄露，把 handler 定义成静态来使用，并用WeakReference
  private static   Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Button  btn = (Button) msg.obj;
            if (btn == null){
                btn.setClickable(true);
                return;
            }

            switch (msg.what){
                case WHAT_TIME:
                    btn.setClickable(false);
                    btn.setText(String.format("%d秒后重新获取", code_total_time));

                    break;
                case WHAT_RECODE:
                    btn.setText("重新发送验证码");
                    btn.setClickable(true);
                    break;
            }

            super.handleMessage(msg);
        }
    };

    public static final void startCountdown(final Button showButton){
        new Thread(new Runnable() {
            @Override
            public void run() {


                for (; code_total_time > 0; code_total_time--) {
                    Message msg = new Message();
                    msg.what = WHAT_TIME;
                    msg.obj = showButton;
                    handler.sendMessage(msg);

                    if (code_total_time <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = new Message();
                msg.what = WHAT_RECODE;
                msg.obj = showButton;
                handler.sendMessage(msg);

            }
        }).start();
    }
}
