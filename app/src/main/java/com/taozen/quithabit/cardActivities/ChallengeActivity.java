package com.taozen.quithabit.cardActivities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.taozen.quithabit.R;
import java.util.Locale;
import java.util.Objects;

import static com.taozen.quithabit.MainActivity.SIXTY;
import static com.taozen.quithabit.MainActivity.ZERO;

public class ChallengeActivity extends AppCompatActivity {
    public static final int TWENTY_FOUR = 24;
    private static final long MILLIS_IN_MONTH = (TWENTY_FOUR * SIXTY * SIXTY * 1000L) * 30L;
//    private static final long MILLIS_IN_WEEK = (TWENTY_FOUR * SIXTY * SIXTY * 1000L) * 7L; --> CORRECT
//    private static final long MILLIS_IN_DAY = TWENTY_FOUR * (SIXTY * SIXTY * 1000L); --> CORRECT
    private static final long MILLIS_IN_WEEK = (SIXTY * 1000L) * 4L; // TEST 4minutes
    private static final long MILLIS_IN_DAY = (SIXTY * 1000L) * 3L;// TEST 3minutes
    private static final long MILLIS_IN_HOUR = SIXTY * (SIXTY * 1000L);
    private static final long MILLIS_IN_TEN_MINUTES = (SIXTY * 1000L) * 10L;
    private static final long MILLIS_IN_MINUTE = SIXTY * 1_000L;
    public static final int ONE_THOUSAND = 1_000;
    private static final int COUNT_DOWN_INTERVAL = ONE_THOUSAND;

    private long START_TIME_IN_MILLIS = MILLIS_IN_MINUTE;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;

    //ImageView
    ImageView challengeBack;
    //ProgressCard
    CardView cardView;

    RelativeLayout relativeLayout;

    String THREEHOURS, ONEDAY, ONEWEEK, firstDialog;

    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        relativeLayout = findViewById(R.id.rltvlayout);
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(ChallengeActivity.this);
        editor = preferences.edit();

//        mTextViewCountDown = findViewById(R.id.text_view_countdown);
//        mButtonStartPause = findViewById(R.id.button_start_pause);
//        mButtonReset = findViewById(R.id.button_reset);
//        challengeBack = findViewById(R.id.challBackgroundId);
//        cardView = findViewById(R.id.progressCardIdChallengeInside);

        THREEHOURS = "THREEHOURS";ONEDAY="ONEDAY";ONEWEEK="ONEWEEK";
        try {
            if (preferences.contains("way")){
                //if we have "way" value stored in shared prefs it means that this is not
                //the first challenge so we retrieve anything beside THREEHOURS value or
                //the THREEHOURS value
                firstDialog = preferences.getString("way", null);
            } else {
                //ONLY IN THE FIRST LAUNCH
                firstDialog = THREEHOURS;
                editor.putString("way", firstDialog);
                editor.apply();
            }
            Log.d("TAOLENXY", "oncreate try catch->>ENUMS IS: " + firstDialog);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        cardView.setCardElevation(0);
        //API 21 REQUIRES
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mButtonStartPause.setElevation(ZERO);
            mButtonReset.setElevation(ZERO);
        }

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }//[ON CREATE]

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putString("way", firstDialog);
        editor.apply();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstDialog = preferences.getString("way", null);
        Log.d("TAOLENXY", "onResume->>ENUMS IS: " + firstDialog);
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    private void resetTimer() {
        try {
            if (firstDialog.equals(THREEHOURS)){
                firstDialog = ONEDAY;
                editor.putString("way", firstDialog);
                editor.apply();
                START_TIME_IN_MILLIS = MILLIS_IN_DAY;
                //show dialog with ONE DAY
            } else if (firstDialog.equals(ONEDAY)){
                firstDialog = ONEWEEK;
                editor.putString("way", firstDialog);
                editor.apply();
                START_TIME_IN_MILLIS = MILLIS_IN_WEEK;
                //show dialog with ONE WEEK
            } else if (firstDialog.equals(ONEWEEK)){
                //TODO: END CHALLENGE
                //BYE BYE
            } else {
                firstDialog = ONEDAY;
                editor.putString("way", firstDialog);
                editor.apply();
                START_TIME_IN_MILLIS = MILLIS_IN_DAY;
            }
        } catch (NullPointerException e) {e.printStackTrace();
            Log.d("TAOLENXY", "NullPointerException: " + firstDialog);}
        Log.d("TAOLENXY", "resetTimer->>ENUMS IS: " + firstDialog);
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        challengeBack.setImageResource(R.drawable.on);
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
//        int minutes = (int) (mTimeLeftInMillis / 1000) / SIXTY;
        int seconds = (int) (mTimeLeftInMillis / ONE_THOUSAND) % SIXTY;
        int months = (int) ((mTimeLeftInMillis / ONE_THOUSAND) / (86_400*7*4));
        int weeks = (int) ((mTimeLeftInMillis / ONE_THOUSAND) / (86_400*7));
        int days = (int) ((mTimeLeftInMillis / ONE_THOUSAND) / 86_400);
        int hours = (int) ((mTimeLeftInMillis / ONE_THOUSAND) / 3_600);
        int minutes = (int) ((mTimeLeftInMillis / ONE_THOUSAND) / SIXTY) - hours * SIXTY;
        String timeLeftFormatted;
        if (months == 1) {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = months\n\n%02d = weeks\n\n%02d = daysArray\n\n%02d:%02d:%02d", months,
//                    weeks, daysArray, hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = months", months);
        } else if (weeks >= 1) {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = weeks\n\n%02d = daysArray\n\n%02d:%02d:%02d", weeks,
//                    daysArray, hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = weeks", weeks);
        } else if (days >= 1) {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = daysArray\n\n%02d:%02d:%02d", daysArray,
//                    hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = daysArray", days);
        } else if (hours >= 1) {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = hours\n\n%02d:%02d:%02d", hours,
//                    hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = hours", hours);
        } else if (minutes >= 1) {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = minutes\n\n%02d:%02d:%02d", minutes,
//                    hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = minutes", minutes);
        } else {
//            timeLeftFormatted = String.format(LocaleUtils.getDefault(), "%02d = seconds\n\n%02d:%02d:%02d", seconds,
//                    hours, minutes, seconds);
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d = seconds", seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");
            if (mTimeLeftInMillis <= 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mTextViewCountDown.setText("CONGRATULATIONS!!!");
                challengeBack.setImageResource(R.drawable.congratsbk);
                congratsDialog();
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
                challengeBack.setImageResource(R.drawable.on);
            }
            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateButtons();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }

    //dialog when user pass a day
    private void welcomeDialog(){
        //dialog ------------
        new BottomDialog.Builder(this)
                .setTitle("Challenge!")
                .setContent("Can you pass " + firstDialog + " ?")
                .setPositiveText("GO")
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Log.d("BottomDialogs", "Do something!");
                    }
                }).show();
    }

    //dialog when user pass a day
    private void congratsDialog(){
        //dialog ------------
        new BottomDialog.Builder(this)
                .setTitle("Challenge!")
                .setContent("CONGRATS!! BYE")
                .setPositiveText("GO")
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog dialog) {
                        Log.d("BottomDialogs", "Do something!");
                    }
                }).show();
    }

}//[END OF CLASS]
