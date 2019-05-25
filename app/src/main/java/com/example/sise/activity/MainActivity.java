package com.example.sise.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.sise.R;
import com.example.sise.util.IndexUrlGet;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        QMUIRoundButton schedule = (QMUIRoundButton)findViewById(R.id.schedlue);
        QMUIRoundButton scroes = (QMUIRoundButton) findViewById(R.id.scores);
        QMUIRoundButton TutorEvaluation = (QMUIRoundButton) findViewById(R.id.TutorEvaluation);
        QMUIRoundButton TeachingEvaluation = (QMUIRoundButton)findViewById(R.id.TeachingEvaluation);
        QMUIRoundButton Counsellor  = (QMUIRoundButton)findViewById(R.id.counsellor);
        QMUIRoundButton violations = (QMUIRoundButton)findViewById(R.id.Violations);

        Counsellor.setOnClickListener(this);
        TeachingEvaluation.setOnClickListener(this);
        scroes.setOnClickListener(this);
        TutorEvaluation.setOnClickListener(this);
        schedule.setOnClickListener(this);
        violations.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
//        if (login.isIslogin()){
//            startActivity(new Intent(MainActivity.this,login.class));
//        }
        int  id = view.getId();
        switch (id){
            case R.id.counsellor://辅导员评价
                startActivity(new Intent(this,counsellor.class));
                break;
            case R.id.TeachingEvaluation://教学评价
                startActivity(new Intent(this,TeachingEvaluation.class));
                break;
            case R.id.TutorEvaluation://学习导师评价
                startActivity(new Intent(this,TutorEvaluation.class));
                break;
            case R.id.scores://查询考试成绩
                startActivity(new Intent(this,scores.class));
                break;
            case R.id.schedlue://课程表
                startActivity(new Intent(this,Schedlue.class));
                break;
            case R.id.Violations://晚归，违规用电记录
                startActivity(new Intent(this,Violations.class));
                break;
        }
    }
    public void login(View view){
        startActivity(new Intent(MainActivity.this,login.class));
    }
}
