package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.preference.PreferenceManager;

import com.anupcowkur.herebedragons.SideEffect;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taozen.quithabit.cardActivities.AchievmentsActivity;
import com.taozen.quithabit.cardActivities.SavingsActivity;
import com.taozen.quithabit.options.OptionsActivity;
import com.taozen.quithabit.utils.MyHttpCoreAndroid;
import com.taozen.quithabit.utils.NetworkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "TAOMAO";
    public static final int DEFAULT_NEGATIVE_VALUE = -1000;
    public static final int LEAP_YEAR_DAYS = 366;
    public static final int NORMAL_YEAR_DAYS = 365;
    public static final int ONE_HUNDRED = 100;
    public static final int SEVENTY_FIVE = 75;
    public static final int FIVE = 5;
    public static final int TEN = 10;
    public static final int FIFTEEN = 15;
    public static final int TWENTYFIVE = 25;
    public static final int TWENTY = 20;
    public static final int ZERO = 0;
    public static final int THIRTY = 30;
    public static final int FIFTY = 50;
    public static final int SIXTY = 60;
    public static final int FOURTEEN = 40;
    public static final int EIGHTEEN = 80;
    public static final String RO = "ro";
    public static final String NAME = "name";
    public static final String NAME_RO = "nameRO";
    public static final String WE_FOUND_NO_CONTENT = "We found no content";
    public static final String NO_QUOTES_AVAILABLE_IN_THIS_MOMENT = "No quotes available in this moment ...";
    public static final int SIXTY_NINE = 69;
    public static final int NINETY = 90;
    public static final String HIGHEST = "highest";
    public static final String RANK = "rank";
    public static final String UNRANKED = "unranked";
    public static final int TWO_HUNDRED = 200;
    public static final int ONE = 1;
    public static final int ONE_HUNDRED_AND_EIGHTY = 180;
    public static final int TWO_HUNDRED_AND_SEVENTY = 270;
    public static final int THREE_HUNDRED = 300;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final String FIRSTSAVE = "firstsave";
    public static final int FOUR = 4;
    public static final String YEARLEAP = "yearleap";
    public static final String DAYZEN = "DAYZEN";
    public static final int ONE_THOUSAND_AND_FIVE_HUNDRED = 1500;
    public static final String FIRST_START = "firstStart";
    public static final String ZERO_STRING = "ZERO";
    public static final int EIGHT = 8;
    public static final int TWENTYFOUR = 24;

    String arr = "";
    long firstSave;
    long longFromSavingActivity;

    public static final String HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS = "https://pyflasktao.herokuapp.com/books";
    public static final String WEB_PAGE = "https://bytao7mao.github.io/quithabit/";
    public static final String PRIVACY_POLICY = "https://bytao7mao.github.io/quithabit/legal/privacy_policy.html";
    public static final String TERMS_AND_CONDITIONS = "https://bytao7mao.github.io/quithabit/legal/terms_and_conditions.html";
    public static final String THIRD_PARTY_LICENCES = "https://bytao7mao.github.io/quithabit/legal/licences.html";

    boolean higherThanOne;
    private List<MainActivity.MyAsyncTask> tasks;
    private Timer timer;
    Float lifeRegained;

    final ArrayList<String> quotesForPassingTheDayList = new ArrayList<>();

    private int cigarettesPerDay;

    //share
    @BindView(R.id.shareDays) ImageView shareDays;
    @BindView(R.id.shareProgress) ImageView shareProgress;
    @BindView(R.id.shareAchieve) ImageView shareAchieve;
    @BindView(R.id.shareSavings) ImageView shareSavings;

    //Views
    @BindView(android.R.id.content) View parentLayout;
    @BindView(R.id.InsteadOfLayout) ConstraintLayout insteadOfLayout;
    @BindView(R.id.layoutCheckIn) ConstraintLayout checkInButton;

    //CardViews
    @BindView(R.id.card_view_Bottom) MaterialCardView progressCardView;
    @BindView(R.id.card_view_Bottom_Saving) MaterialCardView savingsCardView;
    @BindView(R.id.card_view_mainID) MaterialCardView cardViewMain;
    @BindView(R.id.card_view_Bottom_Achievments) MaterialCardView achievementRanksCard;
    @BindView(R.id.card_view_Middle) MaterialCardView upperProgressPercentsCard;

    //TextViews
    @BindView(R.id.YourQDay) TextView YourQDay;
    @BindView(R.id.exploreAchievementId) MaterialTextView TvExploreAchievement;
    @BindView(R.id.exploreSavingsId) MaterialTextView TvExploreSavings;
    @BindView(R.id.rank_master) TextView rankMasterTxt;
    @BindView(R.id.counterTextId) MaterialTextView counterText;
    @BindView(R.id.txtProgressIdForGums) TextView txtProgressForGums;
    @BindView(R.id.txtProgressIdForBreath) TextView txtProgressForBreath;
    @BindView(R.id.txtProgressIdForFatigue) TextView txtProgressForFatigue;
    @BindView(R.id.txtProgressIdForEnergy) TextView txtProgressForEnergyLevels;
    @BindView(R.id.targetTxtViewId) TextView targetTxtViewId;
    @BindView(R.id.savingsTxtId) TextView moneySavingsTxt;
    @BindView(R.id.remaining_days_Id) TextView remainingDaysTxt;
    @BindView(R.id.tvTipOfTheDay) TextView tipOfTheDayTxtView;
    @BindView(R.id.textNonSmokerId) TextView textNonSmoker;
    @BindView(R.id.subTextCheckInId) TextView checkInText;
    @BindView(R.id.subTextEnergyId) TextView subTextEnergy;
    @BindView(R.id.subTextBreathId) TextView subTextBreath;
    @BindView(R.id.subTextFatigueId) TextView subTextFatigue;
    @BindView(R.id.subTextGumsId) TextView subTextGums;
    @BindView(R.id.textViewAchievTitle) TextView yourAchievmentTxtTitle;
    @BindView(R.id.textViewProgressTitle) TextView yourProgressTxtTitle;
    @BindView(R.id.textViewSavingsTitle) TextView yourSavingsTxtTitle;
    @BindView(R.id.YourProgressIdCigaretes) TextView userCigaretesProgressTxt;
    @BindView(R.id.YourHighestStreakId) TextView userHighestStreakTxt;
    @BindView(R.id.YourProgressIdHours) TextView userHoursProgressTxt;
    //ProgressBar
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.loadingProgressIdReplacement) ProgressBar progressBarLoading2;
    //ImageViews
//    @BindView(R.id.counterImageId) ImageView counterImgView;
    @BindView(R.id.rankOneId) ImageView rankOneImg;
    @BindView(R.id.rankTwoId) ImageView rankTwoImg;
    @BindView(R.id.rankThreeId) ImageView rankThreeImg;
    //CircularProgressBar
    //progress for percent - this is a circular bar
    @BindView(R.id.counterImageId) CircularProgressBar counterImgView;
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
    //wil start from THIRTY to SIXTY to 90
    private int userMaxCountForHabit = -1;
    //default false
    private boolean buttonClickedToday;
    //Calendar
    private Calendar calendarOnClick, calendarForProgress;
    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final DisplayMetrics metrics = new DisplayMetrics();
    private Configuration config;
    private String challs;
    private final StringBuilder strBuilder = new StringBuilder();

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
    }

    //OnCreate [START]
    @UiThread
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(MainActivity.this);
            //shared pref
            preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            editor = preferences.edit();

            //strictmode ?
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, -1);
            }

            setCounterImageDaysOrDay(counter);

            counterImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkInButton.callOnClick();
                }
            });

            shareProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //share text
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = "Your body here";
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    //share image
//                Bitmap b = Screenshoot.takescreenshot(counterImgView);
                    Bitmap b = Screenshoot.takescreenshot(upperProgressPercentsCard);
//                backgroundImgWall.setImageBitmap(b);

                    Uri uri = null;
                    try {
                        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
                        FileOutputStream stream = new FileOutputStream(file);
                        b.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        stream.close();
                        uri = Uri.fromFile(file);
                    } catch (IOException e) {
                        Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
                    }

                    //Convert to byte array
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    try {
                        startActivity(Intent.createChooser(intent, "Share Progress"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            firstCheckForInitialCiggarettesPerDay();
            startFirstActivity();
            //using API from splash
            final Intent INTENT = getIntent();
            final String NAME = INTENT.getStringExtra("data");
            tipOfTheDayTxtView.setText(NAME);

            numberFormat = new DecimalFormat("#.##");

            //testing area
            final Date DATE = new Date();
            final Calendar CALENDAR = GregorianCalendar.getInstance();
            CALENDAR.setTime(DATE);
            //if user started at hour of 00 or 23 at night and hour of now is 00 then we make hour of now equal
            //to 24 to be able to make the calculation
            //else we calculate only hour of now to be equal to 24 if hour of daylight is 00
            HOUR_OF_DAYLIGHT = CALENDAR.get(Calendar.HOUR_OF_DAY);
            HOUR_OF_DAYLIGHT = HOUR_OF_DAYLIGHT == ZERO ? TWENTYFOUR : HOUR_OF_DAYLIGHT;
            setTheHourOfFirstLaunch(CALENDAR);
            //leave it for pay version
//        setBackgroundForDaylightOrNight();
            tasks = new ArrayList<>();
            config = getResources().getConfiguration();
            //set text for checkin
            setCheckInText();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            //CONDITION TO SET TARGET TEXT AFTER CHECKINNG COUNTER
            setTargetAfterCheckingCounter();
            setTargetDays();
            firstCheckMax();

            progressBarLoading.getIndeterminateDrawable().setColorFilter(BlendModeColorFilterCompat.
                    createBlendModeColorFilterCompat(
                            R.color.colorPrimaryDark, BlendModeCompat.SRC_ATOP));

            progressBarLoading2.getIndeterminateDrawable().setColorFilter(BlendModeColorFilterCompat.
                    createBlendModeColorFilterCompat(
                            R.color.colorPrimaryDark, BlendModeCompat.SRC_ATOP));

//        progressCardView.setCardElevation(ZERO);
//        savingsCardView.setCardElevation(ZERO);
//        cardViewMain.setCardElevation(ZERO);
//        achievementRanksCard.setCardElevation(ZERO);
//        upperProgressPercentsCard.setCardElevation(ZERO);

            if (preferences.contains("firstsave")){
                firstSave = preferences.getLong("firstsave",ZERO);
            } else {
                DAY_OF_CLICK = preferences.getInt(CLICKDAY_SP, ZERO);
                ttfancyDialogForFirstTimeLaunch(getString(R.string.welcome_to_quit_habit), getString(R.string.first_day));
                greenCondition();
            }

            setTxtViewForUserMaxCountDaysOnStringVersion(
                    String.valueOf(userMaxCountForHabit),
                    targetTxtViewId);

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

            ActionBar actionBar = getSupportActionBar();
            Objects.requireNonNull(actionBar).
                    setBackgroundDrawable(
                            new ColorDrawable(
                                    ContextCompat.getColor(
                                            getApplicationContext(), R.color.white)));

//        tipOfTheDayTxtView.setTypeface(montSerratSemiBoldItalicTypeface);
//        TvExploreAchievement.setTypeface(montSerratMediumTypeface);
//        TvExploreSavings.setTypeface(montSerratMediumTypeface);
            counterText.setTypeface(montSerratSimpleBoldTypeface);
//        remainingDaysTxt.setTypeface(montSerratSimpleBoldTypeface);
//        targetTxtViewId.setTypeface(montSerratSimpleBoldTypeface);
//        txtProgressForEnergyLevels.setTypeface(montSerratBoldTypeface);
//        txtProgressForFatigue.setTypeface(montSerratBoldTypeface);
//        txtProgressForBreath.setTypeface(montSerratBoldTypeface);
//        txtProgressForGums.setTypeface(montSerratBoldTypeface);
//        moneySavingsTxt.setTypeface(montSerratMediumTypeface);
//        textNonSmoker.setTypeface(montSerratBoldTypeface);
//        subTextEnergy.setTypeface(montSerratMediumTypeface);
//        subTextBreath.setTypeface(montSerratMediumTypeface);
//        subTextFatigue.setTypeface(montSerratMediumTypeface);
//        subTextGums.setTypeface(montSerratMediumTypeface);
//        yourAchievmentTxtTitle.setTypeface(montSerratBoldTypeface);
//        yourProgressTxtTitle.setTypeface(montSerratBoldTypeface);
//        yourSavingsTxtTitle.setTypeface(montSerratBoldTypeface);
//        userCigaretesProgressTxt.setTypeface(montSerratMediumTypeface);
//        userHighestStreakTxt.setTypeface(montSerratMediumTypeface);
//        userHoursProgressTxt.setTypeface(montSerratMediumTypeface);
//        checkInText.setTypeface(montSerratMediumTypeface);
//        rankMasterTxt.setTypeface(montSerratMediumTypeface);

            showDeviceDensityPixels();
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, -1);
            }
            //setting the achievments images for user
            showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
            setImagesForAchievementCard();
            setImprovementProgressLevels();
            TvExploreAchievement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent INTENT = new Intent(MainActivity.this, AchievmentsActivity.class);
                    startActivity(INTENT);
                }
            });//achievementRanksCard[END]
            TvExploreSavings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent INTENT = new Intent(MainActivity.this, SavingsActivity.class);
                    INTENT.putExtra(SAVINGS_FINAL, savings);
                    startActivity(INTENT);
                }
            });//savingsCardView[END]

            checkInsteadOfSmoking();

            //disable views for "coming soon area"
//        disableViewsForComingSoon();

            //retrieving the counter and minute values
            //run the task
            runningInBackground();
            //counter on click for counter button
            counterButtonInitializer();
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

    private void setCounterImageDaysOrDay(int counterInner) {
        try {
            final Locale localeMAIN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                localeMAIN = getResources().getConfiguration().getLocales().get(0);
            } else {
                localeMAIN = getResources().getConfiguration().locale;
            }
            //get initials like: RO/US/EN/FR
            final String PUT_LANGUAGE_IN_STRING_ON_CREATE = localeMAIN.getLanguage();
            Log.d(TAG, "lang is: " + PUT_LANGUAGE_IN_STRING_ON_CREATE);
            if (counterInner < 2) {
                final Drawable ro_or_eng_for_first_day =
                        (PUT_LANGUAGE_IN_STRING_ON_CREATE.equalsIgnoreCase(RO))
                                ? ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.counterimg_ro)
                                : ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.counterimgone);
//                counterImgView.setImageDrawable(ro_or_eng_for_first_day);
            } else {
                final Drawable ro_or_eng_for_first_day =
                        (PUT_LANGUAGE_IN_STRING_ON_CREATE.equalsIgnoreCase(RO))
                                ? ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.counterimg_ro)
                                : ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.counterimg);
//                counterImgView.setImageDrawable(ro_or_eng_for_first_day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: if needed
    }

    private void showDeviceDensityPixels() {
        final int TV_WIDTH = checkInText.getWidth();
        final int TV_HEIGHT = checkInText.getHeight();

        final float DENSITY = getResources().getDisplayMetrics().density;
        final float DENSITY_WEIGHT = TV_WIDTH / DENSITY;
        final float DENSITY_HEIGHT = TV_HEIGHT / DENSITY;
        Log.d("DENS", "widht: " + DENSITY_WEIGHT + " height: " + DENSITY_HEIGHT + DENSITY);
    }

    private void checkInsteadOfSmoking() {
        insteadOfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insteadOfSmoking();
            }
        });
    }

    private void setTargetAfterCheckingCounter() {
        if (preferences.contains(COUNTER)){ counter = preferences.getInt(COUNTER, -1);counterText.setText(String.valueOf(counter)); }
        //test
        if (!preferences.contains(COUNTER)){counterText.setText(ZERO_STRING);
        } else {
            counterText.setText(String.valueOf(counter));
        }
        if (preferences.contains(INITIAL_CIGG_PER_DAY)){cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, ZERO);}
        if (preferences.contains(LIFEREGAINED)){ lifeRegained = preferences.getFloat(LIFEREGAINED, ZERO); }
    }

    private void firstCheckForInitialCiggarettesPerDay() {
        if (!preferences.contains(INITIAL_CIGG_PER_DAY)){
            isFirstStart = true;editor.putBoolean(FIRST_START,isFirstStart);editor.apply();
            editor.putInt(YEARLEAP, calendarForProgress.get(Calendar.YEAR));
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getDefault());
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
            DAY_OF_CLICK = DAY_OF_PRESENT - 1;
            editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
            editor.apply();
        } else {
            isFirstStart = false;editor.putBoolean(FIRST_START,isFirstStart);editor.apply();
        }
    }

    private void setTheHourOfFirstLaunch(final Calendar CALENDAR) {
        //my personal method to save a value and keep it every time i launch on create :)
        if (preferences.contains(HOUR_OF_FIRSLAUNCH_SP)) {
            HOUR_OF_FIRSTLAUNCH = preferences.getInt(HOUR_OF_FIRSLAUNCH_SP, -1);
        } else {
            //recent refactor that works fine
            HOUR_OF_FIRSTLAUNCH = (CALENDAR.get(Calendar.HOUR_OF_DAY)==ZERO) ? 24 : CALENDAR.get(Calendar.HOUR_OF_DAY);
            final int MONTH_CALENDAR = CALENDAR.get(Calendar.MONTH)+1;
            final int MONTH_LOCALDATE;
            final String MONTH_STRING;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                MONTH_LOCALDATE = LocalDate.now().getMonthValue();
                MONTH_STRING = ""+LocalDate.now().getMonth();
            } else {
                MONTH_LOCALDATE = MONTH_CALENDAR;
                MONTH_STRING = ""+MONTH_CALENDAR;
            }
            editor.putString("qday", CALENDAR.get(Calendar.DAY_OF_MONTH)
                    +"-" + MONTH_STRING + "-" + CALENDAR.get(Calendar.YEAR));
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
//        if (HOUR_OF_DAYLIGHT >= 21 || HOUR_OF_DAYLIGHT < 7) {
////        if (HOUR_OF_DAYLIGHT >= 6 && HOUR_OF_DAYLIGHT <= TWENTY) {
//            backgroundImgWall.setBackgroundResource(R.drawable.ppp);
//            tipOfTheDayTxtView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//            counterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//            counterText.setAlpha(1.0f);
//            remainingDaysTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//            remainingDaysTxt.setAlpha(0.8f);
//            targetTxtViewId.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//            targetTxtViewId.setAlpha(0.8f);
//            textNonSmoker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
//            textNonSmoker.setAlpha(1.0f);
//            checkInText.setBackground((ContextCompat.getDrawable(
//                    getApplicationContext(), R.drawable.custom_text_round_bg)));
//            checkInText.setAlpha(1.0f);
//            backgroundImgWall.setAlpha(1.0f);
//        } else {
//            //change wallpaper during daytime
//            backgroundImgWall.setBackgroundResource(R.drawable.bgday);
//            tipOfTheDayTxtView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
//            counterText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
//            remainingDaysTxt.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
//            targetTxtViewId.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
//            textNonSmoker.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.greish));
//            textNonSmoker.setAlpha(0.8f);
////            checkInText.setTextColor(getResources().getColor(R.color.greish));
//            checkInText.setBackground((ContextCompat.getDrawable(
//                    getApplicationContext(), R.drawable.custom_text_round_bg)));
//            checkInText.setAlpha(0.7f);
////            backgroundImgWall.setAlpha(0.05f);
//            backgroundImgWall.setAlpha(0f);
//        }
    }

    private void firstCheckMax() {
        if (userMaxCountForHabit == -1) {
            userMaxCountForHabit = THIRTY;
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
                    public void onClick(@NonNull BottomDialog dialog) {
                        //call on destroy
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
                        final Calendar CALENDAR = Calendar.getInstance();
                        CALENDAR.setTimeZone(TimeZone.getDefault());
                        final int MONTH_CALENDAR = CALENDAR.get(Calendar.MONTH)+1;
                        final int MONTH_LOCALDATE;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            MONTH_LOCALDATE = LocalDate.now().getMonthValue();
                        } else {
                            MONTH_LOCALDATE = MONTH_CALENDAR;
                        }
                        editor.putString("qday", CALENDAR.get(Calendar.DAY_OF_MONTH)
                                +"-" + MONTH_LOCALDATE + "-" + CALENDAR.get(Calendar.YEAR));
                        editor.apply();
                        YourQDay.setText(getResources().getString(
                                R.string.qday,preferences.getString("qday", "none")));
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
                Log.d(DAYZEN, "thread separat: " + Thread.currentThread().getName());
                if (preferences.contains(FIRST_START)) {
                    isFirstStart = preferences.getBoolean(FIRST_START, false);
                } else {
                    //on first launch this will trigger
                    isFirstStart = true;
                    editor.putBoolean(FIRST_START, false);
                    editor.apply();
                    //fake first day of user to be day of present -1 to enable him/her to check in for the first time
                    //[calendar area]
                    calendarForProgress = Calendar.getInstance();
                    calendarForProgress.setTimeZone(TimeZone.getDefault());
                    DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
                    DAY_OF_CLICK = DAY_OF_PRESENT - ONE;
                    editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
                    editor.apply();
                }
                //  If the activity has never started before...
                if (isFirstStart) {
                    DAY_OF_CLICK = DAY_OF_PRESENT - ONE;
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
                            Log.d(DAYZEN, "thread din ui: " + Thread.currentThread().getName());
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
    private void counterButtonInitializer() {
        //active when user passed a day
        //inactive when user wait
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCounterImageDaysOrDay(counter);
                if (buttonClickedToday) {
                    checkInNotAvailable();
                } else if (!checkInText.getText().toString().equalsIgnoreCase(
                        getResources().getString(R.string.check_in_now))) {
                    checkInNotAvailable();
                } else {
                    setTodayToClickDay();
                    final int yearOfNow = Calendar.getInstance().get(Calendar.YEAR);
                    final int x = isLeap(yearOfNow) ? LEAP_YEAR_DAYS : NORMAL_YEAR_DAYS;
                    final String MESSAGE_FOR_DIALOG;
                    MESSAGE_FOR_DIALOG = higherThanOne ? getString(R.string.abstained_last_days) : getString(R.string.abstained_today);
                    //between 1 and 29
                    if (counter == ZERO) {
                        ttfancyDialogForFirstTimeLaunch(getString(R.string.welcome_to_quit_habit), getString(R.string.first_day));
                    } else if (counter > ZERO && counter < THIRTY-ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, THIRTY), MESSAGE_FOR_DIALOG);
                        //between 29(to show up in THIRTY) and SIXTY
                    } else if (counter > THIRTY-TWO && counter < SIXTY-ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, SIXTY), MESSAGE_FOR_DIALOG);
                        //between 59(to show up in SIXTY) and 90
                    } else if (counter > SIXTY-TWO && counter < NINETY-ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, 90), MESSAGE_FOR_DIALOG);
                        //between 89(to show up in SIXTY) and 180
                    } else if (counter > NINETY-TWO && counter < ONE_HUNDRED_AND_EIGHTY-ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, 180), MESSAGE_FOR_DIALOG);
                        //between 179(to show up in SIXTY) and 270
                    } else if (counter > ONE_HUNDRED_AND_EIGHTY-TWO && counter < TWO_HUNDRED_AND_SEVENTY-ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, 270), MESSAGE_FOR_DIALOG);
                        //between 279(to show up in SIXTY) and 360
                    } else if (counter > TWO_HUNDRED_AND_SEVENTY-TWO && counter < THREE_HUNDRED+SIXTY+ONE) {
                        normalFancyDialog(getString(R.string.beat_milestone, 360), MESSAGE_FOR_DIALOG);
                        //if user has reached almost one year (360 days) we ask if he/she want reset
                    } else if (counter >= x) {
                        dialogForResetForced();
                    }
                    //SHOW FANCY TOAST WITH CONGRATS
                    //[END OF ELSE IFS DIALOGS]
                }
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
                cigarettesPerDay = preferences.getInt(INITIAL_CIGG_PER_DAY, ZERO);
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
            //using "final rethrow" by not specifying throwing a specific exception like NullPointer
            //the final keyword is optional, but in practice, we've found that it helps to use it while
            //adjusting to the new semantics of catch and rethrow
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//[END OF setTodayToClickDay]

    private void normalFancyDialog(String title, String message) {
        MaterialAlertDialogBuilder milestoneAlert = new MaterialAlertDialogBuilder
                (new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        milestoneAlert.setMessage(message);
        milestoneAlert.setTitle(title);
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                setCheckInText();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
                if (counter == ZERO) {
                    savings = preferences.getLong("taoz10", -10);
                }
                if (preferences.contains("diff") && higherThanOne){
                    final int DIF = preferences.getInt("diff", -100);
                    counter = counter + DIF;
                    setCounterImageDaysOrDay(counter);
                    if (preferences.contains("tempLong")){
                        longFromSavingActivity = preferences.getLong("tempLong", ZERO);
                    } else {
                        longFromSavingActivity = ZERO;
                    }
                    savings = longFromSavingActivity + (firstSave * counter);
                    editor.putLong(SAVINGS_FINAL, savings);
                    editor.apply();
                    higherThanOne = false;
                    final int yearOfNow = Calendar.getInstance().get(Calendar.YEAR);
                    final int x = isLeap(yearOfNow) ? LEAP_YEAR_DAYS : NORMAL_YEAR_DAYS;
                    if (counter > x){
                        dialogForResetForced();
                    }
                } else {
                    counter++;
                    setCounterImageDaysOrDay(counter);
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO) + firstSave;
                    Log.d("taogenX", "savings from TAO = " + savings);
                    editor.putLong(SAVINGS_FINAL, savings);
                    editor.apply();
                    higherThanOne = false;
                }
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
                estabilishHighestRecordForCounter();
                showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
//                counterImgView.setProgress(counter);
            }
        });
        milestoneAlert.setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
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
                    editor.putInt(HIGHEST, counter);
                    editor.apply();
                    counter = 1;
                    setCounterImageDaysOrDay(counter);
                    savings = firstSave;
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
//                    counterImgView.setProgress(counter);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
        milestoneAlert.show();
    }

    private void ttfancyDialogForFirstTimeLaunch(String title, String message) {
        MaterialAlertDialogBuilder milestoneAlert = new MaterialAlertDialogBuilder
                (new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        milestoneAlert.setMessage(message);
        milestoneAlert.setTitle(title);
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (preferences.contains(SAVINGS_FINAL)){
                    firstSave = preferences.getLong(SAVINGS_FINAL, ZERO);
                    editor.putLong("firstsave", firstSave);
                    editor.apply();
                    Log.d("taogenX", "firstsave is: " + firstSave);
                }
                setTodayToClickDay();
                firstCheckForInitialCiggarettesPerDay();
                setCheckInText();
                if (counter == ZERO) {
                    savings = preferences.getLong("taoz10", -10);
                }
                if (counter == 1) {
                    if (preferences.contains(SAVINGS_FINAL)){
                        firstSave = preferences.getLong(SAVINGS_FINAL, ZERO);
                        editor.putLong("firstsave", firstSave);
                        editor.apply();
                        Log.d("taogenX", "firstsave is: " + firstSave);
                    }
                }
                Log.d("COUNTERTAO", "before - counter is raised with: " + counter);
                counter++;
                setCounterImageDaysOrDay(counter);
                if (counter == 1){
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO);
                    editor.putLong(SAVINGS_FINAL, savings);
                    if (preferences.contains(SAVINGS_FINAL)){
                        firstSave = preferences.getLong(SAVINGS_FINAL, ZERO);
                        editor.putLong("firstsave", firstSave);
                        editor.apply();
                        Log.d("taogenX", "firstsave is: " + firstSave);
                    }
                } else {
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO) + firstSave;
                    Log.d("taogenX", "savings from TAO = " + savings);
                    editor.putLong(SAVINGS_FINAL, savings);
                    editor.apply();
                }
                higherThanOne = false;
                Log.d("COUNTERTAO", "after - counter is raised with: " + counter);
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
                //var 2 - custom toast after answer
                estabilishHighestRecordForCounter();
                showEntireProgressForUserCard(userCigaretesProgressTxt, userHighestStreakTxt, userHoursProgressTxt);
                //var 1 - dialog after answer
                positiveDialogAfterPassDay();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
//                counterImgView.setProgress(counter);
            }
        });
        milestoneAlert.show();
    }

    private void resetWholeProgress() {
        try {
            estabilishHighestRecordForCounter();
            editor.apply();
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
            Log.d("taogenX", "here is the last max counter for user: " + preferences.getInt("highest", ZERO));
            editor.apply();
            counter = 1;
            savings = firstSave;
            Log.d("taogenX", "firstsave is: " + firstSave);
            //to see
            editor.putLong(SAVINGS_FINAL, savings);//off
            //maybe
            editor.putInt(COUNTER, counter);
            //new edit
            final int TEMP_CIGARETTES = preferences.getInt(INITIAL_CIGG_PER_DAY, ZERO);
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
            //TODO: to replace with another dialog
//            negativeDialogAfterRelapse();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void estabilishHighestRecordForCounter() {
        try {
            if (preferences.contains("highest")){
                //if editor cointains highest, we see which is higher, present counter or last counter;
                final int FINAL_HIGHEST;
                final int HIGHEST = preferences.getInt("highest",ZERO);
                if (counter >= HIGHEST) {
                    FINAL_HIGHEST = counter;
                } else {
                    FINAL_HIGHEST = HIGHEST;
                }
                editor.putInt("highest", FINAL_HIGHEST);
                editor.apply();
            } else {
                editor.putInt("highest", preferences.getInt(COUNTER, ZERO));
                editor.apply();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void setTxtViewForUserSavings(long SAVE_PER_DAY, String TOTAL_SAVINGS,
                                          TextView TEXTVIEW) {
        final DecimalFormat FORMATTER = new DecimalFormat("###,###,##0.00");
        final String VALUE_PER_YEAR_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY))*365);
        final String TOTAL_SAVINGS_LOCAL = FORMATTER.format(Integer.parseInt(TOTAL_SAVINGS));
        final String VALUE_PER_DAY_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY)));
        final String CURRENCY_LOCAL = preferences.getString("currency", "$");
        //target counter string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = stringBuilder.append(getString(R.string.perday)).append(" ").append(VALUE_PER_DAY_LOCAL).append(" ").append(CURRENCY_LOCAL).append("\n")
                .append(getString(R.string.savings, TOTAL_SAVINGS_LOCAL)).append(" ").append(CURRENCY_LOCAL).append("\n")
                .append(getString(R.string.peryear)).append(" ").append(VALUE_PER_YEAR_LOCAL).append(" ").append(CURRENCY_LOCAL);
        TEXTVIEW.setText(stringBuilder);
    }

    private void setTxtViewForUserMaxCountDaysOnStringVersion(
            final String STRING,
            final TextView TEXTVIEW) {
        //target counter string
        TEXTVIEW.setText(getString(R.string.target_string, STRING));
    }

    private void savingsGetAndSetValue() {
        try {
            if (preferences.contains(SAVINGS_FINAL)) {
                savings = preferences.getLong(SAVINGS_FINAL, 1);
            } else {
                savings = ZERO;
                editor.putLong(SAVINGS_FINAL, savings);
                editor.apply();
            }
            firstSave = preferences.contains(FIRSTSAVE) ? preferences.getLong(FIRSTSAVE, ZERO) : ZERO;
            setTxtViewForUserSavings(firstSave,
                    String.valueOf(savings), moneySavingsTxt);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @SideEffect
    private void setTargetDays() {
        try {
            //format string of MAX target txt view
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, ZERO);
            }
            if (counter == ZERO) {
                textNonSmoker.setText(R.string.calculating);
            } else {
                textNonSmoker.setText(R.string.non_smoker_since);
            }
            if (counter >= NINETY*THREE) {
                userMaxCountForHabit = NINETY*FOUR;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY*TWO) {
                userMaxCountForHabit = NINETY*THREE;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY) {
                userMaxCountForHabit = NINETY*TWO;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= SIXTY) {
                userMaxCountForHabit = NINETY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= THIRTY) {
                userMaxCountForHabit = SIXTY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else {
                userMaxCountForHabit = THIRTY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            }
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
            setTxtViewForUserMaxCountDaysOnStringVersion(String.valueOf(userMaxCountForHabit),
                    targetTxtViewId);
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
//            int maxCount = preferences.getInt(getString(R.string.maxCounter), -1);
            int maxCount = Integer.parseInt(CALC_DAYS_TARGET);
            //max 30 always
            Log.d("dayzen", ""+maxCount);
            counterImgView.setProgress((30-maxCount)*3);
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
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
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
            checkInsteadOfSmoking();
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
            DAY_OF_CLICK = preferences.getInt(CLICKDAY_SP, ZERO);
            buttonClickedToday = preferences.getBoolean(CLICKED, false);
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
                HOUR_OF_FIRSTLAUNCH = preferences.getInt(HOUR_OF_FIRSLAUNCH_SP, ZERO);
            }
            //if only one day passed
            if (DAY_OF_PRESENT == DAY_OF_CLICK+1) {
                if (HOUR_OF_FIRSTLAUNCH > HOUR_OF_DAYLIGHT) {
                    //temporarily integer
                    final int HOURS_UNTILL_CHECK_IN = HOUR_OF_FIRSTLAUNCH - HOUR_OF_DAYLIGHT;
                    final String CHECK_IN_TEXT = getResources().getString(R.string.check_in) +
                            ": " + HOURS_UNTILL_CHECK_IN + " "
                            + getResources().getString(R.string.hours);
                    checkInText.setText(CHECK_IN_TEXT);
                    //if user passed one day && hour is passed or equal to hour of first launch
                } else {
                    checkInText.setText(getResources().getString(R.string.check_in_now));
                }
                //if more than one day passed
            } else if (DAY_OF_PRESENT >= DAY_OF_CLICK+2) {
                checkInText.setText(getResources().getString(R.string.check_in_now));
            } else {
                checkInText.setText(getResources().getString(R.string.check_in_tomorrow));
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
            checkActivityOnline();
            counterText.setText(String.valueOf(counter));
            setCheckInText();
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
            if (task != null && !task.isCancelled()) { task.cancel(true); }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//[END of ONDESTROY]

    private void updateButton() {
        if (buttonClickedToday) {
            checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
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
            checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
            //when days are the same and user already clicked
        } else if (DAY_OF_PRESENT == DAY_OF_CLICK && buttonClickedToday) {
            checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
        }
    }

    private int returnLeapForClickYear() {
        final int defaultValue = DEFAULT_NEGATIVE_VALUE;
        //using try catch due to preferences
        try {
            final int lastDayClick = preferences.getInt(CLICKDAY_SP, defaultValue);
            final int yearOfNow = Calendar.getInstance().get(Calendar.YEAR);
            final int x = isLeap(yearOfNow) ? LEAP_YEAR_DAYS : NORMAL_YEAR_DAYS;
            return x - lastDayClick;
        } catch (final Exception e) {
            e.printStackTrace();
            //will never happen'
            return defaultValue;
        }
    }

    private boolean isLeap(final int year){
        return ((year % 4 == ZERO && year % 100 != ZERO) || year % 400 == ZERO);
    }

    //[ENABLE BUTTON]
    @SideEffect
    private void greenCondition() {
        try {
            HOUR_OF_DAYLIGHT = HOUR_OF_DAYLIGHT == ZERO ? TWENTYFOUR : HOUR_OF_DAYLIGHT;
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, ZERO);
            }
            if (Calendar.getInstance().get(Calendar.YEAR) != preferences.getInt(YEARLEAP, 2019)) {
                DAY_OF_CLICK = returnLeapForClickYear();
                DAY_OF_CLICK *= -1;
                editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);editor.apply();
                editor.putInt(YEARLEAP, Calendar.getInstance().get(Calendar.YEAR));editor.apply();
            }
            Log.d(DAYZEN, "DAY OF CLICK " + DAY_OF_CLICK
                    + " DAY OF PRESENT " + DAY_OF_PRESENT + "\n" +
                    " HOUR_OF_FIRSTLAUNCH " + HOUR_OF_FIRSTLAUNCH + " HOUR_OF_DAYLIGHT " + HOUR_OF_DAYLIGHT);
            //if user started at hour of 00 or 23 at night and hour of now is 00 then we make hour of now equal
            //to 24 to be able to make the calculation
            //else we calculate only hour of now to be equal to 24 if hour of daylight is 00
            HOUR_OF_DAYLIGHT = HOUR_OF_DAYLIGHT == ZERO ? TWENTYFOUR : HOUR_OF_DAYLIGHT;
            if (DAY_OF_PRESENT > DAY_OF_CLICK && (DAY_OF_PRESENT == DAY_OF_CLICK+ONE)) {
                if (HOUR_OF_FIRSTLAUNCH <= HOUR_OF_DAYLIGHT) {
                    checkInButton.setBackground(getDrawable(R.drawable.custom_round_primary_color));
                    setCheckInText();
                } else {
                    setCheckInText();
                    checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
                }
            } else if (DAY_OF_PRESENT > DAY_OF_CLICK+1) {
                higherThanOne = true;
                final int DIFF_LOCAL = DAY_OF_PRESENT - DAY_OF_CLICK;
                editor.putInt("diff", DIFF_LOCAL);
                editor.apply();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_primary_color));
                setCheckInText();
            } else {
                setCheckInText();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }//END OF -> [ENABLE BUTTON]

    @SideEffect
    private void setImprovementProgressLevels() {
        try {
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, ZERO);
            }
            //energy levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForEnergyLevels.setText(FIVE + "%");
                progressBarEnergyLevel.setProgress(FIVE);
            } else if (counter > THIRTY-1 && counter < SIXTY) {
                txtProgressForEnergyLevels.setText(FIFTEEN + "%");
                progressBarEnergyLevel.setProgress(FIFTEEN);
            } else if (counter > SIXTY-1 && counter < NINETY) {
                txtProgressForEnergyLevels.setText(THIRTY + "%");
                progressBarEnergyLevel.setProgress(THIRTY);
            } else if (counter > NINETY-1 && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForEnergyLevels.setText(FIFTY + "%");
                progressBarEnergyLevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-1 && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForEnergyLevels.setText(SEVENTY_FIVE + "%");
                progressBarEnergyLevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForEnergyLevels.setText(ONE_HUNDRED + "%");
                progressBarEnergyLevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForEnergyLevels.setText(TWO + "%");
                progressBarEnergyLevel.setProgress(TWO);
            }

            //fatigue levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForFatigue.setText(TEN + "%");
                progressBarFatigueLevel.setProgress(TEN);
            } else if (counter > THIRTY-ONE && counter < SIXTY) {
                txtProgressForFatigue.setText(FIFTEEN + "%");
                progressBarFatigueLevel.setProgress(FIFTEEN);
            } else if (counter > SIXTY-ONE && counter < NINETY) {
                txtProgressForFatigue.setText(THIRTY + "%");
                progressBarFatigueLevel.setProgress(THIRTY);
            } else if (counter > NINETY-ONE && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForFatigue.setText(FIFTY + "%");
                progressBarFatigueLevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-ONE && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForFatigue.setText(SEVENTY_FIVE + "%");
                progressBarFatigueLevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForFatigue.setText(ONE_HUNDRED + "%");
                progressBarFatigueLevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForFatigue.setText(FIVE + "%");
                progressBarFatigueLevel.setProgress(FIVE);
            }

            //gums levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForGums.setText(THREE + "%");
                progressBarGumsLevel.setProgress(THREE);
            } else if (counter > THIRTY-ONE && counter < SIXTY) {
                txtProgressForGums.setText(TWENTY + "%");
                progressBarGumsLevel.setProgress(TWENTY);
            } else if (counter > SIXTY-ONE && counter < NINETY) {
                txtProgressForGums.setText(THIRTY + "%");
                progressBarGumsLevel.setProgress(THIRTY);
            } else if (counter > NINETY-ONE && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForGums.setText(FIFTY + "%");
                progressBarGumsLevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-ONE && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForGums.setText(SEVENTY_FIVE + "%");
                progressBarGumsLevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForGums.setText(ONE_HUNDRED + "%");
                progressBarGumsLevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForGums.setText(ONE + "%");
                progressBarGumsLevel.setProgress(ONE);
            }

            //breath levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForBreath.setText(THREE + "%");
                progressBarBreathlevel.setProgress(THREE);
            } else if (counter > THIRTY-ONE && counter < SIXTY) {
                txtProgressForBreath.setText(TWENTY + "%");
                progressBarBreathlevel.setProgress(TWENTY);
            } else if (counter > SIXTY-ONE && counter < NINETY) {
                txtProgressForBreath.setText(THIRTY + "%");
                progressBarBreathlevel.setProgress(THIRTY);
            } else if (counter > NINETY-ONE && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForBreath.setText(FIFTY + "%");
                progressBarBreathlevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-ONE && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForBreath.setText(SEVENTY_FIVE + "%");
                progressBarBreathlevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForBreath.setText(ONE_HUNDRED + "%");
                progressBarBreathlevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForBreath.setText(TWO + "%");
                progressBarBreathlevel.setProgress(TWO);
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
        View view = findViewById(ID_LOCAL);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Settings area", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        if (ID_LOCAL == R.id.options) {
            final Intent INTENT = new Intent(MainActivity.this, OptionsActivity.class);
            startActivity(INTENT);
            return true;
        }
//        if (ID_LOCAL == R.id.action_settings) {
//            counter++;
//            final Calendar CALENDAR_LOCAL = Calendar.getInstance();
//            final int GET_MINUTE = CALENDAR_LOCAL.get(Calendar.MINUTE);
////            int zet = preferences.getInt(CLICKDAY_SP, ZERO);
//            editor.putInt(CLICKDAY_SP, GET_MINUTE);
//            counterText.setText(String.valueOf(counter));
//            editor.putInt(COUNTER, counter);
//            editor.apply();
//            setImprovementProgressLevels();
//            return true;
//        } else if (ID_LOCAL == R.id.action_about) {
//            final Intent INTENT = new Intent(MainActivity.this, AboutActivity.class);
//            startActivity(INTENT);
//            return true;
//        } else if (ID_LOCAL == R.id.action_help) {
//            counter = EIGHTEEN;
//            counterText.setText(String.valueOf(counter));
//            editor.putInt(COUNTER, counter);
//            editor.apply();
//        } else if (ID_LOCAL == R.id.check_in_hour) {
//            //do nothing for now
//            //TODO: think about we want to let user change the check in hour or not
////            showDialogForChangingCheckInDate();
//        } else if (ID_LOCAL == R.id.reset_button){
//            dialogForReset();
//            return true;
//        } else if (ID_LOCAL == R.id.legal) {
////            final Intent INTENT = new Intent(MainActivity.this, OptionsActivity.class);
////            startActivity(INTENT);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }//END OF -> [MENU]

    //hide the rest of the menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        menu.findItem(R.id.check_in_hour).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
//        menu.findItem(R.id.action_help).setVisible(false);
        menu.findItem(R.id.options).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        return true;
    }

    @SideEffect
    protected boolean isOnline() {
//        final ConnectivityManager connManager =
////                (ConnectivityManager)
////                        getSystemService(Context.CONNECTIVITY_SERVICE);
////        final NetworkInfo networkInfo =
////                Objects.requireNonNull(connManager).getActiveNetworkInfo();
////        return networkInfo != null && networkInfo.isConnected();
        boolean connected;
        connected = NetworkUtils.isConnected(getApplicationContext());
        return connected;
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
                    counter = preferences.getInt(COUNTER, ZERO);
                }
                if (counter == ZERO) {
                    requestDataById(1);
                } else {
                    requestDataById(counter);
                }
                //requestDataById(DAY_OF_PRESENT);
            } else {
                if (counter == ZERO) {
                    requestDataById(1);
                } else {
                    requestDataById(counter);
                }
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
        tipOfTheDayTxtView.setText(MESSAGE + "\n");
    }


    @SuppressLint("StaticFieldLeak")
    public class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            try {
                counter = preferences.getInt(COUNTER, counter);
                if (!preferences.contains(COUNTER)){counterText.setText(ZERO);}else {
                    counterText.setText(String.valueOf(counter));
                }
                updateDisplayString("Trying to get some quotes ...");
                if (tasks.size() == ZERO) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                    progressBarLoading2.setVisibility(View.VISIBLE);
//                    counterImgView.setVisibility(View.INVISIBLE);
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
                //String content = MyHttpManager.getData(params[ZERO]);
                //Thread.sleep(1000);
                //using MyHttpCoreAndroid
                final String CONTENT_LOCAL = MyHttpCoreAndroid.getData(PARAMS_LOCAL[ZERO]);
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
                Log.d(TAG, "lang is: " + PUT_LANGUAGE_IN_STRING);
                //get quote from ro if user is ro, else get default quotes
                NAME_ELEMENT_NODE = (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(RO))
                        ? DETAILS_LOCAL.get(NAME_RO) : DETAILS_LOCAL.get(NAME);

                return NAME_ELEMENT_NODE.getAsString();
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]
        @Override
        protected void onPostExecute(final String RESULT) {
            try {
                final String tempResult = (RESULT == null) ?
                        "No quotes available in this moment ..." : RESULT;
                //using raw JSON PARSER
                updateDisplayString(tempResult);
                //we get rid of the task that we created
                tasks.remove(this);
                if (tasks.size() == ZERO) {
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
                //var 2 to solve null result(quotes)
            } finally {
                final String tempResult = (RESULT == null) ? NO_QUOTES_AVAILABLE_IN_THIS_MOMENT : RESULT;
                updateDisplayString(tempResult);
            }
        }//onPostExecute[END]
        @Override
        protected void onProgressUpdate(final String... VALUES) {
            updateDisplayString(VALUES[ZERO]);
        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

    @SideEffect
    private void setImagesForAchievementCard() {
        try {
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, ZERO);
            }
            if (counter>=ZERO&&counter<EIGHT) {
                //user have between a day and a week
                rankOneImg.setBackgroundResource(R.drawable.ic_oneday);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_oneweek);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_onemonth);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Wolf");
            } else if (counter>(EIGHT -1)&&counter<THIRTY) {
                rankOneImg.setBackgroundResource(R.drawable.ic_oneday);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_oneweek);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_onemonth);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Squirrel");
            } else if (counter>(THIRTY-1)&&counter<SIXTY) {
                rankOneImg.setBackgroundResource(R.drawable.ic_oneweek);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_onemonth);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_twomonths);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Badger");
            } else if (counter>(SIXTY-1)&&counter<NINETY) {
                rankOneImg.setBackgroundResource(R.drawable.ic_oneweek);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_onemonth);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_twomonths);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Bear");
            } else if (counter>(NINETY-1)&&counter<ONE_HUNDRED+20) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_threemonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourmonths);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivemonths);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Wild Bear");
            } else if (counter>(ONE_HUNDRED+19)&&counter<ONE_HUNDRED_AND_EIGHTY-30) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_threemonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourmonths);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivemonths);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Deer");
            } else if (counter>(ONE_HUNDRED_AND_EIGHTY-31)&&counter<ONE_HUNDRED_AND_EIGHTY) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_threemonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourmonths);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivemonths);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Boar");
            } else if (counter>(ONE_HUNDRED_AND_EIGHTY-1)&&counter<TWO_HUNDRED+10) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_sixmonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenmonths);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_ninemonths);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Elk");
            } else if (counter> (TWO_HUNDRED+9)&&counter<TWO_HUNDRED_AND_SEVENTY) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_sixmonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenmonths);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_ninemonths);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Beaver");
            } else if (counter>(TWO_HUNDRED_AND_SEVENTY-1)&&counter< THREE_HUNDRED) {
                //when user pass 1 week
                rankOneImg.setBackgroundResource(R.drawable.ic_sixmonths);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenmonths);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_ninemonths);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Fox");
                //WHEN USER REACH DAY 90 - GREATEST MILESTONE
            } else if (counter >= THREE_HUNDRED) {
                rankThreeImg.setBackgroundResource(R.drawable.ic_threehundreddays);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Lynx");
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
                    public void onClick(@NonNull BottomDialog dialog) {
                        Editable editM = ET_FOR_CHOOSING_SAVINGS.getText();
                        if (TextUtils.isEmpty(editM)){
                            ET_FOR_CHOOSING_SAVINGS.setError("Please input numbers!");
                            return;
                        }
                        HOUR_OF_FIRSTLAUNCH = Integer.parseInt(editM.toString());
                        editor.putInt(HOUR_OF_FIRSLAUNCH_SP, HOUR_OF_FIRSTLAUNCH);
                        editor.apply();
                    }
                }).show();
    }

    @SideEffect
    private void showEntireProgressForUserCard(final TextView TV_USER_CIGARETTES_PROGRESS,
                                               final TextView TV_HIGHEST_STREAK,
                                               final TextView TV_HOURS_PROGRESS) {
        try {
            YourQDay.setText(getResources().getString(
                    R.string.qday,preferences.getString("qday", "none")));
            //getting the highest streak and put it in progress card
            final int HIGHEST_STREAK;
            if (preferences.contains(HIGHEST)){
                HIGHEST_STREAK = preferences.getInt(HIGHEST, ZERO);
            } else {
                HIGHEST_STREAK = preferences.getInt(COUNTER, ZERO);
            }
            final String TEMP_STREAK = getString(R.string.streak_string, HIGHEST_STREAK);
            TV_HIGHEST_STREAK.setText(TEMP_STREAK);
            //getting highest rank and put it into ranks card
            final String LAST_STREAK = preferences.getString(RANK, UNRANKED);
            final String RANK_LOCAL = getString(R.string.rank_master, LAST_STREAK);
            rankMasterTxt.setText(RANK_LOCAL);

            if (counter == ZERO) {
                TV_USER_CIGARETTES_PROGRESS.setText(R.string.cig_press_leaf);
                final String PRESS_LEAF = getString(R.string.life_press_leaf);
                TV_HOURS_PROGRESS.setText(PRESS_LEAF);
            } else {
                if (preferences.contains(LIFEREGAINED)) {
                    lifeRegained = preferences.getFloat(LIFEREGAINED, -1);
                    numberFormat.format(lifeRegained);
                } else {
                    lifeRegained = Float.valueOf((5f * Float.valueOf(preferences.getInt(MODIFIED_CIGG_PER_DAY, ZERO))) / 60f);
                    numberFormat.format(lifeRegained);
                }
                final String CIGARETTES_NOT_SMOKED_STRING = getString(R.string.cig_not_smoked);
                final String LIFE_REGAINED_STRING = getString(R.string.life_reg, numberFormat.format(lifeRegained));
                TV_USER_CIGARETTES_PROGRESS.setText(CIGARETTES_NOT_SMOKED_STRING +" " + preferences.getInt(MODIFIED_CIGG_PER_DAY, ZERO));
                TV_HOURS_PROGRESS.setText(LIFE_REGAINED_STRING);
                editor.putFloat(LIFEREGAINED, lifeRegained);
                editor.apply();
            }
        } catch(final Exception e){ e.printStackTrace(); }
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
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        resetWholeProgress();
                    }
                }).show();
    }
    private void insteadOfSmoking() {
        Random random = new Random();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> icons = new ArrayList<>();
        titles.add("Learn a new language!");
        icons.add(R.drawable.ic_translate);
        titles.add("Read a new book!");
        icons.add(R.drawable.ic_book);
        titles.add("Go to the gym and work out!");
        icons.add(R.drawable.ic_dumbbell);
        titles.add("Try a new cooking recipe!");
        icons.add(R.drawable.ic_recipe_book);
        titles.add("Take a day trip!");
        icons.add(R.drawable.ic_car);
        titles.add("Practice meditation!");
        icons.add(R.drawable.ic_yoga_posture);

        int today = random.nextInt(titles.size());
        new BottomDialog.Builder(this)
                .setTitle(titles.get(today))
                .setPositiveText(R.string.OK)
                .setIcon(icons.get(today))
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {

                    }
                }).show();
    }

    private void checkInNotAvailable() {
        String x = checkInText.getText().toString().equalsIgnoreCase(
                getResources().getString(R.string.check_in_tomorrow)
        ) ? getResources().getString(R.string.please_check_tomorrow) :
                getResources().getString(R.string.cannot_check_in);
        new BottomDialog.Builder(this)
                .setTitle(x)
                .setPositiveText(R.string.OK)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .show();
    }

    private void dialogForResetForced(){
        new BottomDialog.Builder(this)
                .setTitle("You are now one year recovered!")
                .setContent("Congratulations!!! Go for another year ?")
                .setPositiveText(R.string.OK)
                .setNegativeText(R.string.NO)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        clearAppData();
                    }
                })
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        resetWholeProgress();
                    }
                }).show();
    }
    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                // note: it has a return value!
                ((ActivityManager) Objects.requireNonNull(getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("Your progress has been reset! "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setTxtVwToOffer(){
        final String CURRENCY_LOCAL = preferences.getString("currency", "$");
        if (CURRENCY_LOCAL.equalsIgnoreCase("ron")){
            //RON
            return getResources().getString(R.string.unlimited_price_ron, CURRENCY_LOCAL);
        } else {
            //USD OR EUR
            return getResources().getString(R.string.unlimited_price, CURRENCY_LOCAL);
        }
//        return getResources().getString(R.string.unlimited_price, CURRENCY_LOCAL);
    }

}//[END OF MAIN CLASS]
//
////    private boolean isLeap(final int year){
////        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
////    }
//
////milestone dialog ------------
////        AlertDialog.Builder milestoneAlert = new AlertDialog.Builder(this);
////        final EditText editTextForMilestone = new EditText(MainActivity.this);
////        milestoneAlert.setMessage("Set your milestone ?");
////        milestoneAlert.setTitle("Milestone!");
////        milestoneAlert.setView(editTextForMilestone);
////        milestoneAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int whichButton) {
////                //What ever you want to do with the value
//////                Editable milestone = editTextForMilestone.getText();
////                String getMilestone = editTextForMilestone.getText().toString();
////                userMaxCount = Integer.parseInt(getMilestone);
////
////            }
////        });
////        milestoneAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int whichButton) {
////                //do nothing
////            }
////        });
////        milestoneAlert.show();
//
////choose your habit ------------
////        AlertDialog.Builder habitAlert = new AlertDialog.Builder(this);
////        final EditText editTextForChoosingHabit = new EditText(MainActivity.this);
////        habitAlert.setMessage("Type your habit ?");
////        habitAlert.setTitle("Habit!");
////        habitAlert.setView(editTextForChoosingHabit);
////        habitAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int whichButton) {
////                //What ever you want to do with the value
//////                Editable habit = editTextForChoosingHabit.getText();
////                habitString = editTextForChoosingHabit.getText().toString();
////
////            }
////        });
////        habitAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int whichButton) {
////                //do nothing
////            }
////        });
////        habitAlert.show();
//
////        //dialog ------------
////        new BottomDialog.Builder(this)
////                .setTitle("Awesome!")
////                .setContent("What can we improve? Your feedback is always welcome.")
////                .setPositiveText("OK")
////                .setPositiveBackgroundColorResource(R.color.colorPrimary)
////                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
////                .setPositiveTextColorResource(android.R.color.white)
////                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
////                .onPositive(new BottomDialog.ButtonCallback() {
////                    @Override
////                    public void onClick(BottomDialog dialog) {
////                        Log.d("BottomDialogs", "Do something!");
////                    }
////                }).show();

////    //dialog when user pass a day
////    private void showCustomDialogOnFirstLaunch(String title, StringBuilder content){
////        //dialog ------------
////        new BottomDialog.Builder(this)
////                .setTitle(title)
////                .setContent(content)
////                .setPositiveText("OK")
////                .setCancelable(false)
////                .setPositiveBackgroundColorResource(R.color.colorPrimary)
////                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
////                .setPositiveTextColorResource(android.R.color.white)
////                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
////                .onPositive(new BottomDialog.ButtonCallback() {
////                    @Override
////                    public void onClick(BottomDialog dialog) {
////                        Log.d("BottomDialogs", "Do something!");
////                        //intro activity check in a separate thread
//////                        startIntroActivity();
//////                        showDialogForSavingSum();
////                    }
////                }).show();
////    }