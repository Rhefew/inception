package com.rhefew.cocdrive.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.JSONParser;
import com.rhefew.cocdrive.Print;
import com.rhefew.cocdrive.R;
import com.rhefew.cocdrive.WarInfo;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Splash extends Activity {

    PieChart mChart;
    WarInfo info;


    /*GCM*/
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static String TAG = "LaunchActivity";
    protected String SENDER_ID = "151603048070";
    private GoogleCloudMessaging gcm =null;
    private String regid = null;
    private Context context= null;
    /*END GCM*/

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        if (checkPlayServices())
        {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty())
            {
                registerInBackground();
            }
            else
            {
                Log.d(TAG, "No valid Google Play Services APK found.");
            }
        }

        initCountDown();
        initPieChart();

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject o = parser.getJSON("http://coc.rhefew.com/");
                    info = new WarInfo(o);
                } catch (Exception e) {

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                float count = 0;

                float agree = 0;
                float disagree = 0;
                if(info != null) {
                    for (int i = 0; i < info.getCount(); i++) {
                        int votevalue = info.getVotations().optInt(i);
                        count++;
                        if(votevalue>0) {
                            if (votevalue == 2) {
                                agree++;
                            } else {
                                disagree++;
                            }
                        }
                    }

                    float porcentaje = (agree / count * 100);

                    setData(agree,disagree,(count-agree-disagree), 100);

                    // add a selection listener
                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex) {
                            Cons.results = info;
                            Cons.value = e.getXIndex();
                            startActivity(new Intent(Splash.this, Results.class));

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                    if (porcentaje >= 60) {

                        ((TextView) findViewById(R.id.txtMensaje)).setText(info.getStatus());
                        if(info.getStatus_code() > 1){
                            TextView txtAgainst = (TextView)findViewById(R.id.txtAgainst);
                            txtAgainst.setVisibility(View.VISIBLE);
                            txtAgainst.setText("Enemigo: " + info.getAgainst());
                        }


                    }
                }else{
                    Print.dialog(Splash.this, "No se realizaron votaciones");
                }
            }
        }.execute();

        Toast.makeText(getApplicationContext(), "Hola, " + Cons.member, Toast.LENGTH_LONG).show();

    }

    private void initPieChart() {
        mChart = (PieChart) findViewById(R.id.chart);

        // change the color of the center-hole
        mChart.setHoleColor(Color.rgb(235, 235, 235));

        mChart.setHoleRadius(30f);
        mChart.setTransparentCircleRadius(40f);

        mChart.setDescription("");

        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        mChart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // mChart.setTouchEnabled(false);

        mChart.setCenterText("Inception\nwar");

        setData(0, 0, 0, 100);

        mChart.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);

//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(5f);
    }

    private void initCountDown() {

        ((TextView)findViewById(R.id.txtTimer)).setText("Votación abierta hasta las 22:00");
    }

    public void createWar(){
//        insert into votation (select (select MAX(WAR) from wars) as war, member, 0, '' from members)
    }

    private void setData(float pos, float neg, float neu, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        yVals1.add(new Entry(neu, 0));
        yVals1.add(new Entry(neg, 1));
        yVals1.add(new Entry(pos, 2));

        ArrayList<String> xVals = new ArrayList<String>();

        PieDataSet set1 = new PieDataSet(yVals1,"");
        set1.setSliceSpace(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.GOOGLE_COLORS)
            colors.add(c);

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);

        // undo all highlights

        mChart.highlightValues(null);

        mChart.invalidate();
    }

    public void refresh(View view){
        onCreate(null);
    }

    public void vote(View view){

        EditText txtComment = (EditText)findViewById(R.id.txtCommentSplash);
        String comment = txtComment.getText().toString();
        if(!txtComment.getText().toString().equals("")) {
            if(comment.length() > 10) {
                if (view.getContentDescription().equals("Si")) {
                    votar(2, txtComment.getText().toString());
                } else {
                    votar(1, txtComment.getText().toString());
                }
            }else{
                Print.dialog(Splash.this, "El comentario es muy corto");
            }
        }else{
            Print.dialog(Splash.this, "Escribí un comentario");
        }
    }

    private void votar(final int i, final String comment) {

        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://coc.rhefew.com/send_vote?member=" + Cons.member + "&value=" + i + "&comment=" + comment.replaceAll(" ", "%20"));

                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(o.optString("result").equals("success")){
                    Print.dialog(Splash.this, o.optString("result"));

                }else{
                    Print.dialog(Splash.this, o.optString("result"));
                }
                super.onPostExecute(s);
            }
        }.execute();
    }

    public void openStats(View view){
        Cons.results = info;
        startActivity(new Intent(Splash.this, MemberStats.class));
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.d(TAG, "This device is not supported - Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context)
    {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d(TAG, "Registration ID not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.d(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context)
    {
        return getSharedPreferences(Splash.class.getSimpleName(),Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    private void registerInBackground()
    {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }

                    regid = gcm.register(SENDER_ID);
                    Log.d(TAG, "########################################");
                    Log.d(TAG, "Current Device's Registration ID is: "+regid);
                    Log.d(TAG, "########################################");
                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();
                }
                return null;
            }
        }.execute();
    }

    @Override protected void onResume()
    {
        super.onResume();
        checkPlayServices();
    }
}
