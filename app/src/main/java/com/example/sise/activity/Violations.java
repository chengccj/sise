package com.example.sise.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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
 * description:晚归，违规用电
 **/
public class Violations extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violations);
        try {
            initgetinfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initgetinfo() throws IOException {
        String url;
        IndexUrlGet get = new IndexUrlGet();
        url = get.getlate();
        url = (String)("http://class.sise.com.cn:7001"+url.replace("\'",""));
        OkHttpUtil.getAsync(url, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                String html;
                try {
                    html = new String(response,"gb2312");
                    ArrayList result = JsoupUtil.getviolations(html);
                    initview(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void initview(ArrayList result){
        TextView t1 = (TextView)findViewById(R.id.t1);
        TextView t2 = (TextView)findViewById(R.id.t2);
        TextView t3 = (TextView)findViewById(R.id.t3);
        TextView t4 = (TextView)findViewById(R.id.t4);
        t1.setText((String) result.get(0));
        t2.setText((String)result.get(1));
        t3.setText((String)result.get(2));
        t4.setText((String)result.get(3));
    }
}
