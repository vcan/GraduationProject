package com.zszdevelop.planman.http;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by jimmy on 15/12/15.
 */
public class StringWithBomRequest extends Request<String> {

    private HttpRequestListener listener;
    public StringWithBomRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);

    }

    public StringWithBomRequest(String url, HttpRequestListener listener, Response.ErrorListener errorListener, int method) {
        this(method, url, errorListener);
        this.listener = listener;
    }

    @Deprecated //该方法已使用StringWithBomRequest(String url, HttpRequestListener listener, Response.ErrorListener errorListener, int method)来替代
    public StringWithBomRequest(String url, HttpRequestListener listener, Response.ErrorListener errorListener) {
        this(url, listener,  errorListener, 0);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            byte[] b = response.data;
            String jsonString =new String(b, HttpHeaderParser.parseCharset(response.headers));
            String tmpString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
            Response<String> aaa = Response.success(tmpString, HttpHeaderParser.parseCacheHeaders(response));
            return aaa;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
//        return Response.success(new String(response.data), HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String s) {
        this.listener.onResponse(s);
    }
}
