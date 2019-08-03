package com.taozen.quithabit.ProgressCard;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class ChallengeActivity extends AppCompatActivity {
//    public static final String BUTTON_CHALLENGE_PREFS = "buttonchallenge";
//    public static final String DAY_OF_START_CHALL_PREFS = "DAY_OF_START_CHALLENGE";
    TextView challengeTextViewHello;
    Button goBtn, resetBtn;
//    int DAY_OF_START_CHALLENGE;
//    boolean falsetruebtn = true;
//    int DAY_OF_PRESENT;
//    int numberofdaysCh;
//    boolean firstStart;
//    //shared pref
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;

    private CountDownTimer countDownTimer;
    private long timeLeft = 600_000L;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

//        //shared pref
//        preferences = PreferenceManager.getDefaultSharedPreferences(ChallengeActivity.this);
//        editor = preferences.edit();
//
        challengeTextViewHello = findViewById(R.id.challengeTxtFromActivityId);
        goBtn = findViewById(R.id.btnGoId);
//        resetBtn = findViewById(R.id.btnResetId);
//        firstStart = preferences.getBoolean("firstStartBoolean", false);
//        Log.d("taozenXY", "value of firstStart = " + firstStart);
//        if (firstStart) {
//            //if first start then enable the button
//            falsetruebtn = true;
//            editor.putBoolean(BUTTON_CHALLENGE_PREFS, falsetruebtn);
//            goBtn.setEnabled(falsetruebtn);
//        } else {
//            falsetruebtn = preferences.getBoolean(BUTTON_CHALLENGE_PREFS, false);
//        }
//        editor.apply();
//        if (preferences.contains("dayofpresent")){
//            DAY_OF_PRESENT = preferences.getInt("dayofpresent", -1);
//            Log.d("taolenXY", "if (preferences.contains(\"dayofpresent\")){ dayofpresent: " + DAY_OF_PRESENT + "\nDayofstartchallenge: " + DAY_OF_START_CHALLENGE);
//        } else {
//            DAY_OF_PRESENT = -1;
//        }
//
//        if (preferences.contains(DAY_OF_START_CHALL_PREFS)){
//            DAY_OF_START_CHALLENGE = preferences.getInt(DAY_OF_START_CHALL_PREFS, -1);
//            Log.d("taolenXY", "if (preferences.contains(DAY_OF_START_CHALL_PREFS)){ dayofpresent: " + DAY_OF_PRESENT + "\nDayofstartchallenge: " + DAY_OF_START_CHALLENGE);
//        } else {
//            DAY_OF_START_CHALLENGE = DAY_OF_PRESENT;
//        }
//
//        Log.d("taolenZX", "day of pres after shared prefs: " + DAY_OF_PRESENT);
//        goBtn.setEnabled(falsetruebtn);
//
//        if (DAY_OF_START_CHALLENGE == DAY_OF_PRESENT + numberofdaysCh){
//            int ends = DAY_OF_PRESENT + numberofdaysCh;
//            challengeTextViewHello.setText("CHALLENGE FINISHED!!!");
//            goBtn.setEnabled(true);
//            editor.putBoolean(BUTTON_CHALLENGE_PREFS, true);
//            editor.apply();
//            goBtn.setText("started on:" + DAY_OF_START_CHALLENGE + "\nstart " + numberofdaysCh + " days challenge"
//            +"\nends on: " + ends);
//        } else {
//            Log.d("taolenXY", "if (DAY_OF_START_CHALLENGE == DAY_OF_PRESENT + numberofdaysCh){ ELSE\n dayofpresent: " + DAY_OF_PRESENT + "\nDayofstartchallenge: " + DAY_OF_START_CHALLENGE);
//            int ends = DAY_OF_PRESENT + numberofdaysCh;
//            challengeTextViewHello.setText("started on:" + DAY_OF_START_CHALLENGE + "\nstart " + numberofdaysCh + " days challenge"
//                    +"\nends on: " + ends);
//            goBtn.setEnabled(falsetruebtn);
//            editor.putBoolean(BUTTON_CHALLENGE_PREFS, falsetruebtn);
//            editor.apply();
//            goBtn.setText("started on:" + DAY_OF_START_CHALLENGE + "\nstart " + numberofdaysCh + " days challenge"
//                    +"\nends on: " + ends);
//        }
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startStop();








//                numberofdaysCh+=3;
//                DAY_OF_PRESENT = preferences.getInt("dayofpresent", -1);
//                Log.d("taolenZX", "day of pres on click: " + DAY_OF_PRESENT);
//                DAY_OF_START_CHALLENGE = DAY_OF_PRESENT;
//                Log.d("taolenZX", "day of start on click: " + DAY_OF_START_CHALLENGE);
//                int ends = DAY_OF_PRESENT + numberofdaysCh;
//                challengeTextViewHello.setText("started on:" + DAY_OF_START_CHALLENGE + "\nstart " + numberofdaysCh + " days challenge"
//                        +"\nends on: " + ends);
//                goBtn.setEnabled(false);
//                editor.putInt(DAY_OF_START_CHALL_PREFS, DAY_OF_START_CHALLENGE);
//                editor.putBoolean(BUTTON_CHALLENGE_PREFS, false);
//                editor.putBoolean("firstStartBoolean", false);
//                editor.apply();
            }
        });
//        Log.d("taolenZX", "day of challenge: " + DAY_OF_START_CHALLENGE);
//
//        resetBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DAY_OF_PRESENT=DAY_OF_START_CHALLENGE;
//                Log.d("taolenXY", "dayofpresent: " + DAY_OF_PRESENT + "\nDayofstartchallenge: " + DAY_OF_START_CHALLENGE);
//                int ends = DAY_OF_PRESENT + numberofdaysCh;
//                editor.putInt("dayofpresent", DAY_OF_PRESENT);
//                editor.putInt(DAY_OF_START_CHALL_PREFS, DAY_OF_START_CHALLENGE);
//                goBtn.setEnabled(true);
//                editor.apply();
//                challengeTextViewHello.setText("started on:" + DAY_OF_START_CHALLENGE + "\nstart " + numberofdaysCh + " days challenge"
//                        +"\nends on: " + ends + "\nDAYOFPRESENT: " + DAY_OF_PRESENT);
//            }
//        });

    }
    private void startStop(){
        if (timerRunning){
            stopTimer();
        } else { startTimer(); }
    }
    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //TODO
            }
        }.start();
        goBtn.setText("PAUSE");
        timerRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) (timeLeft / 60_000);
        int seconds = (int) (timeLeft % 60_000 / 1_000);
        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        challengeTextViewHello.setText(timeLeftText);
    }

    private void stopTimer(){
        countDownTimer.cancel();
        goBtn.setText("START");
        timerRunning = false;
    }
}
