package com.rhefew.cocdrive;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by rodrigo on 07/10/14.
 */
public class ViewParams {


    public static int Match = 0;
    public static int Wrap = 1;
    public static int MatchWrap = 2;
    public static int WrapMatch = 3;

    public static int[] type(int layoutType){
        switch(layoutType){
            case 0: return new int[]{ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT};
            case 1: return new int[]{ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT};
            case 2: return new int[]{ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT};
            case 3: return new int[]{ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT};
            default: return new int[]{0,0};
        }
    }

    public static ViewGroup.LayoutParams layoutParams(View view, int layoutType){

        int width = type(layoutType)[0];
        int height = type(layoutType)[1];

        if(view instanceof LinearLayout){
            return new LinearLayout.LayoutParams(width, height);
        }
        else if(view instanceof RelativeLayout) {
            return new RelativeLayout.LayoutParams(width, height);
        }
        else if(view instanceof FrameLayout) {
            return new FrameLayout.LayoutParams(width, height);
        }
        else {
            return new RelativeLayout.LayoutParams(width, height);
        }
    }
}
