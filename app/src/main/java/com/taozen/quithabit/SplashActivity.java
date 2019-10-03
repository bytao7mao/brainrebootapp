package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.anupcowkur.herebedragons.SideEffect;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taozen.quithabit.utils.MyHttpManager;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;

public class SplashActivity extends AppCompatActivity {

    SplashActivity.MyAsyncTask task;

    //firstStart bool
    boolean isFirstStart;
    private Calendar calendarForProgress;
    int DAY_OF_PRESENT, DAY_OF_CLICK;

    Handler mHandler = new Handler();
    int a = 3000;
    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        editor = preferences.edit();
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.white));
        if (preferences.contains(COUNTER)){
            int c = preferences.getInt(COUNTER, 0);
            editor.putInt(COUNTER, c);
            editor.apply();
        }

        Random random = new Random();
//        if (isOnline()) {
//            requestDataById(random.nextInt(90));
//        }
        checkActivityOnlineAndGetData();

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//                a = 0;
//            }
//        }, a);
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

    @SideEffect
    private void checkActivityOnlineAndGetData() {
        int c = 0;
        if (isOnline()) {
            try {
                if (preferences.contains(COUNTER)) {
                    c = preferences.getInt(COUNTER, 0);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (c == 0) {
                requestDataById(1);
            } else {
                requestDataById(c);
            }
//            requestDataById(DAY_OF_PRESENT);
        } else {
//            TODO:
//            errorText.setVisibility(View.VISIBLE);
//            tipofthedayTxtViewId.setText("ERROR 404");
//            Snackbar snackbar;
//            snackbar = Snackbar.make(parentLayout, "NO INTERNET CONNECTION!", Snackbar.LENGTH_LONG);
//            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(Color.RED);
//            snackbar.show();
        }
    }

    @SideEffect
    protected boolean isOnline() {
        ConnectivityManager connManager =
                (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                Objects.requireNonNull(connManager).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
                JsonParser parser = new JsonParser();
                //using MyHttpManager getData static method
                String content = MyHttpManager.getData(params[0]);
//                Thread.sleep(1000);
                //using MyHttpCoreAndroid
//                String content = MyHttpCoreAndroid.getData(params[0]);
                assert content != null;
                JsonElement rootNode = parser.parse(content);
                JsonObject details = rootNode.getAsJsonObject();
                JsonElement nameNode = details.get("name");
                return nameNode.getAsString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]
        @Override
        protected void onPostExecute(String result) {
            if (preferences.contains("splash")){
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.putExtra("data", result);
                startActivity(i);
            } else {
                Intent i = new Intent(SplashActivity.this, FirstScreenActivity.class);
                i.putExtra("data", result);
                firstCheckForInitialCiggarettesPerDay();
                startActivity(i);
            }
            finish();
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


