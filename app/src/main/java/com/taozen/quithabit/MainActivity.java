package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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

import org.w3c.dom.Text;

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
import static com.taozen.quithabit.utils.Constants.SharedPreferences.LANGUAGE_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.LIFEREGAINED;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.MODIFIED_CIGG_PER_DAY;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;

public class MainActivity extends AppCompatActivity {

    public static final int FIVE_HUNDRED = 500;
    private static final String TAG = MainActivity.class.getSimpleName() + "TAOMAO";
    public static final int ONE_THOUSAND = 1000;
    public static final int DEFAULT_NEGATIVE_VALUE = -1000;
    public static final int LEAP_YEAR_DAYS = 365;
    public static final int NORMAL_YEAR_DAYS = 364;
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
    public static final int FOUR_HUNDRED = 400;
    public static final int SIX_HUNDRED = 600;
    public static final int SEVEN_HUNDRED = 700;
    public static final int EIGHT_HUNDRED = 800;
    public static final int NINE_HUNDRED = 900;
    public static final String CRAVINGTITLE = "cravingtitle";
    public static final String CRAVINGTEXT = "cravingtext";

    String[] monthName = {"Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"};

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

    //imageviews
    @BindView(R.id.shareDays) ImageView shareDays;
    @BindView(R.id.shareProgress) ImageView shareProgress;
    @BindView(R.id.shareAchieve) ImageView shareAchieve;
    @BindView(R.id.shareSavings) ImageView shareSavings;
    @BindView(R.id.sunny) ImageView sunny;
    @BindView(R.id.appCompatImageViewSavings_top) ImageView refreshCravingText;

    //Views
    @BindView(android.R.id.content) View parentLayout;
//    @BindView(R.id.InsteadOfLayout) ConstraintLayout insteadOfLayout;
    @BindView(R.id.layoutCheckIn) ConstraintLayout checkInButton;

    //CardViews
    @BindView(R.id.card_view_Bottom) MaterialCardView progressCardView;
    @BindView(R.id.card_view_Bottom_Saving) MaterialCardView savingsCardView;
    @BindView(R.id.card_view_mainID) MaterialCardView cardViewMain;
    @BindView(R.id.card_view_Bottom_Achievments) MaterialCardView achievementRanksCard;
    @BindView(R.id.card_view_Middle) MaterialCardView upperProgressPercentsCard;

    //TextViews
    @BindView(R.id.totalSavingsTopIdBottom) MaterialTextView cravingTitleText;
    @BindView(R.id.savingsTopIdBottom) MaterialTextView cravingTipsText;
    @BindView(R.id.savingsTopId) MaterialTextView savingsTopText;
//    @BindView(R.id.QuitDateTopId) MaterialTextView quitDateTopText;
    @BindView(R.id.YourQDay) TextView YourQDay;
    @BindView(R.id.YourQDay2) TextView YourQDay2;
    @BindView(R.id.YourProgressIdCigaretes) TextView userCigaretesProgressTxt;
    @BindView(R.id.YourProgressIdCigaretes2) TextView userCigaretesProgressTxt2;
    @BindView(R.id.YourHighestStreakId) TextView userHighestStreakTxt;
    @BindView(R.id.YourHighestStreakId2) TextView userHighestStreakTxt2;
    @BindView(R.id.YourProgressIdHours) TextView userHoursProgressTxt;
    @BindView(R.id.YourProgressIdHours2) TextView userHoursProgressTxt2;
    @BindView(R.id.exploreAchievementId) MaterialTextView TvExploreAchievement;
    @BindView(R.id.exploreSavingsId) MaterialTextView TvExploreSavings;
//    @BindView(R.id.rank_master) TextView rankMasterTxt;
    @BindView(R.id.counterTextId) MaterialTextView counterText;
    @BindView(R.id.txtProgressIdForBlood) TextView txtProgressForBlood;
    @BindView(R.id.txtProgressIdForBreath) TextView txtProgressForBreath;
    @BindView(R.id.txtProgressIdForHeart) TextView txtProgressForHeart;
    @BindView(R.id.txtProgressIdForEnergy) TextView txtProgressForEnergyLevels;
    @BindView(R.id.subTextEnergyId) TextView subTextEnergy;
    @BindView(R.id.subTextBreathId) TextView subTextBreath;
    @BindView(R.id.subTextHeartId) TextView subTextHeart;
    @BindView(R.id.subTextBloodId) TextView subTextBlood;

    @BindView(R.id.targetTxtViewId) TextView targetTxtViewId;
    @BindView(R.id.savingsTxtIdPerDay) TextView moneySavingsTxtPerDay;
    @BindView(R.id.savingsTxtIdPerDay2) TextView moneySavingsTxtPerDayResult;
    @BindView(R.id.savingsTxtIdPerTotal) TextView moneySavingsTxtPerTotal;
    @BindView(R.id.savingsTxtIdPerTotal2) TextView moneySavingsTxtPerTotalResult;
    @BindView(R.id.savingsTxtIdPerYear) TextView moneySavingsTxtPerYear;
    @BindView(R.id.savingsTxtIdPerYear2) TextView moneySavingsTxtPerYearResult;

    @BindView(R.id.remaining_days_Id) TextView remainingDaysTxt;
    @BindView(R.id.tvTipOfTheDay) TextView tipOfTheDayTxtView;
    @BindView(R.id.textNonSmokerId) TextView textNonSmoker;
    @BindView(R.id.subTextCheckInId) TextView checkInText;
    @BindView(R.id.textViewAchievTitle) TextView yourAchievmentTxtTitle;
    @BindView(R.id.textViewProgressTitle) TextView yourProgressTxtTitle;
    @BindView(R.id.textViewSavingsTitle) TextView yourSavingsTxtTitle;
    //ProgressBar
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.loadingProgressIdReplacement) ProgressBar progressBarLoading2;
    //ImageViews
//    @BindView(R.id.counterImageId) ImageView counterImgViewProgressbar;
    @BindView(R.id.rankOneId) ImageView rankOneImg;
    @BindView(R.id.rankTwoId) ImageView rankTwoImg;
    @BindView(R.id.rankThreeId) ImageView rankThreeImg;
    //CircularProgressBar
    //progress for percent - this is a circular bar
    @BindView(R.id.counterImageId) CircularProgressBar counterImgViewProgressbar;
    @BindView(R.id.progress_bar_energy) CircularProgressBar progressBarEnergyLevel;
    @BindView(R.id.progress_bar_blood) CircularProgressBar progressBarBloodLevel;
    @BindView(R.id.progress_bar_heart) CircularProgressBar progressBarHeartLevel;
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
        //TODO: add more strings/quotes
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
            if (BuildConfig.DEBUG){
            Log.d(DAYZEN, "onCreate");
            }
            //shared pref
            preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            editor = preferences.edit();
            setContentView(R.layout.activity_main);
            ButterKnife.bind(MainActivity.this);
            setBackgroundForDaylightOrNight();

            //strictmode ?
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, -1);
            }

            setCounterImageDaysOrDay(counter);

            counterImgViewProgressbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkInButton.callOnClick();
                }
            });

//            generateCravingText();

            refreshCravingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateCravingText();
                }
            });
            if (preferences.contains(CRAVINGTEXT)) {
                cravingTipsText.setText(preferences.getString(CRAVINGTEXT, getString(R.string.example_instead_of_text)));
                cravingTitleText.setText(preferences.getString(CRAVINGTITLE, getString(R.string.instead_of_smoking)));
            }

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
//                Bitmap b = Screenshoot.takescreenshot(counterImgViewProgressbar);
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
                        e.printStackTrace();
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
                        }
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
            checkActivityFromSplashScreen();


            numberFormat = new DecimalFormat("#.##");

            //testing area
            final Date DATE = new Date();
            final Calendar CALENDAR = GregorianCalendar.getInstance();
            CALENDAR.setTime(DATE);
            //if user started at hour of 00 or 23 at night and hour of now is 00 then we make hour of now equal
            //to 24 to be able to make the calculation
            //else we calculate only hour of now to be equal to 24 if hour of daylight is 00
            HOUR_OF_DAYLIGHT = CALENDAR.get(Calendar.HOUR_OF_DAY);
//            HOUR_OF_DAYLIGHT = HOUR_OF_DAYLIGHT == ZERO ? TWENTYFOUR : HOUR_OF_DAYLIGHT;
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
            Objects.requireNonNull(actionBar).setTitle("Q! NoSmoke");
            Objects.requireNonNull(actionBar).
                    setBackgroundDrawable(
                            new ColorDrawable(
                                    ContextCompat.getColor(
                                            getApplicationContext(), R.color.grey_100)));
            TextView textView = new TextView(getApplicationContext());
            textView.setText(getResources().getString(R.string.progress));
            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.robotoblack));
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            textView.setTextSize(28f);
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(textView);

//            actionBar.setElevation(0.2f);
            actionBar.setElevation(0f);
//            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.actionbar_round));

//        tipOfTheDayTxtView.setTypeface(montSerratSemiBoldItalicTypeface);
//        TvExploreAchievement.setTypeface(montSerratMediumTypeface);
//        TvExploreSavings.setTypeface(montSerratMediumTypeface);
//            counterText.setTypeface(montSerratSimpleBoldTypeface);
//        remainingDaysTxt.setTypeface(montSerratSimpleBoldTypeface);
//        targetTxtViewId.setTypeface(montSerratSimpleBoldTypeface);
//        txtProgressForEnergyLevels.setTypeface(montSerratBoldTypeface);
//        txtProgressForHeart.setTypeface(montSerratBoldTypeface);
//        txtProgressForBreath.setTypeface(montSerratBoldTypeface);
//        txtProgressForBlood.setTypeface(montSerratBoldTypeface);
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
            showEntireProgressForUserCard(userCigaretesProgressTxt,
                    userCigaretesProgressTxt2,
                    userHighestStreakTxt,
                    userHighestStreakTxt2,
                    userHoursProgressTxt,
                    userHoursProgressTxt2);
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

//            checkInsteadOfSmoking();

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

            //updating textviews
            showEntireProgressForUserCard(userCigaretesProgressTxt,
                    userCigaretesProgressTxt2,
                    userHighestStreakTxt,
                    userHighestStreakTxt2,
                    userHoursProgressTxt,
                    userHoursProgressTxt2);
            setTextViewsForProgress();

            //using "final rethrow" by not specifying throwing a specific exception like NullPointer
            //the final keyword is optional, but in practice, we've found that it helps to use it while
            //adjusting to the new semantics of catch and rethrow
        } catch (final Exception e) {
            e.printStackTrace();
        }//[END OF RETRIEVING VALUES]
    }//[END OF ONCREATE]

    private void checkActivityFromSplashScreen() {
        //using API from splash
        final Intent INTENT = getIntent();
        final String NAME = INTENT.getStringExtra("data");
        if (NAME!=null){
            tipOfTheDayTxtView.setText(NAME);
            editor.putString("tipoftheday", NAME);
            editor.apply();
        } else {
            String example = getString(R.string.example_of_tip_of_the_day);
            //if text cannot be get from server we put R.string.example_of_tip_of_the_day
//                checkActivityOnline();
            preferences.getString("tipoftheday", example);
        }
    }

    //    not using it now
    private void setCounterImageDaysOrDay(int counterInner) {
        try {
//            final Locale localeMAIN;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                localeMAIN = getResources().getConfiguration().getLocales().get(0);
//            } else {
//                localeMAIN = getResources().getConfiguration().locale;
//            }
//            //get initials like: RO/US/EN/FR
//            final String PUT_LANGUAGE_IN_STRING_ON_CREATE = localeMAIN.getLanguage();
            if (counterInner < 2) {
//                final Drawable ro_or_eng_for_first_day =
//                        (PUT_LANGUAGE_IN_STRING_ON_CREATE.equalsIgnoreCase(RO))
//                                ? ContextCompat.getDrawable(
//                                getApplicationContext(),
//                                R.drawable.counterimg_ro)
//                                : ContextCompat.getDrawable(getApplicationContext(),
//                                R.drawable.counterimgone);
                textNonSmoker.setText(getString(R.string.non_smoker_sincer_one_day));
                textNonSmoker.setTextSize(28f);
            } else {
//                final Drawable ro_or_eng_for_first_day =
//                        (PUT_LANGUAGE_IN_STRING_ON_CREATE.equalsIgnoreCase(RO))
//                                ? ContextCompat.getDrawable(
//                                getApplicationContext(),
//                                R.drawable.counterimg_ro)
//                                : ContextCompat.getDrawable(getApplicationContext(),
//                                R.drawable.counterimg);
                textNonSmoker.setText(getString(R.string.non_smoker_since));
                textNonSmoker.setTextSize(36f);
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
        if (BuildConfig.DEBUG) {
            Log.d("DAYZEN", "widht: " + DENSITY_WEIGHT + " height: " + DENSITY_HEIGHT + DENSITY);
        }
    }

    private void checkInsteadOfSmoking() {
//        insteadOfLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                insteadOfSmoking();
//                insteadOfLayout.setEnabled(false);
//                insteadOfLayout.setBackgroundColor(Color.WHITE);
//            }
//        });
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
            HOUR_OF_FIRSTLAUNCH = CALENDAR.get(Calendar.HOUR_OF_DAY);
//            HOUR_OF_FIRSTLAUNCH = (CALENDAR.get(Calendar.HOUR_OF_DAY)==ZERO) ? 24 : CALENDAR.get(Calendar.HOUR_OF_DAY);
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
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        //change wallpaper during nighttime
//        if (HOUR_OF_DAYLIGHT >= 21 || HOUR_OF_DAYLIGHT < 7) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "hour of daylight " + h);
        }
        if (h >= 6 && h <= 18) {
            sunny.setImageResource(R.drawable.ic_sunlast);
        } else {
            sunny.setImageResource(R.drawable.ic_bat);
        }
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
        if (BuildConfig.DEBUG) {
            Log.d("QUOTE", " " + DAILY_QUOTE);
        }
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
                        YourQDay.setText(getResources().getString(R.string.qday));
//                        YourQDay2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        YourQDay2.setText(preferences.getString("qday", "none"));
                        String q2;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            q2 = YourQDay2.getText().toString().substring(3,6) +
                                    YourQDay2.getText().toString().substring(YourQDay2.length()-5,YourQDay2.length());
                        } else {
                            int q2b = Integer.parseInt(YourQDay2.getText().toString().substring(3,5));
                            String m = monthName[q2b-1];
                            q2 = m+YourQDay2.getText().toString().substring(YourQDay2.length()-5,YourQDay2.length());
                        }
//                        quitDateTopText.setText(q2);
                        if (BuildConfig.DEBUG) {
                            Log.d("DAYZEN2", "date: " + q2);
                        }
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
                if (BuildConfig.DEBUG) {
                    Log.d(DAYZEN, "thread separat: " + Thread.currentThread().getName());
                }
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
                            if (BuildConfig.DEBUG) {
                                Log.d(DAYZEN, "thread din ui: " + Thread.currentThread().getName());
                            }
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
//                    setTodayToClickDay();
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
                    } else if (counter > TWO_HUNDRED_AND_SEVENTY-TWO && counter < x) {
                        normalFancyDialog(getString(R.string.beat_milestone, x), MESSAGE_FOR_DIALOG);
                        //if user has reached almost one year (360 days) we ask if he/she want reset
                        //TODO: add more ifs until 1000
                    } else if (counter >= x && counter < 401) {
                        normalFancyDialog(getString(R.string.beat_milestone, 400), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 401 && counter < 501) {
                        normalFancyDialog(getString(R.string.beat_milestone, 500), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 501 && counter < 601) {
                        normalFancyDialog(getString(R.string.beat_milestone, 600), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 601 && counter < 701) {
                        normalFancyDialog(getString(R.string.beat_milestone, 700), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 701 && counter < 801) {
                        normalFancyDialog(getString(R.string.beat_milestone, 800), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 801 && counter < 901) {
                        normalFancyDialog(getString(R.string.beat_milestone, 900), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 901 && counter < 1001) {
                        normalFancyDialog(getString(R.string.beat_milestone, 1000), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 1000 && counter < 2001) {
                        normalFancyDialog(getString(R.string.beat_milestone, 2000), MESSAGE_FOR_DIALOG);
                    } else if (counter >= 2000) {
                        //impossible
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
        milestoneAlert.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.popup_menu_round));
        milestoneAlert.setMessage(message);
        milestoneAlert.setTitle(title);
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                generateCravingText();
                setCheckInText();
                setTodayToClickDay();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
                if (counter == ZERO) {
                    savings = preferences.getLong("taoz10", -10);
                }
                if (preferences.contains("diff") && higherThanOne) {
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
                    if (counter >= ONE_THOUSAND_AND_FIVE_HUNDRED){
                        dialogForResetForced();
                    }
                } else {
                    counter++;
                    setCounterImageDaysOrDay(counter);
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO) + firstSave;
                    if (BuildConfig.DEBUG) {
                        Log.d("taogenX", "savings from TAO = " + savings);
                    }
                    editor.putLong(SAVINGS_FINAL, savings);
                    editor.apply();
                    higherThanOne = false;
                }
                editor.putInt(COUNTER, counter);
                setTextViewsForProgress();
//                checkActivityOnline();
                checkActivityFromSplashScreen();
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
                showEntireProgressForUserCard(userCigaretesProgressTxt,
                        userCigaretesProgressTxt2,
                        userHighestStreakTxt,
                        userHighestStreakTxt2,
                        userHoursProgressTxt,
                        userHoursProgressTxt2);
//                counterImgViewProgressbar.setProgress(counter);
            }
        });
        milestoneAlert.setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    generateCravingText();
                    setTodayToClickDay();
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
                    final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE;
                    userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
                    userCigaretesProgressTxt2.setText(String.valueOf(TEMP_CIGARETTES));
                    editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
                    lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
                    final String USER_HOURS_PROGRESS = getString(R.string.life_r);
                    userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
                    userHoursProgressTxt2.setText(String.valueOf(numberFormat.format(lifeRegained) + " " + getString(R.string.hours)));
                    editor.putFloat(LIFEREGAINED, lifeRegained);
                    editor.apply();
//                    checkActivityOnline();
                    //                        setTheSavingsPerDay();
                    savingsGetAndSetValue();
                    setImprovementProgressLevels();
                    counter = preferences.getInt(COUNTER, -1);
                    counterText.setText(String.valueOf(counter));
                    setTargetDays();
                    negativeDialogAfterRelapse();
                    estabilishHighestRecordForCounter();
//                    counterImgViewProgressbar.setProgress(counter);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
        milestoneAlert.show();
    }

    private void setTextViewsForProgress() {
        final int TEMP_CIGARETTES = cigarettesPerDay * counter;
        final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
        final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE;
        userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
        userCigaretesProgressTxt2.setText(String.valueOf(TEMP_CIGARETTES));
        editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
        lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
        final String USER_HOURS_PROGRESS = getString(R.string.life_r);
        userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
        userHoursProgressTxt2.setText(String.valueOf(numberFormat.format(lifeRegained) + " " + getString(R.string.hours)));
        editor.putFloat(LIFEREGAINED, lifeRegained);
        editor.putLong(SAVINGS_FINAL, savings);
        editor.apply();
    }

    private void ttfancyDialogForFirstTimeLaunch(String title, String message) {
        MaterialAlertDialogBuilder milestoneAlert = new MaterialAlertDialogBuilder
                (new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        milestoneAlert.setMessage(message);
        milestoneAlert.setTitle(title);
        milestoneAlert.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.popup_menu_round));
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                generateCravingText();
                setTodayToClickDay();
                if (preferences.contains(SAVINGS_FINAL)){
                    firstSave = preferences.getLong(SAVINGS_FINAL, ZERO);
                    editor.putLong("firstsave", firstSave);
                    editor.apply();
                    if (BuildConfig.DEBUG) {
                        Log.d("taogenX", "firstsave is: " + firstSave);
                    }
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
                    }
                }
                counter++;
                setCounterImageDaysOrDay(counter);
                if (counter == 1){
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO);
                    editor.putLong(SAVINGS_FINAL, savings);
                    if (preferences.contains(SAVINGS_FINAL)){
                        firstSave = preferences.getLong(SAVINGS_FINAL, ZERO);
                        editor.putLong("firstsave", firstSave);
                        editor.apply();
                    }
                } else {
                    savings = preferences.getLong(SAVINGS_FINAL, ZERO) + firstSave;
                    editor.putLong(SAVINGS_FINAL, savings);
                    editor.apply();
                }
                higherThanOne = false;
                editor.putInt(COUNTER, counter);
                setTextViewsForProgress();
//                checkActivityOnline();
                checkActivityFromSplashScreen();
//                        setTheSavingsPerDay();
                savingsGetAndSetValue();
                setImprovementProgressLevels();
                setImagesForAchievementCard();
                counterText.setText(String.valueOf(counter));
                setTargetDays();
                //var 2 - custom toast after answer
                estabilishHighestRecordForCounter();
                showEntireProgressForUserCard(userCigaretesProgressTxt,
                        userCigaretesProgressTxt2,
                        userHighestStreakTxt,
                        userHighestStreakTxt2,
                        userHoursProgressTxt,
                        userHoursProgressTxt2);
                //var 1 - dialog after answer
                positiveDialogAfterPassDay();
                checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
//                counterImgViewProgressbar.setProgress(counter);
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
            editor.apply();
            counter = 1;
            savings = firstSave;
            //to see
            editor.putLong(SAVINGS_FINAL, savings);//off
            //maybe
            editor.putInt(COUNTER, counter);
            //new edit
            final int TEMP_CIGARETTES = preferences.getInt(INITIAL_CIGG_PER_DAY, ZERO);
            final String CIGARETTES_NOT_SMOKE = getString(R.string.cig_not_smoked);
            final String USER_CIGARETTES_PROGRESS = CIGARETTES_NOT_SMOKE;
            userCigaretesProgressTxt.setText(USER_CIGARETTES_PROGRESS);
            userCigaretesProgressTxt2.setText(String.valueOf(TEMP_CIGARETTES));
            editor.putInt(MODIFIED_CIGG_PER_DAY, TEMP_CIGARETTES);
            lifeRegained = (5f * (float) TEMP_CIGARETTES) / 60f;
            final String USER_HOURS_PROGRESS = getString(R.string.life_r);
            userHoursProgressTxt.setText(USER_HOURS_PROGRESS);
            userHoursProgressTxt2.setText(String.valueOf(numberFormat.format(lifeRegained) + " " + getString(R.string.hours)));
            editor.putFloat(LIFEREGAINED, lifeRegained);
            editor.apply();
//            checkActivityOnline();
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

    private void setTxtViewForUserSavings(long SAVE_PER_DAY, String TOTAL_SAVINGS) {
        final DecimalFormat FORMATTER = new DecimalFormat("###,###,##0.00");
        final DecimalFormat FORMATTER2= new DecimalFormat("###,###,##0");
        final String VALUE_PER_YEAR_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY))*365);
        final String TOTAL_SAVINGS_LOCAL = FORMATTER.format(Integer.parseInt(TOTAL_SAVINGS));
        final String TOTAL_SAVINGS_LOCAL2 = FORMATTER.format(Integer.parseInt(TOTAL_SAVINGS));
        final String VALUE_PER_DAY_LOCAL = FORMATTER.format(Integer.parseInt(String.valueOf(SAVE_PER_DAY)));
        final String CURRENCY_LOCAL = preferences.getString("currency", "$");
        //target counter string
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder =
//                //per day
//                stringBuilder.append(getString(R.string.perday)).append(" ").append(VALUE_PER_DAY_LOCAL).append(" ").append(CURRENCY_LOCAL).append("\n")
//                //per total
//                .append(getString(R.string.savings, TOTAL_SAVINGS_LOCAL)).append(" ").append(CURRENCY_LOCAL).append("\n")
//                //per year
//                .append(getString(R.string.peryear)).append(" ").append(VALUE_PER_YEAR_LOCAL).append(" ").append(CURRENCY_LOCAL);
//        TEXTVIEW.setText(stringBuilder);
        String perDayNormal = getString(R.string.perday);
        moneySavingsTxtPerDay.setText(perDayNormal);
        String perDayResult = " " + VALUE_PER_DAY_LOCAL + " " + CURRENCY_LOCAL;
        moneySavingsTxtPerDayResult.setText(perDayResult);
        String perTotalNormal = getString(R.string.savings);
        moneySavingsTxtPerTotal.setText(perTotalNormal);
        String perTotalResult = " " + TOTAL_SAVINGS_LOCAL + " " + CURRENCY_LOCAL;
        String perTotalResult2 = " " + TOTAL_SAVINGS_LOCAL2 + " " + CURRENCY_LOCAL;
        moneySavingsTxtPerTotalResult.setText(perTotalResult);
        savingsTopText.setText(perTotalResult2);
        String perYearNormal = getString(R.string.peryear);
        moneySavingsTxtPerYear.setText(perYearNormal);
        String perYearResult = " " + VALUE_PER_YEAR_LOCAL + " " + CURRENCY_LOCAL;
        moneySavingsTxtPerYearResult.setText(perYearResult);
    }

    private void setTxtViewForUserMaxCountDaysOnStringVersion(
            final String STRING,
            final TextView TEXTVIEW) {
        //target counter string
        TEXTVIEW.setText(getResources().getString(R.string.target_string, STRING));
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
            setTxtViewForUserSavings(firstSave, String.valueOf(savings));
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
            } else if (counter == 1) {
                textNonSmoker.setText(R.string.non_smoker_sincer_one_day);
            } else {
                textNonSmoker.setText(R.string.non_smoker_since);
            }
            //TODO: finish calculation for MAXCOUNT
            if (counter >= ONE_THOUSAND) {
                //12 months//1 year
                userMaxCountForHabit = ONE_THOUSAND*2;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINE_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = ONE_THOUSAND;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= EIGHT_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = NINE_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= SEVEN_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = EIGHT_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= SIX_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = SEVEN_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= FIVE_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = SIX_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= FOUR_HUNDRED) {
                //12 months//1 year
                userMaxCountForHabit = FIVE_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY*FOUR) {
                //12 months//1 year
                userMaxCountForHabit = FOUR_HUNDRED;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY*THREE) {
                //12 months//1 year
                userMaxCountForHabit = NINETY*FOUR;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY*TWO) {
                //9 months
                userMaxCountForHabit = NINETY*THREE;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= NINETY) {
                //6 months
                userMaxCountForHabit = NINETY*TWO;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= SIXTY) {
                //3 months
                userMaxCountForHabit = NINETY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else if (counter >= THIRTY) {
                //2 months
                userMaxCountForHabit = SIXTY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            } else {
                //1 month
                userMaxCountForHabit = THIRTY;
                editor.putInt(getString(R.string.maxCounter), userMaxCountForHabit);
                editor.apply();
            }
            userMaxCountForHabit = preferences.getInt(getString(R.string.maxCounter), -1);
            counterImgViewProgressbar.setMaximum(userMaxCountForHabit);
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
            if (BuildConfig.DEBUG){
                Log.d("dayzen", ""+maxCount);
            }
//            counterImgViewProgressbar.setProgress((float) ((30-maxCount)*3.3));
            counterImgViewProgressbar.setProgress(preferences.getInt(COUNTER, 0));
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
                }, 100, 20_000);//Timertask once per 10 SECONDS
            }//run from async
        });//async
    }//runningInBackground

    @SideEffect
    private void startTheEngine() {
        try {
//            checkInsteadOfSmoking();
            //set text for checkin
            setCheckInText();
            showEntireProgressForUserCard(userCigaretesProgressTxt,
                    userCigaretesProgressTxt2,
                    userHighestStreakTxt,
                    userHighestStreakTxt2,
                    userHoursProgressTxt,
                    userHoursProgressTxt2);
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
                    final int HOURS_UNTIL_CHECK_IN = HOUR_OF_FIRSTLAUNCH - HOUR_OF_DAYLIGHT;
                    final String CHECK_IN_TEXT = getResources().getString(R.string.check_in) +
                            ": " + HOURS_UNTIL_CHECK_IN + " "
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
//            onCreate(new Bundle());
            if (BuildConfig.DEBUG){
                Log.d(DAYZEN, "onResume");
            }
//            checkActivityOnline();
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
//            onCreate(new Bundle());
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
            //if year has passed
            if (Calendar.getInstance().get(Calendar.YEAR) != preferences.getInt(YEARLEAP, 2019)) {
                //366 - DAY OF CLICK
                DAY_OF_CLICK = returnLeapForClickYear();
                DAY_OF_CLICK *= -1;
                editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);editor.apply();
                editor.putInt(YEARLEAP, Calendar.getInstance().get(Calendar.YEAR));editor.apply();
            }
            if (BuildConfig.DEBUG) {
                Log.d(DAYZEN, "DAY OF CLICK " + DAY_OF_CLICK
                        + " DAY OF PRESENT " + DAY_OF_PRESENT + "\n" +
                        " HOUR_OF_FIRSTLAUNCH " + HOUR_OF_FIRSTLAUNCH + " HOUR_OF_DAYLIGHT " + HOUR_OF_DAYLIGHT);
            }

            //if user started at hour of 00 or 23 at night and hour of now is 00 then we make hour of now equal
            //to 24 to be able to make the calculation
            //else we calculate only hour of now to be equal to 24 if hour of daylight is 00
//            HOUR_OF_DAYLIGHT = HOUR_OF_DAYLIGHT == ZERO ? TWENTYFOUR : HOUR_OF_DAYLIGHT;
            if (DAY_OF_PRESENT > DAY_OF_CLICK && (DAY_OF_PRESENT == DAY_OF_CLICK+ONE)) {
                if (HOUR_OF_DAYLIGHT==24){
                    HOUR_OF_FIRSTLAUNCH=23;
                    DAY_OF_PRESENT=DAY_OF_PRESENT-1;
                } else {
                    HOUR_OF_FIRSTLAUNCH=preferences.getInt(HOUR_OF_FIRSLAUNCH_SP,0);
                    DAY_OF_PRESENT = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
                }
                if (HOUR_OF_FIRSTLAUNCH <= HOUR_OF_DAYLIGHT && DAY_OF_PRESENT > DAY_OF_CLICK) {
                    checkInButton.setBackground(getDrawable(R.drawable.custom_round_primary_color));
                    setCheckInText();
                } else {
                    checkInButton.setBackground(getDrawable(R.drawable.custom_round_grey_color));
                    setCheckInText();
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
                txtProgressForEnergyLevels.setText(FIVE+3 + "%");
                progressBarEnergyLevel.setProgress(FIVE+3);
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
                txtProgressForEnergyLevels.setText(THREE + "%");
                progressBarEnergyLevel.setProgress(THREE);
            }

            //heart levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForHeart.setText(TEN + "%");
                progressBarHeartLevel.setProgress(TEN);
            } else if (counter > THIRTY-ONE && counter < SIXTY) {
                txtProgressForHeart.setText(FIFTEEN + "%");
                progressBarHeartLevel.setProgress(FIFTEEN);
            } else if (counter > SIXTY-ONE && counter < NINETY) {
                txtProgressForHeart.setText(THIRTY + "%");
                progressBarHeartLevel.setProgress(THIRTY);
            } else if (counter > NINETY-ONE && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForHeart.setText(FIFTY + "%");
                progressBarHeartLevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-ONE && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForHeart.setText(SEVENTY_FIVE + "%");
                progressBarHeartLevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForHeart.setText(ONE_HUNDRED + "%");
                progressBarHeartLevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForHeart.setText(FIVE + "%");
                progressBarHeartLevel.setProgress(FIVE);
            }

            //blood levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForBlood.setText(FIFTEEN + "%");
                progressBarBloodLevel.setProgress(FIFTEEN);
            } else if (counter > THIRTY-ONE && counter < SIXTY) {
                txtProgressForBlood.setText(TWENTY + "%");
                progressBarBloodLevel.setProgress(TWENTY);
            } else if (counter > SIXTY-ONE && counter < NINETY) {
                txtProgressForBlood.setText(THIRTY + "%");
                progressBarBloodLevel.setProgress(THIRTY);
            } else if (counter > NINETY-ONE && counter < ONE_HUNDRED_AND_EIGHTY) {
                txtProgressForBlood.setText(FIFTY + "%");
                progressBarBloodLevel.setProgress(FIFTY);
            } else if (counter > ONE_HUNDRED_AND_EIGHTY-ONE && counter < TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForBlood.setText(SEVENTY_FIVE + "%");
                progressBarBloodLevel.setProgress(SEVENTY_FIVE);
            } else if (counter > TWO_HUNDRED_AND_SEVENTY) {
                txtProgressForBlood.setText(ONE_HUNDRED + "%");
                progressBarBloodLevel.setProgress(ONE_HUNDRED);
            } else {
                txtProgressForBlood.setText(FIVE + "%");
                progressBarBloodLevel.setProgress(FIVE);
            }

            //breath levels
            if (counter >= TEN-1 && counter < THIRTY) {
                txtProgressForBreath.setText(TEN + "%");
                progressBarBreathlevel.setProgress(TEN);
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

    private void generateCravingText() {
        try {
            Random random = new Random();
            int i = random.nextInt(9 - 0 + 1)+0;

            ArrayList<String> cravingText = new ArrayList<>();
            cravingText.add(getString(R.string.example_instead_of_text));//0
            cravingText.add("You might have tried to quit smoking before and not managed it, but don't let that put you off. \n" +
                    "\n" +
                    "Look back at the things your experience has taught you and think about how you're really going to do it this time.");//1
            cravingText.add("Make a promise, set a date and stick to it. Sticking to the \"not a drag\" rule can really help.\n" +
                    "\n" +
                    "Whenever you find yourself in difficulty, say to yourself, " +
                    "\"I won't even have a single drag\", and stick with this until the cravings pass.");//2
            cravingText.add("Is your after-dinner cigarette your favourite? A US study revealed that some foods, including meat, make cigarettes more satisfying.\n" +
                    "\n" +
                    "Others, including cheese, fruit and vegetables, make cigarettes taste terrible. So swap your usual steak or burger for a veggie pizza instead.  ");//3
            cravingText.add("The same US study as above also looked at drinks. Fizzy drinks, alcohol, cola, tea and coffee all make cigarettes taste better.\n" +
                    "\n" +
                    "So when you're out, drink more water and juice. Some people find simply changing their drink (for example, switching from wine to a vodka and tomato juice) affects their need to reach for a cigarette.");//4
            cravingText.add("A craving can last 5 minutes. Before you give up, make a list of 5-minute strategies.\n" +
                    "\n" +
                    "For example, you could leave the party for a minute, dance or go to the bar.\n" +
                    "\n" +
                    "And think about this: the combination of smoking and drinking raises your risk of mouth cancer by 38 times.");//5
            cravingText.add("If friends or family members want to give up, too, suggest to them that you give up together.\n" +
                    "\n" +
                    "There's also support available from your local stop smoking service. Did you know that you're up to 4 times more likely to quit successfully with their expert help and advice? ");//6
            cravingText.add("A review of scientific studies has proved exercise, even a 5-minute walk or stretch, cuts cravings and may help your brain produce anti-craving chemicals.");//7
            cravingText.add("Nicotine replacement therapy (NRT) can double your chances of success.\n" +
                    "\n" +
                    "As well as patches, there are tablets, lozenges, gum and a nasal spray. And if you like holding a cigarette, there are handheld products like the inhalator or e-cigarettes.\n" +
                    "\n" +
                    "When you're out, try putting your drink in the hand that usually holds a cigarette, or drink from a straw to keep your mouth busy.");//8
            cravingText.add("Keep reminding yourself why you made the decision to give up. Make a list of the reasons and read it when you need support.\n" +
                    "\n" +
                    "Ex-smoker Daniel, 26, says: \"I used to take a picture of my baby daughter with me when I went out. If I was tempted, I'd look at that.\"\n" +
                    "\n" +
                    "Read more about the stop smoking treatments available on the NHS.");//9

            ArrayList<String> cravingTitles = new ArrayList<>();
            cravingTitles.add(getString(R.string.instead_of_smoking));//0
            cravingTitles.add("Think positive");//1
            cravingTitles.add("Make a plan to quit smoking");//2
            cravingTitles.add("Consider your diet");//3
            cravingTitles.add("Change your drink");//4
            cravingTitles.add("Identify when you crave cigarettes");//5
            cravingTitles.add("Get some stop smoking support");//6
            cravingTitles.add("Get moving");//7
            cravingTitles.add("Keep your hands and mouth busy");//8
            cravingTitles.add("Make a list of reasons to quit");//9

            cravingTitleText.setText(cravingTitles.get(i));
            cravingTipsText.setText(cravingText.get(i));

            editor.putString(CRAVINGTITLE, cravingTitles.get(i));
            editor.putString(CRAVINGTEXT, cravingText.get(i));
            editor.apply();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @SideEffect
    private void checkActivityOnline() {
        try {
            Random random = new Random();
            int i = random.nextInt(10 - 1 + 1) + 1;
            if (isOnline()) {
                if (preferences.contains(COUNTER)) {
                    counter = preferences.getInt(COUNTER, ZERO);
                }
//                if (counter == ZERO) {
//                    requestDataById(1);
//                } else {
//                    requestDataById(i);
//                }
                if (counter == ZERO) {
                    requestDataById(1);
                } else if (DAY_OF_PRESENT > 100 && DAY_OF_PRESENT < 200) {
                    requestDataById(DAY_OF_PRESENT - 100);
                } else if (DAY_OF_PRESENT > 200 && DAY_OF_PRESENT < 300) {
                    requestDataById(DAY_OF_PRESENT - 200);
                } else if (DAY_OF_PRESENT > 300 && DAY_OF_PRESENT < 400) {
                    requestDataById(DAY_OF_PRESENT - 300);
                } else if (DAY_OF_PRESENT > 400 && DAY_OF_PRESENT < 500) {
                    requestDataById(DAY_OF_PRESENT - 400);
                } else if (DAY_OF_PRESENT > 500 && DAY_OF_PRESENT < 600) {
                    requestDataById(DAY_OF_PRESENT - 500);
                } else if (DAY_OF_PRESENT > 600 && DAY_OF_PRESENT < 700) {
                    requestDataById(DAY_OF_PRESENT - 600);
                } else if (DAY_OF_PRESENT > 700 && DAY_OF_PRESENT < 800) {
                    requestDataById(DAY_OF_PRESENT - 700);
                } else if (DAY_OF_PRESENT > 800 && DAY_OF_PRESENT < 900) {
                    requestDataById(DAY_OF_PRESENT - 800);
                } else if (DAY_OF_PRESENT > 900 && DAY_OF_PRESENT <= 1000) {
                    requestDataById(DAY_OF_PRESENT - 900);
                } else {
                    requestDataById(DAY_OF_PRESENT);
                }
                //requestDataById(DAY_OF_PRESENT);
            } else {
                if (counter == ZERO) {
                    requestDataById(1);
                } else {
                    requestDataById(i);
                }
                final Snackbar SNACKBAR;
                SNACKBAR = Snackbar.make(parentLayout, R.string.no_connection, Snackbar.LENGTH_LONG);
                final View SNACKBAR_VIEW = SNACKBAR.getView();
                SNACKBAR_VIEW.setBackgroundColor(Color.YELLOW);
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
//                    counterImgViewProgressbar.setVisibility(View.INVISIBLE);
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
                    Log.d(DAYZEN, "lang is: " + PUT_LANGUAGE_IN_STRING);
                }
                //get quote from ro if user is ro, else get default quotes
//                NAME_ELEMENT_NODE = (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(RO))
//                        ? DETAILS_LOCAL.get(NAME_RO) : DETAILS_LOCAL.get(NAME);

                if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase(RO)){
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME_RO);
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase("es")){
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get("nameES");
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase("fr")) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get("nameFR");
                } else if (PUT_LANGUAGE_IN_STRING.equalsIgnoreCase("de")) {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get("nameDE");
                } else {
                    NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME);
                }

//                NAME_ELEMENT_NODE = DETAILS_LOCAL.get(NAME);

                return NAME_ELEMENT_NODE.getAsString();
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]
        @Override
        protected void onPostExecute(final String RESULT) {
            try {
                //TODO: to think about putting here R.string.example_of_tip_of_the_day instead of no quotes
                final String tempResult = (RESULT == null) ?
                        "No quotes available in this moment ..." : RESULT;
                //using raw JSON PARSER
                updateDisplayString(tempResult);
                //we get rid of the task that we created
                tasks.remove(this);
                if (tasks.size() == ZERO) {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    progressBarLoading2.setVisibility(View.GONE);
                    counterImgViewProgressbar.setVisibility(View.VISIBLE);
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
            final int yearOfNow = Calendar.getInstance().get(Calendar.YEAR);
            final int x = isLeap(yearOfNow) ? LEAP_YEAR_DAYS : NORMAL_YEAR_DAYS;
            if (preferences.contains(COUNTER)) {
                counter = preferences.getInt(COUNTER, ZERO);
            }
            if (BuildConfig.DEBUG) {
                Log.d("DAYZEN2", "counter " + counter);
            }

            if (counter >= ZERO && counter < 4) {
                //user have between a day and a week
                rankOneImg.setBackgroundResource(R.drawable.ic_qdate);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_oneday);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_oneweek);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Wolf");
            } else if (counter>=4&&counter<EIGHT) {
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
            } else if (counter >= THREE_HUNDRED && counter < x) {
                rankOneImg.setBackgroundResource(R.drawable.ic_threehundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourhundreddays);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivehundreddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Lynx");
            } else if (counter >= x && counter < FOUR_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_threehundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourhundreddays);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivehundreddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Eagle");
            } else if (counter >= FOUR_HUNDRED && counter < FIVE_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_threehundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourhundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivehundreddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Elephant");
            } else if (counter >= FIVE_HUNDRED && counter < SIX_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_threehundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_fourhundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_fivehundreddays);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Leopard");
            } else if (counter >= SIX_HUNDRED && counter < SEVEN_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_sixhundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenhundreddays);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Buffalo");
            } else if (counter >= SEVEN_HUNDRED && counter < EIGHT_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_sixhundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenhundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Shark");
            } else if (counter >= EIGHT_HUNDRED && counter < ONE_THOUSAND-100) {
                rankOneImg.setBackgroundResource(R.drawable.ic_sixhundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_sevenhundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Panther");
            } else if (counter >= ONE_THOUSAND-100 && counter < ONE_THOUSAND) {
                rankOneImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_ninehundreddays);
                rankTwoImg.setAlpha(0.2f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_onethousanddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Tiger");
            } else if (counter >= ONE_THOUSAND && counter < ONE_THOUSAND+ONE_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_ninehundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_onethousanddays);
                rankThreeImg.setAlpha(0.2f);
                editor.putString(RANK, "Lion");
                //higher than 1200
            } else if (counter >= ONE_THOUSAND+ONE_HUNDRED) {
                rankOneImg.setBackgroundResource(R.drawable.ic_eighthundreddays);
                rankOneImg.setAlpha(1.0f);
                rankTwoImg.setBackgroundResource(R.drawable.ic_ninehundreddays);
                rankTwoImg.setAlpha(1.0f);
                rankThreeImg.setBackgroundResource(R.drawable.ic_onethousanddays);
                rankThreeImg.setAlpha(1.0f);
                editor.putString(RANK, "Lion");
        }
            editor.apply();
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
    private void showEntireProgressForUserCard(final TextView TV_USER_CIGARETTES_PROGRESS,final TextView TV_USER_CIGARETTES_PROGRESS2,
                                               final TextView TV_HIGHEST_STREAK,final TextView TV_HIGHEST_STREAK2,
                                               final TextView TV_HOURS_PROGRESS, final TextView TV_HOURS_PROGRESS2) {
        try {
            YourQDay.setText(getResources().getString(
                    R.string.qday));
            YourQDay2.setText(preferences.getString("qday", "none"));
            String q2;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                q2 = YourQDay2.getText().toString().substring(3,6) +
                        YourQDay2.getText().toString().substring(YourQDay2.length()-5,YourQDay2.length());
            } else {
                int q2b = Integer.parseInt(YourQDay2.getText().toString().substring(3,5));
                String m = monthName[q2b-1];
                q2 = m+YourQDay2.getText().toString().substring(YourQDay2.length()-5,YourQDay2.length());
            }
//            quitDateTopText.setText(q2);
            if (BuildConfig.DEBUG) {
                Log.d("DAYZEN2", "date: " + q2);
            }

            //getting the highest streak and put it in progress card
            final int HIGHEST_STREAK;
            if (preferences.contains(HIGHEST)){
                HIGHEST_STREAK = preferences.getInt(HIGHEST, ZERO);
            } else {
                HIGHEST_STREAK = preferences.getInt(COUNTER, ZERO);
            }
            final String TEMP_STREAK = getString(R.string.streak_string);
            TV_HIGHEST_STREAK.setText(TEMP_STREAK);
            TV_HIGHEST_STREAK2.setText(String.valueOf(HIGHEST_STREAK));
            //getting highest rank and put it into ranks card
            final String LAST_STREAK = preferences.getString(RANK, UNRANKED);
            final String RANK_LOCAL = getString(R.string.rank_master, LAST_STREAK);
//            rankMasterTxt.setText(RANK_LOCAL);

            if (counter == ZERO) {
                TV_USER_CIGARETTES_PROGRESS.setText(R.string.cig_press_leaf);
                final String PRESS_LEAF = getString(R.string.life_press_leaf);
//                TV_HOURS_PROGRESS.setText(PRESS_LEAF);
            } else {
                if (preferences.contains(LIFEREGAINED)) {
                    lifeRegained = preferences.getFloat(LIFEREGAINED, -1);
                    numberFormat.format(lifeRegained);
                } else {
                    lifeRegained = Float.valueOf((5f * Float.valueOf(preferences.getInt(MODIFIED_CIGG_PER_DAY, ZERO))) / 60f);
                    numberFormat.format(lifeRegained);
                }
                final String CIGARETTES_NOT_SMOKED_STRING = getString(R.string.cig_not_smoked);
                final String LIFE_REGAINED_STRING = getString(R.string.life_reg);
                TV_USER_CIGARETTES_PROGRESS.setText(CIGARETTES_NOT_SMOKED_STRING);
                TV_USER_CIGARETTES_PROGRESS2.setText(preferences.getInt(MODIFIED_CIGG_PER_DAY, ZERO));
//                TV_HOURS_PROGRESS.setText(LIFE_REGAINED_STRING);
//                TV_HOURS_PROGRESS2.setText(String.valueOf(numberFormat.format(lifeRegained)));
                if (BuildConfig.DEBUG){
                    Log.d("DAYZEN", "txt1: " + LIFE_REGAINED_STRING+ "txt2: "
                            + String.valueOf(numberFormat.format(lifeRegained)));
                }
//                editor.putFloat(LIFEREGAINED, lifeRegained);
//                editor.apply();
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

}//[END OF MAIN CLASS]