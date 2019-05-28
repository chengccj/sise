package com.example.sise.util;

import android.util.ArrayMap;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupUtil {
    static String [] time = new String[] {"周一","周二","周三","周四","周五","周六","周日"};

    /**
     * 获取登录页的隐藏信息
     * @param html
     * @return
     */
    public static String[] getinfo(String html){
        String[] infolist = new String[4];
        Document document = Jsoup.parse(html);
        Elements input = document.getElementsByTag("input");
        for (int i = 0,m = 0; i < 2;i ++,m += 2){
            String name = input.get(i).attr("name");
            String value = input.get(i).attr("value");
            infolist[m] = name;
            infolist[m+1] = value;
        }
        return infolist;
    }
    //获取各个项目的url
    public static ArrayList getindexurl(String html){
        ArrayList indexurl = new ArrayList();
        int index = 0;
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("table");
        Element table = elements.get(5);
        Elements tr = table.getElementsByTag("tr");
        for (int i=1;i <= tr.size();i++){
            //若需查询课表，考勤，平时成绩则i <= 5
            if (i <= 2 || i >= 19 && i <= 21 || i == 27){
                Elements tr1 = tr.get(i).getElementsByTag("tr");
                Elements td = tr1.get(0).getElementsByTag("td");
                String td1 = td.attr("onclick");
                String td2 = td1.split("=",2)[1];
                indexurl.add(td2);
                index ++;
            }
        }
        return indexurl;
    }



    //获取课表信息
    public static ArrayList getcourse(String html){
        ArrayList courselist = new ArrayList();
        if (html == null){
            return courselist;
        }
        Document document = Jsoup.parse(html);
        Elements table = document.getElementsByTag("table");
        Element data = table.get(6);
        Elements tr = data.getElementsByTag("tr");

        int index = 0;
        for (int i = 1; i <= tr.size()-1; i += 1) {
            Elements td = tr.get(i).getElementsByTag("td");
            Elements course = td.select("td[align=\"left\"]");

            for (int j = 0; j < 5; j++) {
                String content = course.get(j).text();
                courselist.add(content);
                index++;
            }
            index = 0;
        }
        return courselist;
    }
    //考试成绩
    //仅爬取了必修课程
    public static ArrayList getscore(String html){
        ArrayList scorelist = new ArrayList();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("td");
        for (int i = 30;i < elements.size()-29;i ++){
//            if (i >=520 && i <= 528){
//                continue;
//            }
            String str = elements.get(i).text();
            Log.d("test","test");
            scorelist.add(str);
        }
        return scorelist;
    }

    //学习导师评价

    //教学评价
    //获取隐藏信息
    public static ArrayList gethidden(String html){
        ArrayList arrayList = new ArrayList();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("input");
        for (int i =0;i <elements.size();i++){
            String str = elements.get(i).attr("value");
            arrayList.add(str);
        }
        return arrayList;
    }
    //获取课程代码
    public static ArrayList getcourseid(String html){
        ArrayList courseid = new ArrayList();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("option");
        for (int i = 1;i < elements.size()-1;i++){
            Element option = elements.get(i);
            String id = option.attr("value");
            courseid.add(id);
        }
        return courseid;
    }

    //教师id获取
    public static String getteacherid(String html){
        String teacherid = null;
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("option");
        Element option = elements.get(elements.size()-1);
        teacherid = option.attr("value");
        return teacherid;
    }

   //辅导员评价

    //违规用电
    public static ArrayList getviolations(String html){
       ArrayList violationslist = new ArrayList();

        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("table");
        Element table = elements.get(2);
        Elements tr = table.getElementsByTag("tr");
        for (int i = 0;i < tr.size();i++){
            Elements td = tr.get(i).getElementsByTag("td");
            for (int j = 0;j < td.size();j ++){
                String value = td.get(j).text();
                violationslist.add(value);
            }
        }
        return violationslist;
    }


}
