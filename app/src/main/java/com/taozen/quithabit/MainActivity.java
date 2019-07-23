package com.taozen.quithabit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.herebedragons.SideEffect;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.taozen.quithabit.AboutActivity.AboutActivity;
import com.taozen.quithabit.Intro.IntroActivity;
import com.taozen.quithabit.ProgressCard.FailLogsActivity;
import com.taozen.quithabit.ProgressCard.ProgressActivity_HerokuStyleFetching;
import com.taozen.quithabit.ProgressCard.SavingsActivity;
import com.taozen.quithabit.Utils.MyHttpCoreAndroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final String HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS = "https://pyflasktao.herokuapp.com/books";
    static final int REQUEST_TAKE_PHOTO = 123;
    Random ran;
    List<MainActivity.MyAsyncTask> tasks;
    Handler handler;
    Timer timer;
    int cigaretesPerDay = 2;
    int minutesPerDayResisted = 30 * cigaretesPerDay;

    //view
    @BindView(android.R.id.content) View parentLayout;
    //fab
    @BindView(R.id.fab) FloatingActionButton fab;
    //card views
    @BindView(R.id.progressCardId) CardView progressCardView;
    @BindView(R.id.progressCardId2) CardView savingsCardView;
    @BindView(R.id.progressCardId3) CardView timeStampLogsCardview;
    @BindView(R.id.card_view_mainID) CardView cardViewMain;
    @BindView(R.id.YourAchievmentsCardId) CardView achievmentCard;
    //text views
    @BindView(R.id.counterTextId) TextView counterText;
    @BindView(R.id.txtProgressIdForGums) TextView txtProgressForGums;
    @BindView(R.id.txtProgressIdForBreath) TextView txtProgressForBreath;
    @BindView(R.id.txtProgressIdForFatigue) TextView txtProgressForFatigue;
    @BindView(R.id.txtProgressIdForEnergy) TextView txtProgressForEnergyLevels;
    @BindView(R.id.targetTxtViewId) TextView targetTxtViewId;
    @BindView(R.id.moneyortimeId) TextView moneyOrTimeTextView;
    @BindView(R.id.remaining_days_Id) TextView remainingDaysTxt;
    @BindView(R.id.tipofthedayTxtViewId) TextView tipofthedayTxtViewId;
    @BindView(R.id.progressActivityId) TextView progressActivityId;
    @BindView(R.id.logsTxtId) TextView failLogsTxtView;
    @BindView(R.id.tvErrorId) TextView errorText;
    @BindView(R.id.TxttilliquitsmokingId) TextView tilliquitsmokingTxtView;
    @BindView(R.id.textProg) TextView textProg;
    @BindView(R.id.textProg2) TextView textProg2;
    @BindView(R.id.textProg22) TextView textProg22;
    @BindView(R.id.textProg3) TextView textProg3;
    @BindView(R.id.YourAchievmentsId) TextView yourAchievmentTxt;
    @BindView(R.id.YourProgressId) TextView yourProgressTxt;
    @BindView(R.id.YourProgressIdCigaretes) TextView userCigaretesProgressTxt;
    @BindView(R.id.YourProgressIdRank) TextView userRankProgressTxt;
    @BindView(R.id.YourProgressIdHours) TextView userHoursProgressTxt;
    @BindView(R.id.YourProgressIdCravings) TextView userCravingsProgressTxt;
    //progressbar
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.loadingProgressId2) ProgressBar progressBarLoading2;
    //image view
    @BindView(R.id.counterImageId) ImageView counterImgView;
    @BindView(R.id.rankOneId) ImageView rankOneImg;
    @BindView(R.id.rankTwoId) ImageView rankTwoImg;
    @BindView(R.id.rankThreeId) ImageView rankThreeImg;
    @BindView(R.id.rankFourId) ImageView rankFourImg;

    //counter for user
    int counter;
    //user input from start dialog
    String habitString = "userHabit";//smoke, porn or alcohool
    int savings = 0;
    int progressPercent = 0, DAY_OF_CLICK = 0, DAY_OF_PRESENT = 0, HOUR_OF_TODAY = 0;
    //wil start from 1 to 3 to 7 to 14 to 21 to 30
    int userMaxCountForHabit = -1;
    boolean buttonClickedToday;

    //Toolbar
    private Toolbar toolbar;

    //Calendar
    Calendar calendarOnClick, calendarForProgress;
    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CircularProgressBar progressBarEnergyLevel, progressBarRemainingDays, progressBarGumsLevel, progressBarFatigueLevel, progressBarBreathlevel;
    WaveLoadingView waveLoadingView, waveLoadingViewBigger;
//    SeekBar seekBar;

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        parentLayout = findViewById(R.id.drawer_layout);
        tasks = new ArrayList<>();
        ran = new Random();
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = preferences.edit();

        Log.d("LETSEE", "counter before: " + counter);
        getTargetDays();

        if (counter == 0){
            counter = 1;
            editor.putInt("counter", counter);
            editor.apply();
        } else {
            counter = preferences.getInt("counter", 0);
        }
        if (userMaxCountForHabit == -1){
            userMaxCountForHabit = 30;
            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
            editor.apply();
        } else {
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
        }

//        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.grey_800)); -TESTING PURPOSE

        progressBarLoading.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
        progressBarLoading2.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setElevation(0); //remove shadow - but now it is already removed in xml file
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        progressCardView.setCardElevation(0);
        savingsCardView.setCardElevation(0);
        timeStampLogsCardview.setCardElevation(0);
        cardViewMain.setCardElevation(0);
        achievmentCard.setCardElevation(0);

        //check online state
        checkActivityOnline();
        //settting progress card
        showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);

        //progress for percent - this is a circular bar
        progressBarEnergyLevel = findViewById(R.id.progress_bar_energy);
        progressBarFatigueLevel = findViewById(R.id.progress_bar_fatigue);
        progressBarBreathlevel = findViewById(R.id.progress_bar_breath);
        progressBarGumsLevel = findViewById(R.id.progress_bar_gums);
        progressBarRemainingDays = findViewById(R.id.progress_bar_outer);

        //CONDITION TO SET TARGET TEXT AFTER CHECKINNG COUNTER
        getTargetDays();

        setTxtViewForUserMaxCountDaysOnStringVersion(String.valueOf(userMaxCountForHabit), R.string.target_string, targetTxtViewId);
        //add font to counter number
        Typeface montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        Typeface montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
        Typeface montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        Typeface montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        Typeface montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        Typeface montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");

        counterText.setTypeface(montSerratBoldTypeface);
        targetTxtViewId.setTypeface(montSerratMediumTypeface);
        tipofthedayTxtViewId.setTypeface(montSerratItallicTypeface);
        txtProgressForEnergyLevels.setTypeface(montSerratMediumTypeface);
        txtProgressForFatigue.setTypeface(montSerratMediumTypeface);
        txtProgressForBreath.setTypeface(montSerratMediumTypeface);
        txtProgressForGums.setTypeface(montSerratMediumTypeface);
        moneyOrTimeTextView.setTypeface(montSerratLightTypeface);
        remainingDaysTxt.setTypeface(montSerratMediumTypeface);
        progressActivityId.setTypeface(montSerratMediumTypeface);
        failLogsTxtView.setTypeface(montSerratLightTypeface);
        tilliquitsmokingTxtView.setTypeface(montSerratBoldTypeface);
        textProg.setTypeface(montSerratMediumTypeface);
        textProg2.setTypeface(montSerratMediumTypeface);
        textProg22.setTypeface(montSerratMediumTypeface);
        textProg3.setTypeface(montSerratMediumTypeface);
        yourAchievmentTxt.setTypeface(montSerratMediumTypeface);
        yourProgressTxt.setTypeface(montSerratMediumTypeface);
        userCigaretesProgressTxt.setTypeface(montSerratLightTypeface);
        userRankProgressTxt.setTypeface(montSerratLightTypeface);
        userHoursProgressTxt.setTypeface(montSerratLightTypeface);
        userCravingsProgressTxt.setTypeface(montSerratLightTypeface);

        try {
            //set margin for counter
            setTheMarginOfCounter();
            //setting the achievments images for user
            setImagesForAchievmentCard();
            updatePercent();
            setImprovementProgressLevels();
            Log.d("counterval", "try { on creat " + counter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //intro activity check in a separate thread
        startIntroActivity();

        achievmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AchievmentsActivity.class);
                startActivity(intent);
            }
        });
        //GOTO percent Activity
        progressCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressPercent = preferences.getInt("progressPercent", progressPercent);
                    Intent intent = new Intent(MainActivity.this, ProgressActivity_HerokuStyleFetching.class);
                    intent.putExtra("pro", progressPercent);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });//progressCardView[END]
        timeStampLogsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarOnClick2 = Calendar.getInstance();
                calendarOnClick2.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                Intent intent = new Intent(MainActivity.this, FailLogsActivity.class);
                intent.putExtra("log", calendarOnClick2.getTime().toString());
                startActivity(intent);
            }
        });//timeStampCardView[END]
        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavingsActivity.class);
                intent.putExtra("savings", savings);
                startActivity(intent);
            }
        });//savingsCardView[END]
        //run the task
        runningInBackground();
        //counter on click for fab button
        counterFabButtonInitializer();

        //retrieving the counter, progressPercent and minute values
        try {
            getTargetDays();
            savings = setTheSavingsPerDay(counter);
            Log.d("LOGG", "in oncreate "+"savings = " + savings + " counter = " + counter);
            moneyOrTimeAndGetValueOfItFromSharedPreferences();
            Log.d("counterval", "counter value = " + counter);
            buttonClickedToday = preferences.getBoolean("clicked", false);
            progressPercent = preferences.getInt("progressPercent", 0);
            updatePercent();
            setImprovementProgressLevels();
            progressBarRemainingDays.setProgress(progressPercent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }//[END OF RETRIEVING VALUES]














































        //milestone dialog ------------
//        AlertDialog.Builder milestoneAlert = new AlertDialog.Builder(this);
//        final EditText editTextForMilestone = new EditText(MainActivity.this);
//        milestoneAlert.setMessage("Set your milestone ?");
//        milestoneAlert.setTitle("Milestone!");
//        milestoneAlert.setView(editTextForMilestone);
//        milestoneAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //What ever you want to do with the value
////                Editable milestone = editTextForMilestone.getText();
//                String getMilestone = editTextForMilestone.getText().toString();
//                userMaxCount = Integer.parseInt(getMilestone);
//
//            }
//        });
//        milestoneAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do nothing
//            }
//        });
//        milestoneAlert.show();

        //choose your habit ------------
//        AlertDialog.Builder habitAlert = new AlertDialog.Builder(this);
//        final EditText editTextForChoosingHabit = new EditText(MainActivity.this);
//        habitAlert.setMessage("Type your habit ?");
//        habitAlert.setTitle("Habit!");
//        habitAlert.setView(editTextForChoosingHabit);
//        habitAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //What ever you want to do with the value
////                Editable habit = editTextForChoosingHabit.getText();
//                habitString = editTextForChoosingHabit.getText().toString();
//
//            }
//        });
//        habitAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do nothing
//            }
//        });
//        habitAlert.show();


        //dialog ------------
//        new BottomDialog.Builder(this)
//                .setTitle("Awesome!")
//                .setContent("What can we improve? Your feedback is always welcome.")
//                .setPositiveText("OK")
//                .setPositiveBackgroundColorResource(R.color.colorPrimary)
//                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
//                .setPositiveTextColorResource(android.R.color.white)
//                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
//                .onPositive(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(BottomDialog dialog) {
//                        Log.d("BottomDialogs", "Do something!");
//                    }
//                }).show();
        //fancy dialog gif ------------
//        new FancyGifDialog.Builder(this)
//                .setTitle("Granny eating chocolate dialog box")
//                .setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
//                .setNegativeBtnText("Cancel")
//                .setPositiveBtnBackground("#FF4081")
//                .setPositiveBtnText("Ok")
//                .setNegativeBtnBackground("#FFA9A7A8")
//                .setGifResource(R.drawable.braingif)   //Pass your Gif here
//                .isCancellable(true)
//                .OnPositiveClicked(new FancyGifDialogListener() {
//                    @Override
//                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .OnNegativeClicked(new FancyGifDialogListener() {
//                    @Override
//                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .build();
    }//[END OF ONCREATE]




    @SideEffect
    private void startIntroActivity() {
        //intro
        //code for INTRO
        Thread threadForSlider = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Create a new boolean and preference and set it to true
                Log.d("taozenD", "thread separat: " + Thread.currentThread().getName());
                boolean isFirstStart = preferences.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {
                    counter = 1;
                    editor.putInt("counter", counter);
                    editor.apply();
                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Log.d("taozenD", "thread din ui: " + Thread.currentThread().getName());
                            startActivity(i);
                        }
                    });
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = preferences.edit();
                    //  Edit preference to make it false because we don'threadForSlider want this to run again
                    e.putBoolean("firstStart", false);
                    //  Apply changes
                    e.apply();
                }
            }
        });
        threadForSlider.start();//end of INTRO
    }

    private void startCheckForMaxActivity() {
        SharedPreferences.Editor i = preferences.edit();
        //intro
        //code for INTRO
        boolean is30MaxCounter = preferences.getBoolean("ismaxcounter30", true);
        boolean is60MaxCounter = preferences.getBoolean("ismaxcounter60", true);
        //  If the activity has never started before...
        if (is30MaxCounter){
            checkTheMaxCounterSixty();
            i.putBoolean("ismaxcounter30", false);
            i.apply();
        } else if (is60MaxCounter){
            checkTheMaxCounterNinety();
            i.putBoolean("ismaxcounter60", false);
            i.apply();
        }
    }
    @SideEffect
    private void counterFabButtonInitializer() {
        //active when user passed a day
        //inactive when user wait
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set margin for counter
                setTheMarginOfCounter();
                buttonClickedToday = true;
                editor.putBoolean("clicked", buttonClickedToday);
                resetProgressBar(progressPercent);
                //[calendar area]
                calendarOnClick = Calendar.getInstance();
                calendarOnClick.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                DAY_OF_CLICK = calendarOnClick.get(Calendar.DAY_OF_YEAR);
//                DAY_OF_CLICK = calendarOnClick.get(Calendar.MINUTE);  --TESTING PURPOSE
                editor.putInt("presentday", DAY_OF_CLICK);
                editor.putInt("progressPercent", progressPercent);
                editor.apply();
                setImagesForAchievmentCard();
                Log.d("taolenX", "counter from onclick = " + counter);

                new FancyGifDialog.Builder(MainActivity.this)
                        .setTitle("No smoke dialog!")
                        .setMessage("Did you abtained to smoke today ?")
                        .setNegativeBtnText("Cancel")
                        .setPositiveBtnBackground("#FF4081")
                        .setPositiveBtnText("Ok")
                        .setNegativeBtnBackground("#FFA9A7A8")
                        .setGifResource(R.drawable.source)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                counter++;
                                editor.putInt("counter", counter);
                                editor.apply();
                                checkActivityOnline();
                                //set margin for counter
                                setTheMarginOfCounter();
                                savings = setTheSavingsPerDay(counter);
                                setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                                editor.putInt("savings", savings);
                                Log.d("LOGG", "in fab "+"savings = " + savings + " counter = " + counter);
                                setImprovementProgressLevels();
                                setImagesForAchievmentCard();
                                counterText.setText(String.valueOf(counter));
                                getTargetDays();
                                showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                                Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                counter=0;
                                editor.putInt("counter", counter);
                                editor.apply();
                                checkActivityOnline();
                                Log.d("taolenX", "[after asigning] counter is = " + counter);
                                try {
                                    counter = preferences.getInt("counter", 0);
                                } catch (NullPointerException e){e.printStackTrace();}
                                Log.d("taolenX", "[after getting from preferences] counter is = " + counter);
                                savings = setTheSavingsPerDay(counter);
                                //set margin for counter
                                setTheMarginOfCounter();
                                setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                                editor.putInt("savings", savings);
                                Log.d("LOGG", "in fab "+"savings = " + savings + " counter = " + counter);
                                setImprovementProgressLevels();
                                counterText.setText(String.valueOf(counter));
                                getTargetDays();
                                showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                                Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();//[END of DIALOG]

                getTargetDays();
                fab.hide();
            }
        });
    }

    private void setTxtViewForUserSavingValueOfMoneyOrTime(String string, int androiId, TextView textview, String secondString) {
        //target counter string
        String userMax = string;
        String target = getString(androiId, secondString, userMax);
        textview.setText(target);
    }
    private void setTxtViewForUserMaxCountDaysOnStringVersion(String string, int androiId, TextView textview) {
        //target counter string
        String userMaxString = string;
        String target = getString(androiId, userMaxString);
        textview.setText(target);
    }

    private void moneyOrTimeAndGetValueOfItFromSharedPreferences() {
        savings = preferences.getInt("savings", 0);
        Log.d("LOGG", "in moneyOrTimeAndGetValueOfItFromSharedPreferences "+"savings = " + savings + " counter = " + counter);
        setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("money"));
    }

    private void getTargetDays() {
        try{
            //format string of MAX target txt view
            counter = preferences.getInt("counter", 0);
            if (counter>=60){
                userMaxCountForHabit = 90;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= 30){
                userMaxCountForHabit = 60;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else {
                userMaxCountForHabit = 30;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            }
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
            int tempMax = preferences.getInt(getString(R.string.maxCounter), 0);
            Log.d("MAX", "maxCounter from getTargetDays method is: " + tempMax);
            setTxtViewForUserMaxCountDaysOnStringVersion(String.valueOf(userMaxCountForHabit), R.string.target_string, targetTxtViewId);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        //remaining days -- + "  " for space between number of days and text
        String calcDaysTarget = "";
        if (userMaxCountForHabit-counter < 10) {
            calcDaysTarget = String.valueOf(userMaxCountForHabit-counter) + "     ";
        } else {
            calcDaysTarget = String.valueOf(userMaxCountForHabit-counter) + "   ";
        }
        String targetCalcDaysTarget = getString(R.string.remaining_days, calcDaysTarget);
        remainingDaysTxt.setText(targetCalcDaysTarget);
    }

    private int setTheSavingsPerDay(int c){
        savings = c * 10;//dollars per day
        editor.putInt("savings", savings);
        editor.apply();
        return savings;
    }

    //running task
    @SideEffect
    private void runningInBackground(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("targaryen", "this AsyncTask running on: " + Thread.currentThread().getName());
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Log.d("targaryen", "this scheduleAtFixedRate running on: " + Thread.currentThread().getName());
                        //run till status bar is 100%
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startTheEngine();
//                                updatePercent();
                            }//run from runonuithread
                        });//runonuithread
                    }//run from Timertask
                }, 0, 10000);//Timertask
            }//run from async
        });//async
    }//runningInBackground

    @SideEffect
    public void startTheEngine() {
        try {
            updatePercent();
            try {
                counter = preferences.getInt("counter", 0);
            } catch (NullPointerException e){e.printStackTrace();}

            setImagesForAchievmentCard();
            setImprovementProgressLevels();
            Log.d("TAGG", "try { counterText = " + counter);
            //set margin for counter
            setTheMarginOfCounter();
            resetProgressBar(progressPercent);
            Log.d("TAGG", "resetProgressBar = " + counter);
            counterText.setText(String.valueOf(counter));
            Log.d("TAGG", "counter = preferences.getInt(\"counter\", 0); = " + counter);

            progressBarRemainingDays.setProgress(progressPercent);
            DAY_OF_CLICK = preferences.getInt("presentday", 0);
            buttonClickedToday = preferences.getBoolean("clicked", false);
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);

            //testing area
            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            HOUR_OF_TODAY = calendar.get(Calendar.HOUR);
            Log.d("taolenZ", "hour of now: " + HOUR_OF_TODAY);
//            DAY_OF_PRESENT = calendarForProgress.get(Calendar.MINUTE);
            Log.d("taolenZ", "Dhe counter to 1\n" +
                    "                counter = 1;AY_OF_CLICK is " + DAY_OF_CLICK + " presentDAY_today is " + DAY_OF_PRESENT);

            //check both intro and maxcounter
            startCheckForMaxActivity();

            editor.putInt("counter", counter);
            getTargetDays();
//                                    Log.d("taozen", calendarForProgress.getTime().getHours() + " " + "\n" +
//                                            Calendar.HOUR);
            //if the button/check in is already clicked today,
            //we disable it by checking if buttonClickedToday is true
            updateButton();
            updateConditionGreenState();
            //end of the year condition is when clickdate is higher than presentdate
            //for example clickdate remain the day 365 and in the new year eve presentdate day is 1 january
            endOfTheYearCondition();
            //green condition is when present day is higher than click day
            //in order to run our condition = to enable our button, our check in for user
            greenCodition();
            editor.putInt("progressPercent", progressPercent);
            editor.apply();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkTheMaxCounterSixty(){
        if (counter >= userMaxCountForHabit) {
            //to add dialog and ask user if he wants to continue to 60 days
            //if not we will reset

            //dialog for ask user if he wants to go further with his progress
            new FancyGifDialog.Builder(MainActivity.this)
                    .setTitle("Target reached!!!")
                    .setMessage("Do you want to go further and set target to 60 ?")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground("#FFA9A7A8")
                    .setGifResource(R.drawable.source)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            userMaxCountForHabit = 60;
                            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                            editor.apply();
                            checkActivityOnline();
                            try {
                                counter = preferences.getInt("counter", 0);
                            } catch (NullPointerException e){e.printStackTrace();}

                            //set margin for counter
                            setTheMarginOfCounter();
                            savings = setTheSavingsPerDay(counter);
                            setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                            editor.putInt("savings", savings);
                            Log.d("LOGG", "in fab "+"savings = " + savings + " counter = " + counter);
                            setImprovementProgressLevels();
                            setImagesForAchievmentCard();
                            counterText.setText(String.valueOf(counter));
                            if (counter>=30){
                                userMaxCountForHabit = 60;
                                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                                editor.apply();
                            }
                            getTargetDays();
                            showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                            Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            counter = 1;
                            editor.putInt("counter", counter);
                            editor.apply();
                            checkActivityOnline();
                            Log.d("taolenX", "[after asigning] counter is = " + counter);
                            try {
                                counter = preferences.getInt("counter", 0);
                            } catch (NullPointerException e){e.printStackTrace();}
                            Log.d("taolenX", "[after getting from preferences] counter is = " + counter);
                            savings = setTheSavingsPerDay(counter);
                            //set margin for counter
                            setTheMarginOfCounter();
                            setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                            editor.putInt("savings", savings);
                            Log.d("LOGG", "in fab " + "savings = " + savings + " counter = " + counter);
                            setImprovementProgressLevels();
                            counterText.setText(String.valueOf(counter));
                            if (counter>=30){
                                userMaxCountForHabit = 60;
                                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                                editor.apply();
                            }
                            getTargetDays();
                            showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build();
        }//end of if
    }//enf of MaxCounter method

    public void checkTheMaxCounterNinety(){
        if (counter >= userMaxCountForHabit) {
            //to add dialog and ask user if he wants to continue to 60 days
            //if not we will reset

            //dialog for ask user if he wants to go further with his progress
            new FancyGifDialog.Builder(MainActivity.this)
                    .setTitle("Target reached!!!")
                    .setMessage("Do you want to go further and set target to 60 ?")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground("#FFA9A7A8")
                    .setGifResource(R.drawable.source)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            userMaxCountForHabit = 90;
                            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                            editor.apply();
                            checkActivityOnline();
                            try {
                                counter = preferences.getInt("counter", 0);
                            } catch (NullPointerException e){e.printStackTrace();}
                            //set margin for counter
                            setTheMarginOfCounter();
                            savings = setTheSavingsPerDay(counter);
                            setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                            editor.putInt("savings", savings);
                            Log.d("LOGG", "in fab "+"savings = " + savings + " counter = " + counter);
                            setImprovementProgressLevels();
                            setImagesForAchievmentCard();
                            counterText.setText(String.valueOf(counter));
                            getTargetDays();
                            showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                            Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            counter = 1;
                            editor.putInt("counter", counter);
                            editor.apply();
                            checkActivityOnline();
                            Log.d("taolenX", "[after asigning] counter is = " + counter);
                            try {
                                counter = preferences.getInt("counter", 0);
                            } catch (NullPointerException e){e.printStackTrace();}
                            Log.d("taolenX", "[after getting from preferences] counter is = " + counter);
                            savings = setTheSavingsPerDay(counter);
                            //set margin for counter
                            setTheMarginOfCounter();
                            setTxtViewForUserSavingValueOfMoneyOrTime(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                            editor.putInt("savings", savings);
                            Log.d("LOGG", "in fab " + "savings = " + savings + " counter = " + counter);
                            setImprovementProgressLevels();
                            counterText.setText(String.valueOf(counter));
                            getTargetDays();
                            showEntireProgressForUserCard(userCigaretesProgressTxt, userRankProgressTxt, userHoursProgressTxt, userCravingsProgressTxt);
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build();
        }//end of if
    }//enf of MaxCounter method

    //onResume
    @Override
    protected void onResume() {
        super.onResume();
        updateButton();
        setImagesForAchievmentCard();
        runningInBackground();
        Log.d("taolenX", "onResume SIMPLE>> AND counter is " + counter);
        try {
            //only retrieve and save in onpause
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
            counter = preferences.getInt("counter", 0);
            buttonClickedToday = preferences.getBoolean("clicked", false);
            progressPercent = preferences.getInt("progressPercent", progressPercent);
            Log.i("taolenX", "[onResume]progressPercent is = " + progressPercent);
            Log.i("taolenX", "NotClicked is : " + buttonClickedToday);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //onPause
    @Override
    protected void onPause() {
        super.onPause();
        //save in onpause
        editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
        editor.putInt("counter", counter);
        editor.putBoolean("clicked", buttonClickedToday);
        editor.putInt("progressPercent", progressPercent);
        editor.apply();
        Log.d("taolenX", "onPause SIMPLE>> AND counter is " + counter);
    }

    //onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
        editor.putInt("counter", counter);
        Log.d("counterval", "onDestroy " + counter);
        editor.putInt("progressPercent", progressPercent);
        editor.putBoolean("clicked", buttonClickedToday);
        editor.apply();
    }

    //i have in mind to use this when user FAIL to keep his promise on not abstaining on his habit
    private void resetProgressBar(Integer progressBar){
        updatePercent();
        setImprovementProgressLevels();
        if (progressBar == 100) {
            //to do: if press yes go counter = 1
            progressPercent = 0;
            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
            editor.putInt("progressPercent", progressPercent);
            editor.apply();
        }
    }

    public void updateButton(){
        if (buttonClickedToday){
            fab.hide();
        }
    }

    @SideEffect
    public void updateConditionGreenState() {
        //when click day is lower than today (present) && button was already clicked
        //we make the boolean false (in order for greenCondition to work) and for enabling button
        if (DAY_OF_PRESENT > DAY_OF_CLICK && buttonClickedToday) {
            buttonClickedToday = false;
            editor.putBoolean("clicked", buttonClickedToday);
            editor.apply();
            fab.hide();
            Log.d("taolenX", "buttonClickedToday from !=favoriteUserHourAM is " + buttonClickedToday);
            Log.d("taolenX", " counter = " + counter);
            //when days are the same and user already clicked
        } else if (DAY_OF_PRESENT == DAY_OF_CLICK && buttonClickedToday) {
            fab.hide();
        }
    }

    @SideEffect
    public void greenCodition(){
        if ((DAY_OF_PRESENT > DAY_OF_CLICK) && !buttonClickedToday && (HOUR_OF_TODAY >= 1)) {
            //to do
            //show the activate button
            Log.d("taolenX777", "greenCodition WORKINGGGGG");
            fab.show();
            //instead of counter add dialog to ask user if he did his habit
            Log.i("taolenX777", "counter from greenCodition in async is " + counter);
            //NEED SOME CHECKS/TESTS FOR THIS---TO SEE IF I NEED THIS BOOLEAN TO GET
//            buttonClickedToday = preferences.getBoolean("clicked", false);
            Log.d("taolenX", "buttonClickedToday from async is " + buttonClickedToday);
            editor.putInt("counter", counter);
            editor.apply();
        }
    }

    @SideEffect
    public void endOfTheYearCondition(){
        if ((DAY_OF_CLICK >= 365 && DAY_OF_PRESENT > 0) && (!buttonClickedToday)) {
            fab.show();
            //instead of counter add dialog to ask user if he did his habit
            //dialog and onclick counter++;
            Log.i("taolenX", "counter from endOfTheYearCondition in async is " + counter);
            buttonClickedToday = preferences.getBoolean("clicked", false);
            Log.d("taolenX", "buttonClickedToday from async is " + buttonClickedToday);
            editor.putInt("counter", counter);
            editor.apply();
        }
    }

    @SideEffect
    public void updatePercent(){
        if (counter < userMaxCountForHabit*9/100){
            progressPercent = 10;
        }else if (counter < userMaxCountForHabit*19/100){
            progressPercent = 15;
        }else if (counter < userMaxCountForHabit*25/100){
            progressPercent = 20;
        }else if (counter < userMaxCountForHabit*30/100){
            progressPercent = 25;
        }else if (counter < userMaxCountForHabit*40/100){
            progressPercent = 30;
        }else if (counter < userMaxCountForHabit*50/100){
            progressPercent = 40;
        }else if (counter < userMaxCountForHabit*60/100){
            progressPercent = 50;
        }else if (counter < userMaxCountForHabit*70/100){
            progressPercent = 60;
        }else if (counter < userMaxCountForHabit*80/100){
            progressPercent = 70;
        }else if (counter < userMaxCountForHabit*90/100){
            progressPercent = 80;
        }else if (counter < userMaxCountForHabit*95/100 || counter > userMaxCountForHabit*90/100){
            progressPercent = 90;
        }else if (counter == userMaxCountForHabit){
            progressPercent = 100;
            counter = 1;
            editor.putInt("counter", counter);
            editor.apply();
            Log.d("TAGG2", "counter == userMaxCountForHabit = " + counter);
        }
        Log.d("TAGG2", "counter in %%% = " + counter);
        editor.putInt("counter", counter);
        editor.putInt("progressPercent", progressPercent);
        editor.apply();
        Log.d("TAGG", progressPercent+" progressPercent");
//        waveLoadingViewBigger.setProgressValue(progressPercent);
        progressBarRemainingDays.setProgress(progressPercent);
    }

    @SideEffect
    private void setImprovementProgressLevels(){
        try {
            counter = preferences.getInt("counter", 0);
        } catch (NullPointerException e){e.printStackTrace();}
        Log.d("taolenX", "[setImprovementProgressLevels] counter is = " + counter);

        //energy levels
        if (counter > 4 && counter < 15) {
            txtProgressForEnergyLevels.setText(25 + "%");
            progressBarEnergyLevel.setProgress(25);
        } else if (counter > 14 && counter < 21) {
            txtProgressForEnergyLevels.setText(50 + "%");
            progressBarEnergyLevel.setProgress(50);
        } else if (counter > 20 && counter < 26) {
            txtProgressForEnergyLevels.setText(75 + "%");
            progressBarEnergyLevel.setProgress(75);
        } else if (counter > 25) {
            txtProgressForEnergyLevels.setText(100 + "%");
            progressBarEnergyLevel.setProgress(100);
        } else {
            txtProgressForEnergyLevels.setText(5 + "%");
            progressBarEnergyLevel.setProgress(5);
        }

        //fatigue levels
        if (counter >= 25 && counter < 31) {
            txtProgressForFatigue.setText(25+"%");
            progressBarFatigueLevel.setProgress(25);
        } else if (counter > 30 && counter < 40) {
            txtProgressForFatigue.setText(50+"%");
            progressBarFatigueLevel.setProgress(50);
        } else if (counter > 39 && counter < 50) {
            txtProgressForFatigue.setText(75+"%");
            progressBarFatigueLevel.setProgress(75);
        } else if (counter > 49) {
            txtProgressForFatigue.setText(100+"%");
            progressBarFatigueLevel.setProgress(100);
        } else {
            txtProgressForFatigue.setText(5+"%");
            progressBarFatigueLevel.setProgress(5);
        }

        //gums levels
        if (counter >= 0 && counter < 5) {
            txtProgressForGums.setText(25 + "%");
            progressBarGumsLevel.setProgress(25);
        } else if (counter > 4 && counter < 8) {
            txtProgressForGums.setText(50 + "%");
            progressBarGumsLevel.setProgress(50);
        } else if (counter > 7 && counter < 13) {
            txtProgressForGums.setText(75 + "%");
            progressBarGumsLevel.setProgress(75);
        } else if (counter > 12) {
            txtProgressForGums.setText(100 + "%");
            progressBarGumsLevel.setProgress(100);
        }

        //breath levels
        if (counter >= 0 && counter < 3) {
            txtProgressForBreath.setText(25 + "%");
            progressBarBreathlevel.setProgress(25);
        } else if (counter > 2 && counter < 6) {
            txtProgressForBreath.setText(50 + "%");
            progressBarBreathlevel.setProgress(50);
        } else if (counter > 5 && counter < 8) {
            txtProgressForBreath.setText(75 + "%");
            progressBarBreathlevel.setProgress(75);
        } else if (counter > 7) {
            txtProgressForBreath.setText(100 + "%");
            progressBarBreathlevel.setProgress(100);
        }
    }

    //MENU DRAWER
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            counter++;
            Calendar calendar = Calendar.getInstance();
            int zet2 = calendar.get(Calendar.MINUTE);
            int zet = preferences.getInt("presentday", 0);
            editor.putInt("presentday", zet2);
            Log.d("taolenZ777", "zet = " + zet2);
            counterText.setText(String.valueOf(counter));
            editor.putInt("counter", counter);
            editor.apply();
            updatePercent();
            setImprovementProgressLevels();
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

//    //MENU
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.add:
//                counter++;
//                editor.putInt("counter", counter);
//                editor.apply();
//                updatePercent();
//                resetProgressBar(progressPercent);
//                try {
//                    counter = preferences.getInt("counter", 0);
//                    counterText.setText(String.valueOf(counter));
//                    buttonClickedToday = preferences.getBoolean("clicked", false);
//                    progressPercent = preferences.getInt("progressPercent", 0);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                //add the function to perform here
//                return(true);
//            case R.id.reset:
//                counter = 0;
//                counterText.setText(String.valueOf(counter));
//                buttonClickedToday = false;
//                DAY_OF_CLICK = DAY_OF_PRESENT;
//                progressPercent = 0;
//                editor.putInt("progressPercent", progressPercent);
//                editor.putInt("counter", counter);
//                editor.putBoolean("clicked", buttonClickedToday);
//                editor.putInt("presentday", DAY_OF_CLICK);
//                editor.apply();
//                return(true);
//            case R.id.about:
//
//                return(true);
//            case R.id.exit:
//
//                return(true);
//        }
//        return super.onOptionsItemSelected(item);
//    }
    //[END OF MENU]

    //        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                waveLoadingView.setProgressValue(progress);
//                if (progress < 50) {
//                    waveLoadingView.setBottomTitle(String.format("%d", progress));
//                    waveLoadingView.setCenterTitle("");
//                    waveLoadingView.setTopTitle("");
//                } else if (progress < 80) {
//                    waveLoadingView.setBottomTitle("");
//                    waveLoadingView.setTopTitle("");
//                    waveLoadingView.setCenterTitle(String.format("%d", progress));
//                } else {
//                    waveLoadingView.setBottomTitle("");
//                    waveLoadingView.setCenterTitle("");
//                    waveLoadingView.setTopTitle(String.format("%d", progress));
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });



    @SideEffect
    protected boolean isOnline() {
        ConnectivityManager connManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }//isOnline[END]
    private void requestDataById(String url, int id) {
        MainActivity.MyAsyncTask task = new MainActivity.MyAsyncTask();
        task.execute(url + "/" + id);
    }
    @SideEffect
    private void checkActivityOnline(){
        //TODO replace ran.next with int i to be equal to the present day
        //ex: DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
        //int i = DAY_OF_PRESENT;
        //for this to work i have to provide 366 quotes
        if (isOnline()) {
            //int i = ran.nextInt(366)+1; to add 1000 quotes or so
            try {
                counter = preferences.getInt("counter", 0);
            } catch (NullPointerException e){e.printStackTrace();}
            Log.d("COUNTER777", "counter: " +counter);
            requestDataById(HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS, counter);
        } else {
            errorText.setVisibility(View.VISIBLE);
            tipofthedayTxtViewId.setText("ERROR 404");
            Snackbar.make(parentLayout, "NO INTERNET CONNECTION!", Snackbar.LENGTH_LONG).show();
        }
    }
    protected void updateDisplayString(String message) {
        tipofthedayTxtViewId.setText(message + "\n");
    }
    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            counter = preferences.getInt("counter", counter);
            counterText.setText(String.valueOf(counter));
            updateDisplayString("Starting to fetch data from heroku ...");
            if (tasks.size() == 0) {
                progressBarLoading.setVisibility(View.VISIBLE);
                progressBarLoading2.setVisibility(View.VISIBLE);
                counterImgView.setVisibility(View.INVISIBLE);
                counterText.setVisibility(View.INVISIBLE);
            }
            //if we click we add a task
            tasks.add(this);
        }//onPreExecute[END]

        @Override
        protected String doInBackground(String... params) {
            try {
                //using GSON
                JsonParser parser = new JsonParser();
                //using MyHttpManager getData static method
//              String content = MyHttpManager.getData(params[0]);
                Thread.sleep(1000);
                //using MyHttpCoreAndroid
                String content = MyHttpCoreAndroid.getData(params[0]);
                JsonElement rootNode = parser.parse(content);
                JsonObject details = rootNode.getAsJsonObject();
                JsonElement nameNode = details.get("name");
                return nameNode.getAsString();
//                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]

        @Override
        protected void onPostExecute(String result) {
            //using raw JSON PARSER
            updateDisplayString(result);
            //we get rid of the task that we created
            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBarLoading.setVisibility(View.INVISIBLE);
                progressBarLoading2.setVisibility(View.GONE);
                counterImgView.setVisibility(View.VISIBLE);
                counterText.setVisibility(View.VISIBLE);
            }
            if (result == null) {
                Toast.makeText(MainActivity.this, "Can't connect to web service",
                        Toast.LENGTH_LONG).show();
            }
        }//onPostExecute[END]

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplayString(values[0]);
        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

    private void setTheMarginOfCounter(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        Log.d("LETSEE", "usermAX before: " + userMaxCountForHabit);
        getTargetDays();
        Log.d("LETSEE", "usermAX after: " + userMaxCountForHabit);
        Log.d("MARGIN", "counter is in margin: " + counter);
        if (Build.VERSION.SDK_INT == 25 ){
            //IF ONE DECIMAL
            if ((userMaxCountForHabit-counter) < 10) {
                params.setMargins(-60, 30, 30, 0);
                //IF TWO DECIMALS
            } else {
                params.setMargins(-70, 30, 30, 0);
            }
        } else if (Build.VERSION.SDK_INT == 23) {
            //IF ONE DECIMAL
            if ((userMaxCountForHabit - counter) < 10) {
                params.setMargins(-70, 30, 30, 0);
                //IF TWO DECIMALS
            } else {
                params.setMargins(-78, 35, 30, 0);
            }
        } else if (Build.VERSION.SDK_INT == 24){
            //IF ONE DECIMAL
            if ((userMaxCountForHabit-counter) < 10) {
                params.setMargins(-80, 40, 30, 0);
                //IF TWO DECIMALS
            } else {
                params.setMargins(-90, 40, 30, 0);
            }
        } else {
            //IF ONE DECIMAL
            if ((userMaxCountForHabit-counter) < 10) {
                params.setMargins(-85, 50, 30, 0);
                //IF TWO DECIMALS
            } else {
                params.setMargins(-100, 50, 30, 0);
            }
        }
        remainingDaysTxt.setLayoutParams(params);
    }

    @SideEffect
    private void setImagesForAchievmentCard(){
        try {
            counter = preferences.getInt("counter", 0);
        } catch (NullPointerException e){e.printStackTrace();}
        Log.d("taoAchiev", "counter from achiev = " + counter);
        if (counter>0&&counter<4) {//user have between a day and a week
            rankOneImg.setBackgroundResource(R.mipmap.chevron7);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
            rankTwoImg.setAlpha(0.2f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron11);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "NOOB");
            Log.d("taoAchiev", "i am counter 0 && 8 working");
        } else if (counter>3&&counter<7){
            rankOneImg.setBackgroundResource(R.mipmap.chevron7);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron11);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "NOOB");
        } else if (counter>6&&counter<10){
            rankOneImg.setBackgroundResource(R.mipmap.chevron7);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
            rankThreeImg.setAlpha(1.0f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron11);
            rankFourImg.setAlpha(1.0f);
            editor.putString("rank", "NOOB");
        } else if (counter>9&&counter<13) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron16);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
            rankTwoImg.setAlpha(0.2f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron10);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "SOLDIER");
            Log.d("taoAchiev", "i am counter 7 && 13 working");
        } else if (counter>12&&counter<16) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron16);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron10);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "SOLDIER");
            Log.d("taoAchiev", "i am counter 7 && 13 working");
        } else if (counter>15&&counter<19) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron16);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
            rankThreeImg.setAlpha(1.0f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron10);
            rankFourImg.setAlpha(1.0f);
            editor.putString("rank", "SOLDIER");
            Log.d("taoAchiev", "i am counter 7 && 13 working");
        } else if (counter>18&&counter<22) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron3);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron4);
            rankTwoImg.setAlpha(0.2f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron5);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron12);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "CAPTAIN");
            Log.d("taoAchiev", "i am counter 7 && 13 working");
        } else if (counter>21&&counter<25) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron3);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron4);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron5);
            rankThreeImg.setAlpha(0.2f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron12);
            rankFourImg.setAlpha(0.2f);
            editor.putString("rank", "CAPTAIN");
            Log.d("taoAchiev", "i am counter 7 && 13 working");
        } else if (counter>24&&counter<28) {//when user pass 1 week
            rankOneImg.setBackgroundResource(R.mipmap.chevron3);
            rankOneImg.setAlpha(1.0f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron4);
            rankTwoImg.setAlpha(1.0f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron5);
            rankThreeImg.setAlpha(1.0f);
            rankFourImg.setBackgroundResource(R.mipmap.chevron12);
            rankFourImg.setAlpha(1.0f);
            editor.putString("rank", "CAPTAIN");
            Log.d("taoAchiev", "i am counter 7 && 13 working");}
        //TODO the rest of badges to receive
        else {
            rankOneImg.setBackgroundResource(R.mipmap.chevron7);
            rankOneImg.setAlpha(0.1f);
            rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
            rankTwoImg.setAlpha(0.1f);
            rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
            rankThreeImg.setAlpha(0.1f);
            editor.putString("rank", "PRO");
        }
    }

    private void showEntireProgressForUserCard(TextView userCigaretesProgressTxt, TextView userRankProgressTxt, TextView userHoursProgressTxt, TextView userCravingsProgressTxt){
        try {
            counter = preferences.getInt("counter", 0);
            int cigaretess = cigaretesPerDay * counter;
            String theLatestRank = preferences.getString("rank", "null");
            String lifeRegained = Integer.toString((minutesPerDayResisted / 60)*counter);
            //todo: cravings
            String cravingsTotal = Integer.toString(0);
            //setting textview with the assigned text
            userCigaretesProgressTxt.setText("Ciggaretes not smoked: " + String.valueOf(cigaretess));
            userRankProgressTxt.setText("Rank: " + String.valueOf(theLatestRank));
            userHoursProgressTxt.setText("Life regained: " + String.valueOf(lifeRegained) + " hours");
            userCravingsProgressTxt.setText("Cravings resisted: " + String.valueOf(cravingsTotal));
        } catch (NullPointerException e){e.printStackTrace();}
    }
}