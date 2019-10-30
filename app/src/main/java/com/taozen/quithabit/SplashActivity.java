package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.anupcowkur.herebedragons.SideEffect;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taozen.quithabit.utils.MyHttpCoreAndroid;
import com.taozen.quithabit.utils.NetworkUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import butterknife.BindView;

import static com.taozen.quithabit.MainActivity.NAME;
import static com.taozen.quithabit.MainActivity.NAME_RO;
import static com.taozen.quithabit.MainActivity.RO;
import static com.taozen.quithabit.MainActivity.WE_FOUND_NO_CONTENT;
import static com.taozen.quithabit.MainActivity.ZERO;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;

public class SplashActivity extends AppCompatActivity {

    SplashActivity.MyAsyncTask task;

    //firstStart bool
    boolean isFirstStart;
    private Calendar calendarForProgress;
    int DAY_OF_PRESENT, DAY_OF_CLICK;

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //Views
    @BindView(android.R.id.content) View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        editor = preferences.edit();
        if (preferences.contains(COUNTER)){
            int c = preferences.getInt(COUNTER, 0);
            editor.putInt(COUNTER, c);
            editor.apply();
        }
        checkActivityOnlineAndGetData();
    }//[END] ON CREATE

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null && !task.isCancelled()) { task.cancel(true); }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressWarnings("ConstantConditions")
    @SideEffect
    private void checkActivityOnlineAndGetData() {
        try {
            Random random = new Random();
            int i = random.nextInt(100 - 1 + 1) + 1;
            int counter = 0;
            if (isOnline()) {
                if (preferences.contains(COUNTER)) {
                    counter = preferences.getInt(COUNTER, 0);
                }
                if (counter == 0) {
                    requestDataById(1);
                } else {
                    requestDataById(i);
                }
                //requestDataById(DAY_OF_PRESENT);
                if (BuildConfig.DEBUG){
                    Log.d("DAYZEN", "is connected to net");
                }
            } else {
                if (BuildConfig.DEBUG){
                    Log.d("DAYZEN", "is NOT connected to net");
                }
                if (counter == 0) {
                    requestDataById(1);
                } else {
                    requestDataById(i);
                }
                Snackbar snackbar;
                snackbar = Snackbar.make(parentLayout, R.string.no_connection, Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @SideEffect
    protected boolean isOnline() {
        boolean connected;
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        Network[] networks = Objects.requireNonNull(cm).getAllNetworks();
//        if (cm != null) {
//            for (Network netinfo : networks) {
//                NetworkInfo ni = cm.getNetworkInfo(netinfo);
//                if (ni.isConnected() && ni.isAvailable()) {
//                    connected = true;
//                }
//            }
//        }
        connected = NetworkUtils.isConnected(getApplicationContext());
        return connected;

//        ConnectivityManager connManager =
//                (ConnectivityManager)
//                        getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo =
//                Objects.requireNonNull(connManager).getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.isConnected();
    }//isOnline[END]

    private void requestDataById(int id) {
        task = new SplashActivity.MyAsyncTask();
        task.execute(MainActivity.HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS + "/" + id);
    }
    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

        }//onPreExecute[END]
        @Override
        protected String doInBackground(String... params) {
            try {
                //using GSON
                final JsonParser JSON_PARSER = new JsonParser();
                //using MyHttpManager getData static method
                //String content = MyHttpManager.getData(params[ZERO]);
                //Thread.sleep(1000);
                //using MyHttpCoreAndroid
                final String CONTENT_LOCAL = MyHttpCoreAndroid.getData(params[ZERO]);
                assert CONTENT_LOCAL != null : WE_FOUND_NO_CONTENT;
                final JsonElement ROOT_NODE = JSON_PARSER.parse(CONTENT_LOCAL);
                final JsonObject DETAILS_LOCAL = ROOT_NODE.getAsJsonObject();
                final JsonElement NAME_ELEMENT_NODE;
                //Log.d(TAG, "Language is: " + LocaleUtils.getDefault().getDisplayLanguage());//get language like romana
                final Locale locale;
                //version api 23 higher or equal
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locale = getResources().getConfiguration().getLocales().get(0);
                } else {
                    locale = getResources().getConfiguration().locale;
                }
                //get initials like: RO/US/EN/FR
                final String PUT_LANGUAGE_IN_STRING = locale.getLanguage();
                //get quote from ro if user is ro, else get default quotes
//                NAME_ELEMENT_NODE = (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(RO))
//                        ? DETAILS_LOCAL.get(NAME_RO) : DETAILS_LOCAL.get(NAME);
                NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME);

                if (BuildConfig.DEBUG) {
                    Log.d("TAOZEN7", "lang is: " + PUT_LANGUAGE_IN_STRING);
                }

                return NAME_ELEMENT_NODE.getAsString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]
        @Override
        protected void onPostExecute(String result) {
            try {
                final String tempResult = (result == null) ?
                        "No quotes available in this moment ..." : result;
                if (preferences.contains("splash")){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.putExtra("data", tempResult);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, FirstScreenActivity.class);
                    i.putExtra("data", tempResult);
                    firstCheckForInitialCiggarettesPerDay();
                    startActivity(i);
                }
                finish();
            } catch (final Exception e){
                e.printStackTrace();
            }
        }//onPostExecute[END]

        private void firstCheckForInitialCiggarettesPerDay() {
            if (!preferences.contains(INITIAL_CIGG_PER_DAY)){
                isFirstStart = true;editor.putBoolean("firstStart",isFirstStart);editor.apply();
                //[calendar area]
                calendarForProgress = Calendar.getInstance();
                calendarForProgress.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
                DAY_OF_CLICK = DAY_OF_PRESENT - 1;
                editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
                editor.apply();
            } else {
                isFirstStart = false;editor.putBoolean("firstStart",isFirstStart);editor.apply();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

}


