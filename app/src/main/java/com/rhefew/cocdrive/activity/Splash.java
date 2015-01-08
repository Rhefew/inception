package com.rhefew.cocdrive.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.example.games.basegameutils.GameHelper;
import com.rhefew.cocdrive.ClanInfo;
import com.rhefew.cocdrive.Cons;
import com.rhefew.cocdrive.Donation;
import com.rhefew.cocdrive.JSONParser;
import com.rhefew.cocdrive.Print;
import com.rhefew.cocdrive.R;
import com.rhefew.cocdrive.RankView;
import com.rhefew.cocdrive.Troop;
import com.rhefew.cocdrive.TroopsView;
import com.rhefew.cocdrive.ViewParams;
import com.rhefew.cocdrive.WarStats;
import com.rhefew.cocdrive.WarStatsView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Splash extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    PieChart mChart;
    ClanInfo info;
    Bundle savedInstanceState;
    ActionBar actionBar;

    /*GCM*/
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static String TAG = "LaunchActivity";
    protected String SENDER_ID = "151603048070";
    private GoogleCloudMessaging gcm = null;
    private String regid = null;
    private Context context = null;
    /*END GCM*/

    /*Google Play Game Services*/
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    /*END*/

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;


        setContentView(R.layout.activity_splash);

        actionBar = getActionBar();

        // Create the Google Api Client with access to Plus and Games
        Cons.mGoogleClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();

        context = this;
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            } else {
                Log.d(TAG, "No valid Google Play Services APK found.");
            }
        }

        View view = findViewById(R.id.btnSignIn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInClicked(v);
            }
        });

        loadWarInfo();
        Toast.makeText(getApplicationContext(), "Hola, " + Cons.member, Toast.LENGTH_LONG).show();
        if (Cons.member.toLowerCase().equals("rhefew")) {
            findViewById(R.id.imgAdmin).setVisibility(View.VISIBLE);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Inception", 0);
        if(sharedPreferences.getBoolean("google_connected", false)){
            signInClicked(findViewById(R.id.btnSignIn));
        }


    }

    private void loadWarInfo() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject o = parser.getJSON("http://coc.rhefew.com/");
                    info = new ClanInfo(o);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                info.setStatus_code(2);
                if(info.getStatus_code() == 1) {
                    initCountDown();
                    displayVotationControls();

                    initPieChart();

                    float count = 0;

                    float agree = 0;
                    float disagree = 0;
                    if (info != null) {
                        for (int i = 0; i < info.getCount(); i++) {
                            int votevalue = info.getVotations().optInt(i);
                            count++;
                            if (votevalue > 0) {
                                if (votevalue == 2) {
                                    agree++;
                                } else {
                                    disagree++;
                                }
                            }
                        }

                        setData(agree, disagree, (count - agree - disagree), 100);

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

                    } else {
                        Print.dialog(Splash.this, "No se realizaron votaciones");
                    }
                }else if(info.getStatus_code() ==2){
                    createTroopsView();
                    hideVotationControls();
                }else{
                    createStatsView();
                    hideVotationControls();
                }



                actionBar.setTitle(info.getStatus());

                 /*unlocking achievements*/

                JSONObject achievements_stats = info.getAchievements_stats().optJSONObject(Cons.member);
                if (achievements_stats != null) {
                    int voteCount = achievements_stats.optInt("vote_count");
                    int votePositive = achievements_stats.optInt("vote_positive_count");
                    int voteNegative = achievements_stats.optInt("vote_negative_count");

                    unlockVoteAchievements(voteCount, votePositive, voteNegative);

                }

            }


        }.execute();
    }


    private void unlockVoteAchievements(int voteCount, int votePositive, int voteNegative) {
        if(Cons.mGoogleClient.isConnected()) {
            if (voteCount > 0) {
                Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_votante_novato));
            }
            if (votePositive >= 5) {
                Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_guerrero));
            }
            if (votePositive >= 10) {
                Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_hroe));
            }
            if (voteNegative >= 5) {
                Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_aguafiestas));
            }
            if (voteNegative >= 10) {
                Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_pacifista));
            }
        }

        for(int i=0; i< info.getMember_stars().length(); i++){

            /*Adding activity_results to ResultsView rows*/
            JSONObject member = info.getMember_stars().optJSONObject(String.valueOf(i+1));
            String nickName = member.optString("member");

            if(Cons.mGoogleClient.isConnected()) {
                if (i >=0 && i <=2 && Cons.member.toUpperCase().equals(nickName.toUpperCase())) {
                    switch (i) {
                        case 0:
                            Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_dorado));
                            break;
                        case 1:
                            Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_plateado));
                            break;
                        case 2:
                            Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_bronceado));
                            break;
                    }
                }
            }
        }
    }

    private void displayVotationControls() {
        findViewById(R.id.llVotationControls).setVisibility(View.VISIBLE);
    }

    private void hideVotationControls() {
        findViewById(R.id.llVotationControls).setVisibility(View.GONE);
    }

    private void createStatsView() {
        new AsyncTask<Void, Void, Void>() {

            WarStats stats;

            @Override
            protected Void doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject json = parser.getJSON("http://coc.rhefew.com/wars_info.php");
                    stats = new WarStats(json);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;


            }

            @Override
            protected void onPostExecute(Void aVoid) {
                LinearLayout llMasterDetails = (LinearLayout) findViewById(R.id.llMasterDetails);
                llMasterDetails.removeAllViews();
                llMasterDetails.addView(new WarStatsView(Splash.this, stats));
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void createTroopsView() {

        LinearLayout llMasterDetails = (LinearLayout) findViewById(R.id.llMasterDetails);
        llMasterDetails.removeAllViews();

        Donation donation = new Donation(25);
        ScrollView scrollView = new ScrollView(Splash.this);
        LinearLayout holder = new LinearLayout(getApplicationContext());
        holder.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(holder);
        ViewParams.layoutParams(holder, ViewParams.Match);
        ViewParams.layoutParams(scrollView, ViewParams.Match);
        for (int i =0; i<Troop.Type.values().length; i++){
            holder.addView(new TroopsView(Splash.this, new Troop(Troop.Type.values()[i]), donation));
        }

        llMasterDetails.addView(scrollView);
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

        Date votation = new Date(info.getVotation_date());
        Date current = new Date(info.getCurrent_date());
        long diffInMillisec;

        diffInMillisec = votation.getTime() - current.getTime();

        new CountDownTimer(diffInMillisec, 1000) {

            @Override
            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
                String text = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                ((TextView) findViewById(R.id.txtTimer)).setText("Faltan " + text);
            }

            @Override
            public void onFinish() {
                ((TextView) findViewById(R.id.txtTimer)).setText("La votación iniciará pronto");
            }
        }.start();
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

        PieDataSet set1 = new PieDataSet(yVals1, "");
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

    public void refresh(View view) {
        loadWarInfo();
    }

    public void vote(View view) {

        EditText txtComment = (EditText) findViewById(R.id.txtCommentSplash);
        String comment = txtComment.getText().toString();
        if (!txtComment.getText().toString().equals("")) {
            if (comment.length() > 10) {
                if (view.getContentDescription().equals("Si")) {
                    votar(2, txtComment.getText().toString());
                } else {
                    votar(1, txtComment.getText().toString());
                }
            } else {
                Print.dialog(Splash.this, "El comentario es muy corto");
            }
        } else {
            Print.dialog(Splash.this, "Escribí un comentario");
        }
    }

    private void votar(final int i, final String comment) {

        new AsyncTask<Void, Void, String>() {

            JSONObject o;

            @Override
            protected String doInBackground(Void... params) {
                JSONParser parser = new JSONParser();
                try {
                    o = parser.getJSON("http://coc.rhefew.com/send_vote.php?member=" + Cons.member + "&value=" + i + "&comment=" + comment.replaceAll(" ", "%20"));

                    if(comment.length() >= 100){
                        Games.Achievements.unlock(Cons.mGoogleClient, getString(R.string.achievement_literato));
                    }

                    SharedPreferences sp = getSharedPreferences("Inception", 0);
                    if(!sp.getBoolean("voted_war_" + info.getWar(), false)){
                        Games.Achievements.increment(Cons.mGoogleClient, getString(R.string.achievement_votante_casual), 1);
                        Games.Achievements.increment(Cons.mGoogleClient, getString(R.string.achievement_votante_experto), 1);
                        if(i==1){
                            Games.Achievements.increment(Cons.mGoogleClient, getString(R.string.achievement_aguafiestas), 1);
                            Games.Achievements.increment(Cons.mGoogleClient, getString(R.string.achievement_pacifista), 1);
                        }

                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("voted_war_" + info.getWar(), true);
                    }
                } catch (Exception e) {
                    o = new JSONObject();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (o.optString("result").equals("success")) {
                    Print.dialog(Splash.this, o.optString("result"));

                } else {
                    Print.dialog(Splash.this, o.optString("result"));
                }
                super.onPostExecute(s);
            }
        }.execute();
    }

    public void openStats(View view) {
        Cons.results = info;
        startActivity(new Intent(Splash.this, Rank.class));
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

    private String getRegistrationId(Context context) {
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

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(Splash.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }

                    regid = gcm.register(SENDER_ID);
                    Log.d(TAG, "########################################");
                    Log.d(TAG, "Current Device's Registration ID is: " + regid);
                    Log.d(TAG, "########################################");

                    sendRegID();
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return null;
            }
        }.execute();
    }

    private void sendRegID() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    JSONParser parser = new JSONParser();
                    parser.get("http://coc.rhefew.com/reg_id.php?member=" + Cons.member + "&id=" + regid);
                } catch (Exception e) {
                    Log.e(getApplicationContext().getPackageName(), e.getMessage());
                    e.printStackTrace();
                }

                return null;

            }
        }.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    public void openAdminPanel(View view) {
        if (Cons.member.toLowerCase().equals("rhefew")) {
            Cons.results = info;
            startActivity(new Intent(Splash.this, AdminPanel.class));
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        SharedPreferences sharedPreferences = getSharedPreferences("Inception", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("google_connected", true);
        editor.commit();

        findViewById(R.id.viewAchievements).setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {

        // Attempt to reconnect
        Cons.mGoogleClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    Cons.mGoogleClient, connectionResult,
                    RC_SIGN_IN, "Error al conectarse con google play services")) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                Cons.mGoogleClient.connect();

            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, 0);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // Call when the sign-in button is clicked
    public void signInClicked(View v) {
        v.setVisibility(View.GONE);
        mSignInClicked = true;
        Cons.mGoogleClient.connect();
    }

    // Call when the sign-out button is clicked
    public void signOutclicked(View view) {
        SignInButton btn = (SignInButton) findViewById(R.id.btnSignIn);
        btn.setStyle(SignInButton.SIZE_STANDARD, SignInButton.COLOR_LIGHT);
        mSignInClicked = false;
        Games.signOut(Cons.mGoogleClient);
        Print.dialog(Splash.this, "Te desconectaste de Google+");
    }

    public void gameServicesGetAchievements(View view) {
        startActivityForResult(Games.Achievements.getAchievementsIntent(Cons.mGoogleClient), 555);
    }
}