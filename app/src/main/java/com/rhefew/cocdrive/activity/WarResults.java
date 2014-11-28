package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.os.Bundle;

import com.rhefew.cocdrive.Action;
import com.rhefew.cocdrive.Async;
import com.rhefew.cocdrive.R;

/**
 * Created by rodrigo on 28/11/14.
 */
public class WarResults extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.war_results);

        Async.execute(new Action() {
            @Override
            public void run() {

            }
        });


    }
}
