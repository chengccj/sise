package com.example.sise.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sise.R;

public class addclass extends LinearLayout {
    private TextView monday,tuesday,wednesday,thursday,friday;


    public addclass(Context context) {
        this(context,null);
    }

    public addclass(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public addclass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.activity_addclass, this);

        monday = (TextView)view.findViewById(R.id.monday);
        tuesday = (TextView)view.findViewById(R.id.tuesday);
        wednesday = (TextView)view.findViewById(R.id.wednesday);
        thursday = (TextView)view.findViewById(R.id.thursday);
        friday = (TextView) view.findViewById(R.id.friday);
    }

    public void setMonday(String str) {
       monday.setText(str);
    }

    public void setTuesday(String str) {
        tuesday.setText(str);
    }

    public void setWednesday(String str) {
        wednesday.setText(str);
    }

    public void setThursday(String str) {
        thursday.setText(str);
    }

    public void setFriday(String str) {
        friday.setText(str);
    }
}
