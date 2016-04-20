package com.zszdevelop.planman.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseApplication;
import com.zszdevelop.planman.bean.UpdateInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.http.ToastUtil;

import java.io.File;

/**
 * Created by ShengZhong on 2016/3/11.
 */
public class UpdateVersionUtils {
    private DownloadManager downloadManager;
    private String fileSavePath = "";
    private UpdateInfo info = new UpdateInfo();


    public void requestUpdata(final Context context){
        HttpRequest.get(API.LATEST_VERSION_CHECK_URI, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                UpdateInfo updateInfo = gson.fromJson(json, UpdateInfo.class);

                showAlertDialog(context, updateInfo);
            }
        });
    }


    private void showAlertDialog(Context context,UpdateInfo updateInfo) {
        info = updateInfo;
        if (!SystemUtils.getVersionName().equals(updateInfo.getVersionName())) {
            AlertUtils.alert(context, context.getString(R.string.new_version),
                    context.getString(R.string.latest_version, updateInfo.getVersionName()
                            + "\n" + context.getString(R.string.version_description, updateInfo.getDescription())),
                    context.getString(R.string.update),
                    context.getString(R.string.ignore_update),
                    onConfirmUpdateLisener,
                    onCancelUpdateLisener,
                    true);
        }
    }

    private AlertUtils.AlertListener onConfirmUpdateLisener = new AlertUtils.AlertListener() {

        @Override
        public void confirmClick() {
            downloadManager = (DownloadManager) BaseApplication.getApplication().getSystemService(BaseApplication.getApplication().DOWNLOAD_SERVICE);
            BaseApplication.getApplication().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            String url = info.getUrl();
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            fileSavePath = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS +
                    fileName;
            downloadManager.enqueue(new DownloadManager.Request(Uri.parse(url))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                            DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(info.getName())
                    .setDescription(info.getDescription())
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            fileName))
            ;


        }
    };
    private AlertUtils.AlertListener onCancelUpdateLisener = new AlertUtils.AlertListener() {

        @Override
        public void confirmClick() {

        }
    };


    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            File apkfile = new File(Uri.parse(downloadManager.getUriForDownloadedFile(downloadId).toString()).getPath());
            if (!apkfile.exists()) {
                ToastUtil.showToast("没有找到下载的文件，请去下载栏里查看");

                return;
            }
            // 通过Intent安装APK文件
            Intent i = new Intent(Intent.ACTION_VIEW);
            System.out.println("filepath=" + apkfile.toString() + "  " + apkfile.getPath());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            BaseApplication.getApplication().startActivity(i);
            android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃

            BaseApplication.getApplication().unregisterReceiver(this);
        }
    };

}
