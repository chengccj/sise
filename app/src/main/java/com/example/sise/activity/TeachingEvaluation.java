package com.example.sise.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sise.R;
import com.example.sise.util.IndexUrlGet;
import com.example.sise.util.JsoupUtil;
import com.example.sise.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/23:54
 * Contact:
 * description:教学评价
 **/
public class TeachingEvaluation extends AppCompatActivity {

    private List<String> list = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();
    private TextView myTextView;
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachinevaluation);
        try {
            one();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//一键提交评价
//        try {
//            teachinevaluation();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
    }

    private void one() throws UnsupportedEncodingException {
        String dostartname = URLEncoder.encode("同意", "gb2312");
        Map<String,String> body = new LinkedHashMap<>();
        body.put("studentID","197805");
        body.put("studentGzCode","1740129228");
        body.put("radiobutton","radiobutton");
        body.put("doStart", dostartname);

        OkHttpUtil.postvaluation("http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doMain&flag=Y ",body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response,"gb2312");
                    final String[] kemu = new String[1];
                    ArrayList courseid = JsoupUtil.getcourseid(html);
                    for (int i = 0;i < courseid.size();i++) {
                        list.add((String)courseid.get(i));
                    }
                    myTextView = (TextView)findViewById(R.id.kemu);
                    mySpinner = (Spinner)findViewById(R.id.one);
                    //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
                    adapter = new ArrayAdapter<String>(TeachingEvaluation.this,android.R.layout.simple_spinner_item, list);
                    //第三步：为适配器设置下拉列表下拉时的菜单样式。
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //第四步：将适配器添加到下拉列表上
                    mySpinner.setAdapter(adapter);
                    //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
                    mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            kemu[0] = (String)adapter.getItem(arg2);

                        }
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                            myTextView.setText("NONE");
                            arg0.setVisibility(View.VISIBLE);
                        }
                    });

            } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void teachinevaluation() throws IOException {
        String dostartname = URLEncoder.encode("同意", "gb2312");
        Map<String,String> body = new LinkedHashMap<>();
        body.put("studentID","197805");
        body.put("studentGzCode","1740129228");
        body.put("radiobutton","radiobutton");
        body.put("doStart", dostartname);

     OkHttpUtil.postvaluation("http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doMain&flag=Y ",body, new OkHttpUtil.ResultCallback() {
         @Override
         public void onError(Call call, Exception e) {

         }

         @Override
         public void onResponse(byte[] response) {
             try {
                 String html = new String(response,"gb2312");
                 ArrayList courseid = JsoupUtil.getcourseid(html);
                 for (int i = 0;i < courseid.size();i++) {
                     initTeachname((String)courseid.get(i));
                 }
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }

         }
     });
    }

    public void initTeachname(final String courseid) {
        //TODO 或许有个问题，大课小课不同老师可能会导致获取到的老师id不合法，待验证
        String url = "http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doCourseSelect";
        Map<String, String> body = new LinkedHashMap<>();
        //TODO 待修改趁动态获取参数
        body.put("year", "2018");
        body.put("term", "2");
        body.put("studentID", "197805");
        body.put("courseID", courseid);
        body.put("teacherID", "0");
        OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    String teacherid = JsoupUtil.getteacherid(html);
                    postEvaluation(courseid,teacherid);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postEvaluation(String courseid,String teacherid) throws UnsupportedEncodingException {
        String url = "http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doSave";
        String courseOpinionSuggest = URLEncoder.encode("老师为人很好，讲课很认真，对知识点讲得也很透彻，赞","gb2312");
        String grade = URLEncoder.encode("优秀","gb2312");
        String dosave = URLEncoder.encode("确定","gb2312");
        Map<String, String> body = new LinkedHashMap<>();
        //TODO 待修改趁动态获取参数
        body.put("year", "2018");
        body.put("term", "2");
        body.put("studentID", "197805");
        body.put("courseID", courseid);
        body.put("teacherID", teacherid);
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","1");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","2");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","3");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","4");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","5");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","6");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","7");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","8");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","9");
        body.put("evaluateScore","10");
        body.put("evaluateEntryID","10");
        body.put("courseOpinionSuggest",courseOpinionSuggest);
        body.put("sumScore","10");
        body.put("grade",grade);
        body.put("doSave",dosave);
        OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                Toast.makeText(TeachingEvaluation.this,"评价成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
