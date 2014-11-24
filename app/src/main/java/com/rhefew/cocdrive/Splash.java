package com.rhefew.cocdrive;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class Splash extends Activity {

    PieChart mChart;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initCountDown();
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

        new AsyncTask<Void, Void, Void>(){

            JSONObject o;

            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://inceptioncoc.comeze.com/");
                } catch (Exception e) {
                    o = new JSONObject();
                    try {
                        JSONArray values = new JSONArray("values");
                        values.put(0);
                        o.put("values", values);

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                TextView txtPercent = (TextView)findViewById(R.id.txtPercent);

                float count = 0;

                float agree = 0;
                float disagree = 0;
                if(o != null) {
                    for (int i = 0; i < o.optJSONArray("votations").length(); i++) {
                        int votevalue = o.optJSONArray("votations").optInt(i);
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
                            Cons.results = o;
                            Cons.value = e.getXIndex();
                            startActivity(new Intent(Splash.this, Results.class));

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                    if (porcentaje >= 60) {

                        ((TextView) findViewById(R.id.txtMensaje)).setText("Se inicia la guerra");
                    }
                }else{
                    Print.dialog(Splash.this, "No se realizaron votaciones");
                }
            }
        }.execute();

        Toast.makeText(getApplicationContext(), "Hola, " + Cons.member, Toast.LENGTH_LONG).show();

    }

    private void initCountDown() {

        ((TextView)findViewById(R.id.txtTimer)).setText("Votación abierta hasta las 22:00");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if(view.getContentDescription().equals("Si")){
            votar(2, "");
        }else{
            votar(1, "");
        }
    }

    private void votar(final int i, final String comment) {
        new AsyncTask<Void, Void, String>(){

            JSONObject o;
            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://inceptioncoc.comeze.com/send_vote.php?member=" + Cons.member + "&value=" + i + "&comment=" + comment);

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
}
