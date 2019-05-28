package com.example.sise.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sise.R;
/**
 * Created by Android Studio
 * Author: cheng
 * Time: 2019/05/2019/5/19/23:54
 * Contact:
 * description:辅导员评价
 **/

public class counsellor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor);
        TextView error = (TextView)findViewById(R.id.error);
        Toast.makeText(this,"非可评时间",Toast.LENGTH_SHORT).show();
        finish();
    }
    
}
