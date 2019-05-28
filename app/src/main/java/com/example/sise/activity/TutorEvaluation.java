package com.example.sise.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/23:54
 * Contact:
 * description:学习导师评价
 **/
public class TutorEvaluation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"非可评时间！！！",Toast.LENGTH_SHORT).show();
        finish();
    }
}
