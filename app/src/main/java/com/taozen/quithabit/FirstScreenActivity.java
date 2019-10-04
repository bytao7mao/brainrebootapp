package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.CLICKDAY_SP;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.COUNTER;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;

public class FirstScreenActivity extends AppCompatActivity {
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.textView3) TextView textView3;
    @BindView(R.id.fsTitleId) TextView fsTitleId;
    @BindView(R.id.edtTxtForSavingsId) TextInputLayout editTxtForSavings;
    @BindView(R.id.editText) TextInputEditText editText;
    @BindView(R.id.edtTxtLayoutForCiggsId) TextInputLayout editTxtLayoutCiggs;
    @BindView(R.id.edtTxtForCiggarettedId) TextInputEditText editTxtForCiggs;
    @BindView(R.id.confirmBtn) Button confirmationButton;


    //firstStart bool
    private boolean isFirstStart;
    private Calendar calendarForProgress;
    private int DAY_OF_PRESENT, DAY_OF_CLICK;

    private String nameCigg, nameSav;
    private String currency;

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //add font to counter number
    static Typeface montSerratBoldTypeface;
    static Typeface montSerratItallicTypeface;
    static Typeface montSerratLightTypeface;
    static Typeface montSerratMediumTypeface;
    static Typeface montSerratSemiBoldTypeface;
    static Typeface montSerratExtraBoldTypeface;
    static Typeface montSerratSimpleBoldTypeface;

    private boolean go,go2;

    private boolean visible;
    @SuppressLint("CommitPrefEdits")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ButterKnife.bind(FirstScreenActivity.this);
        go = false;
        go2 = false;
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);

        montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
        montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
        montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        currency = Currency.getInstance(new Locale("",
                Objects.requireNonNull(telephonyManager).getNetworkCountryIso())).getCurrencyCode();
        Log.d("taogX", currency+"");

        getWindow().setStatusBarColor(ContextCompat.getColor(FirstScreenActivity.this, R.color.white));
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle =  findViewById(R.id.toolbar_subtitle);
        mTitle.setTypeface(montSerratSemiBoldTypeface);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.logowalk);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(FirstScreenActivity.this);
        editor = preferences.edit();

        //set ciggs
        fsTitleId.setTypeface(montSerratSemiBoldTypeface);
        textView3.setTypeface(montSerratLightTypeface);
        textView.setTypeface(montSerratItallicTypeface);
        editTxtForSavings.setTypeface(montSerratSemiBoldTypeface);
        editTxtLayoutCiggs.setTypeface(montSerratSemiBoldTypeface);
        confirmationButton.setTypeface(montSerratSemiBoldTypeface);

        assert editTxtForCiggs != null;
        editTxtForCiggs.setInputType(InputType.TYPE_CLASS_NUMBER);
        assert editTxtForSavings != null;
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        editTxtForSavings.setHint("How much do you spend ? (" + currency +")");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.append(currency);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                editTxtForSavings.append(currency);
            }
        });

        //set cigg
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCiggsPerDay();
                setSavings();
                TransitionManager.beginDelayedTransition(transitionsContainer,
                        new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(800));
                if (go && go2){
                    visible = !visible;
                    editTxtForSavings.setVisibility(visible ? View.VISIBLE : View.GONE);
                    editTxtForCiggs.setVisibility(visible ? View.VISIBLE : View.GONE);
                    Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
//                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    currency = Currency.getInstance(new Locale("",
//                            Objects.requireNonNull(telephonyManager).getNetworkCountryIso())).getCurrencyCode();
                    Log.d("taogX", currency+"");
                    editor.putString("currency", currency);
                    editor.apply();
                    editor.putInt("splash", 1);
                    editor.apply();
                    firstCheckForInitialCiggarettesPerDay();
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private void firstCheckForInitialCiggarettesPerDay() {
        if (!preferences.contains(INITIAL_CIGG_PER_DAY)){
            isFirstStart = true;editor.putBoolean("firstStart",isFirstStart);editor.apply();
            //[calendar area]
            calendarForProgress = Calendar.getInstance();
            calendarForProgress.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            DAY_OF_PRESENT = calendarForProgress.get(Calendar.DAY_OF_YEAR);
            DAY_OF_CLICK = DAY_OF_PRESENT - 1;
            editor.putInt(CLICKDAY_SP, DAY_OF_CLICK);
            editor.apply();
        } else {
            isFirstStart = false;editor.putBoolean("firstStart",isFirstStart);editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Optional
    private void setSavings() {
        Editable editM = editText.getText();
        nameSav = editText.getText().toString();
        if (TextUtils.isEmpty(nameSav)) {
            go = false;
            editText.setError("Please input a sum");
            return;
        } else {
            if (go2){
                confirmationButton.setEnabled(false);
            }
            go = true;
        }
        long moneyInt = (long) Integer.parseInt(editM.toString());
        editor.putLong(SAVINGS_FINAL, moneyInt);
        editor.putLong("taoz10", moneyInt);
        editor.apply();
        Log.d("INTROTAO", "moneyInt saved in INTROACTIVITY ? :  " + moneyInt);
    }
    @Optional
    private void setCiggsPerDay() {
        Editable editM = editTxtForCiggs.getText();
        nameCigg = editTxtForCiggs.getText().toString();
        if (TextUtils.isEmpty(nameCigg)) {
            go2 = false;
            editTxtForCiggs.setError("Please input a cigg number");
            return;
        } else {
            if (go){
                confirmationButton.setEnabled(false);
            }
            go2 = true;
        }
        int cigInt = Integer.parseInt(editM.toString());
        editor.putInt(INITIAL_CIGG_PER_DAY, cigInt);
        editor.apply();
        Log.d("INTROTAO", "cigg saved in INTROACTIVITY ? :  " + cigInt);
    }
}
