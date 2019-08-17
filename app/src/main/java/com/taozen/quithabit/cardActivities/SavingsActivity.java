package com.taozen.quithabit.cardActivities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.anupcowkur.herebedragons.SideEffect;
import com.taozen.quithabit.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taozen.quithabit.utils.Constants.SharedPreferences.SAVINGS_FINAL;

public class SavingsActivity extends AppCompatActivity {
    @BindView(R.id.savingsTxt) TextView savingsTxt;
    @BindView(R.id.addSavingsBtnId) Button otherSavingsBtn;
    @BindView(R.id.editTxtSavingsId) EditText editTxtSavings;
    long otherIntSavings;
    long finalSum;
    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        ButterKnife.bind(SavingsActivity.this);

        getWindow().setStatusBarColor(ContextCompat.getColor(SavingsActivity.this, R.color.white));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTxtSavings.setInputType(InputType.TYPE_CLASS_NUMBER);
        getIntentOrPrefsAndStore();

        otherSavingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    otherIntSavings = Integer.parseInt(String.valueOf(editTxtSavings.getText()));
                    finalSum = finalSum + otherIntSavings;
                    savingsTxt.setText("Your total savings: " + finalSum + " $");
                    Log.d("LOGGTAO", "savings from onClick: " + finalSum);
                    editor.putLong(SAVINGS_FINAL, finalSum);
                    editor.apply();
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
