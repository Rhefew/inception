package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rhefew.cocdrive.Async;
import com.rhefew.cocdrive.Cons;
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

        Spinner spinner = (Spinner)findViewById(R.id.spinnerMembersAdmin);
        String[] members = new String[Cons.results.getMembers().length()];

        for (int i =0; i < Cons.results.getMembers().length(); i++){
            members[i] = Cons.results.getMembers().optString(i);
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(members);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    public void adminVote(final View view){
        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    int val = 1;
                    Spinner spinner = (Spinner)findViewById(R.id.spinnerMembersAdmin);
                    if(view.getContentDescription().equals("Si")){
                        val = 2;
                    }
                    o = parser.getJSON("http://coc.rhefew.com/send_vote.php?member=" + spinner.getSelectedItem().toString() + "&value=" + val + "&comment=" + ((EditText)findViewById(R.id.txtCommentAdmin)).getText().toString().replaceAll(" ", "%20"));

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Print.dialog(AdminPanel.this, o.optString("result"));
            }
        }.execute();
    }

    public void sendPush(View view){
        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    EditText txtPushTitle = (EditText)findViewById(R.id.txtPushTitle);
                    EditText txtPushMessage = (EditText)findViewById(R.id.txtPushMessage);
                    String query_string ="title=" + txtPushTitle.getText().toString().replaceAll(" ", "%20") + "&message=" + txtPushMessage.getText().toString().replaceAll(" ", "%20");
                    o = parser.getJSON("http://coc.rhefew.com/push.php?" + query_string);

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(o!=null)
                    Print.dialog(AdminPanel.this, "Mensaje enviado");
            }
        }.execute();
    }
    public void updateWar(View view){
        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://coc.rhefew.com/update_war.php?war=" + Cons.results.getWar());

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(o!=null)
                    Print.dialog(AdminPanel.this, o.optString("result"));
            }
        }.execute();
    }
    public void newWar(View view){
        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://coc.rhefew.com/new_war.php?war=" + Cons.results.getWar());

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(o!=null)
                    Print.dialog(AdminPanel.this, o.optString("result"));
            }
        }.execute();
    }
}













