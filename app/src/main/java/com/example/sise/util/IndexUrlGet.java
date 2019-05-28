package com.example.sise.util;

import android.content.Intent;
import android.util.Log;
import android.widget.SimpleCursorTreeAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class IndexUrlGet {
    private String url = "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";
    private static ArrayList urllist;
    String indexhtml;

    private static IndexUrlGet indexUrlGet;

    public IndexUrlGet() throws IOException {
        indexhtml = OkHttpUtil.getindexinfo(url);
        urllist = JsoupUtil.getindexurl(indexhtml);
    }

    public static IndexUrlGet getInstance() {
        if (indexUrlGet == null) {
            synchronized (IndexUrlGet.class) {
                if (indexUrlGet == null) {
                    try {
                        indexUrlGet = new IndexUrlGet();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return indexUrlGet;
    }

    public static String getinfo() {
        String info = (String) urllist.get(0);
        return info.replace("../../../../..", "");
    }

    public static String getcourseurl() {
        return (String) urllist.get(1);
    }


    //教学评价
    public static String getstudentEvaluate() {
        String student = (String) urllist.get(2);
        return student.replace("../../../../..", "");
    }

    //学习导师
    public static String getmanagerEvaluateAction() {
        String evaluateaction = (String) urllist.get(3);
        return evaluateaction.replace("../../../../..", "");
    }

    //辅导员
    public static String getcounsellor() {
        String counsellor = (String) urllist.get(4);
        return counsellor.replace("../../../../..", "");
    }

    public static String getlate() {
        String late = (String) urllist.get(5);
        return late.replace("../../../../..", "");
    }


}
