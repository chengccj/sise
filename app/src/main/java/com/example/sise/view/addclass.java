package com.example.sise.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sise.R;

public class addclass extends LinearLayout {
    private TextView monday,tuesday,wednesday,thursday,friday;

    public addclass(Context context){
        super(context);
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addclass);
//        initview();
//    }

    private void initview() {
        monday = (TextView)findViewById(R.id.monday);
        tuesday = (TextView)findViewById(R.id.tuesday);
        wednesday = (TextView)findViewById(R.id.wednesday);
        thursday = (TextView)findViewById(R.id.thursday);
        friday = (TextView)findViewById(R.id.friday);
    }

    public void setMonday(String str){
        monday.setText(str);
    }

    public void setTuesday(String str){
        monday.setText(str);
    }
    public void setWednesday(String str){
        monday.setText(str);
    }
    public void setThursday(String str){
        monday.setText(str);
    }
    public void setFriday(String str){
        monday.setText(str);
    }

}
