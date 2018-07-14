package com.example.administrator.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Utils.ChatUtils;
import Utils.GetScreen;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class TestChart extends Activity   {
    private LineChartView lineChartView;
    private TextView textView;
    private TextView tv1;
    private int screenWidth;
    private int DownX;
    String[] date = { "0","10-22", "11-22", "12-22", "13-22", "14-22","15-23", "16-22", "17-23", "18-22","19-22","20-22","21-22"};//X轴的标注
    int[] score = { 50, 52, 50, 53, 50, 54, 52, 58,54,58,54,58,56};//图表的数据点
    private boolean isshow;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testchart);
        lineChartView = findViewById(R.id.ChatView);
        textView = findViewById(R.id.tv_click);
        tv1=findViewById(R.id.view1);
        //设置是否可交互
        lineChartView.setInteractive(false);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        LineChartData line = ChatUtils.getChat(date, score);
        lineChartView.setLineChartData(line);
        lineChartView.setValueTouchEnabled(true);

        Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.bottom = 0;  //Y轴最小值
        v.top = 60;    //Y轴最大值
        //固定Y轴的范围,如果没有这个,Y轴的范围会根据数据的最大值和最小值决定,这不是我想要的
        lineChartView.setMaximumViewport(v);

//        这2个属性的设置一定要在lineChart.setMaximumViewport(v);这个方法之后,不然显示的坐标数据是不能左右滑动查看更多数据的
//        v.left = score.length - 7;        //10的意思最大显示的X轴的个数
//        v.right = score.length - 1;
        lineChartView.setCurrentViewport(v);

        GetScreen getScreen = new GetScreen(this);
//        screenHeight = getScreen.getScreenHeight();
        screenWidth = getScreen.getScreenWith();

        lineChartView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DownX = (int) motionEvent.getX();
                        int index = getindex(DownX);
                        textView.setText(score[index] + "\n" + date[index]);
                        textView.setTranslationX(DownX);
                        tv1.setTranslationX(DownX);
                        textView.setVisibility(View.VISIBLE);
                        tv1.setVisibility(View.VISIBLE);
                        isshow=true;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isshow) {
                            isshow = false;
                            textView.setVisibility(View.GONE);
                            tv1.setVisibility(View.GONE);
                        }
                        break;
                }

                return true;
            }
        });
        lineChartView.setVisibility(View.VISIBLE);
    }

    private int getindex(int X) {
        int distant = screenWidth / (score.length - 1);
        for (int i = 1; i < score.length; i++) {
            if (X > distant * (i - 1) && X < distant * i) {
                if (X - distant * (i - 1) < distant - X) {
                    return i - 1;
                } else {
                    return i;
                }
            }
        }
        return 0;
    }

}
