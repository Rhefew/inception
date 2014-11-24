package com.rhefew.cocdrive;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by rodrigo on 20/11/14.
 */
public class Results extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        LinearLayout llResultsMaster = (LinearLayout)findViewById(R.id.llResultsMaster);
        for(int i=0; i< Cons.results.optInt("count"); i++){
            /*Adding results to ResultsView rows*/
            String member = Cons.results.optJSONArray("members").optString(i);
            int value = Cons.results.optJSONArray("votations").optInt(i);
            if(value == Cons.value){
                llResultsMaster.addView(new ResultsView(this, member, value, Cons.results.optJSONArray("comments").optString(i)));
            }
        }
    }
}
