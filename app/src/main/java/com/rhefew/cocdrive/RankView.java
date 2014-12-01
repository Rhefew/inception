package com.rhefew.cocdrive;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class RankView extends LinearLayout {


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RankView(Context context, int position,  String member, String stars) {
        super(context);

        this.setPadding(5,5,5,5);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.rank_item, this, false);

        TextView txtUserRank = (TextView) rootView.findViewById(R.id.txtUserRank);
        TextView txtStarsRank = (TextView) rootView.findViewById(R.id.txtStarsRank);
        ImageView imgMedalRank = (ImageView) rootView.findViewById(R.id.imgMedalRank);
        TextView txtNumberRank = (TextView) rootView.findViewById(R.id.txtNumberRank);

        if(position==0){
            imgMedalRank.setImageResource(R.drawable.gold);
            this.setBackgroundColor(Color.parseColor("#EED249"));
        }else if(position==1){
            imgMedalRank.setImageResource(R.drawable.silver);
            this.setBackgroundColor(Color.parseColor("#D6D3C8"));
        }else if(position==2){
            imgMedalRank.setImageResource(R.drawable.bronze);
            this.setBackgroundColor(Color.parseColor("#F3CB7A"));
        }else{
            imgMedalRank.setVisibility(View.INVISIBLE);
            txtNumberRank.setText(String.valueOf(position+1) + ".");
        }

        txtUserRank.setText(member);
        txtStarsRank.setText(stars);
        this.addView(rootView);
    }
}