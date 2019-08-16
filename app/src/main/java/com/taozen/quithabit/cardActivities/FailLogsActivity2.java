package com.taozen.quithabit.cardActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FailLogsActivity2 extends AppCompatActivity {

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    StringBuilder b = new StringBuilder();

    @BindView(R.id.logtxtVwId) TextView logTxt;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_logs);
        ButterKnife.bind(FailLogsActivity2.this);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(FailLogsActivity2.this);
        editor = preferences.edit();

        getWindow().setStatusBarColor(ContextCompat.getColor(FailLogsActivity2.this, R.color.white));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (preferences.contains("oka")){
            temp = preferences.getString("oka", "null");
        }
        logTxt.setText(temp + "\n");
        getArrayListFromMain();

        Log.d("ZAZEN", "let's see: shared? " + preferences.getString("oka", "null")
        + "strinb" + b.toString());
        editor.putString("oka", logTxt.getText().toString());
        editor.apply();
    }

    private void getArrayListFromMain(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<String> s = new ArrayList<>();
        if (bundle!= null){
            s = bundle.getStringArrayList("arr");
            for (int i=0;i<s.size();i++){
                b.append(s.get(i) + "\n");
            }
            editor.putString("oka" ,b.toString());
            editor.apply();
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
