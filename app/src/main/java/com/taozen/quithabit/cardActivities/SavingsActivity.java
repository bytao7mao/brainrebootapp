package com.taozen.quithabit.cardActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taozen.quithabit.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;

public class SavingsActivity extends AppCompatActivity {
    @BindView(R.id.savingsTxt) TextView savingsTxt;
    @BindView(R.id.addSavingsBtnId) Button otherSavingsBtn;
    @BindView(R.id.editTxtSavingsId) EditText editTxtSavings;
    @BindView(R.id.savings_TitleId) TextView titleTxt;
    long otherIntSavings;
    long finalSum;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_savings);
        ButterKnife.bind(SavingsActivity.this);

        getWindow().setStatusBarColor(ContextCompat.getColor(SavingsActivity.this, R.color.white));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //fonts
        montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
        montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
        montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
        montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
        montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");

        savingsTxt.setTypeface(montSerratMediumTypeface);
        editTxtSavings.setTypeface(montSerratMediumTypeface);
        otherSavingsBtn.setTypeface(montSerratMediumTypeface);
        titleTxt.setTypeface(montSerratMediumTypeface);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        otherSavingsBtn.setEnabled(true);
        editTxtSavings.setInputType(InputType.TYPE_CLASS_NUMBER);
        getIntentOrPrefsAndStore();

        otherSavingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    otherIntSavings = Integer.parseInt(String.valueOf(editTxtSavings.getText()));
                    finalSum = finalSum + otherIntSavings;
                    savingsTxt.setText("Total savings: " + finalSum + " $");
                    Log.d("LOGGTAO", "savings from onClick: " + finalSum);
                    editor.putLong(SAVINGS_FINAL, finalSum);
                    long tempLong = otherIntSavings;
                    editor.putLong("tempLong", tempLong);
                    editor.apply();
//                    otherSavingsBtn.setEnabled(false);
//                    editTxtSavings.setEnabled(false);
//                    editTxtSavings.setInputType(InputType.TYPE_NULL);
                    otherSavingsBtn.setVisibility(View.GONE);
                    editTxtSavings.setVisibility(View.GONE);
                    titleTxt.setText("Well done!");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
    });
}

    //fetching value from MainActivity
        private void getIntentOrPrefsAndStore() {
            try {
                Intent intent = getIntent();
                if (intent != null) {
                    //retrieve by intent(outside activity)
                    finalSum = intent.getLongExtra(SAVINGS_FINAL, -1);
                    Log.d("LOGGTAO", "get from intent ? = " + finalSum);
                    savingsTxt.setText("Your total savings: " + finalSum + " $");
                } else {
                    //retrieve by prefs (inside activity)
                    finalSum = preferences.getLong(SAVINGS_FINAL, -1);
                    Log.d("LOGGTAO", "get from preferences ? = " + finalSum);
                    savingsTxt.setText("Your total savings: " + finalSum + " $");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }//fetching value from main activity[END]


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOGGTAO", "hello onResume!");
        getIntentOrPrefsAndStore();
    }
}
