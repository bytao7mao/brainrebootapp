package com.taozen.quithabit;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.anupcowkur.herebedragons.SideEffect;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.github.fabtransitionactivity.SheetLayout;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.taozen.quithabit.about.AboutActivity;
import com.taozen.quithabit.cardActivities.AchievmentsActivity;
import com.taozen.quithabit.cardActivities.ChallengeActivity;
import com.taozen.quithabit.cardActivities.FailLogsActivity;
import com.taozen.quithabit.cardActivities.SavingsActivity;
import com.taozen.quithabit.notif.MyReceiver;
import com.taozen.quithabit.utils.MyHttpCoreAndroid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.CHALLENGES_STRING;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKED;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.DAYOFPRESENT;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.HOUR_OF_FIRSLAUNCH_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.LIFEREGAINED;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.MODIFIED_CIGG_PER_DAY;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;

public class MainActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "TAOMAO";

    String arr = "";
    long firstSave;
    long longFromSavingActivity;

    public static final String HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS = "https://pyflasktao.herokuapp.com/books";

    boolean higherThanOne;
    private List<MainActivity.MyAsyncTask> tasks;
    private Timer timer;
    Float lifeRegained;

    ArrayList<String> quotesForPassingTheDayList = new ArrayList<>();

    private int cigarettesPerDay;

    //Views
    @BindView(android.R.id.content) View parentLayout;
    //Fab
    @BindView(R.id.fab) FloatingActionButton fab;
    //CardViews
    @BindView(R.id.progressCardIdProgress) CardView progressCardView;
    @BindView(R.id.progressCardIdSavings) CardView savingsCardView;
    @BindView(R.id.progressCardIdChallenge) CardView challengeCardView;
    @BindView(R.id.progressCardIdLogs) CardView timeStampLogsCardview;
    @BindView(R.id.card_view_mainID) CardView cardViewMain;
    @BindView(R.id.progressCardIdAchievments) CardView achievementRanksCard;
    @BindView(R.id.progressCardId) CardView upperProgressPercentsCard;

    //TextViews
    @BindView(R.id.exploreAchievementId) TextView exploreAId;
    @BindView(R.id.exploreSavingsId) TextView exploreSId;
    //    @BindView(R.id.rankFourIdText) TextView rankFourTxt;
//    @BindView(R.id.rankThreeIdText) TextView rankThreeTxt;
//    @BindView(R.id.rankTwoIdText) TextView rankTwoTxt;
//    @BindView(R.id.rankOneIdText) TextView rankOneTxt;
    @BindView(R.id.comingSoonTxtForChallenge2) TextView comingSoonTxtForChallenge2;
    //    @BindView(R.id.comingSoonTxtForProgress) TextView comingSoonTxtForProgress;
    @BindView(R.id.comingSoonTxtForChallenge) TextView comingSoonTxtForChallenge;
    @BindView(R.id.rank_master) TextView rankMasterTxt;
    @BindView(R.id.toolbar_subtitle) TextView subTextToolbar;
    @BindView(R.id.counterTextId) TextView counterText;
    @BindView(R.id.txtProgressIdForGums) TextView txtProgressForGums;
    @BindView(R.id.txtProgressIdForBreath) TextView txtProgressForBreath;
    @BindView(R.id.txtProgressIdForFatigue) TextView txtProgressForFatigue;
    @BindView(R.id.txtProgressIdForEnergy) TextView txtProgressForEnergyLevels;
    @BindView(R.id.targetTxtViewId) TextView targetTxtViewId;
    @BindView(R.id.savingsTxtId) TextView moneySavingsTxt;
    @BindView(R.id.remaining_days_Id) TextView remainingDaysTxt;
    @BindView(R.id.tipofthedayTxtViewId) TextView tipofthedayTxtView;
    @BindView(R.id.progressActivityId) TextView progressBarsTxt;
    //    @BindView(R.id.logsTxtId) TextView failLogsTxtView;
    @BindView(R.id.challengeTxtIdTitleId) TextView challengeTextViewTitle;
    //    @BindView(R.id.challengeTextId) TextView challengeTextViewSubtitle;
    @BindView(R.id.tvErrorId) TextView errorText;
    @BindView(R.id.textNonSmokerId) TextView textNonSmoker;
    @BindView(R.id.subTextSmokeId) TextView subTextNonSmoker;
    @BindView(R.id.subTextEnergyId) TextView subTextEnergy;
    @BindView(R.id.subTextBreathId) TextView subTextBreath;
    @BindView(R.id.subTextFatigueId) TextView subTextFatigue;
    @BindView(R.id.subTextGumsId) TextView subTextGums;
    @BindView(R.id.YourAchievmentsId) TextView yourAchievmentTxt;
    @BindView(R.id.YourProgressId) TextView yourProgressTxt;
    @BindView(R.id.YourSavingsId) TextView yourSavingsTxt;
    @BindView(R.id.YourLogsId) TextView yourLogsTxt;
    @BindView(R.id.YourProgressIdCigaretes) TextView userCigaretesProgressTxt;
    @BindView(R.id.YourHighestStreakId) TextView userHighestStreakTxt;
    @BindView(R.id.YourProgressIdHours) TextView userHoursProgressTxt;
    //ProgressBar
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.loadingProgressId2) ProgressBar progressBarLoading2;
    //ImageViews
//    @BindView(R.id.addSavingsSumImageId)
//    AppCompatImageButton addSavingsSumImg;
    @BindView(R.id.counterImageId) ImageView counterImgView;
    @BindView(R.id.rankOneId) ImageView rankOneImg;
    @BindView(R.id.rankTwoId) ImageView rankTwoImg;
    @BindView(R.id.rankThreeId) ImageView rankThreeImg;
    @BindView(R.id.rankFourId) ImageView rankFourImg;
    @BindView(R.id.backgroundId) ImageView backgroundImgWall;
    //  @BindView(R.id.imageViewMiddleId) ImageView imageViewMiddle;
    @BindView(R.id.pulsator) PulsatorLayout pulsator;
    //CircularProgressBar
    //progress for percent - this is a circular bar
    @BindView(R.id.progress_bar_energy) CircularProgressBar progressBarEnergyLevel;
    @BindView(R.id.progress_bar_gums) CircularProgressBar progressBarGumsLevel;
    @BindView(R.id.progress_bar_fatigue) CircularProgressBar progressBarFatigueLevel;
    @BindView(R.id.progress_bar_breath) CircularProgressBar progressBarBreathlevel;

    //firstStart bool
    boolean isFirstStart;
    //counter for user
    private int counter;
    private long savings;
    private int DAY_OF_CLICK,
            DAY_OF_PRESENT,
            HOUR_OF_DAYLIGHT,
            HOUR_OF_FIRSTLAUNCH;
    //wil start from 30 to 60 to 90
    private int userMaxCountForHabit = -1;
    //default false
    private boolean buttonClickedToday;
    //Toolbar
    private Toolbar toolbar;
    //Calendar
    private Calendar calendarOnClick,
            calendarForProgress;
    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DisplayMetrics metrics = new DisplayMetrics();
    private Configuration config;
    private String challs;
    private StringBuilder strBuilder = new StringBuilder();

    //fonts
    static Typeface montSerratBoldTypeface;
    static Typeface montSerratItallicTypeface;
    static Typeface montSerratLightTypeface;
    static Typeface montSerratMediumTypeface;
    static Typeface montSerratSemiBoldTypeface;
    static Typeface montSerratExtraBoldTypeface;
    static Typeface montSerratSimpleBoldTypeface;
    static Typeface montSerratThinItalicTypeface;
    static Typeface montSerratMediumItalicTypeface;
    static Typeface montSerratSemiBoldItalicTypeface;

    DecimalFormat numberFormat;
    ObjectAnimator anim;
    int i = 1;

    MainActivity.MyAsyncTask task;

    private String generateQuoteForPassingTheDay() {
        Random random = new Random();
        quotesForPassingTheDayList.add(getString(R.string.pass_one));
        quotesForPassingTheDayList.add(getString(R.string.pass_two));
        quotesForPassingTheDayList.add(getString(R.string.pass_three));
        quotesForPassingTheDayList.add(getString(R.string.pass_four));
        quotesForPassingTheDayList.add(getString(R.string.pass_five));
        quotesForPassingTheDayList.add(getString(R.string.pass_six));

        return quotesForPassingTheDayList.get(random.nextInt(quotesForPassingTheDayList.size()));
//        return quotesForPassingTheDayList.get(0);
    }


    @BindView(R.id.bottom_sheet) SheetLayout sheetLayout;
    //OnCreate [START]
    @UiThread
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    try {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
//        StringBuffer stringBuffer = new StringBuffer();
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = preferences.edit();

        sheetLayout.setFab(fab);
        sheetLayout.setFabAnimationEndListener(this);

//        sheetLayout.setBackgroundColor(getResources().getColor(R.color.white));

        firstCheckForInitialCiggarettesPerDay();
        startFirstActivity();
        //using API from splash
        final Intent INTENT = getIntent();
        final String NAME = INTENT.getStringExtra("data");
        tipofthedayTxtView.setText(NAME);
//        tipofthedayTxtView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        numberFormat = new DecimalFormat("#.##");

        //anim for subtext
        anim = ObjectAnimator.ofInt(subTextNonSmoker,
                "TextColor",
                Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.greish),
                ContextCompat.getColor(getApplicationContext(), R.color.greish));
        anim.setDuration(1200);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(Animation.INFINITE);

        //testing area
        final Date DATE = new Date();
        final Calendar CALENDAR = GregorianCalendar.getInstance();
        CALENDAR.setTime(DATE);
        HOUR_OF_DAYLIGHT = CALENDAR.get(Calendar.HOUR_OF_DAY);
        setTheHourOfFirstLaunch(CALENDAR);
        //pn
        triggerPushNotification(HOUR_OF_FIRSTLAUNCH);
        //leave it for pay version
//        setBackgroundForDaylightOrNight();
        tasks = new ArrayList<>();
        config = getResources().getConfiguration();
        //set text for checkin
        setCheckInText();

        //color of the FAB - NOW IS already changed in XML
        //fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //CONDITION TO SET TARGET TEXT AFTER CHECKINNG COUNTER
        setTargetAfterCheckingCounter();
//        long TempSavings = preferences.getLong(SAVINGS_FINAL, -100);
        setTargetDays();
        firstCheckMax();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        progressBarLoading.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBarLoading2.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//      getSupportActionBar().setElevation(0); //remove shadow - but now it is already removed in xml file

        progressCardView.setCardElevation(0);
        savingsCardView.setCardElevation(0);
        timeStampLogsCardview.setCardElevation(0);
        cardViewMain.setCardElevation(0);
        achievementRanksCard.setCardElevation(0);
        challengeCardView.setCardElevation(0);
        upperProgressPercentsCard.setCardElevation(0);

        //setMargin
        setMarginForProgress();

        if (preferences.contains("firstsave")){
            firstSave = preferences.getLong("firstsave",0);
            Log.d("taogenX", "firstsave: " + firstSave);
        } else {
            DAY_OF_CLICK = preferences.getInt(CLICKDAY_SP, 0);
            ttfancyDialogForFirstTimeLaunch(getString(R.string.welcome_to_quit_habit), getString(R.string.first_day));
            greenCondition();
            Log.d("taogenX", "firstsave: " + firstSave);
        }

        setTxtViewForUserMaxCountDaysOnStringVersion(
                String.valueOf(userMaxCountForHabit),
                R.string.target_string, targetTxtViewId);

        montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
        montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
        montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");
        montSerratThinItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ThinItalic.ttf");
        montSerratMediumItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-MediumItalic.ttf");
        montSerratSemiBoldItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBoldItalic.ttf");

        tipofthedayTxtView.setTypeface(montSerratSemiBoldItalicTypeface);
        exploreAId.setTypeface(montSerratMediumTypeface);
        exploreSId.setTypeface(montSerratMediumTypeface);
        counterText.setTypeface(montSerratBoldTypeface);
        remainingDaysTxt.setTypeface(montSerratSimpleBoldTypeface);
        targetTxtViewId.setTypeface(montSerratSimpleBoldTypeface);
        txtProgressForEnergyLevels.setTypeface(montSerratBoldTypeface);
        txtProgressForFatigue.setTypeface(montSerratBoldTypeface);
        txtProgressForBreath.setTypeface(montSerratBoldTypeface);
        txtProgressForGums.setTypeface(montSerratBoldTypeface);
        moneySavingsTxt.setTypeface(montSerratMediumTypeface);
        challengeTextViewTitle.setTypeface(montSerratBoldTypeface);
        progressBarsTxt.setTypeface(montSerratBoldTypeface);
        textNonSmoker.setTypeface(montSerratBoldTypeface);
        subTextEnergy.setTypeface(montSerratMediumTypeface);
        subTextBreath.setTypeface(montSerratMediumTypeface);
        subTextFatigue.setTypeface(montSerratMediumTypeface);
        subTextGums.setTypeface(montSerratMediumTypeface);
        yourAchievmentTxt.setTypeface(montSerratBoldTypeface);
        yourProgressTxt.setTypeface(montSerratBoldTypeface);
        yourSavingsTxt.setTypeface(montSerratBoldTypeface);
        yourLogsTxt.setTypeface(montSerratBoldTypeface);
        userCigaretesProgressTxt.setTypeface(montSerratMediumTypeface);
        userHighestStreakTxt.setTypeface(montSerratMediumTypeface);
        userHoursProgressTxt.setTypeface(montSerratMediumTypeface);
        subTextNonSmoker.setTypeface(montSerratMediumTypeface);
        subTextToolbar.setTypeface(montSerratSemiBoldTypeface);
        rankMasterTxt.setTypeface(montSerratMediumTypeface);
        comingSoonTxtForChallenge.setTypeface(montSerratMediumTypeface);
        comingSoonTxtForChallenge2.setTypeface(montSerratMediumTypeface);

        final int TV_WIDTH = subTextNonSmoker.getWidth();
        final int TV_HEIGHT = subTextNonSmoker.getHeight();

        final float DENSITY = getResources().getDisplayMetrics().density;
        final float DENSITY_WEIGHT = TV_WIDTH / DENSITY;
        final float DENSITY_HEIGHT = TV_HEIGHT / DENSITY;
        Log.d("DENS", "widht: " + DENSITY_WEIGHT + " height: " + DENSITY_HEIGHT + DENSITY);
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, -1);
            }
            //setting the achievments images for user
            showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
            setImagesForAchievementCard();
            setImprovementProgressLevels();
        if (preferences.contains("saveimg")) {
            if (!preferences.getBoolean("saveimg", true)) {
//                TODO: addSavingsSumImg.setVisibility(View.INVISIBLE);
            } else {
//                addSavingsSumImg.setVisibility(View.VISIBLE);
                editor.putBoolean("saveimg", true);
                editor.apply();
            }
        } else {
//            addSavingsSumImg.setVisibility(View.VISIBLE);
            editor.putBoolean("saveimg", true);
            editor.apply();
        }
        achievementRanksCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent INTENT = new Intent(MainActivity.this, AchievmentsActivity.class);
                startActivity(INTENT);
            }
        });//achievementRanksCard[END]
        timeStampLogsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: finish logs activity
                final Intent INTENT = new Intent(MainActivity.this, FailLogsActivity.class);
                startActivity(INTENT);
            }
        });//timeStampCardView[END]
        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent INTENT = new Intent(MainActivity.this, SavingsActivity.class);
                INTENT.putExtra(SAVINGS_FINAL, savings);
                startActivity(INTENT);
            }
        });//savingsCardView[END]
        challengeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent INTENT = new Intent(MainActivity.this, ChallengeActivity.class);
                startActivity(INTENT);
                challs = getString(R.string.see_progress_challenge);
                editor.putString(CHALLENGES_STRING, challs);
                editor.apply();
            }
        });//challengeCardView[END]

        //disable views for "coming soon area"
        disableViewsForComingSoon();

        //retrieving the counter and minute values
        //run the task
        runningInBackground();
        //counter on click for fab button
        counterFabButtonInitializer();
        setTargetDays();

        savingsGetAndSetValue();
        if (preferences.contains(COUNTER)){
            counter = preferences.getInt(COUNTER, -1);
        }
        if (preferences.contains(CLICKED)){
            buttonClickedToday = preferences.getBoolean(CLICKED, false);
        }
        setImprovementProgressLevels();
            //using "final rethrow" by not specifying throwing a specific exception like NullPointer
            //the final keyword is optional, but in practice, we've found that it helps to use it while
            //adjusting to the new semantics of catch and rethrow
        } catch (final Exception e) {
            e.printStackTrace();
        }//[END OF RETRIEVING VALUES]
    }//[END OF ONCREATE]

    private void setTargetAfterCheckingCounter() {
        if (preferences.contains(COUNTER)){ counter = preferences.getInt(COUNTER, -1);counterText.setText(String.valueOf(counter)); }
        //test
        if (!preferences.contains(COUNTER)){counterText.setText("0");
        } else {
            counterText.setText(String.valueOf(counter));
        }
        if (preferences.contains(INITIAL_CIGG_PER_DAY)){cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, 0);}
        if (preferences.contains(LIFEREGAINED)){ lifeRegained = preferences.getFloat(LIFEREGAINED, 0); }
    }

    private void firstCheckForInitialCiggarettesPerDay() {
        if (!preferences.contains(INITIAL_CIGG_PER_DAY)){
            isFirstStart = true;editor.putBoolean("firstStart",isFirstStart);editor.apply();
            editor.putInt("yearleap", calendarForProgress.get(Calendar.YEAR));
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getDefault());
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
            DAY_OF_CLICK = DAY_OF_PRESENT - 1;
            editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
            editor.apply();
        } else {
            isFirstStart = false;editor.putBoolean("firstStart",isFirstStart);editor.apply();
        }
    }

    private void triggerPushNotification(int hourOfFirstLaunch) {
        final Calendar ALARM_FOR = Calendar.getInstance();
        ALARM_FOR.set(Calendar.HOUR_OF_DAY, hourOfFirstLaunch);
        ALARM_FOR.set(Calendar.MINUTE, 1);
        ALARM_FOR.set(Calendar.SECOND, 1);
        final Intent MY_INTENT = new Intent(getApplicationContext(), MyReceiver.class);
        final PendingIntent PENDING_INTENT = PendingIntent.getBroadcast(getApplicationContext(),
                0, MY_INTENT, PendingIntent.FLAG_CANCEL_CURRENT);

        final AlarmManager ALARM = (AlarmManager) getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(ALARM)
                .setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        ALARM_FOR.getTimeInMillis(), PENDING_INTENT);
    }

    private void setTheHourOfFirstLaunch(final Calendar CALENDAR) {
        //my personal method to save a value and keep it every time i launch on create :)
        if (preferences.contains(HOUR_OF_FIRSLAUNCH_SP)) {
            HOUR_OF_FIRSTLAUNCH = preferences.getInt(HOUR_OF_FIRSLAUNCH_SP, -1);
        } else {
            //recent refactor that works fine
            HOUR_OF_FIRSTLAUNCH = (CALENDAR.get(Calendar.HOUR_OF_DAY)==0) ? 24 : CALENDAR.get(Calendar.HOUR_OF_DAY);
            editor.putInt(HOUR_OF_FIRSLAUNCH_SP, HOUR_OF_FIRSTLAUNCH);
            editor.apply();
            //TODO: to implement later version
            strBuilder.append(String.format(getString(R.string.checkinStr), HOUR_OF_FIRSTLAUNCH));
            strBuilder.append("\n").append(R.string.we_will_start_tutorial);
//            showCustomDialogOnFirstLaunch("Welcome", strBuilder);
        }
    }

    @SideEffect
    private void setBackgroundForDaylightOrNight() {
        //change wallpaper during nighttime
        if (HOUR_OF_DAYLIGHT >= 21 || HOUR_OF_DAYLIGHT < 7) {
//        if (HOUR_OF_DAYLIGHT >= 6 && HOUR_OF_DAYLIGHT <= 20) {
            backgroundImgWall.setBackgroundResource(R.drawable.ppp);
            tipofthedayTxtView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            counterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            counterText.setAlpha(1.0f);
            remainingDaysTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            remainingDaysTxt.setAlpha(0.8f);
            targetTxtViewId.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            targetTxtViewId.setAlpha(0.8f);
            textNonSmoker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            textNonSmoker.setAlpha(1.0f);
            subTextNonSmoker.setBackground((ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.custom_button_round)));
            subTextNonSmoker.setAlpha(1.0f);
            backgroundImgWall.setAlpha(1.0f);
        } else {
            //change wallpaper during daytime
            backgroundImgWall.setBackgroundResource(R.drawable.bgday);
            tipofthedayTxtView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            counterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            remainingDaysTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            targetTxtViewId.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            textNonSmoker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            textNonSmoker.setAlpha(0.8f);
//            subTextNonSmoker.setTextColor(getResources().getColor(R.color.greish));
            subTextNonSmoker.setBackground((ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.custom_button_round)));
            subTextNonSmoker.setAlpha(0.7f);
//            backgroundImgWall.setAlpha(0.05f);
            backgroundImgWall.setAlpha(0f);
        }
    }

    private void firstCheckMax() {
        if (userMaxCountForHabit == -1) {
            userMaxCountForHabit = 30;
            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
            editor.apply();
        } else {
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
        }
    }

    //dialog when user pass a day
    private void positiveDialogAfterPassDay() {
        final String DAILY_QUOTE_TITLE = getResources().getString(R.string.awesome)
                + "\n" + getResources().getString(R.string.daily_quote) + "\n";
        final String DAILY_QUOTE = generateQuoteForPassingTheDay()+"";
        Log.d("QUOTE", " " + DAILY_QUOTE);
        BottomDialog bottomDialog = new BottomDialog.Builder(this)
                .setTitle(DAILY_QUOTE_TITLE)
                .setContent(DAILY_QUOTE)
                .setPositiveText(R.string.OK)
                .setCancelable(false)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        //call on destroy
//                        finish();
                        sheetLayout.contractFab();
                        Log.d("BottomDialogs", "Do something!");
                    }
                }).build();
        bottomDialog.show();
    }

    //dialog when user pass a day
    private void negativeDialogAfterRelapse() {
        //dialog ------------
        new BottomDialog.Builder(this)
                .setTitle(getString(R.string.ok_to_fail))
                .setContent(getString(R.string.fail_content))
                .setPositiveText(R.string.OK)
                .setCancelable(false)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Log.d("BottomDialogs", "Do something!");
                    }
                }).show();
    }

    @SideEffect
    private void startFirstActivity() {
        //intro
        //code for INTRO
        final Thread THREAD_FOR_SLIDER = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Create a new boolean and preference and set it to true
                Log.d("taozenD", "thread separat: " + Thread.currentThread().getName());
                if (preferences.contains("firstStart")) {
                    isFirstStart = preferences.getBoolean("firstStart", false);
                } else {
                    //on first launch this will trigger
                    isFirstStart = true;
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                    //fake first day of user to be day of present -1 to enable him/her to check in for the first time
                    //[calendar area]
                    calendarForProgress = Calendar.getInstance();
                    calendarForProgress.setTimeZone(TimeZone.getDefault());
                    DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
                    DAY_OF_CLICK = DAY_OF_PRESENT - 1;
                    editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
                    editor.apply();
                    Log.d("DAYOFTAOZEN", DAY_OF_CLICK + " ");
                }
                //  If the activity has never started before...
                if (isFirstStart) {
                    DAY_OF_CLICK = DAY_OF_PRESENT - 1;
                    editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
                    editor.apply();
                    if (preferences.contains(COUNTER)) {
                        counter = preferences.getInt(COUNTER, -1);
                        editor.putInt(COUNTER, counter);
                        editor.apply();
                    }
                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, FirstScreenActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            Log.d("taozenD", "thread din ui: " + Thread.currentThread().getName());
                            startActivity(i);
//                            startIntroActivity();
                        }
                    });
                }
            }
        });
        THREAD_FOR_SLIDER.start();
    }//end of INTRO

    @SideEffect
    private void counterFabButtonInitializer() {
        //active when user passed a day
        //inactive when user wait
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTodayToClickDay();
                final String MESSAGE_FOR_DIALOG;
                MESSAGE_FOR_DIALOG = higherThanOne ? getString(R.string.abstained_last_days) : getString(R.string.abstained_today);
                //between 1 and 29
                if (counter == 0) {
                    ttfancyDialogForFirstTimeLaunch(getString(R.string.welcome_to_quit_habit), getString(R.string.first_day));
                } else if (counter > 0 && counter < 29) {
                    normalFancyDialog(getString(R.string.beat_milestone, 30), MESSAGE_FOR_DIALOG);
                    //between 29(to show up in 30) and 60
                } else if (counter > 28 && counter < 59) {
                    normalFancyDialog(getString(R.string.beat_milestone, 60), MESSAGE_FOR_DIALOG);
                    //between 59(to show up in 60) and 90
                } else if (counter > 58 && counter < 89) {
                    normalFancyDialog(getString(R.string.beat_milestone, 90), MESSAGE_FOR_DIALOG);
                    //between 89(to show up in 60) and 180
                } else if (counter > 88 && counter < 179) {
                    normalFancyDialog(getString(R.string.beat_milestone, 180), MESSAGE_FOR_DIALOG);
                    //between 179(to show up in 60) and 270
                } else if (counter > 178 && counter < 269) {
                    normalFancyDialog(getString(R.string.beat_milestone, 270), MESSAGE_FOR_DIALOG);
                    //between 279(to show up in 60) and 360
                } else if (counter > 278 && counter < 361) {
                    normalFancyDialog(getString(R.string.beat_milestone, 360), MESSAGE_FOR_DIALOG);
                }
                //SHOW FANCY TOAST WITH CONGRATS
                //[END OF ELSE IFS DIALOGS]
                fab.hide();
                anim.cancel();
                subTextNonSmoker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
            }
        });
    }

    private void setTodayToClickDay() {
        try {
            if (preferences.contains(COUNTER)){
                counter = preferences.getInt(COUNTER, -1);
            }
            buttonClickedToday = true;
            editor.putBoolean(CLICKED, buttonClickedToday);
            editor.apply();
            if (preferences.contains(INITIAL_CIGG_PER_DAY)){
                cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, 0);
            }
            if (!preferences.contains("taoz10")){
                savings = 10;
                editor.putLong("taoz10", savings);
                editor.apply();
            }
            //[calendar area]
            calendarOnClick = Calendar.getInstance();
            calendarOnClick.setTimeZone(TimeZone.getDefault());
            DAY_OF_CLICK = calendarOnClick.get(Calendar.DAY_OF_YEAR);
            editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
            editor.apply();
            setImagesForAchievementCard();
            Log.d("INTROTAO", "counter from onclick = " + counter + buttonClickedToday);
            //using "final rethrow" by not specifying throwing a specific exception like NullPointer
            //the final keyword is optional, but in practice, we've found that it helps to use it while
            //adjusting to the new semantics of catch and rethrow
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//[END OF setTodayToClickDay]

    private void normalFancyDialog(String title, String message) {
        new FancyGifDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeBtnText(getString(R.string.NO))
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText(getString(R.string.YES))
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.source)   //Pass your Gif here
                .isCancellable(false)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        setCheckInText();
                        if (counter == 0) {
                            savings = preferences.getLong("taoz10", -10);
                        }
                        if (preferences.contains("diff") && higherThanOne){
                            Log.d("COUNTERTAO", "before - counter is raised with: " + counter);
                            final int DIF = preferences.getInt("diff", -100);
                            counter = counter + DIF;
                            if (preferences.contains("tempLong")){
                                longFromSavingActivity = preferences.getLong("tempLong", 0);
                            } else {
                                longFromSavingActivity = 0;
                            }
                            savings = longFromSavingActivity + (firstSave * counter);
                            editor.putLong(SAVINGS_FINAL, savings);
                            editor.apply();

                            Log.d("COUNTERTAO", "after - counter is raised with: " + counter
                                    +"\n savings = " + savings);
                            higherThanOne = false;
                        } else {
                            if (counter == 1) {
                                if (preferences.contains(SAVINGS_FINAL)){
                                    firstSave = preferences.getLong(SAVINGS_FINAL, 0);
                                    editor.putLong("firstsave", firstSave);
                                    editor.apply();
                                    Log.d("taogenX", "firstsave is: " + firstSave);
                                }
                            }
                            Log.d("COUNTERTAO", "before - counter is raised with: " + counter);
                            counter++;
                            if (counter == 1){
                                savings = preferences.getLong(SAVINGS_FINAL, 0);
                                editor.putLong(SAVINGS_FINAL, savings);
                                if (preferences.contains(SAVINGS_FINAL)){
                                    firstSave = preferences.getLong(SAVINGS_FINAL, 0);
                                    editor.putLong("firstsave", firstSave);
                                    editor.apply();
                                    Log.d("taogenX", "firstsave is: " + firstSave);
                                }
                            } else {
                                savings = preferences.getLong(SAVINGS_FINAL, 0) + firstSave;
                                Log.d("taogenX", "savings from TAO = " + savings);
                                editor.putLong(SAVINGS_FINAL, savings);
                                editor.apply();
                            }
                            higherThanOne = false;
                            Log.d("COUNTERTAO", "after - counter is raised with: " + counter);
                        }
                        i = 1;
                        editor.putInt(COUNTER, counter);
                        final int TEMP_CIGARETTES = cigarettesPerDay * counter;
                        final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
                        final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE + " " + TEMP_CIGARETTES;
                        userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
                        editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
                        lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
                        final String USER_HOURS_PROGRESS = getString(R.string.life_r)
                                + " " + numberFormat.format(lifeRegained) + " " + getString(R.string.hours);
                        userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
                        editor.putFloat(LIFEREGAINED, lifeRegained);
                        editor.putLong(SAVINGS_FINAL, savings);
                        editor.apply();
                        checkActivityOnline();
//                        setTheSavingsPerDay();
                        savingsGetAndSetValue();
                        setImprovementProgressLevels();
                        setImagesForAchievementCard();
                        counterText.setText(String.valueOf(counter));
                        setTargetDays();
                        sheetLayout.expandFab();
                        //var 1 - dialog after answer
                        positiveDialogAfterPassDay();
                        //var 2 - custom toast after answer
//                        FancyToast.makeText(MainActivity.this, "Congratulations! One day healthier than yesterday!",
//                                    20, FancyToast.SUCCESS, true).show();
                        estabilishHighestRecordForCounter();
                        showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);

                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        try {
                            i = 1;
                            //get time of relapse and put it into arraylist to send in logs activity
                            final Calendar CALENDAR_ON_CLICK = Calendar.getInstance();
                            CALENDAR_ON_CLICK.setTimeZone(TimeZone.getDefault());
                            final String tem = CALENDAR_ON_CLICK.getTime().toString() + " - fail\n";
                            if (preferences.contains("arr")){
                                arr = tem + preferences.getString("arr", "no value");
                            } else {
                                arr = tem;
                            }
                            editor.putString("arr", arr);
                            editor.putInt("highest", counter);
                            Log.d("taogenX", "here is the last max counter for user: " + preferences.getInt("highest", 0));
                            editor.apply();
                            counter = 1;
                            savings = firstSave;
                            Log.d("taogenX", "firstsave is: " + firstSave);
                            //to see
                            editor.putLong(SAVINGS_FINAL, savings);//off
                            //maybe
                            editor.putInt(COUNTER, counter);
                            //new edit
                            final int TEMP_CIGARETTES = cigarettesPerDay * counter;
                            final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
                            final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE + " " + TEMP_CIGARETTES;
                            userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
                            editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
                            lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
                            final String USER_HOURS_PROGRESS = getString(R.string.life_r)
                                    + " " + numberFormat.format(lifeRegained) + " " + getString(R.string.hours);
                            userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
                            editor.putFloat(LIFEREGAINED, lifeRegained);
                            editor.apply();
                            checkActivityOnline();
    //                        setTheSavingsPerDay();
                            savingsGetAndSetValue();
                            setImprovementProgressLevels();

                            counter = preferences.getInt(COUNTER, -1);
                            counterText.setText(String.valueOf(counter));
                            setTargetDays();
                            negativeDialogAfterRelapse();
                            estabilishHighestRecordForCounter();
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build();//[END of NORMAL DIALOG]
    }

    private void ttfancyDialogForFirstTimeLaunch(String title, String message){
        new TTFancyGifDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#FF4081")
                .setGifResource(R.drawable.source)      //pass your gif, png or jpg
                .isCancellable(false)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        setTodayToClickDay();
                        firstCheckForInitialCiggarettesPerDay();
                        setCheckInText();
                        if (counter == 0) {
                            savings = preferences.getLong("taoz10", -10);
                        }
                        if (preferences.contains("diff") && higherThanOne){
                            Log.d("COUNTERTAO", "before - counter is raised with: " + counter);
                            int DIF = preferences.getInt("diff", -100);
                            counter = counter + DIF;
                            if (preferences.contains("tempLong")){
                                longFromSavingActivity = preferences.getLong("tempLong", 0);
                            } else {
                                longFromSavingActivity = 0;
                            }
                            savings = longFromSavingActivity + (firstSave * counter);
                            editor.putLong(SAVINGS_FINAL, savings);
                            editor.apply();

                            Log.d("COUNTERTAO", "after - counter is raised with: " + counter
                                    +"\n savings = " + savings);
                            higherThanOne = false;
                        } else {
                            if (counter == 1) {
                                if (preferences.contains(SAVINGS_FINAL)){
                                    firstSave = preferences.getLong(SAVINGS_FINAL, 0);
                                    editor.putLong("firstsave", firstSave);
                                    editor.apply();
                                    Log.d("taogenX", "firstsave is: " + firstSave);
                                }
                            }
                            Log.d("COUNTERTAO", "before - counter is raised with: " + counter);
                            counter++;
                            if (counter == 1){
                                savings = preferences.getLong(SAVINGS_FINAL, 0);
                                editor.putLong(SAVINGS_FINAL, savings);
                                if (preferences.contains(SAVINGS_FINAL)){
                                    firstSave = preferences.getLong(SAVINGS_FINAL, 0);
                                    editor.putLong("firstsave", firstSave);
                                    editor.apply();
                                    Log.d("taogenX", "firstsave is: " + firstSave);
                                }
                            } else {
                                savings = preferences.getLong(SAVINGS_FINAL, 0) + firstSave;
                                Log.d("taogenX", "savings from TAO = " + savings);
                                editor.putLong(SAVINGS_FINAL, savings);
                                editor.apply();
                            }
                            higherThanOne = false;
                            Log.d("COUNTERTAO", "after - counter is raised with: " + counter);
                        }
                        i = 1;
                        editor.putInt(COUNTER, counter);
                        final int TEMP_CIGARETTES = cigarettesPerDay * counter;
                        final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
                        final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE + " " + TEMP_CIGARETTES;
                        userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
                        editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
                        lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
                        final String USER_HOURS_PROGRESS = getString(R.string.life_r)
                                + " " + numberFormat.format(lifeRegained) + " " + getString(R.string.hours);
                        userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
                        editor.putFloat(LIFEREGAINED, lifeRegained);
                        editor.putLong(SAVINGS_FINAL, savings);
                        editor.apply();
                        checkActivityOnline();
//                        setTheSavingsPerDay();
                        savingsGetAndSetValue();
                        setImprovementProgressLevels();
                        setImagesForAchievementCard();
                        counterText.setText(String.valueOf(counter));
                        setTargetDays();
                        //var 1 - dialog after answer
                        positiveDialogAfterPassDay();
                        //var 2 - custom toast after answer
//                        FancyToast.makeText(MainActivity.this, "Congratulations! One day healthier than yesterday!",
//                                    20, FancyToast.SUCCESS, true).show();
                        estabilishHighestRecordForCounter();
                        showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
                        cleanUpAfterFirstCounter();
                    }
                })
                .build();//[END of NORMAL DIALOG]
    }

    private void cleanUpAfterFirstCounter() {
        fab.hide();
        anim.cancel();
        subTextNonSmoker.setBackground((ContextCompat.getDrawable(
                getApplicationContext(), R.drawable.custom_button_round)));
        subTextNonSmoker.setTextColor(ContextCompat.getColor(
                getApplicationContext(), R.color.greish));
        subTextNonSmoker.setAlpha(0.7f);
    }

    private void resetWholeProgress() {
        try {
            estabilishHighestRecordForCounter();
            editor.apply();
            i = 1;
            //get time of relapse and put it into arraylist to send in logs activity
            final Calendar CALENDAR_ON_CLICK = Calendar.getInstance();
            CALENDAR_ON_CLICK.setTimeZone(TimeZone.getDefault());
            //Get time of fail
            final String TEM = CALENDAR_ON_CLICK.getTime().toString() + " - reset\n";
            if (preferences.contains("arr")){
                arr = TEM + preferences.getString("arr", "no value");
            } else {
                arr = TEM;
            }
            editor.putString("arr", arr);
            Log.d("taogenX", "here is the last max counter for user: " + preferences.getInt("highest", 0));
            editor.apply();
            counter = 1;
            savings = firstSave;
            Log.d("taogenX", "firstsave is: " + firstSave);
            //to see
            editor.putLong(SAVINGS_FINAL, savings);//off
            //maybe
            editor.putInt(COUNTER, counter);
            //new edit
            final int TEMP_CIGARETTES = preferences.getInt(INITIAL_CIGG_PER_DAY, 0);
            final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
            final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE + " " + TEMP_CIGARETTES;
            userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
            editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
            lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
            final String USER_HOURS_PROGRESS = getString(R.string.life_r)
                    + " " + numberFormat.format(lifeRegained) + " " + getString(R.string.hours);
            userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
            editor.putFloat(LIFEREGAINED, lifeRegained);
            editor.apply();
            checkActivityOnline();
            savingsGetAndSetValue();
            setImprovementProgressLevels();
            counter = preferences.getInt(COUNTER, -1);
            counterText.setText(String.valueOf(counter));
            setTargetDays();
            negativeDialogAfterRelapse();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void estabilishHighestRecordForCounter() {
        try {
            if (preferences.contains("highest")){
                //if editor cointains highest, we see which is higher, present counter or last counter;
                final int FINAL_HIGHEST;
                final int HIGHEST = preferences.getInt("highest",0);
                if (counter >= HIGHEST) {
                    FINAL_HIGHEST = counter;
                } else {
                    FINAL_HIGHEST = HIGHEST;
                }
                editor.putInt("highest", FINAL_HIGHEST);
                editor.apply();
            } else {
                editor.putInt("highest", preferences.getInt(COUNTER, 0));
                editor.apply();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void setTxtViewForUserSavings(long SAVE_PER_DAY, String STRING,
                                          int ANDROID_ID, TextView TEXTVIEW) {
        final DecimalFormat FORMATTER = new DecimalFormat("###,###,##0.00");
        final String VALUE_PER_YEAR_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY))*365);
        final String TOTAL_SAVINGS_LOCAL = FORMATTER.format(Integer.parseInt(STRING));
        final String VALUE_PER_DAY_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY)));
        final String CURRENCY_LOCAL = preferences.getString("currency", "$");
        //target counter string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = stringBuilder.append(getString(R.string.perday)).append(" ").append(VALUE_PER_DAY_LOCAL).append(" ").append(CURRENCY_LOCAL).append("\n")
                .append(getString(ANDROID_ID, TOTAL_SAVINGS_LOCAL)).append(" ").append(CURRENCY_LOCAL).append("\n")
                .append(getString(R.string.peryear)).append(" ").append(VALUE_PER_YEAR_LOCAL).append(" ").append(CURRENCY_LOCAL);
//        String finalS = R.string.perday + " "  + valuePerDay + " "+currency + "\n"+
//                getString(androiId, totalSavings) + " "+currency + "\nPer year: " + valuePerYear + " " + currency;
        TEXTVIEW.setText(stringBuilder);
    }

    private void setTxtViewForUserMaxCountDaysOnStringVersion(
            final String STRING,
            final int ANDROID_ID,
            final TextView TEXTVIEW) {
        //target counter string
        TEXTVIEW.setText(getString(ANDROID_ID, STRING));
    }

    private void savingsGetAndSetValue() {
        try {
            if (preferences.contains(SAVINGS_FINAL)) {
                savings = preferences.getLong(SAVINGS_FINAL, 1);
            } else {
                savings = 0;
                editor.putLong(SAVINGS_FINAL, savings);
                editor.apply();
            }
            if (preferences.contains("firstsave")) {
                firstSave = preferences.getLong("firstsave", 0);
                Log.d("taogenX", "firstsave: " + firstSave);
            } else {
                Log.d("taogenX", "firstsave: " + firstSave);
            }
            setTxtViewForUserSavings(firstSave,
                    String.valueOf(savings), R.string.savings, moneySavingsTxt);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @SideEffect
    private void setTargetDays() {
        try {
            //format string of MAX target txt view
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, 0);
            }
            if (counter == 0) {
                textNonSmoker.setText(R.string.press_leaf);
            } else {
                textNonSmoker.setText(R.string.non_smoker_since);
            }
            if (counter >= 270) {
                userMaxCountForHabit = 360;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= 180) {
                userMaxCountForHabit = 270;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= 90) {
                userMaxCountForHabit = 180;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= 60) {
                userMaxCountForHabit = 90;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= 30) {
                userMaxCountForHabit = 60;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else {
                userMaxCountForHabit = 30;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            }
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
            setTxtViewForUserMaxCountDaysOnStringVersion(String.valueOf(userMaxCountForHabit),
                    R.string.target_string, targetTxtViewId);
            if (!preferences.contains(CHALLENGES_STRING)) {
                challs = getString(R.string.tap_to_start_challenge);
//                challengeTextViewSubtitle.setText(challs);
            } else {
                challs = preferences.getString(CHALLENGES_STRING, challs);
//                challengeTextViewSubtitle.setText(challs);
            }
            //remaining days -- + "  " for space between number of days and text
            final String CALC_DAYS_TARGET = (userMaxCountForHabit - counter) + "";
            final String PUT_TARGET_DAYS_IN_STRING = getString(R.string.remaining_days, CALC_DAYS_TARGET);
            remainingDaysTxt.setText(PUT_TARGET_DAYS_IN_STRING);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    //running task
    @SideEffect
    private void runningInBackground() {
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
                            }//run from runonuithread
                        });//runonuithread
                    }//run from Timertask
                }, 100, 10_000);//Timertask once per 10 SECONDS
            }//run from async
        });//async
    }//runningInBackground

    @SideEffect
    private void startTheEngine() {
        try {
            //set text for checkin
            setCheckInText();
            showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, -1);
                editor.putInt(COUNTER, counter);
                editor.apply();
            }
            setImagesForAchievementCard();
            setImprovementProgressLevels();
            DAY_OF_CLICK = preferences.getInt(CLICKDAY_SP, 0);
            buttonClickedToday = preferences.getBoolean(CLICKED, false);
            Log.d("INTROTAO", "value for boolean clicked = " + buttonClickedToday);
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getDefault());
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
            //for later usage
            editor.putInt(DAYOFPRESENT, DAY_OF_PRESENT);
            editor.apply();
            //testing area
            final Date DATE = new Date();
            final Calendar CALENDAR = GregorianCalendar.getInstance();
            CALENDAR.setTime(DATE);
            HOUR_OF_DAYLIGHT = CALENDAR.get(Calendar.HOUR_OF_DAY);
            Log.d("taolenZ", "hour of now: " + HOUR_OF_DAYLIGHT);
            setTargetDays();
            //if the button/check in is already clicked today,
            //we disable it by checking if buttonClickedToday is true
            updateButton();
            updateConditionGreenState();
            //green condition is when present day is higher than click day
            //in order to run our condition = to enable our button, our check in for user
            greenCondition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SideEffect
    private void setCheckInText() {
        try {
            if (preferences.contains(HOUR_OF_FIRSLAUNCH_SP)) {
                HOUR_OF_FIRSTLAUNCH = preferences.getInt(HOUR_OF_FIRSLAUNCH_SP, 0);
            }
            //if only one day passed
            if (DAY_OF_PRESENT == DAY_OF_CLICK+1) {
                if (HOUR_OF_FIRSTLAUNCH > HOUR_OF_DAYLIGHT) {
                    //temporarily integer
                    final int HOURS_UNTILL_CHECK_IN = HOUR_OF_FIRSTLAUNCH - HOUR_OF_DAYLIGHT;
                    final String CHECK_IN_TEXT = getResources().getString(R.string.check_in) +
                            ": " + HOURS_UNTILL_CHECK_IN + " "
                            + getResources().getString(R.string.hours);
                    subTextNonSmoker.setText(CHECK_IN_TEXT);
                    //if user passed one day && hour is passed or equal to hour of first launch
                } else {
                    subTextNonSmoker.setText(getResources().getString(R.string.check_in_now));
                }
                //if more than one day passed
            } else if (DAY_OF_PRESENT >= DAY_OF_CLICK+2) {
                subTextNonSmoker.setText(getResources().getString(R.string.check_in_now));
            } else {
                subTextNonSmoker.setText(getResources().getString(R.string.check_in_tomorrow));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    //onResume
    @Override
    protected void onResume() {
        super.onResume();
        try {
            counterText.setText(String.valueOf(counter));
            setCheckInText();
            if (preferences.contains("saveimg")) {
                if (!preferences.getBoolean("saveimg", true)) {
//                    TODO: addSavingsSumImg.setVisibility(View.INVISIBLE);
                } else {
//                    TODO: addSavingsSumImg.setVisibility(View.VISIBLE);
                }
            } else {
//                TODO: addSavingsSumImg.setVisibility(View.VISIBLE);
            }
            //Only retrieve and save in onpause
            //-3 default values
            if (preferences.contains(getString(R.string.maxCounter))){userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -3);}
            if (preferences.contains(COUNTER)){ counter = preferences.getInt(COUNTER, -3);}
            if (preferences.contains(INITIAL_CIGG_PER_DAY)){cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, -3);}
            if (preferences.contains(LIFEREGAINED)){ lifeRegained = preferences.getFloat(LIFEREGAINED, -3); }
            if (preferences.contains(SAVINGS_FINAL)) {savings = preferences.getLong(SAVINGS_FINAL, -3); }
            updateButton();
            setImagesForAchievementCard();
            runningInBackground();
            savingsGetAndSetValue();
            setTargetDays();
            setCheckInText();
            Log.d("INTROTAO", "values in onResume: " + "cigperday " + cigarettesPerDay+ " savings: " + savings + " counter: " + counter);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//[END of ONRESUME]

    //onPause
    @Override
    protected void onPause() {
        super.onPause();
        //-5 default
        try {
            if (preferences.contains(INITIAL_CIGG_PER_DAY)){cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, -5);editor.putInt(INITIAL_CIGG_PER_DAY, cigarettesPerDay);editor.apply();}
            if (preferences.contains(SAVINGS_FINAL)) {savings = preferences.getLong(SAVINGS_FINAL, -5); editor.putLong(SAVINGS_FINAL, savings);editor.apply();}
            if (preferences.contains(COUNTER)){ counter = preferences.getInt(COUNTER, -5);editor.putInt(COUNTER, counter);editor.apply();}
            if (preferences.contains(LIFEREGAINED)){ lifeRegained = preferences.getFloat(LIFEREGAINED, -5);editor.putFloat(LIFEREGAINED, lifeRegained);editor.apply(); }
            if (preferences.contains(getString(R.string.maxCounter))){userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -5);editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);editor.apply();}
        } catch (final Exception e) {
            e.printStackTrace();
        }
        Log.d("INTROTAO", "values in onPause: " + "cigperday " + cigarettesPerDay+ " savings: " + savings+ " counter: " + counter);
    }//[END of ONPAUSE]

    //onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
            editor.putInt(COUNTER, counter);
            editor.putBoolean(CLICKED, buttonClickedToday);
            editor.putInt(INITIAL_CIGG_PER_DAY, cigarettesPerDay);
            editor.putFloat(LIFEREGAINED, lifeRegained);
            editor.putLong(SAVINGS_FINAL, savings);
            editor.apply();
            Log.d("INTROTAO", "values in onDestroy: " + "cigperday " + cigarettesPerDay+ " savings: " + savings+ " counter: " + counter);
            if (task != null && !task.isCancelled()) { task.cancel(true); }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//[END of ONDESTROY]

    private void updateButton() {
        if (buttonClickedToday) {
            fab.hide();
        }
    }
    //[ENABLE BOOLEAN FOR DAY PASSED]
    @SideEffect
    private void updateConditionGreenState() {
        //when click day is lower than today (present) && button was already clicked
        //we make the boolean false (in order for greenCondition to work) and for enabling button
        if (DAY_OF_PRESENT > DAY_OF_CLICK && buttonClickedToday) {
            buttonClickedToday = false;
            editor.putBoolean(CLICKED, buttonClickedToday);
            editor.apply();
            fab.hide();
            //when days are the same and user already clicked
        } else if (DAY_OF_PRESENT == DAY_OF_CLICK && buttonClickedToday) {
            fab.hide();
        }
    }

    private int returnLeapForClickYear() {
        final int defaultValue = -1000;
        //using try catch due to preferences
        try {
            final int lastDayClick = preferences.getInt(CLICKDAY_SP, defaultValue);
            final int yearOfNow = Calendar.getInstance().get(Calendar.YEAR);
            final int x = isLeap(yearOfNow) ? 366 : 365;
            return x - lastDayClick;
        } catch (final Exception e) {
            e.printStackTrace();
            //will never happen'
            return defaultValue;
        }
    }

    private boolean isLeap(final int year){
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    //[ENABLE BUTTON]
    @SideEffect
    private void greenCondition() {
        try {
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, 0);
            }
            if (Calendar.getInstance().get(Calendar.YEAR) != preferences.getInt("yearleap", 2019)) {
                DAY_OF_CLICK = returnLeapForClickYear();
                DAY_OF_CLICK *= -1;
                editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);editor.apply();
                Log.d("DAYZEN", "DAY_OF_PRESENT: "  + DAY_OF_PRESENT);
                editor.putInt("yearleap", Calendar.getInstance().get(Calendar.YEAR));editor.apply();
            }
            Log.d("DAYZEN", "DAY OF CLICK " + DAY_OF_CLICK
                    + " DAY OF PRESENT " + DAY_OF_PRESENT + "\n" +
                    " HOUR_OF_FIRSTLAUNCH " + HOUR_OF_FIRSTLAUNCH + " HOUR_OF_DAYLIGHT " + HOUR_OF_DAYLIGHT);
            if (DAY_OF_PRESENT > DAY_OF_CLICK && (DAY_OF_PRESENT == DAY_OF_CLICK+1)) {
                HOUR_OF_DAYLIGHT = (HOUR_OF_FIRSTLAUNCH==24 || HOUR_OF_FIRSTLAUNCH==23)
                        && (HOUR_OF_DAYLIGHT==0) ? 24 : HOUR_OF_DAYLIGHT;
                if (HOUR_OF_FIRSTLAUNCH <= HOUR_OF_DAYLIGHT) {
                    fab.show();
                    editor.putBoolean("saveimg", true);
                    editor.apply();
                    setCheckInText();
                    if (i == 1)
                        startAnimatorForUpperCard();
                    anim.start();
                    Log.d("DAYZEN", "if (HOUR_OF_FIRSTLAUNCH <= HOUR_OF_DAYLIGHT ) {" + " ACTIVATED");
                } else {
                    setCheckInText();
                    anim.cancel();
                    fab.hide();
                }
            } else if (DAY_OF_PRESENT > DAY_OF_CLICK+1) {
                higherThanOne = true;
                final int DIFF_LOCAL = DAY_OF_PRESENT - DAY_OF_CLICK;
                editor.putInt("diff", DIFF_LOCAL);
                editor.apply();
                fab.show();
                editor.putBoolean("saveimg", true);
                editor.apply();
                setCheckInText();
                if (i == 1)
                    startAnimatorForUpperCard();
                anim.start();
                Log.d("DAYZEN", "} else if (DAY_OF_PRESENT > DAY_OF_CLICK+1) {" + " ACTIVATED");
            } else {
                setCheckInText();
                anim.cancel();
                fab.hide();
                Log.d("DAYZEN", "BIG ELSE FROM GREENCONDITION " + " ACTIVATED");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//END OF -> [ENABLE BUTTON]

    private void startAnimatorForUpperCard() {
        final View MY_VIEW = findViewById(R.id.card_view_mainID);
        // get the center for the clipping circle
        final int CX = (MY_VIEW.getLeft() + MY_VIEW.getRight()) / 2;
        final int CY = (MY_VIEW.getTop() + MY_VIEW.getBottom()) / 2;

        // get the final radius for the clipping circle
        final int DX = Math.max(CX, MY_VIEW.getWidth() - CX);
        final int DY = Math.max(CY, MY_VIEW.getHeight() - CY);
        final float FINAL_RADIUS = (float) Math.hypot(DX, DY);

        // Android native animator
        final Animator ANIMATOR_LOCAL =
                ViewAnimationUtils.createCircularReveal(MY_VIEW, CX, CY, 0, FINAL_RADIUS);
        ANIMATOR_LOCAL.setInterpolator(new AccelerateDecelerateInterpolator());
        ANIMATOR_LOCAL.setDuration(1500);
        ANIMATOR_LOCAL.start();
        i = 2;
    }

    @SideEffect
    private void setImprovementProgressLevels() {
        try {
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, 0);
            }
            //energy levels
            if (counter >= 0 && counter < 6) {
                txtProgressForEnergyLevels.setText(5 + "%");
                progressBarEnergyLevel.setProgress(5);
            } else if (counter > 5 && counter < 10) {
                txtProgressForEnergyLevels.setText(10 + "%");
                progressBarEnergyLevel.setProgress(10);
            } else if (counter > 9 && counter < 25) {
                txtProgressForEnergyLevels.setText(25 + "%");
                progressBarEnergyLevel.setProgress(25);
            } else if (counter > 24 && counter < 40) {
                txtProgressForEnergyLevels.setText(50 + "%");
                progressBarEnergyLevel.setProgress(50);
            } else if (counter > 39 && counter < 56) {
                txtProgressForEnergyLevels.setText(75 + "%");
                progressBarEnergyLevel.setProgress(75);
            } else if (counter > 55) {
                txtProgressForEnergyLevels.setText(100 + "%");
                progressBarEnergyLevel.setProgress(100);
            }

            //fatigue levels
            if (counter >= 0 && counter < 6) {
                txtProgressForFatigue.setText(5 + "%");
                progressBarFatigueLevel.setProgress(5);
            } else if (counter > 5 && counter < 15) {
                txtProgressForFatigue.setText(10 + "%");
                progressBarFatigueLevel.setProgress(10);
            } else if (counter > 14 && counter < 30) {
                txtProgressForFatigue.setText(25 + "%");
                progressBarFatigueLevel.setProgress(25);
            } else if (counter > 29 && counter < 60) {
                txtProgressForFatigue.setText(50 + "%");
                progressBarFatigueLevel.setProgress(50);
            } else if (counter > 59 && counter < 80) {
                txtProgressForFatigue.setText(75 + "%");
                progressBarFatigueLevel.setProgress(75);
            } else if (counter > 79) {
                txtProgressForFatigue.setText(100 + "%");
                progressBarFatigueLevel.setProgress(100);
            }

            //gums levels
            if (counter >= 0 && counter < 6) {
                txtProgressForGums.setText(15 + "%");
                progressBarGumsLevel.setProgress(15);
            } else if (counter > 5 && counter < 20) {
                txtProgressForGums.setText(25 + "%");
                progressBarGumsLevel.setProgress(25);
            } else if (counter > 19 && counter < 40) {
                txtProgressForGums.setText(50 + "%");
                progressBarGumsLevel.setProgress(50);
            } else if (counter > 39 && counter < 60) {
                txtProgressForGums.setText(75 + "%");
                progressBarGumsLevel.setProgress(75);
            } else if (counter > 59) {
                txtProgressForGums.setText(100 + "%");
                progressBarGumsLevel.setProgress(100);
            }

            //breath levels
            if (counter >= 0 && counter < 6) {
                txtProgressForBreath.setText(15 + "%");
                progressBarBreathlevel.setProgress(15);
            } else if (counter > 0 && counter < 10) {
                txtProgressForBreath.setText(25 + "%");
                progressBarBreathlevel.setProgress(25);
            } else if (counter > 9 && counter < 20) {
                txtProgressForBreath.setText(50 + "%");
                progressBarBreathlevel.setProgress(50);
            } else if (counter > 19 && counter < 30) {
                txtProgressForBreath.setText(75 + "%");
                progressBarBreathlevel.setProgress(75);
            } else if (counter > 29) {
                txtProgressForBreath.setText(100 + "%");
                progressBarBreathlevel.setProgress(100);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        counterText.setText(String.valueOf(counter));
        finish();
    }

    //[MENU]
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //onOptionsItemSelected is called everytime user clicks
        //options menu, so here it is ok to have a final int assigned as id
        final int ID_LOCAL = item.getItemId();
        if (ID_LOCAL == R.id.action_settings) {
            counter++;
            final Calendar CALENDAR_LOCAL = Calendar.getInstance();
            final int GET_MINUTE = CALENDAR_LOCAL.get(Calendar.MINUTE);
//            int zet = preferences.getInt(CLICKDAY_SP, 0);
            editor.putInt(CLICKDAY_SP, GET_MINUTE);
            Log.d("taolenZ777", "zet = " + GET_MINUTE);
            counterText.setText(String.valueOf(counter));
            editor.putInt(COUNTER, counter);
            editor.apply();
            setImprovementProgressLevels();
            return true;
        } else if (ID_LOCAL == R.id.action_about) {
            final Intent INTENT = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(INTENT);
            return true;
        } else if (ID_LOCAL == R.id.action_help) {
            counter = 80;
            counterText.setText(String.valueOf(counter));
            editor.putInt(COUNTER, counter);
            editor.apply();
        } else if (ID_LOCAL == R.id.check_in_hour) {
            //do nothing for now
            //TODO: think about we want to let user change the check in hour or not
//            showDialogForChangingCheckInDate();
        } else if (ID_LOCAL == R.id.reset_button){
            dialogForReset();
        }
        return super.onOptionsItemSelected(item);
    }//END OF -> [MENU]

    //hide the rest of the menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.check_in_hour).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_help).setVisible(false);

        return true;
    }

    @SideEffect
    protected boolean isOnline() {
        final ConnectivityManager connManager =
                (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo =
                Objects.requireNonNull(connManager).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }//isOnline[END]

    //i am not using it for now (using the version from splash)
    private void requestDataById(final int id) {
        task = new MainActivity.MyAsyncTask();
        task.execute(MainActivity.HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS + "/" + id);
    }
    @SideEffect
    private void checkActivityOnline() {
        try {
            if (isOnline()) {
                if (preferences.contains(COUNTER)) {
                    counter = preferences.getInt(COUNTER, 0);
                }
                if (counter == 0) {
                    requestDataById(1);
                } else {
                    requestDataById(counter);
                }
                //requestDataById(DAY_OF_PRESENT);
            } else {
                errorText.setVisibility(View.VISIBLE);
                tipofthedayTxtView.setText(R.string.error_fourtyfour);
                final Snackbar SNACKBAR;
                SNACKBAR = Snackbar.make(parentLayout, R.string.no_connection, Snackbar.LENGTH_LONG);
                final View SNACKBAR_VIEW = SNACKBAR.getView();
                SNACKBAR_VIEW.setBackgroundColor(Color.RED);
                SNACKBAR.show();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDisplayString(final String MESSAGE) {
        tipofthedayTxtView.setText(MESSAGE + "\n");
    }

    @Override
    public void onFabAnimationEnd() {
        //TODO: decide what to put here
    }


    @SuppressLint("StaticFieldLeak")
    public class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            try {
                counter = preferences.getInt(COUNTER, counter);
                if (!preferences.contains(COUNTER)){counterText.setText(0);}else {
                    counterText.setText(String.valueOf(counter));
                }
                updateDisplayString("Starting to fetch data from heroku ...");
                if (tasks.size() == 0) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                    progressBarLoading2.setVisibility(View.VISIBLE);
                    counterImgView.setVisibility(View.INVISIBLE);
                    counterText.setVisibility(View.INVISIBLE);
                }
                //if we click we add a task
                tasks.add(this);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }//onPreExecute[END]
        @Override
        protected String doInBackground(final String... PARAMS_LOCAL) {
            try {
                //using GSON
                final JsonParser JSON_PARSER = new JsonParser();
                //using MyHttpManager getData static method
//                String content = MyHttpManager.getData(params[0]);
//                Thread.sleep(1000);
                //using MyHttpCoreAndroid
                final String CONTENT_LOCAL = MyHttpCoreAndroid.getData(PARAMS_LOCAL[0]);
                assert CONTENT_LOCAL != null : "We found no content";
                final JsonElement ROOT_NODE = JSON_PARSER.parse(CONTENT_LOCAL);
                final JsonObject DETAILS_LOCAL = ROOT_NODE.getAsJsonObject();
                final JsonElement NAME_ELEMENT_NODE;
                //Log.d(TAG, "Language is: " + Locale.getDefault().getDisplayLanguage());//get language like romana
                //get initials like: ro
                final String PUT_LANGUAGE_IN_STRING = Resources.getSystem().getConfiguration().locale.getLanguage();
                Log.d(TAG, "lang is: " + PUT_LANGUAGE_IN_STRING);
                if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase("ro")) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get("nameRO");
                } else {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get("name");
                }
//                JsonElement nameNode = details.get("name");
                return NAME_ELEMENT_NODE.getAsString();
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]
        @Override
        protected void onPostExecute(final String RESULT) {
            try {
                //using raw JSON PARSER
                updateDisplayString(RESULT);
                //we get rid of the task that we created
                tasks.remove(this);
                if (tasks.size() == 0) {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    progressBarLoading2.setVisibility(View.GONE);
                    counterImgView.setVisibility(View.VISIBLE);
                    counterText.setVisibility(View.VISIBLE);
                }
                //keep this as secondary solve instead of finally
                //if (RESULT == null) {
                    //Toast.makeText(MainActivity.this, R.string.cant_connect,
                    //Toast.LENGTH_LONG).show();
                //}
            } finally {
                final String tempResult = (RESULT == null) ?  "No quotes available in this moment ..." : RESULT;
                updateDisplayString(tempResult);
            }
        }//onPostExecute[END]
        @Override
        protected void onProgressUpdate(final String... VALUES) {
            updateDisplayString(VALUES[0]);
        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

    @SideEffect
    private void setImagesForAchievementCard() {
        try {
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, 0);
            }
            Log.d("taoAchiev", "counter from achiev = " + counter);
            if (counter>=0&&counter<10) {
                //user have between a day and a week
                rankOneImg.setBackgroundResource(R.mipmap.chevron7);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron11);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Recruit");
            } else if (counter>9&&counter<20) {
                rankOneImg.setBackgroundResource(R.mipmap.chevron7);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron11);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Recruit II");
            } else if (counter>19&&counter<30) {
                rankOneImg.setBackgroundResource(R.mipmap.chevron7);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron8);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron9);
                rankThreeImg.setAlpha(1.0f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron11);
                rankFourImg.setAlpha(1.0f);
                editor.putString("rank", "Recruit III");
            } else if (counter>29&&counter<40) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron19);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron20);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron21);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron10);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Silver");
            } else if (counter>39&&counter<50) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron19);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron20);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron21);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron10);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Silver II");
            } else if (counter>49&&counter<60) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron19);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron20);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron21);
                rankThreeImg.setAlpha(1.0f);
                rankFourImg.setBackgroundResource(R.mipmap.chevron10);
                rankFourImg.setAlpha(1.0f);
                editor.putString("rank", "Silver III");
            } else if (counter>59&&counter<70) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron16);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.gnm);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Gold");
            } else if (counter>69&&counter<80) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron16);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
                rankThreeImg.setAlpha(0.2f);
                rankFourImg.setBackgroundResource(R.mipmap.gnm);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Gold I");
            } else if (counter>79&&counter<90) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.mipmap.chevron16);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
                rankThreeImg.setAlpha(1.0f);
                rankFourImg.setBackgroundResource(R.mipmap.gnm);
                rankFourImg.setAlpha(0.2f);
                editor.putString("rank", "Gold II");
                //WHEN USER REACH DAY 90 - GREATEST MILESTONE
            } else if (counter == 90) {
                rankOneImg.setBackgroundResource(R.mipmap.chevron16);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.mipmap.chevron17);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.mipmap.chevron18);
                rankThreeImg.setAlpha(1.0f);
                rankFourImg.setBackgroundResource(R.mipmap.gnm);
                rankFourImg.setAlpha(1.0f);
                editor.putString("rank", "Gold III");
            }
        //using "final rethrow" by not specifying throwing a specific exception like NullPointer
        //the final keyword is optional, but in practice, we've found that it helps to use it while
        //adjusting to the new semantics of catch and rethrow
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogForChangingCheckInDate() {
        final EditText ET_FOR_CHOOSING_SAVINGS =
                new EditText(MainActivity.this);
        ET_FOR_CHOOSING_SAVINGS.setInputType(InputType.TYPE_CLASS_NUMBER);
        //impl bottom dialog instead of normal dialog
        new BottomDialog.Builder(this)
                .setTitle("Check in customization!")
                .setContent("Type in what time is suitable for you to check in.")
                .setPositiveText("SET")
                .setCancelable(false)
                .setCustomView(ET_FOR_CHOOSING_SAVINGS)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Editable editM = ET_FOR_CHOOSING_SAVINGS.getText();
                        if (TextUtils.isEmpty(editM)){
                            ET_FOR_CHOOSING_SAVINGS.setError("Please input numbers!");
                            return;
                        }
                        HOUR_OF_FIRSTLAUNCH = Integer.parseInt(editM.toString());
                        editor.putInt(HOUR_OF_FIRSLAUNCH_SP, HOUR_OF_FIRSTLAUNCH);
                        editor.apply();
                        Log.d("INTROTAO", "money saved in INTROACTIVITY ? :  " + HOUR_OF_FIRSTLAUNCH);
                    }
                }).show();
    }

    @SideEffect
    private void showEntireProgressForUserCard(final TextView TV_USER_CIGARETTES_PROGRESS,
                                               final TextView TV_HIGHEST_STREAK,
                                               final TextView TV_HOURS_PROGRESS) {
        try {
            //getting the highest streak and put it in progress card
            final int HIGHEST_STREAK;
            if (preferences.contains("highest")){
                HIGHEST_STREAK = preferences.getInt("highest", 0);
            } else {
                HIGHEST_STREAK = preferences.getInt(COUNTER, 0);
            }
            final String TEMP_STREAK = getString(R.string.streak_string, HIGHEST_STREAK);
            TV_HIGHEST_STREAK.setText(TEMP_STREAK);
            //getting highest rank and put it into ranks card
            final String LAST_STREAK = preferences.getString("rank", "unranked yet");
            final String RANK_LOCAL = getString(R.string.rank_master, LAST_STREAK);
            rankMasterTxt.setText(RANK_LOCAL);
            rankMasterTxt.setBackground(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.custom_button_round));

            if (counter == 0) {
                TV_USER_CIGARETTES_PROGRESS.setText(R.string.cig_press_leaf);
                final String PRESS_LEAF = getString(R.string.life_press_leaf);
                TV_HOURS_PROGRESS.setText(PRESS_LEAF);
            } else {
                if (preferences.contains(LIFEREGAINED)) {
                    lifeRegained = preferences.getFloat(LIFEREGAINED, -1);
                    numberFormat.format(lifeRegained);
                } else {
                    lifeRegained = Float.valueOf((5f * Float.valueOf(preferences.getInt(MODIFIED_CIGG_PER_DAY, 0))) / 60f);
                    numberFormat.format(lifeRegained);
                }
                final String CIGARETTES_NOT_SMOKED_STRING = getString(R.string.cig_not_smoked);
                final String LIFE_REGAINED_STRING = getString(R.string.life_reg, numberFormat.format(lifeRegained));
                TV_USER_CIGARETTES_PROGRESS.setText(CIGARETTES_NOT_SMOKED_STRING +" " + preferences.getInt(MODIFIED_CIGG_PER_DAY, 0));
                TV_HOURS_PROGRESS.setText(LIFE_REGAINED_STRING);
                editor.putFloat(LIFEREGAINED, lifeRegained);
                editor.apply();
            }
        } catch(final Exception e){ e.printStackTrace(); }
    }

    private void setMarginForProgress() {
        final Resources RESOURCES = this.getResources();
        final int PX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, RESOURCES.getDisplayMetrics());
        final LinearLayout.LayoutParams PARAMS_LOCAL = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        PARAMS_LOCAL.setMargins(PX, 0, 0, 0);
        Log.d("TAOLEN10", " works the SETMARGIN + " + progressBarEnergyLevel.getProgress());
        if (progressBarEnergyLevel.getProgress() == 100) {
//            txtProgressForEnergyLevels.setLayoutParams(params);
            setMargins(txtProgressForEnergyLevels, PX, 0 , 0, 0);
            Log.d("TAOLEN10", " works the SETMARGIN + " + progressBarEnergyLevel.getProgress());
        }
    }

    private void setMargins (final View VIEW, final int LEFT, final int TOP, final int RIGHT, final int BOTTOM) {
        if (VIEW.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams LAYOUT_PARAMS = (ViewGroup.MarginLayoutParams) VIEW.getLayoutParams();
            LAYOUT_PARAMS.setMargins(LEFT, TOP, RIGHT, BOTTOM);
            VIEW.requestLayout();
        }
    }

    private void disableViewsForComingSoon(){
        challengeCardView.setClickable(false);
        timeStampLogsCardview.setClickable(false);
    }

    private void dialogForReset(){
        new BottomDialog.Builder(this)
                .setTitle(R.string.reset_whole_progress)
                .setContent(R.string.are_you_sure)
                .setPositiveText(R.string.YES)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .setNegativeText(R.string.NO)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        resetWholeProgress();
                    }
                }).show();
    }

}//[END OF MAIN CLASS]

//    private boolean isLeap(final int year){
//        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
//    }

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

//        //dialog ------------
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

//    //dialog when user pass a day
//    private void showCustomDialogOnFirstLaunch(String title, StringBuilder content){
//        //dialog ------------
//        new BottomDialog.Builder(this)
//                .setTitle(title)
//                .setContent(content)
//                .setPositiveText("OK")
//                .setCancelable(false)
//                .setPositiveBackgroundColorResource(R.color.colorPrimary)
//                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
//                .setPositiveTextColorResource(android.R.color.white)
//                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
//                .onPositive(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(BottomDialog dialog) {
//                        Log.d("BottomDialogs", "Do something!");
//                        //intro activity check in a separate thread
////                        startIntroActivity();
////                        showDialogForSavingSum();
//                    }
//                }).show();
//    }