package com.rhefew.cocdrive;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by rodrigo on 20/11/14.
 */
public class ResultsView extends LinearLayout {
    public ResultsView(final Activity context, String member, final int votation, final String comment) {
        super(context);

        this.setPadding(10,10,10,10);
        this.setBackgroundResource(R.drawable.back_result);
        this.setDividerPadding(2);

        LayoutInflater inflater = context.getLayoutInflater();
        final View result = inflater.inflate(R.layout.result_item, this, false);

        TextView txtMember = (TextView) result.findViewById(R.id.txtUser);
        TextView txtComment = (TextView) result.findViewById(R.id.txtComment);
        FrameLayout frameVoteColor = (FrameLayout) result.findViewById(R.id.frameVoteColor);

        if(comment!= null && comment!= ""){
            txtComment.setText(comment);
        }

        txtMember.setText(member);
        if(votation==1){
            frameVoteColor.setBackgroundColor(Color.parseColor("#D74336"));
        }else if(votation==2){
            frameVoteColor.setBackgroundColor(Color.parseColor("#0F8E52"));
        }

        result.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if(comment!=null && !comment.equals("")) {
                    final LinearLayout llComment = (LinearLayout) result.findViewById(R.id.llComments);

                    if (llComment.getVisibility() == View.GONE) {
                        Animation animation = new TranslateAnimation(0, 0, -100, 0);
                        animation.setDuration(500);
                        llComment.setAnimation(animation);
                        llComment.startAnimation(animation);

                        llComment.setVisibility(View.VISIBLE);
                    } else {
                        Animation animation = new TranslateAnimation(0, 0, 0, -100);
                        animation.setDuration(500);
                        llComment.setAnimation(animation);
                        llComment.startAnimation(animation);

                        llComment.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                llComment.setVisibility(View.GONE);
                            }
                        }, 500);

                    }
                }
            }
        });

        this.addView(result);
    }
}

