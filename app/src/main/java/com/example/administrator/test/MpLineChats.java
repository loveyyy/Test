package com.example.administrator.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MpLineChats  extends Activity {
        private LineChart lineChart;
        int [] score = {50,52,50, 53, 50, 54,52,58,54,58,50,52,50, 53, 50, 54,52,58,54,58,50,52,50, 53, 50, 54,52,58,54,58};
        private   List<Entry> entries = new ArrayList<>();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpandroidcharts);
        lineChart=findViewById(R.id.MpLinechart);
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(true);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("Tag","onValueSelected");
                Log.e("Tag",""+e.toString());
            }

            @Override
            public void onNothingSelected() {
                Log.e("Tag","onNothingSelected");
            }
        });
        initLineChart(/**数据集**/);
    }

    private void initLineChart() {
        //显示边界
        lineChart.setDrawBorders(false);

        //设置数据
        for (int i = 0; i < score.length; i++)
        {
            entries.add(new Entry(i, score[i]));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "收入");
        //线颜色
        lineDataSet.setColor(Color.parseColor("#97FFFF"));
        //线宽度
        lineDataSet.setLineWidth(1.6f);
        //不显示圆点
        lineDataSet.setDrawCircles(false);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        //提示线颜色和是否出现横向提示线
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setHighLightColor(Color.WHITE);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setHighlightLineWidth(3);
        final LineData data = new LineData(lineDataSet);
        //无数据时显示的文字
        lineChart.setNoDataText("暂无数据");
        //折线图不显示数值
        data.setDrawValues(false);


        //得到X轴
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount( 6, false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) score.length);
        //不显示网格线
        xAxis.setDrawGridLines(false);
        // 标签倾斜
        xAxis.setLabelRotationAngle(0);
        //设置X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                int IValue = (int) value;
                CharSequence format = DateFormat.format("MM/dd",
                        System.currentTimeMillis()-(long)(score.length-IValue)*24*60*60*1000);
                return format.toString();
            }
        });
        //得到Y轴
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        YAxis rightYAxis = lineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);
        //设置y轴的刻度数量
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //y轴
        yAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });

        //图例：得到Lengend
        Legend legend = lineChart.getLegend();
        //隐藏Lengend
        legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        //折线图点的标记
        MyMarkerView mv = new MyMarkerView(MpLineChats.this);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        //设置数据
        lineChart.setData(data);
        //图标刷新
        lineChart.invalidate();

    }
      public String format(float x) {
        CharSequence format = DateFormat.format("MM-dd",
                System.currentTimeMillis() - (long) (30 - (int) x) * 24 * 60 * 60 * 1000);
        return format.toString();
    }
    class MyMarkerView extends Mark {
        private TextView tvContent;

        private DecimalFormat format = new DecimalFormat("##0");

        public MyMarkerView(Context context ) {
            super(context, R.layout.chart_marker_view);//这个布局自己定义
            tvContent =  findViewById(R.id.txt_tips);
        }

        //显示的内容
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            super.refreshContent(e, highlight);
            tvContent.setText(format(e.getX()) + "\n" + format.format(e.getY()) + "辆");
            Log.e("Tga",e.toString());
        }

        //标记相对于折线图的偏移量
        @Override
        public MPPointF getOffset() {
            return  new MPPointF(-(getWidth() / 2), -getHeight());
        }


    }
}

