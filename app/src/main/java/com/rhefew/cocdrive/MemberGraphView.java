package com.rhefew.cocdrive;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.XLabels;

import java.util.ArrayList;

/**
 * Created by rodrigo on 26/11/14.
 */
public class MemberGraphView extends LinearLayout {

    String member;
    int stars_0;
    int stars_1;
    int stars_2;
    int stars_3;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public MemberGraphView(Context context, String member, int stars_0, int stars_1, int stars_2, int stars_3, boolean is_memory_low) {
        super(context);

        this.member = member;
        this.stars_0 = stars_0;
        this.stars_1 = stars_1;
        this.stars_2 = stars_2;
        this.stars_3 = stars_3;

        this.setBackgroundColor(Color.parseColor("#EEEEEE"));
        this.setOrientation(VERTICAL);
        TextView txtMember = new TextView(context);
        txtMember.setText(member);
        txtMember.setPadding(5, 5, 5, 5);
        txtMember.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        txtMember.setTextColor(Color.parseColor("#1b1b1b"));

        this.addView(txtMember);

        BarChart chart = new BarChart(context);
        chart.setDrawYValues(false);

        chart.setPadding(5,5,5,5);
        chart.setUnit(" ★");
        chart.setDescription("");

        chart.setDrawYValues(true);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // disable 3D
        chart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);


        if(!is_memory_low) {
            chart.setDrawBarShadow(true);
            chart.setDrawVerticalGrid(true);
            chart.setDrawHorizontalGrid(true);
            chart.setDrawGridBackground(true);
        }
        XLabels xLabels = chart.getXLabels();
        xLabels.setPosition(XLabels.XLabelPosition.BOTTOM);
        xLabels.setCenterXLabelText(true);
        xLabels.setSpaceBetweenLabels(0);

        chart.setDrawYLabels(false);
        chart.setDrawLegend(false);

        // add a nice and smooth animation
        if(!is_memory_low) {
            chart.animateY(2500);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(stars_0, 0));
        yVals1.add(new BarEntry(stars_1, 1));
        yVals1.add(new BarEntry(stars_2, 2));
        yVals1.add(new BarEntry(stars_3, 3));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(0, 0 + " " + chart.getUnit());
        xVals.add(1, 1 + " " + chart.getUnit());
        xVals.add(2, 2 + " " + chart.getUnit());
        xVals.add(3, 3 + " " + chart.getUnit());

        BarDataSet set1 = new BarDataSet(yVals1, "Leyenda");
        set1.setColors(ColorTemplate.STARS_COLORS);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        chart.setData(data);
        chart.invalidate();


        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 300));

        this.addView(chart);

    }
}