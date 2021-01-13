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

import static com.taozen.quithabit.FirstScreenActivity.SPLASH;
import static com.taozen.quithabit.MainActivity.DAYZEN;
import static com.taozen.quithabit.MainActivity.FIRST_START;
import static com.taozen.quithabit.MainActivity.NAME;
import static com.taozen.quithabit.MainActivity.NAME_RO;
import static com.taozen.quithabit.MainActivity.ONE;
import static com.taozen.quithabit.MainActivity.RO;
import static com.taozen.quithabit.MainActivity.WE_FOUND_NO_CONTENT;
import static com.taozen.quithabit.MainActivity.ZERO;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;

public class SplashActivity extends AppCompatActivity {

    public static final String NAME_ES = "nameES";
    public static final String NAME_DE = "nameDE";
    public static final String NAME_FR = "nameFR";
    public static final String ES = "es";
    public static final String FR = "fr";
    public static final String DE = "de";
    public static final String CHINESE_SIMPLIFIED = "zh";
    public static final String NAME_ZH = "nameZH";
    public static final String ZH_CN = "zh_CN";
    public static final String ZH_TW = "zh_TW";
    SplashActivity.MyAsyncTask task;

    //firstStart bool
    boolean isFirstStart;
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
            DAY_OF_PRESENT = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            Random random = new Random();
            //i is RANDOM number from min 0 and MAX 15
            int i = random.nextInt(15 - ONE + ONE) + ONE;
            int counter = 0;
            if (isOnline()) {
                if (preferences.contains(COUNTER)) {
                    counter = preferences.getInt(COUNTER, 0);
                }
//                if (counter == 0) {
//                    requestDataById(1);
//                } else {
//                    requestDataById(i);
//                }
                //MAX 100 id's
//                if (counter == ZERO) {
//                    requestDataById(1);
//                } else if (DAY_OF_PRESENT > 100 && DAY_OF_PRESENT < 200) {
//                    requestDataById(DAY_OF_PRESENT - 100);
//                } else if (DAY_OF_PRESENT > 200 && DAY_OF_PRESENT < 300) {
//                    requestDataById(DAY_OF_PRESENT - 200);
//                } else if (DAY_OF_PRESENT > 300 && DAY_OF_PRESENT < 400) {
//                    requestDataById(DAY_OF_PRESENT - 300);
//                } else {
//                    requestDataById(i);
//                }
                if (counter == ZERO) {
                    requestDataById(1);
                } else {
                    requestDataById(i);
                }
                //requestDataById(DAY_OF_PRESENT);
                if (BuildConfig.DEBUG) {
                    Log.d("DAYZEN", "is connected to net");
                    Log.d("DAYZEN", "day of present: " + DAY_OF_PRESENT);
                    Log.d("DAYZEN", "RANDOM number of tipoftheday is: " + i);
                }
            } else {

                if (counter == 0) {
                    requestDataById(ONE);
                } else {
                    requestDataById(i);
                }

                if (BuildConfig.DEBUG) {
                    Log.d("DAYZEN", "is NOT connected to net");
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

        return NetworkUtils.isConnected(getApplicationContext());

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
                if (BuildConfig.DEBUG) {
                    Log.d(DAYZEN, "lang is: " + PUT_LANGUAGE_IN_STRING +
                            "\nstring is: " + DETAILS_LOCAL.get(NAME_ES));
                }
                //get quote from ro if user is ro, else get default quotes
                if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(RO)){
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_RO);
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(ES)){
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_ES);
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(FR)) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_FR);
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(DE)) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_DE);
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(CHINESE_SIMPLIFIED)
                        || PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(ZH_CN)
                        || PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(ZH_TW)) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_ZH);
                } else {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME);
                }

//                NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME);

                if (BuildConfig.DEBUG) {
                    Log.d(DAYZEN, "lang is: " + PUT_LANGUAGE_IN_STRING +
                            "\nstring is: " + NAME_ELEMENT_NODE.getAsString());
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
//                final String tempResult = (result == null) ?
//                        getString(R.string.no_quotes) : result;
                final String tempResult = result;
                if (preferences.contains(SPLASH)) {
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
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }//onPostExecute[END]

        private void firstCheckForInitialCiggarettesPerDay() {
            if (!preferences.contains(INITIAL_CIGG_PER_DAY)) {
                isFirstStart = true;
                editor.putBoolean(FIRST_START, isFirstStart);
                editor.apply();
                //[calendar area]
                Calendar calendarForProgress = Calendar.getInstance();
                calendarForProgress.setTimeZone(TimeZone.getDefault());
                DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
                DAY_OF_CLICK = DAY_OF_PRESENT - ONE;
                editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
                editor.apply();
            } else {
                isFirstStart = false;
                editor.putBoolean(FIRST_START, isFirstStart);
                editor.apply();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

//    final String[] value = new String[1];
//    // Write a message to the database
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("books");
//    // Read from the database
//                myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            int c;
//            if (preferences.contains(COUNTER)){
//                c = preferences.getInt(COUNTER, 0)-1;
//            } else {
//                c = 0;
//            }
//            // This method is called once with the initial value and again
//            // whenever data at this location is updated.
//            value[0] = dataSnapshot.child(String.valueOf(c)).child("name").getValue(String.class);
//            Log.d("DAYZEN10", value[0]);
//            editor.putString("firebase", value[0]);
//            editor.apply();
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//        }
//    });

}


