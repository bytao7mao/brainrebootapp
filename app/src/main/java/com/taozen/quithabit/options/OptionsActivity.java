package com.taozen.quithabit.options;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.taozen.quithabit.BuildConfig;
import com.taozen.quithabit.FirstScreenActivity;
import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import java.util.Locale;
import java.util.Objects;

import static com.taozen.quithabit.MainActivity.PRIVACY_POLICY;
import static com.taozen.quithabit.MainActivity.TERMS_AND_CONDITIONS;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.LANGUAGE_SP;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private final int RESET_PROGRESS_INTEGER = R.id.first;
    private final int PRIVACY_INTEGER = R.id.first2;
    private final int TERMS_AND_CONDITIONS_INTEGER = R.id.first3;
    private final int THIRD_PARTY_LICENCES_INTEGER = R.id.first4;
    private final int FEEDBACK_INTEGER = R.id.first5;
    private final int ABOUT_INTEGER = R.id.first6;
    private final int QUIT_DATE = R.id.firstb;
    private final int CHANGE_SMOKING_DATA = R.id.firstc;
    private final int LANGUAGE = R.id.firstd;

    String[] titles;
    String[] descriptions = new String[] {
            "", "", "", "", "", "", "", "", "version 0.1 (100)"};

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    ListView mListView;

    //fonts
//    static Typeface montSerratBoldTypeface;
//    static Typeface montSerratItallicTypeface;
//    static Typeface montSerratLightTypeface;
//    static Typeface montSerratMediumTypeface;
//    static Typeface montSerratSemiBoldTypeface;
//    static Typeface montSerratExtraBoldTypeface;
//    static Typeface montSerratSimpleBoldTypeface;
//    static Typeface montSerratThinItalicTypeface;
//    static Typeface montSerratMediumItalicTypeface;
//    static Typeface montSerratSemiBoldItalicTypeface;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
        editor = preferences.edit();
        titles = getResources().getStringArray(R.array.options_array);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Options");
//        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.TitleBarToolbarCustom);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //reset
        TextView listview_title = findViewById(R.id.listview_title1);
        listview_title.setText(titles[0]);
        //quitdata
        TextView listview_title2 = findViewById(R.id.listview_title2);
        listview_title2.setText(titles[4]);
        //smoking data
        TextView listview_title3 = findViewById(R.id.listview_title3);
        listview_title3.setText(titles[5]);
//      language
        TextView listview_title4 = findViewById(R.id.listview_title4);
        listview_title4.setText(titles[6]);
        //third instead of privacy
        TextView listview_title5 = findViewById(R.id.listview_title5);
        listview_title5.setText(titles[7]);
        //privacy instead of terms
        TextView listview_title6 = findViewById(R.id.listview_title6);
        listview_title6.setText(titles[8]);
        //terms instead of third
        TextView listview_titleb = findViewById(R.id.listview_titleb);
        listview_titleb.setText(titles[1]);
        //feedback
        TextView listview_titlec = findViewById(R.id.listview_titlec);
        listview_titlec.setText(titles[2]);
        //about
        TextView listview_titled = findViewById(R.id.listview_titled);
        listview_titled.setText(titles[3]);

        ConstraintLayout resetConstraintLayout = findViewById(RESET_PROGRESS_INTEGER);
        ConstraintLayout privacyConstraintLayout = findViewById(PRIVACY_INTEGER);
        ConstraintLayout termsConstraintLayout = findViewById(TERMS_AND_CONDITIONS_INTEGER);
        ConstraintLayout thirdPartyConstraintLayout = findViewById(THIRD_PARTY_LICENCES_INTEGER);
        ConstraintLayout feedbackConstraintLayout = findViewById(FEEDBACK_INTEGER);
        ConstraintLayout aboutConstraintLayout = findViewById(ABOUT_INTEGER);
        ConstraintLayout changeQuitData = findViewById(QUIT_DATE);
        ConstraintLayout changeSmokingData = findViewById(CHANGE_SMOKING_DATA);
        ConstraintLayout changeLang = findViewById(LANGUAGE);

        resetConstraintLayout.setOnClickListener(this);
        privacyConstraintLayout.setOnClickListener(this);
        termsConstraintLayout.setOnClickListener(this);
        thirdPartyConstraintLayout.setOnClickListener(this);
        feedbackConstraintLayout.setOnClickListener(this);
        aboutConstraintLayout.setOnClickListener(this);
        changeLang.setOnClickListener(this);
        changeSmokingData.setOnClickListener(this);
        changeQuitData.setOnClickListener(this);

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void dialogForResetForced() {
        MaterialAlertDialogBuilder milestoneAlert = new MaterialAlertDialogBuilder
                (new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        milestoneAlert.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.popup_menu_round));
        milestoneAlert.setMessage("Are you sure ?");
        milestoneAlert.setTitle("Your progress will reset permanently!");
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                new BottomDialog.Builder(OptionsActivity.this)
                        .setTitle("Your progress has been reset!")
                        .setContent("App will close now.")
                        .setPositiveText(R.string.OK)
                        .setPositiveTextColorResource(android.R.color.white)
                        .setCancelable(false)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog bottomDialog) {
//                                clearAppData();
                                restartActivity();
                            }
                        }).show();
            }
        });
        milestoneAlert.setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });
        milestoneAlert.show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case PRIVACY_INTEGER:
                final Intent INTENT_PRIVACY = new Intent(Intent.ACTION_VIEW);
                INTENT_PRIVACY.setData(Uri.parse(PRIVACY_POLICY));
                startActivity(INTENT_PRIVACY);
                break;
            case TERMS_AND_CONDITIONS_INTEGER:
                final Intent INTENT_TERMS = new Intent(Intent.ACTION_VIEW);
                INTENT_TERMS.setData(Uri.parse(TERMS_AND_CONDITIONS));
                startActivity(INTENT_TERMS);
                break;
            case THIRD_PARTY_LICENCES_INTEGER:
                //TODO: third party licences like ip app
                break;
            case FEEDBACK_INTEGER:
                final Intent INTENT_FEEDBACK = new Intent(Intent.ACTION_SEND);
                INTENT_FEEDBACK.setType("message/rfc822");
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rebootway@outlook.com"});
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_SUBJECT, "feedback quithabit");
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(INTENT_FEEDBACK, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(OptionsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case RESET_PROGRESS_INTEGER:
                dialogForResetForced();
                break;
            case CHANGE_SMOKING_DATA:
                //TODO: take values from shared prefs and change them

                break;
            case QUIT_DATE:
                //TODO: take value of smoking date and put calendar to give user freedom to choose whatever start
                //date he/she wants, also change the counter to be equal from that date
                break;
            case LANGUAGE:
                //TODO: let user choose from german, french, spanish, english, romanian languages
                break;
            case ABOUT_INTEGER:
                final Intent INTENT_ABOUT = new Intent(OptionsActivity.this, AboutActivity.class);
                startActivity(INTENT_ABOUT);
                break;
        }
    }

    private void restartActivity(){
        Intent mStartActivity = new Intent(OptionsActivity.this, FirstScreenActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent =
                PendingIntent.getActivity(getApplicationContext(),
                        mPendingIntentId,
                        mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
}


