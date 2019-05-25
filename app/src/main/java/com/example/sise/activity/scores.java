package com.example.sise.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sise.R;
import com.example.sise.util.IndexUrlGet;
import com.example.sise.util.JsoupUtil;
import com.example.sise.util.OkHttpUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/11:15
 * Contact:
 * description:查询分数
 **/
public class scores extends AppCompatActivity {
    private static Map<String, String> requestHeadersMap, loginRequestBodyMap, scheduleRequestBodyMap, scoreRequestBodyMap;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        try {
            initview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initview() throws IOException {
        String url;
        IndexUrlGet urlGet = new IndexUrlGet();
        url = urlGet.getinfo();
        url = (String)("http://class.sise.com.cn:7001"+url.replace("\'",""));
        OkHttpUtil.getAsync(url, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response,"gb2312");
                    ArrayList result = JsoupUtil.getscore(html);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
