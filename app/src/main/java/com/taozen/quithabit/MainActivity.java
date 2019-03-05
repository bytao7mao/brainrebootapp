package com.taozen.quithabit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.taozen.quithabit.Intro.IntroActivity;
import com.taozen.quithabit.ProgressCard.FailLogsActivity;
import com.taozen.quithabit.ProgressCard.ProgressActivity_HerokuStyleFetching;
import com.taozen.quithabit.ProgressCard.SavingsActivity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    Handler handler;
    Timer timer;

    //view
    @BindView(android.R.id.content) View parentLayout;
    //fab
    @BindView(R.id.fab) FloatingActionButton fab;
    //card views
    @BindView(R.id.progressCardId) CardView progressCardView;
    @BindView(R.id.progressCardId2) CardView savingsCardView;
    @BindView(R.id.progressCardId3) CardView timeStampLogsCardview;
    //text views
    @BindView(R.id.counterTextId) TextView counterText;
    @BindView(R.id.txtProgressId) TextView txtProgress;
    @BindView(R.id.targetTxtViewId) TextView targetTxtViewId;
    @BindView(R.id.moneyortimeId) TextView moneyOrTimeTextView;
    @BindView(R.id.remaining_days_Id) TextView remainingDaysTxt;


    //counter for user
    int counter;
    //user input from start dialog
    String habitString = "userHabit";//smoke, porn or alcohool
    int savings = 0;
    int progressPercent = 0, DAY_OF_CLICK = 0, DAY_OF_PRESENT = 0;
    //wil start from 1 to 3 to 7 to 14 to 21 to 30
    int userMaxCountForHabit = 35;
    boolean buttonClickedToday;

    //Toolbar
    private Toolbar toolbar;

    //Calendar
    Calendar calendarOnClick, calendarForProgress;
    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CircularProgressBar progressBar;
    WaveLoadingView waveLoadingView, waveLoadingViewBigger;
//    SeekBar seekBar;

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        parentLayout = findViewById(R.id.mylayoutId);
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = preferences.edit();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //wave loading
//        seekBar = findViewById(R.id.seekbarId);
        waveLoadingView = findViewById(R.id.waveLoadingId);
        waveLoadingViewBigger = findViewById(R.id.waveLoadingIdBigger);
        waveLoadingViewBigger.setProgressValue(80);
        waveLoadingView.setProgressValue(0);
        //animation speed :/
        waveLoadingView.setAnimDuration(2300);

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

        //progress for percent - this is a circular bar
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(10f);
        //format string of MAX target txt view
        targetDaysInitializer2(String.valueOf(userMaxCountForHabit), R.string.target_string, targetTxtViewId);

        //add font to counter number
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        counterText.setTypeface(typeface);

        try {
            updatePercent();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //intro activity check in a separate thread
        startIntroActivity();

        //GOTO percent Activity
        progressCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressPercent = preferences.getInt("progressPercent", progressPercent);
//                    Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
//                    Intent intent = new Intent(MainActivity.this, ProgressKotlinActivity.class);
                    Intent intent = new Intent(MainActivity.this, ProgressActivity_HerokuStyleFetching.class);
//                    Intent intent = new Intent(MainActivity.this, Test.class);
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
            counter = preferences.getInt("counter", 0);
            getTargetDays();
            savings = setTheSavingsPerDay(counter);
            Log.d("LOGG", "in oncreate "+"savings = " + savings + " counter = " + counter);
            moneyOrTimeInitializer();
            counterText.setText(String.valueOf(counter));
            buttonClickedToday = preferences.getBoolean("clicked", false);
            progressPercent = preferences.getInt("progressPercent", 0);
            progressBar.setProgress(progressPercent);
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

    private void counterFabButtonInitializer() {
        //active when user passed a day
        //inactive when user wait
        //counter++;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickedToday = true;
                editor.putBoolean("clicked", buttonClickedToday);
                updatePercent();
                resetProgressBar(progressPercent);
                //[calendar area]
                calendarOnClick = Calendar.getInstance();
                calendarOnClick.setTimeZone(TimeZone.getTimeZone("GMT+2"));
//                DAY_OF_CLICK = calendarOnClick.get(Calendar.DAY_OF_YEAR);
                DAY_OF_CLICK = calendarOnClick.get(Calendar.MINUTE);
                editor.putInt("presentday", DAY_OF_CLICK);
                editor.putInt("progressPercent", progressPercent);
                editor.apply();
                counter++;
                savings = setTheSavingsPerDay(counter);
                targetDaysInitializer(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
                editor.putInt("savings", savings);
                Log.d("LOGG", "in fab "+"savings = " + savings + " counter = " + counter);
                counterText.setText(String.valueOf(counter));
                editor.putInt("counter", counter);
                editor.apply();
                Log.d("taolenX", "counter from onclick = " + counter);

                Snackbar.make(parentLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                fab.hide();
            }
        });
    }

    private void targetDaysInitializer(String string, int androiId, TextView textview, String secondString) {
        //target counter string
        String userMax = string;
        String target = getString(androiId, secondString, userMax);
        textview.setText(target);
    }
    private void targetDaysInitializer2(String string, int androiId, TextView textview) {
        //target counter string
        String userMaxString = string;
        String target = getString(androiId, userMaxString);
        textview.setText(target);
    }

    private void moneyOrTimeInitializer() {
        savings = preferences.getInt("savings", 0);
        Log.d("LOGG", "in moneyOrTimeInitializer "+"savings = " + savings + " counter = " + counter);
        targetDaysInitializer(String.valueOf(savings), R.string.money_time, moneyOrTimeTextView, String.valueOf("MONEY"));
    }

    private void getTargetDays() {
        //remaining days
        String calcDaysTarget = String.valueOf(userMaxCountForHabit-counter);
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
    private void runningInBackground(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                Log.d("targaryen", "this AsyncTask running on: " + Thread.currentThread().getName());
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
//                        Log.d("targaryen", "this scheduleAtFixedRate running on: " + Thread.currentThread().getName());
                        //run till status bar is 100%
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startTheEngine();
//                                updatePercent();
                            }//run from runonuithread
                        });//runonuithread
                    }//run from Timertask
                }, 100, 1000);//Timertask
            }//run from async
        });//async
    }//runningInBackground

    public void startTheEngine() {
        try {
            updatePercent();
            progressBar.setProgress(progressPercent);
            DAY_OF_CLICK = preferences.getInt("presentday", 0);
            buttonClickedToday = preferences.getBoolean("clicked", false);
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getTimeZone("GMT+2"));
//          DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.MINUTE);
            Log.d("taolenZ", "DAY_OF_CLICK is " + DAY_OF_CLICK + " presentDAY_today is " + DAY_OF_PRESENT);
            if (counter >= userMaxCountForHabit){
                counter = 0;

                progressPercent=0;
            }
            editor.putInt("counter", counter);
            editor.apply();

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

    //onResume
    @Override
    protected void onResume() {
        super.onResume();
        updateButton();
        runningInBackground();
        Log.d("taolenX", "onResume SIMPLE>> AND counter is " + counter);
        try {
            //only retrieve and save in onpause
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
        editor.putInt("counter", counter);
        editor.putInt("progressPercent", progressPercent);
        editor.putBoolean("clicked", buttonClickedToday);
        editor.apply();
    }

    //i have in mind to use this when user FAIL to keep his promise on not abstaining on his habit
    private void resetProgressBar(Integer progressBar){
        updatePercent();
        if (progressBar == 100) {
            progressPercent = 0;
            counter = 0;
            editor.putInt("counter", counter);
            editor.putInt("progressPercent", progressPercent);
            editor.apply();
        }
    }

    public void updateButton(){
        if (buttonClickedToday){
            fab.hide();
        }
    }

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
    public void greenCodition(){
        if ((DAY_OF_PRESENT > DAY_OF_CLICK) && !buttonClickedToday) {
            //to do
            //show the activate button
            Log.d("taolenX", "greenCodition WORKINGGGGG");
            fab.show();
            //instead of counter add dialog to ask user if he did his habit
            Log.i("taolenX", "counter from greenCodition in async is " + counter);
            //NEED SOME CHECKS/TESTS FOR THIS---TO SEE IF I NEED THIS BOOLEAN TO GET
//            buttonClickedToday = preferences.getBoolean("clicked", false);
            Log.d("taolenX", "buttonClickedToday from async is " + buttonClickedToday);
            editor.putInt("counter", counter);
            editor.apply();
        }
    }

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

    public void updatePercent(){
        if (counter < userMaxCountForHabit*9/100){
            progressPercent = 10;
        } else if (counter < userMaxCountForHabit*19/100){
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
        }else if (counter < userMaxCountForHabit*95/100 && counter > userMaxCountForHabit*90/100){
            progressPercent = 90;
        }else if (counter == userMaxCountForHabit){
            progressPercent = 97;
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressPercent = 100;
                    editor.putInt("progressPercent", progressPercent);
                    txtProgress.setText(progressPercent + " %");
                }
            }, 5000);
        }
        txtProgress.setText(progressPercent + " %");
        editor.putInt("progressPercent", progressPercent);
        editor.apply();
        Log.d("TAGG", progressPercent+" ");
        waveLoadingView.setProgressValue(progressPercent);
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
}
