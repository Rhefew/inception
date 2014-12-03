package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                execSQL(((EditText)findViewById(R.id.editQuery)).getText().toString());
            }
        });

    }

    public void execSQL(final String query){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject jsonObject = parser.getJSON("http://coc.rhefew.com/admin_query?sql=" + query);
                    Print.dialog(AdminPanel.this, jsonObject.getString("result"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog  = new ProgressDialog(AdminPanel.this);
                dialog.setTitle("Admin - Ejecutar Query");
                dialog.setMessage("Ejecutando sentencia SQL...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
}













