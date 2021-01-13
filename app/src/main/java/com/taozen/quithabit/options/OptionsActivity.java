package com.taozen.quithabit.options;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.taozen.quithabit.utils.NumberPickerDialog;

import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import static com.taozen.quithabit.MainActivity.CURRENCY;
import static com.taozen.quithabit.MainActivity.DAYZEN;
import static com.taozen.quithabit.MainActivity.HIGHEST;
import static com.taozen.quithabit.MainActivity.ONE;
import static com.taozen.quithabit.MainActivity.ZERO;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private final int RESET_PROGRESS_INTEGER = R.id.reset;
    private final int FEEDBACK_INTEGER = R.id.feedback;
    private final int LEGAL = R.id.legal;
    private final int STREAK_PERIOD = R.id.qdate;
    private final int CHANGE_SMOKING_DATA = R.id.smokingdata;
    private final int ABOUT = R.id.about;

    String[] titles;

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    TextView smoking_data_detail;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(OptionsActivity.this);
        editor = preferences.edit();
        titles = getResources().getStringArray(R.array.options_array);
        final MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.options));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //reset
        final TextView listview_title_reset = findViewById(R.id.listview_title1);
        listview_title_reset.setText(titles[0]);
        //quitdata
        final TextView listview_title_quit_data = findViewById(R.id.change_counter_title);
        listview_title_quit_data.setText(titles[1]);
        smoking_data_detail = findViewById(R.id.change_counter_description);
        if (preferences.contains(HIGHEST)){
            final int HIGHEST_STREAK = preferences.getInt(HIGHEST, ONE);
            smoking_data_detail.setText(getResources().getString(R.string.quit_date_change, String.valueOf(HIGHEST_STREAK)));
        }
        //currency
        final TextView listView_title_change_smoking_data = findViewById(R.id.change_smoking_data_title);
        listView_title_change_smoking_data.setText(titles[2]);
        //feedback
        final TextView listview_title_feedback = findViewById(R.id.listview_title5);
        listview_title_feedback.setText(titles[3]);
        //Legal
        final TextView listview_title_legal = findViewById(R.id.listview_title6);
        listview_title_legal.setText(titles[4]);
        //About
        final TextView listview_title_about = findViewById(R.id.listview_title7);
        listview_title_about.setText(titles[5]);

        final ConstraintLayout resetConstraintLayout = findViewById(RESET_PROGRESS_INTEGER);
        final ConstraintLayout changeQuitData = findViewById(STREAK_PERIOD);
        final ConstraintLayout changeSmokingData = findViewById(CHANGE_SMOKING_DATA);
        final ConstraintLayout legal = findViewById(LEGAL);
        final ConstraintLayout feedback = findViewById(FEEDBACK_INTEGER);
        final ConstraintLayout about = findViewById(ABOUT);

        resetConstraintLayout.setOnClickListener(this);
        changeSmokingData.setOnClickListener(this);
        changeQuitData.setOnClickListener(this);
        legal.setOnClickListener(this);
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.contains(HIGHEST)){
            final int HIGHEST_STREAK = preferences.getInt(HIGHEST, ONE);
            smoking_data_detail.setText(getResources().getString(R.string.quit_date_change, String.valueOf(HIGHEST_STREAK)));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void dialogForResetForced() {
        final MaterialAlertDialogBuilder milestoneAlert = new MaterialAlertDialogBuilder
                (new ContextThemeWrapper(this, R.style.AlertDialogTheme));
        milestoneAlert.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.popup_menu_round));
        milestoneAlert.setMessage(getResources().getString(R.string.are_you_sure));
        milestoneAlert.setTitle(getResources().getString(R.string.your_progress_will_be_reset));
        milestoneAlert.setCancelable(false);
        milestoneAlert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                new BottomDialog.Builder(OptionsActivity.this)
                        .setTitle(getResources().getString(R.string.your_progress_has_been_reset))
                        .setContent(getResources().getString(R.string.app_will_restart))
                        .setPositiveText(R.string.OK)
                        .setPositiveTextColorResource(android.R.color.white)
                        .setCancelable(false)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog bottomDialog) {
                                clearAppData();
//                                restartActivity();
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
                final String packageName = getApplicationContext().getPackageName();
                final Runtime runtime = Runtime.getRuntime();
                runtime.exec(getResources().getString(R.string.your_progress_has_been_reset)+packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case FEEDBACK_INTEGER:
                final Intent INTENT_FEEDBACK = new Intent(Intent.ACTION_SEND);
                INTENT_FEEDBACK.setType("message/rfc822");
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_EMAIL  , new String[]{getResources().getString(R.string.reboot_way_address)});
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback) + " " + getResources().getString(R.string.app_name));
                INTENT_FEEDBACK.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(INTENT_FEEDBACK, getResources().getString(R.string.send_email)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(OptionsActivity.this, getResources().getString(R.string.there_are_no_email), Toast.LENGTH_SHORT).show();
                }
                break;
            case RESET_PROGRESS_INTEGER:
                dialogForResetForced();
                break;
            case CHANGE_SMOKING_DATA:
                //TODO: take values from shared prefs and change them
                setInfoPreferenceByUser();
                break;
            case STREAK_PERIOD:
                //TODO: take value of smoking date and put calendar to give user freedom to choose whatever start
                //date he/she wants, also change the counter to be equal from that date
                setCounterPreferenceByUser();
                break;
            case LEGAL:
                final Intent INTENT_LEGAL = new Intent(OptionsActivity.this, LegalActivity.class);
                startActivity(INTENT_LEGAL);
                break;
            case ABOUT:
                final Intent INTENT_ABOUT = new Intent(OptionsActivity.this, AboutActivity.class);
                startActivity(INTENT_ABOUT);
                break;
        }
    }

    //choose between restart activity or clear app data cache
    private void restartActivity() {
        final Intent mStartActivity = new Intent(OptionsActivity.this, FirstScreenActivity.class);
        final int mPendingIntentId = 123456;
        PendingIntent mPendingIntent =
                PendingIntent.getActivity(getApplicationContext(),
                        mPendingIntentId,
                        mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        editor.putInt(HIGHEST, ONE);
        editor.apply();
        AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(mgr).set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(ZERO);
    }
    private void restartActivityInMain() {
        final Intent mStartActivity = new Intent(OptionsActivity.this, MainActivity.class);
        startActivity(mStartActivity);
    }

    @Override
    public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(this,
                getResources().getString(R.string.personal_counter_changed_to) +
                        " " + picker.getValue(), Toast.LENGTH_SHORT).show();
        editor.putInt(HIGHEST, picker.getValue());
        editor.apply();
        if (preferences.contains(HIGHEST)){
            final int HIGHEST_STREAK = preferences.getInt(HIGHEST, ONE);
            smoking_data_detail.setText(getResources().getString(R.string.quit_date_change, String.valueOf(HIGHEST_STREAK)));
        }
    }

    public void setCounterPreferenceByUser() {
        final NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), getResources().getString(R.string.time_picker));
    }

    public void setInfoPreferenceByUser() {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(OptionsActivity.this);
        builderSingle.setTitle(getResources().getString(R.string.choose_currency));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OptionsActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getResources().getString(R.string.usd_currency));//1
        arrayAdapter.add(getResources().getString(R.string.eur_currency));//2
        arrayAdapter.add(getResources().getString(R.string.gbp_currency));//3
        arrayAdapter.add(getResources().getString(R.string.jpy_currency));//4
        arrayAdapter.add(getResources().getString(R.string.chf_currency));//5
        arrayAdapter.add(getResources().getString(R.string.cad_currency));//6
        arrayAdapter.add(getResources().getString(R.string.aud_currency));//7
        arrayAdapter.add(getResources().getString(R.string.zar_currency));//8
        arrayAdapter.add(getResources().getString(R.string.mxn_currency));//9
        arrayAdapter.add(getResources().getString(R.string.try_currency));//10
        arrayAdapter.add(getResources().getString(R.string.ro_currency));//11

        builderSingle.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                //get the last 3 chars (but not parentheses)
                final String getCurrencyName = Objects.requireNonNull(strName).substring(strName.length()-4, strName.length()-1);
                if (BuildConfig.DEBUG){
                    Log.d(DAYZEN, getResources().getString(R.string.selected_currency) + getCurrencyName);
                }
                final AlertDialog.Builder builderInner = new AlertDialog.Builder(OptionsActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle(getResources().getString(R.string.currency_changed));
                builderInner.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        editor.putString(CURRENCY, getCurrencyName);
                        editor.apply();
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }
}


