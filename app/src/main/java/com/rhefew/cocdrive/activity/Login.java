package com.rhefew.cocdrive.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.JSONParser;
import com.rhefew.cocdrive.Print;
import com.rhefew.cocdrive.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rodrigo on 23/11/14.
 */
public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            ((EditText)findViewById(R.id.txtUserName)).setText(sp.getString("member", ""));
        }

        ((ImageView)findViewById(R.id.imgTroop)).setImageDrawable(getRandomTroop());
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
                        startActivity(new Intent(Login.this, Splash.class));

                    }else{
                        Print.dialog(Login.this, o.optString("result"));
                    }
                    super.onPostExecute(s);
                }
            }.execute();
        }else{
            Print.dialog(Login.this, "Las contraseñas no coinciden");
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
                if(o != null && o.optString("result").toUpperCase().equals(user.toUpperCase())){

                    SharedPreferences sp = getSharedPreferences("Inception", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("registered", true);
                    editor.putString("member", user);
                    editor.commit();

                    Cons.member = user;
                    startActivity(new Intent(Login.this, Splash.class));

                }else{
                    Print.dialog(Login.this, o.optString("result"));
                }
                super.onPostExecute(s);
            }
        }.execute();
    }



    public Drawable getRandomTroop() {
        ArrayList<Integer> troops = new ArrayList<Integer>();
        troops.add(R.drawable.barbarian);
        troops.add(R.drawable.archer);
        troops.add(R.drawable.goblin);
        troops.add(R.drawable.giant);
        troops.add(R.drawable.wallbreaker);
        troops.add(R.drawable.balloon);
        troops.add(R.drawable.wizard);
        troops.add(R.drawable.healer);
        troops.add(R.drawable.dragon);
        troops.add(R.drawable.pekka);
        troops.add(R.drawable.minion);
        troops.add(R.drawable.hogrider);
        troops.add(R.drawable.valkirie);
        troops.add(R.drawable.golem);
        troops.add(R.drawable.witch);
        troops.add(R.drawable.lavahound);

        Random r = new Random();
        int randomNumber = r.nextInt(16);
        return getResources().getDrawable(troops.get(randomNumber));
    }
}
