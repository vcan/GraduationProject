package com.zszdevelop.planman.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zszdevelop.planman.base.BaseApplication;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.NetworkState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jimmy on 15/12/15.
 */
public class HttpRequest {

    public static boolean isDebug = true;


    public static final int HTTP_OK = 200;


    private static HttpRequest instance = new HttpRequest();



    /**
     * 获取某种指定类型的HTTP请求，可附加传输的数据data
     *
     * @param url  接口信息
     * @param data 传输的数据
     */
    public static void get(String url, final Map<String, String> data, HttpRequestListener listener) {
        HttpRequest(url, data, listener, Request.Method.GET);
    }

    public static void get(String url, HttpRequestListener listener) {
        HttpRequest(url, null, listener, Request.Method.GET);
    }


    public static void post(String url, final Map<String, String> data, HttpRequestListener listener) {
        HttpRequest(url, data, listener, Request.Method.POST);
    }

    private static void HttpRequest(String url, final Map<String, String> data, HttpRequestListener listener, int method) {
        String requestUri = instance.generateRequestUrl(url);
        if (instance.isDebug) {

            LogUtils.e(requestUri);
        }
        StringWithBomRequest requestQueue = new StringWithBomRequest(requestUri, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                instance.onSystemError(volleyError);
            }
        }, method) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                instance.preParseNetworkResponse(response);
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = instance.getCommonHead();
                LogUtils.e(map.toString());
                if (data != null) {
                    map.putAll(data);
                }
                return map;
            }
        };

        instance.addQueue(requestQueue);
    }

    private String generateRequestUrl(String url) {
        String serverUrl = API.SERVER_DEBUG_URI;
        if (!isDebug) {
            serverUrl = API.SERVER_URI;
        }

        serverUrl = String.format(serverUrl,url);

        return serverUrl;
    }

    /**
     * 预先处理服务器返回的数据，如果需要的话，请重写这个方法
     * TODO 需要处理可重写的逻辑
     *
     * @param response 服务器返回的数据
     * @return Response<String>
     */
    public void preParseNetworkResponse(NetworkResponse response) {

    }

    public void onSystemError(VolleyError volleyError) {

        String message = volleyError.getMessage();
        if (NetworkState.isNetworkAvailable()) {// 检测网络连接是否可用

            ToastUtil.showToast(BaseApplication.getApplication().getApplicationContext(), "系统出错了");
            LogUtils.e("网络请求异常:"+message);

        } else {
            ToastUtil.showToast("您的网络连接异常，请检查网络");
        }

    }

    private Map<String, String> getCommonHead() {
        Map<String, String> map = new HashMap<>();
        //这里可以添加那些需要默认发送的数据信息

        return map;
    }

    private void addQueue(Request request) {
        BaseApplication.getRequestQueue(BaseApplication.getApplication().getApplicationContext()).add(request);
    }
}
