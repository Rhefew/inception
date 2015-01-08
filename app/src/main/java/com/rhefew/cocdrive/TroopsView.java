package com.rhefew.cocdrive;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

/**
 * Created by rodrigo on 20/11/14.
 */
public class TroopsView extends LinearLayout {

    public TroopsView(Activity activity, final Troop troop, final Donation donation) {
        super(activity);

        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View troop_item = inflater.inflate(R.layout.troop_request_item, this, false);

        ImageView imgTroopItem = (ImageView) troop_item.findViewById(R.id.imgTroopItem);
        imgTroopItem.setImageResource(troop.getTroop_drawable());

        TextView txtTroopName = (TextView) troop_item.findViewById(R.id.txtTroopName);
        txtTroopName.setText(troop.getName());

        final TextView txtTroopNum = (TextView) troop_item.findViewById(R.id.txtTroopNum);
        txtTroopNum.setText(String.valueOf(0));


        Button btnRestarTropa = (Button)troop_item.findViewById(R.id.btnRestarTropa);
        btnRestarTropa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donation.removeTroop(troop)){
                    txtTroopNum.setText(String.valueOf(Integer.valueOf(txtTroopNum.getText().toString()) - 1));
                }
            }
        });

        Button btnSumarTropa = (Button)troop_item.findViewById(R.id.btnSumarTropa);
        btnSumarTropa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donation.setTroop(troop)){
                    txtTroopNum.setText(String.valueOf(Integer.valueOf(txtTroopNum.getText().toString())+1));
                }
            }
        });

        this.addView(troop_item);
    }
}

