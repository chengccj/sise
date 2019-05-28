package com.example.sise.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class mycookiejar implements CookieJar {
//    CookieJar cookieJar = new CookieJar() {
        //Cookie缓存区
        private static Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();
        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
            // TODO Auto-generated method stub
            //移除相同的url的Cookie
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null) {
                cookiesMap.remove(host);
            }
            //再重新添加
            cookiesMap.put(host, arg1);
//        }
        }

    @Override
    public List<Cookie> loadForRequest(HttpUrl arg0) {
        // TODO Auto-generated method stub
        List<Cookie> cookiesList = cookiesMap.get(arg0.host());
        //注：这里不能返回null，否则会报NULLException的错误。
        //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
        return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
    }
    public static void reset(HttpUrl httpUrl){
            String host = httpUrl.host();
            cookiesMap.put(host,null);
    }
}
