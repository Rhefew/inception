package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.JSONParser;
import com.rhefew.cocdrive.Print;
import com.rhefew.cocdrive.R;

import org.json.JSONObject;

/**
 * Created by rodrigo on 23/11/14.
 */
public class NewUser extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        final TextView txtShowRegister = (TextView)findViewById(R.id.txtShowRegister);
        txtShowRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtShowRegister.setVisibility(View.GONE);
                findViewById(R.id.btnLogin).setVisibility(View.GONE);
                findViewById(R.id.txtPassword2).setVisibility(View.VISIBLE);
                findViewById(R.id.btnRegister).setVisibility(View.VISIBLE);
            }
        });


        SharedPreferences sp = getSharedPreferences("Inception", 0);
        if(sp.getBoolean("registered", false)){
            Cons.member = sp.getString("member", "");
            startActivity(new Intent(NewUser.this, Splash.class));
            finish();
        }
    }

    public void register(View view){
        final String user = ((EditText)findViewById(R.id.txtUserName)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword1)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.txtPassword2)).getText().toString();

        if(password.equals(password2)){
            new AsyncTask<Void, Void, String>(){

                JSONObject o;
                @Override
                protected String doInBackground(Void... params) {
                    JSONParser parser = new JSONParser();
                    try {
                        o = parser.getJSON("http://coc.rhefew.com/new_user.php?member=" + user + "&password=" + password );

                    } catch (Exception e) {
                        o = new JSONObject();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    if(o.optString("result").equals("success")){

                        SharedPreferences sp = getSharedPreferences("Inception", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("registered", true);
                        editor.putString("member", user);
                        editor.commit();

                        Cons.member = user;
                        startActivity(new Intent(NewUser.this, Splash.class));

                    }else{
                        Print.dialog(NewUser.this, o.optString("result"));
                    }
                    super.onPostExecute(s);
                }
            }.execute();
        }else{
            Print.dialog(NewUser.this, "Las contraseñas no coinciden");
        }
    }


    public void login(View view){
        final String user = ((EditText)findViewById(R.id.txtUserName)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword1)).getText().toString();

        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://coc.rhefew.com/login.php?member=" + user + "&password=" + password );

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(o != null && !o.optString("result").equals("Usuario o contraseña incorrectos")){


                    SharedPreferences sp = getSharedPreferences("Inception", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("registered", true);
                    editor.putString("member", user);
                    editor.commit();

                    Cons.member = user;
                    startActivity(new Intent(NewUser.this, Splash.class));

                }else{
                    Print.dialog(NewUser.this, o.optString("result"));
                }
                super.onPostExecute(s);
            }
        }.execute();
    }
}
