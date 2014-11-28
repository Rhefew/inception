package com.rhefew.cocdrive;

import android.os.AsyncTask;

/**
 * Created by rodrigo on 28/11/14.
 */
public class Async implements Action{

    public static void execute(Action action){

    }

    @Override
    public void run() {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                return null;
            }
        };
    }
}
