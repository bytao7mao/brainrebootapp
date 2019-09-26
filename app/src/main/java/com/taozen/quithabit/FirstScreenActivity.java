package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

import java.util.Currency;
import java.util.Locale;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.INITIAL_CIGG_PER_DAY;
import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;


public class FirstScreenActivity extends AppCompatActivity {
//    @Nullable
//    @BindView(R.id.editTxtSavingsId) EditText editTxtForSavings;
//    @Nullable
//    @BindView(R.id.edtTxtForCiggarettedId) EditText editTxtForCiggs;
//    @BindView(R.id.ciggBtnId) Button confirmationButton;
//    @BindView(R.id.savBtnId) Button btnSav;

    TextView textView, textView3, fsTitleId;
    EditText editTxtForSavings, editTxtForCiggs;
    Button confirmationButton;
    String nameCigg, nameSav;
    String currency;

    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //add font to counter number
    static Typeface montSerratBoldTypeface;
    static Typeface montSerratItallicTypeface;
    static Typeface montSerratLightTypeface;
    static Typeface montSerratMediumTypeface;
    static Typeface montSerratSemiBoldTypeface;
    static Typeface montSerratExtraBoldTypeface;
    static Typeface montSerratSimpleBoldTypeface;

    boolean go,go2;
    Handler mHandler = new Handler();

    boolean visible;
    @SuppressLint("CommitPrefEdits")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
//        ButterKnife.bind(FirstScreenActivity.this);
        go = false;go2 = false;
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);

        montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
        montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
        montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");

//        SpannableString s = new SpannableString("brainreboot");
//        s.setSpan(montSerratBoldTypeface, 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getWindow().setStatusBarColor(ContextCompat.getColor(FirstScreenActivity.this, R.color.white));
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(s);
        TextView mTitle =  findViewById(R.id.toolbar_subtitle);
        mTitle.setTypeface(montSerratSemiBoldTypeface);
//        mTitle.setTypeface(montSerratSemiBoldTypeface);
//        getSupportActionBar().setLogo(R.drawable.logowalk);
//        toolbar.setTitleTextAppearance(this, R.style.blabla);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.logowalk);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(FirstScreenActivity.this);
        editor = preferences.edit();
//        getWindow().getAttributes().windowAnimations = R.style.Fade;

        fsTitleId = findViewById(R.id.fsTitleId);
        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        editTxtForSavings = findViewById(R.id.edtTxtForSavingsId);
        editTxtForCiggs = findViewById(R.id.edtTxtForCiggarettedId);
//        editTxtForCurrency = findViewById(R.id.edtTxtForCurrencyId);
        confirmationButton = findViewById(R.id.confirmBtn);
//        btnSav = findViewById(R.id.savBtnId);
        //set ciggs
        fsTitleId.setTypeface(montSerratSemiBoldTypeface);
        textView3.setTypeface(montSerratLightTypeface);
        textView.setTypeface(montSerratItallicTypeface);
        editTxtForSavings.setTypeface(montSerratSemiBoldTypeface);
        editTxtForCiggs.setTypeface(montSerratSemiBoldTypeface);
//        editTxtForCurrency.setTypeface(montSerratSemiBoldTypeface);
        confirmationButton.setTypeface(montSerratSemiBoldTypeface);

//        editTxtForSavings.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
//        editTxtForCiggs.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);

        assert editTxtForCiggs != null;
        editTxtForCiggs.setInputType(InputType.TYPE_CLASS_NUMBER);
        assert editTxtForSavings != null;
        editTxtForSavings.setInputType(InputType.TYPE_CLASS_NUMBER);
//        assert editTxtForCurrency != null;
//        editTxtForCurrency.setInputType(InputType.TYPE_CLASS_TEXT);




        //set cigg
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCiggsPerDay();
                setSavings();
//                setCurrency();
//                overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
//                TransitionManager.beginDelayedTransition(transitionsContainer);
                TransitionManager.beginDelayedTransition(transitionsContainer,
                        new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(800));
                if (go && go2){

//                    mHandler.postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
                            visible = !visible;
                            editTxtForSavings.setVisibility(visible ? View.VISIBLE : View.GONE);
                            editTxtForCiggs.setVisibility(visible ? View.VISIBLE : View.GONE);
//                          Intent i = new Intent(FirstScreenActivity.this, IntroActivity.class);
                            //skip Intro screens and go into main
                            Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
//                          overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    currency = Currency.getInstance(new Locale("", telephonyManager.getNetworkCountryIso())).getCurrencyCode();
                    Log.d("taogX", currency+"");
                    editor.putString("currency", currency);
                    editor.apply();
                            startActivity(i);
                            finish();
                        }
//                    }, 2000);

//                }
            }
        });



//  //      set sum
//        editTxtForSavings.setInputType(InputType.TYPE_CLASS_NUMBER);
//        btnSav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setCiggsPerDay();
//            } //Oncreate
//            private void setCiggsPerDay() {
//                Editable editM = editTxtForSavings.getText();
//                nameSav = editTxtForSavings.getText().toString();
//                if (TextUtils.isEmpty(nameSav)) {
//                    editTxtForSavings.setError("Please input a sum");
//                    return;
//                }
//                long moneyInt = (long) Integer.parseInt(editM.toString());
//                editor.putLong(SAVINGS_FINAL, moneyInt);
//                editor.apply();
//                Log.d("INTROTAO", "moneyInt saved in INTROACTIVITY ? :  " + moneyInt);
//                btnSav.setEnabled(false);
//            }
//        });
//        Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
//        startActivity(i);

    }
//    @Optional
//    @OnClick(R.id.ciggBtnId)
//    void checkSum(){
//        try {
//            Editable editM = editTxtForSavings.getText();
//
////        Editable editM = editTxtForSavings.getText();
//        nameSav = editTxtForSavings.getText().toString();
//        if (TextUtils.isEmpty(nameSav)) {
//            editTxtForSavings.setError("Please input a sum");
//            return;
//        }
//        long moneyInt = (long) Integer.parseInt(editM.toString());
//        editor.putLong(SAVINGS_FINAL, moneyInt);
//        editor.apply();
//        Log.d("INTROTAO", "money saved in INTROACTIVITY ? :  " + moneyInt);
////                Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
////                startActivity(i);
//        } catch (NullPointerException e){e.printStackTrace();}
//    }
//    @Optional
//    @OnClick(R.id.ciggBtnId)
//    void checkCigg(){
//        try {
//        Editable editM = editTxtForCiggs.getText();
//        nameCigg = editTxtForCiggs.getText().toString();
//        if (TextUtils.isEmpty(nameCigg)) {
//            editTxtForCiggs.setError("Please input a cigg number");
//            return;
//        }
//        int cigInt = Integer.parseInt(editM.toString());
//        editor.putInt(INITIAL_CIGG_PER_DAY, cigInt);
//        editor.apply();
//        Log.d("INTROTAO", "cigg saved in INTROACTIVITY ? :  " + cigInt);
////                Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
////                startActivity(i);
//        } catch (NullPointerException e){e.printStackTrace();}
//    }

//    private void setCurrency(){
//        currency = editTxtForCurrency.getText().toString();
//        if (TextUtils.isEmpty(currency)) {
//            go = false;
//            editTxtForCurrency.setError("Please input a currency");
//            confirmationButton.setEnabled(true);
//            return;
//        } else if (!TextUtils.equals("$", currency)) {
//            go = false;
//            editTxtForCurrency.setError("Please input a valid currency");
//            confirmationButton.setEnabled(true);
//            return;
//        } else if (!TextUtils.equals("€", currency)) {
//                go = false;
//                editTxtForCurrency.setError("Please input a valid currency");
//                confirmationButton.setEnabled(true);
//                return;
//        } else {
//            if (go2){
//                confirmationButton.setEnabled(false);
//            }
//            go = true;
//        }
//        editor.putString("currency", currency);
//        editor.apply();
//        Log.d("INTROTAO2", "currency saved in INTROACTIVITY ? :  " + currency);
//    }

    private void setSavings(){
        Editable editM = editTxtForSavings.getText();
        nameSav = editTxtForSavings.getText().toString();
        if (TextUtils.isEmpty(nameSav)) {
            go = false;
            editTxtForSavings.setError("Please input a sum");
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
