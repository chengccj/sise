package com.example.sise.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.sise.R;
import com.example.sise.util.IndexUrlGet;
import com.example.sise.util.JsoupUtil;
import com.example.sise.util.OkHttpUtil;
import com.example.sise.view.addclass;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/10:25
 * Contact:
 * description:课程表
 **/
public class Schedlue extends AppCompatActivity {
    private  ScrollView scrollView;
    private LinearLayout layout_content;
    private addclass addclass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courselist);
//        scrollView = (ScrollView) findViewById(R.id.course_scrollview);
//        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        layout_content = (LinearLayout) findViewById(R.id.layout_schedule_content);
        try {
            initobtain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initobtain()throws IOException {
        IndexUrlGet urlget = new IndexUrlGet();
        String url = IndexUrlGet.getcourseurl();
        url = (String)("http://class.sise.com.cn:7001"+url.replace("\'",""));
       OkHttpUtil.getAsync(url, new OkHttpUtil.ResultCallback() {
           @Override
           public void onError(Call call, Exception e) {
               Toast.makeText(Schedlue.this,"获取失败，请重新获取",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(byte[] response) {
               try {
                   String html = new String(response,"gb2312");
                   ArrayList result = JsoupUtil.getcourse(html);
                   initview(result);
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               }

           }
       });
    }

    private void initview(ArrayList result) {
        scrollView = (ScrollView)findViewById(R.id.course_scrollview);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        layout_content = (LinearLayout)findViewById(R.id.course_layout);

        for (int i = 0;i<result.size();i+=5){
//            for (int j =0;j < 5;j++) {
                addclass = new addclass(this);
                addclass.setMonday(result.get(i).toString());
                addclass.setTuesday(result.get(i+1).toString());
                addclass.setWednesday(result.get(i+2).toString());
                addclass.setThursday(result.get(i+3).toString());
                addclass.setFriday(result.get(i+4).toString());
                layout_content.addView(addclass);
//            }
        }
    }
}
