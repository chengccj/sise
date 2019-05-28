package com.example.sise.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sise.R;

import org.w3c.dom.Text;

public class addScore extends LinearLayout {
    TextView s1,s2,s3,s4,s5,s6,s7;

    public addScore(Context context) {
        this(context,null);
    }

    public addScore(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public addScore(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.activity_add_score, this);

        s1 = (TextView)view.findViewById(R.id.s1);
        s2 = (TextView) view.findViewById(R.id.s2);
        s3 = (TextView) view.findViewById(R.id.s3);
        s4 = (TextView) view.findViewById(R.id.s4);
        s5 = (TextView) view.findViewById(R.id.s5);
        s6 = (TextView) view.findViewById(R.id.s6);
        s7 = (TextView)view.findViewById(R.id.s7);
    }
    public void sets1(String str){
        s1.setText(str);
    }
    public void sets2(String str){
        s2.setText(str);
    }
    public void sets3(String str){
        s3.setText(str);
    }
    public void sets4(String str){
        s4.setText(str);
    }
    public void sets5(String str){
        s5.setText(str);
    }

    public void setS6(String str) {
        s6.setText(str);
    }
    public void setS7(String str){
        s7.setText(str);
    }
    public void setColor(){
        s1.setTextColor(Color.rgb(0,180,131));
        s2.setTextColor(Color.rgb(0,180,131));
        s3.setTextColor(Color.rgb(0,180,131));
        s4.setTextColor(Color.rgb(0,180,131));
        s5.setTextColor(Color.rgb(0,180,131));
        s6.setTextColor(Color.rgb(0,180,131));
        s7.setTextColor(Color.rgb(0,180,131));
    }
}
