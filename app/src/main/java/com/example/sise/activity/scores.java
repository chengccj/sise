package com.example.sise.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.sise.R;
import com.example.sise.util.IndexUrlGet;
import com.example.sise.util.JsoupUtil;
import com.example.sise.util.OkHttpUtil;
import com.example.sise.view.addScore;

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
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    com.example.sise.view.addScore addScore;


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
//        IndexUrlGet urlGet = new IndexUrlGet();
//        url = urlGet.getinfo();
        url = IndexUrlGet.getInstance().getinfo();
        url = (String) ("http://class.sise.com.cn:7001" + url.replace("\'", ""));
        OkHttpUtil.getAsync(url, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    ArrayList result = JsoupUtil.getscore(html);
                    initlist(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initlist(ArrayList result) {
        int c = result.indexOf("必修课的总学分：");
        int d = result.indexOf("已选修课程");
//        scrollView = (ScrollView)findViewById(R.id.scroll);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        linearLayout = (LinearLayout) findViewById(R.id.line);

        //必修课程
        for (int i = 0; i < c; i += 10) {
            addScore = new addScore(this);
            addScore.sets1(result.get(i + 1).toString());
            addScore.sets2(result.get(i + 2).toString());
            addScore.sets3(result.get(i + 3).toString());
            addScore.sets4(result.get(i + 4).toString());
            addScore.sets5(result.get(i + 7).toString());
            addScore.setS6(result.get(i + 8).toString());
            addScore.setS7(result.get(i + 9).toString());
            linearLayout.addView(addScore);
        }
        addScore = new addScore(this);
        addScore.sets1("***以****");
        addScore.sets2("***下****");
        addScore.sets3("***为****");
        addScore.sets4("***选****");
        addScore.sets5("***修****");
        addScore.setS6("***课****");
        addScore.setS7("****程***");
        addScore.setColor();
        linearLayout.addView(addScore);

        //选修课程
        for (int k = d + 1; k <= result.size() - 7; k += 9) {
            addScore = new addScore(this);
            addScore.sets1(result.get(k).toString());
            addScore.sets2(result.get(k + 1).toString());
            addScore.sets3(result.get(k + 2).toString());
            addScore.sets4(result.get(k + 3).toString());
            addScore.sets5(result.get(k + 6).toString());
            addScore.setS6(result.get(k + 7).toString());
            addScore.setS7(result.get(k + 8).toString());
            linearLayout.addView(addScore);
        }
    }
}
