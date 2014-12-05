package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rhefew.cocdrive.Async;
import com.rhefew.cocdrive.JSONParser;
import com.rhefew.cocdrive.Print;
import com.rhefew.cocdrive.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by rodrigo on 02/12/14.
 */


public class AdminPanel extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button btnExecQuery = (Button)findViewById(R.id.btnExecQuery);
        btnExecQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execSQL(((EditText) findViewById(R.id.editQuery)).getText().toString());
            }
        });

    }

    public void execSQL(final String query){
        new AsyncTask<Void, Void, Void>(){

            JSONObject jsonObject;
            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    jsonObject = parser.getJSON("http://coc.rhefew.com/admin_query?sql=" + query);
                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AdminPanel.this, jsonObject.optString("result"), Toast.LENGTH_LONG);
            }
        }.execute();
    }
}













