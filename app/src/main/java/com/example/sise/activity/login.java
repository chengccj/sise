package com.example.sise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sise.R;
import com.example.sise.util.JsoupUtil;
import com.example.sise.util.OkHttpUtil;
import com.example.sise.util.mycookiejar;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/11:15
 * Contact:
 * description:登录页面
 **/
public class login extends AppCompatActivity {

    private static boolean islogin = false;
    /**
     * 用于存放请求头和请求参数
     */
    private static Map<String, String> requestHeadersMap, loginRequestBodyMap, scheduleRequestBodyMap, scoreRequestBodyMap;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private OkHttpClient mOkHttpClient;
    private String username;
    private String passwd;
    private String[] infolist;
    //    @Setter
    private String hidden1;
    //    @Setter
    private String hidden1_value;
    //    @Setter
    private String ramdon;
    //    @Setter
    private String ramdon_value;
    private Button logout;
    private Handler uiHandler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("标题");
        setSupportActionBar(toolbar);
        //TODO 修改成登录即开始获取
        getInfo();
        //TODO 修改为子线程更新，而非主线程更新
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void getInfo() {
        OkHttpUtil.getinfo("http://class.sise.com.cn:7001/sise/login.jsp", new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(login.this,"错误，可能会导致登录失败，请稍后再试",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(byte[] response) {
                try {
                    String html = new String(response, "gb2312");
                    Log.d("login", html);
                    infolist = JsoupUtil.getinfo(html);
                    setHidden1(infolist[0]);
                    setHidden1_value(infolist[1]);
                    setRamdon(infolist[2]);
                    setRamdon_value(infolist[3]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void login1(View view) {
        if (isIslogin()) {
            Toast.makeText(this, "您已登陆，请勿重复操作", Toast.LENGTH_SHORT).show();
        } else {
            final TextInputLayout Username = (TextInputLayout) findViewById(R.id.login_usename);
            final TextInputLayout Passwd = (TextInputLayout) findViewById(R.id.login_passwd);
            username = Username.getEditText().getText().toString();
            passwd = Passwd.getEditText().getText().toString();
            requestHeadersMap = new LinkedHashMap<String, String>();
            loginRequestBodyMap = new LinkedHashMap<>(); // java 1.7 开始可以通过这种写法初始化集合
            scheduleRequestBodyMap = new LinkedHashMap<>();
            scoreRequestBodyMap = new LinkedHashMap<>();
            requestHeadersMap.put("Referer", "http://class.sise.com.cn:7001/sise/login.jsp");
            loginRequestBodyMap.put((String) hidden1, (String) hidden1_value);
            loginRequestBodyMap.put((String) ramdon, (String) ramdon_value);
            loginRequestBodyMap.put("username", username);
            loginRequestBodyMap.put("password", passwd);
            OkHttpUtil.postLogin("http://class.sise.com.cn:7001/sise/login_check_login.jsp", loginRequestBodyMap, requestHeadersMap, new OkHttpUtil.ResultCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(login.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(byte[] response) {
                    try {
                        String html = new String(response, "gb2312");
                        //TODO 检测登录是否成功
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    setloginsuccess(true);
                    finish();
                }
            });
        }
    }

    public void logout(View view) {
        if (!isIslogin()) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            HttpUrl l = HttpUrl.parse("http://class.sise.com.cn:7001");
            //TODO 注销登陆动作的实现,可改成随登陆状态进行显示界面
            setloginsuccess(false);
            mycookiejar.reset(l);
            Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void setHidden1(String hidden1) {
        this.hidden1 = hidden1;
    }

    public void setHidden1_value(String hidden1_value) {
        this.hidden1_value = hidden1_value;
    }

    public void setRamdon(String ramdon) {
        this.ramdon = ramdon;
    }

    public void setRamdon_value(String ramdon_value) {
        this.ramdon_value = ramdon_value;
    }

    public static void setloginsuccess(boolean islogin1) {
        islogin = islogin1;
    }

    public static boolean isIslogin() {
        return islogin;
    }
}
