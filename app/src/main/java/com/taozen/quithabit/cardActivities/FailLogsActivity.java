package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.taozen.quithabit.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FailLogsActivity extends AppCompatActivity {

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @BindView(R.id.logtxtVwId) TextView logTxt;
    StringBuilder temp = new StringBuilder();

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_logs);
        ButterKnife.bind(FailLogsActivity.this);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(FailLogsActivity.this);
        editor = preferences.edit();

        getWindow().setStatusBarColor(ContextCompat.getColor(FailLogsActivity.this, R.color.white));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getArrayListFromMain();

    }

    private void getArrayListFromMain() {
        if (preferences.contains("arr")) {
            logTxt.append(preferences.getString("arr", "no value"));
        } else {
            logTxt.setText("nothing yet");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        preferences.getString("oka", "null");
//        if (preferences.contains("oka")){
////            b.append(preferences.getString("oka", "null"));
//            editor.putString("oka", b.toString());
//            editor.apply();
//            Log.d("ZAZEN", "onResume === let's see: shared? " + preferences.getString("oka", "null")
//                    + "strinb" + b.toString());
//        }
//        editor.putString("oka" ,b.toString());
//        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        editor.putString("oka" ,b.toString());
//        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        editor.putString("oka" ,b.toString());
//        editor.apply();
//        Log.d("ZAZEN", "onResume === let's see: shared? " + preferences.getString("oka", "null")
//                + "strinb" + b.toString());
    }
}
