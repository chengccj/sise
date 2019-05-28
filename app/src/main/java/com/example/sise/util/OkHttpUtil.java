package com.example.sise.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private static OkHttpUtil mHttpUtil;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Response loginRespose;
    private Call logincall;
    HttpUrl loginUrl;
    HttpUrl httpUrl;

    private OkHttpUtil() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();
        mOkHttpClientBuilder.cookieJar(new mycookiejar());
        mOkHttpClient = mOkHttpClientBuilder.build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private static OkHttpUtil getInstance() {
        if (mHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new OkHttpUtil();
                }
            }
        }
        return mHttpUtil;
    }

    /**
     * 异步post请求登录
     */
    private void _postlogin(String url, Map<String, String> body, Map<String, String> headers, final ResultCallback callback) {
        //body == params
        RequestData[] bodyArr = mapToRequestDatas(body);
        RequestData[] headersArr = mapToRequestDatas(headers);
        final Request request = buildPostRequest(url, bodyArr, headersArr);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();//去掉注释会挂掉，原因未知
                Headers headers = response.headers();
                httpUrl = request.url();
//                List<Cookie> cookies = Cookie.parseAll(httpUrl, headers);
//                mOkHttpClient.cookieJar().saveFromResponse(httpUrl, cookies);
                Log.d("cookie", "cookie");
                final byte[] bytes = response.body().bytes();
                sendSuccessResultCallback(bytes, callback);
            }
        });
    }

    /**
     * 返回登录页的隐藏信息
     *
     * @return
     */
    private void _getInfo(String url, final ResultCallback callback) {
        final Request infoRequest = new Request.Builder()
                .url(url)
                .build();
//        deliveryResult(callback, infoRequest);
        mOkHttpClient.newCall(infoRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String html = response.body().string();
                final byte[] bytes = response.body().bytes();
                Headers headers = response.headers();
                HttpUrl url = infoRequest.url();
                List<Cookie> cookies = Cookie.parseAll(url, headers);
                mOkHttpClient.cookieJar().saveFromResponse(url, cookies);
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onResponse(bytes);
                        }
                    }
                });
            }
        });
    }

    /**
     * @param url
     * @return 个人信息首页对应的链接
     * @throws IOException
     */
    private String _getindexinfo(String url) throws IOException {
        StringBuilder cookieStr = new StringBuilder();
        //从缓存中获取Cookie
        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(httpUrl);
        //将Cookie数据弄成一行
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.name()).append("=").append(cookie.value() + ";");
        }
        Request indexrequest = new Request.Builder()
                .url(url)
                .header("Cookie", cookieStr.toString())
                .build();
        Call call = mOkHttpClient.newCall(indexrequest);
        Response indexresponse = call.execute();
        return indexresponse.body().string();
    }

    /**
     * 同步返回response(或选择异步返回?)
     */
    private void _postSearch(String url, final ResultCallback callback) {
        StringBuilder cookieStr = new StringBuilder();
        //从缓存中获取Cookie
        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(loginUrl);
        //将Cookie数据弄成一行
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.name()).append("=").append(cookie.value() + ";");
        }
        Request searchRequest = new Request.Builder()
                .url(url)
                .header("Cookie", cookieStr.toString())
                .build();

    }

    /**
     * 各个项目获取response的方法
     *
     * @param url
     * @param callback
     */
    private void _getAsync(String url, final ResultCallback callback) {
        StringBuilder cookieStr = new StringBuilder();
        //从缓存中获取Cookie
        HttpUrl l = HttpUrl.parse("http://class.sise.com.cn:7001/sise/login_check_login.jsp");
        HttpUrl u = httpUrl;
        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(httpUrl);
        //将Cookie数据弄成一行
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.name()).append("=").append(cookie.value() + ";");
        }
        //TODO 改写成get请求方法
        final Request request = new Request.Builder().header("Cookie", cookieStr.toString()).url(url).build();
        deliveryResult(callback, request);
    }

    /**
     * 教学评价相关
     *
     * @param url
     * @param body
     * @param callback
     */
    private void _postevaluation(String url, FormBody.Builder body, final ResultCallback callback) {
        //body == params
//        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        StringBuilder cookieStr = new StringBuilder();
        //从缓存中获取Cookie
        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(httpUrl);
        //将Cookie数据弄成一行
        for (Cookie cookie : cookies) {
            cookieStr.append(cookie.name()).append("=").append(cookie.value() + ";");
        }
//
//        RequestData[] bodyArr = mapToRequestDatas(body);
//        for (RequestData param : bodyArr) {
//            formBodyBuilder.add(param.key, param.value);
//        }
        RequestBody Body = body.build();
        final Request request = new Request.Builder()
                .url(url)
                .post(Body)
                .header("Cookie", cookieStr.toString())
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                sendSuccessResultCallback(bytes, callback);
            }
        });
    }

    /**
     * @param params
     * @return Map键值对数据转化为RequestData数组
     */
    private RequestData[] mapToRequestDatas(Map<String, String> params) {
        int index = 0;

        if (params == null) {
            return new RequestData[0];
        }
        int size = params.size();

        RequestData[] res = new RequestData[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            res[index++] = new RequestData(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * 构建post请求参数
     */
    private Request buildPostRequest(String url, RequestData[] params, RequestData... headers) {
        if (headers == null) {
            headers = new RequestData[0];
        }
        Headers.Builder headersBuilder = new Headers.Builder();
        for (RequestData header : headers) {
            headersBuilder.add(header.key, header.value);
        }
        Headers requestHeaders = headersBuilder.build();

        if (params == null) {
            params = new RequestData[0];
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (RequestData param : params) {
            formBodyBuilder.add(param.key, param.value);
        }
        RequestBody requestBody = formBodyBuilder.build();
        return new Request.Builder()
                .url(url)
                .headers(requestHeaders)
                .post(requestBody)
                .build();
    }

    public static abstract class ResultCallback {
        public abstract void onError(Call call, Exception e);

        public abstract void onResponse(byte[] response);
    }


    private void deliveryResult(final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    switch (response.code()) {
                        case 200:
                            final byte[] bytes = response.body().bytes();
                            sendSuccessResultCallback(bytes, callback);
                            break;
                        case 500:
                            sendSuccessResultCallback(null, callback);
                            break;
                        default:
                            throw new IOException();
                    }

                } catch (IOException e) {
                    sendFailedStringCallback(call, e, callback);
                }
            }
        });
    }


    /**
     * 调用请求失败对应的回调方法，利用handler.post使得回调方法在UI线程中执行
     */
    private void sendFailedStringCallback(final Call call,
                                          final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(call, e);
            }
        });
    }

    /**
     * 调用请求成功对应的回调方法，利用handler.post使得回调方法在UI线程中执行
     */
    private void sendSuccessResultCallback(final byte[] bytes,
                                           final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(bytes);
                }
            }
        });
    }

    public static class RequestData {
        String key;
        String value;

        public RequestData() {

        }

        public RequestData(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**************************公开方法******************************/

    /**
     * 登录页面获取隐藏值
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static void getinfo(String url, ResultCallback callback) {
        getInstance()._getInfo(url, callback);
    }

    public static void postLogin(String url, Map<String, String> body, Map<String, String> headers, ResultCallback callback) {
        getInstance()._postlogin(url, body, headers, callback);
    }

    public static String getindexinfo(String url) throws IOException {
        return getInstance()._getindexinfo(url);
    }


    public static void postSearch(String url, ResultCallback callback) {
        getInstance()._postSearch(url, callback);
    }

    public static void getAsync(String url, ResultCallback callback) {
        getInstance()._getAsync(url, callback);
    }

    public static void postvaluation(String url, FormBody.Builder body, ResultCallback callback) {
        getInstance()._postevaluation(url, body, callback);
    }

}
