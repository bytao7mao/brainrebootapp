package com.taozen.quithabit;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.taozen.quithabit.MainActivity.INITIAL_CIGG_PER_DAY;
import static com.taozen.quithabit.MainActivity.SAVINGS_FINAL;

public class FirstScreenActivity extends AppCompatActivity {
//    @Nullable
//    @BindView(R.id.editTxtSavingsId) EditText editTxtForSavings;
//    @Nullable
//    @BindView(R.id.edtTxtForCiggarettedId) EditText editTxtForCiggs;
//    @BindView(R.id.ciggBtnId) Button btnCigg;
//    @BindView(R.id.savBtnId) Button btnSav;

    TextView textView;
    EditText editTxtForSavings, editTxtForCiggs;
    Button btnCigg;
    String nameCigg, nameSav;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
//        ButterKnife.bind(FirstScreenActivity.this);
        go = false;go2 = false;

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
        TextView mTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        mTitle.setTypeface(montSerratSemiBoldTypeface);
//        mTitle.setTypeface(montSerratSemiBoldTypeface);
//        getSupportActionBar().setLogo(R.drawable.logowalk);
//        toolbar.setTitleTextAppearance(this, R.style.blabla);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.logowalk);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(FirstScreenActivity.this);
        editor = preferences.edit();


        textView = findViewById(R.id.fsTitleId);
        editTxtForSavings = findViewById(R.id.edtTxtForSavingsId);
        editTxtForCiggs = findViewById(R.id.edtTxtForCiggarettedId);
        btnCigg = findViewById(R.id.ciggBtnId);
//        btnSav = findViewById(R.id.savBtnId);
        //set ciggs
        textView.setTypeface(montSerratSemiBoldTypeface);
        editTxtForSavings.setTypeface(montSerratSemiBoldTypeface);
        editTxtForCiggs.setTypeface(montSerratSemiBoldTypeface);
        btnCigg.setTypeface(montSerratSemiBoldTypeface);

//        editTxtForSavings.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
//        editTxtForCiggs.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);


        assert editTxtForCiggs != null;
        editTxtForCiggs.setInputType(InputType.TYPE_CLASS_NUMBER);
        assert editTxtForSavings != null;
        editTxtForSavings.setInputType(InputType.TYPE_CLASS_NUMBER);



        //set cigg
        btnCigg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCiggsPerDay();
                setSavings();
                if (go && go2){
                    Intent i = new Intent(FirstScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }
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

    private void setSavings(){
        Editable editM = editTxtForSavings.getText();
        nameSav = editTxtForSavings.getText().toString();
        if (TextUtils.isEmpty(nameSav)) {
            go = false;
            editTxtForSavings.setError("Please input a sum");
            return;
        } else {
            if (go2){
                btnCigg.setEnabled(false);
            }
            go = true;
        }
        long moneyInt = (long) Integer.parseInt(editM.toString());
        editor.putLong(SAVINGS_FINAL, moneyInt);
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
                btnCigg.setEnabled(false);
            }
            go2 = true;
        }
        int cigInt = Integer.parseInt(editM.toString());
        editor.putInt(INITIAL_CIGG_PER_DAY, cigInt);
        editor.apply();
        Log.d("INTROTAO", "cigg saved in INTROACTIVITY ? :  " + cigInt);
    }
}
