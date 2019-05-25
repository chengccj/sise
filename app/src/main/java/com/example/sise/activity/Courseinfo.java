package com.example.sise.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sise.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Courseinfo extends AppCompatActivity {

    private static Map<String, String> requestHeadersMap, loginRequestBodyMap, scheduleRequestBodyMap, scoreRequestBodyMap;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private OkHttpClient mOkHttpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courselist);
//        getSupportActionBar().hide();

        SharedPreferences share = getSharedPreferences("Session",MODE_PRIVATE);
        String sessionid= share.getString("sessionid","null");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody fromBody = new FormBody.Builder()
//                .add("aca2a0aa55965a90f833840267051ddd","5db6c0ad717794f1551b6c4ef9373060")
//                .add("random","1557627213649")
//                .add("token","E1852597E632879281A3467479AC9C92115426A9075D7")
//                .add("username",username)
//                .add("password",passwd)
//                .add("gzCode","1740129228")
//                .add("md5Code","3e7d77feec734a110415fa0c3979b388")
                .build();
        Request request = new Request.Builder()
                .url("http://class.sise.com.cn:7001/sise/module/student_schedular/student_schedular.jsp")
                .post(fromBody)
                .addHeader("cookie",sessionid)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            String s = response.toString();
//                Headers headers = response.headers();
//                List cookies = headers.values("Set-Cookie");
//                String session = (String)cookies.get(0);
////                text.setText(str);
            Log.d("TAG",str);
            Log.d("T",s);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mOkHttpClientBuilder = new OkHttpClient.Builder();
////        mOkHttpClientBuilder.cookieJar(new MyCookieJar());
//        mOkHttpClient = mOkHttpClientBuilder.build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                String s = response.toString();
//                TextView text = (TextView)findViewById(R.id.text2);
//                Headers headers = response.headers();
//                List cookies = headers.values("Set-Cookie");
//                String session = (String)cookies.get(0);
////                text.setText(str);
//                Log.d("TAG",str);
//                Log.d("T",s);
//
//
//            }
//        });
    }

}
