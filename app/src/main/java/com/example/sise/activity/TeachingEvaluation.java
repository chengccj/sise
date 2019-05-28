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
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.BindViews;
import okhttp3.Call;
import okhttp3.FormBody;

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
    private String classid;
    private String teachid;
    private ArrayList arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachinevaluation);
        try {
            gethidden();
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
    public void gethidden(){
        try {
            IndexUrlGet indexUrlGet = new IndexUrlGet();
            String url = indexUrlGet.getstudentEvaluate();
            url = (String) ("http://class.sise.com.cn:7001" + url.replace("\'", ""));
            String html = OkHttpUtil.getindexinfo(url);
            arrayList = JsoupUtil.gethidden(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void one() throws UnsupportedEncodingException {
        String dostartname = URLEncoder.encode("同意", "gb2312");
        FormBody.Builder body = new FormBody.Builder();
        body.add("studentID", arrayList.get(0).toString());
        body.add("studentGzCode", arrayList.get(1).toString());
        body.add("radiobutton", "radiobutton");
        body.add("doStart", dostartname);

        OkHttpUtil.postvaluation("http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doMain&flag=Y ", body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    final String[] kemu = new String[1];
                    ArrayList courseid = JsoupUtil.getcourseid(html);
                    for (int i = 0; i < courseid.size(); i++) {
                        list.add((String) courseid.get(i));
                    }
                    myTextView = (TextView) findViewById(R.id.kemu);
                    mySpinner = (Spinner) findViewById(R.id.one);
                    //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
                    adapter = new ArrayAdapter<String>(TeachingEvaluation.this, android.R.layout.simple_spinner_item, list);
                    //第三步：为适配器设置下拉列表下拉时的菜单样式。
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //第四步：将适配器添加到下拉列表上
                    mySpinner.setAdapter(adapter);
                    //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
                    mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            kemu[0] = (String) adapter.getItem(arg2);
                            String url = "http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doCourseSelect";
                            FormBody.Builder body = new FormBody.Builder();
                            //TODO 待修改趁动态获取参数
                            body.add("year", "2018");
                            body.add("term", "2");
                            body.add("studentID", "197805");
                            body.add("courseID", kemu[0]);
                            body.add("teacherID", "0");
                            OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
                                @Override
                                public void onError(Call call, Exception e) {

                                }

                                @Override
                                public void onResponse(byte[] response) {
                                    try {
                                        String html = new String(response, "gb2312");
                                        String teacherid = JsoupUtil.getteacherid(html);
                                        Toast.makeText(TeachingEvaluation.this, "教师的id为" + teacherid, Toast.LENGTH_SHORT).show();
                                        setTeachid(teacherid);
                                        setClassid(teacherid);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
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

    public void postsubmit(View view) throws UnsupportedEncodingException {
        String teachid = getTeachid();
        String classid = getClassid();
        
//        @BindViews({R.id.et1, R.id.et2, R.id.et3, R.id.et4, R.id.et5, R.id.et6, R.id.et6,R.id.et7, R.id.et8, R.id.et9, R.id.et10, R.id.jianyi})
        List<Button> edittext;
        EditText t1 = (EditText)findViewById(R.id.et1);
        EditText t2 = (EditText)findViewById(R.id.et2);
        EditText t3 = (EditText)findViewById(R.id.et3);
        EditText t4 = (EditText)findViewById(R.id.et4);
        EditText t5 = (EditText)findViewById(R.id.et5);
        EditText t6 = (EditText)findViewById(R.id.et6);
        EditText t7 = (EditText)findViewById(R.id.et7);
        EditText t8 = (EditText)findViewById(R.id.et8);
        EditText t9 = (EditText)findViewById(R.id.et9);
        EditText t10 = (EditText)findViewById(R.id.et10);
        EditText yj = (EditText)findViewById(R.id.jianyi);
        String et1 = t1.getText().toString();
        String et2 = t2.getText().toString();
        String et3 = t3.getText().toString();
        String et4 = t4.getText().toString();
        String et5 = t5.getText().toString();
        String et6 = t6.getText().toString();
        String et7 = t7.getText().toString();
        String et8 = t8.getText().toString();
        String et9 = t9.getText().toString();
        String et10 =t10.getText().toString();
        String yijian = yj.getText().toString();
//        String et1 = edittext.get(0).getText().toString();
//        String et2 = edittext.get(1).getText().toString();
//        String et3 = edittext.get(2).getText().toString();
//        String et4 = edittext.get(3).getText().toString();
//        String et5 = edittext.get(4).getText().toString();
//        String et6 = edittext.get(6).getText().toString();
//        String et7 = edittext.get(7).getText().toString();
//        String et8 = edittext.get(8).getText().toString();
//        String et9 = edittext.get(9).getText().toString();
//        String et10 = edittext.get(10).getText().toString();
//        String yijian = edittext.get(11).getText().toString();
        String url = "http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doSave";
        String courseOpinionSuggest = URLEncoder.encode(yijian, "gb2312");
        String grade = URLEncoder.encode("优秀", "gb2312");
        String dosave = URLEncoder.encode("确定", "gb2312");
        FormBody.Builder body = new FormBody.Builder();
        //TODO 待修改趁动态获取参数
        body.add("year", "2018");
        body.add("term", "2");
        body.add("studentID", "197805");
        body.add("courseID", classid);
        body.add("teacherID", teachid);
        body.add("evaluateScore", et1);
        body.add("evaluateEntryID", "1");
        body.add("evaluateScore", et2);
        body.add("evaluateEntryID", "2");
        body.add("evaluateScore", et3);
        body.add("evaluateEntryID", "3");
        body.add("evaluateScore", et4);
        body.add("evaluateEntryID", "4");
        body.add("evaluateScore", et5);
        body.add("evaluateEntryID", "5");
        body.add("evaluateScore", et6);
        body.add("evaluateEntryID", "6");
        body.add("evaluateScore", et7);
        body.add("evaluateEntryID", "7");
        body.add("evaluateScore", et8);
        body.add("evaluateEntryID", "8");
        body.add("evaluateScore", et9);
        body.add("evaluateEntryID", "9");
        body.add("evaluateScore", et10);
        body.add("evaluateEntryID", "10");
        body.add("courseOpinionSuggest", courseOpinionSuggest);
        body.add("sumScore", "10");
        body.add("grade", grade);
        body.add("doSave", dosave);
        OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(TeachingEvaluation.this, "提交失败，请重新提交", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(byte[] response) {
                Toast.makeText(TeachingEvaluation.this, "评价成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public void setTeachid(String teachid) {
        this.teachid = teachid;
    }

    public String getTeachid() {
        return teachid;
    }

    /********************************************************************************************/
    //下面为一键评价功能，待加进进度条等可视化功能
    public void teachinevaluation() throws IOException {
        String dostartname = URLEncoder.encode("同意", "gb2312");
        FormBody.Builder body = new FormBody.Builder();
        body.add("studentID", "197805");
        body.add("studentGzCode", "1740129228");
        body.add("radiobutton", "radiobutton");
        body.add("doStart", dostartname);

        OkHttpUtil.postvaluation("http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doMain&flag=Y ", body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    ArrayList courseid = JsoupUtil.getcourseid(html);
                    for (int i = 0; i < courseid.size(); i++) {
                        initTeachname((String) courseid.get(i));
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
        FormBody.Builder body = new FormBody.Builder();
        //TODO 待修改趁动态获取参数
        body.add("year", "2018");
        body.add("term", "2");
        body.add("studentID", "197805");
        body.add("courseID", courseid);
        body.add("teacherID", "0");
        OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    String teacherid = JsoupUtil.getteacherid(html);
                    postEvaluation(courseid, teacherid);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postEvaluation(String courseid, String teacherid) throws UnsupportedEncodingException {
        String url = "http://class.sise.com.cn:7001/SISEWeb/pub/student/studentEvaluateAction.do?method=doSave";
        String courseOpinionSuggest = URLEncoder.encode("老师为人很好，讲课很认真，对知识点讲得也很透彻，赞", "gb2312");
        String grade = URLEncoder.encode("优秀", "gb2312");
        String dosave = URLEncoder.encode("确定", "gb2312");
        FormBody.Builder body = new FormBody.Builder();
        //TODO 待修改趁动态获取参数
        body.add("year", "2018");
        body.add("term", "2");
        body.add("studentID", "197805");
        body.add("courseID", courseid);
        body.add("teacherID", teacherid);
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "1");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "2");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "3");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "4");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "5");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "6");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "7");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "8");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "9");
        body.add("evaluateScore", "10");
        body.add("evaluateEntryID", "10");
        body.add("courseOpinionSuggest", courseOpinionSuggest);
        body.add("sumScore", "10");
        body.add("grade", grade);
        body.add("doSave", dosave);
        OkHttpUtil.postvaluation(url, body, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(byte[] response) {
                Toast.makeText(TeachingEvaluation.this, "评价成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
