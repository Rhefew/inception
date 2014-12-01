package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.R;
import com.rhefew.cocdrive.ResultsView;

/**
 * Created by rodrigo on 20/11/14.
 */
public class Results extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        LinearLayout llResultsMaster = (LinearLayout)findViewById(R.id.llResultsMaster);
        for(int i=0; i< Cons.results.getCount(); i++){

            /*Adding activity_results to ResultsView rows*/
            String member = Cons.results.getMembers().optString(i);
            int value = Cons.results.getVotations().optInt(i);
            if(value == Cons.value){
                llResultsMaster.addView(new ResultsView(this, member, value, Cons.results.getComments().optString(i)));
            }
        }
    }
}
