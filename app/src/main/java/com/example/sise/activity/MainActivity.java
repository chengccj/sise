package com.example.sise.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sise.R;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        QMUIRoundButton info = (QMUIRoundButton)findViewById(R.id.info);
        QMUIRoundButton courseinfo = (QMUIRoundButton)findViewById(R.id.courseinfo);
        QMUIRoundButton scroes = (QMUIRoundButton) findViewById(R.id.scores);
        QMUIRoundButton rewards_punishments = (QMUIRoundButton) findViewById(R.id.Rewards_punishments);
        QMUIRoundButton schedule = (QMUIRoundButton)findViewById(R.id.schedlue);
        QMUIRoundButton violations = (QMUIRoundButton)findViewById(R.id.Violations);

        info.setOnClickListener(this);
        courseinfo.setOnClickListener(this);
        scroes.setOnClickListener(this);
        rewards_punishments.setOnClickListener(this);
        schedule.setOnClickListener(this);
        violations.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int  id = view.getId();
        switch (id){
            case R.id.info:
                login.searchInfoOperation(MainActivity.this);
                break;
            case R.id.courseinfo:
                login.searchScheduleOperation(MainActivity.this);
                break;
            case R.id.scores:
                startActivity(new Intent(this,scores.class));
                break;
            case R.id.Rewards_punishments:
                break;
            case R.id.schedlue:
                break;
            case R.id.Violations:
                break;
        }
    }
    public void login(View view){
        startActivity(new Intent(MainActivity.this,login.class));
    }

}
