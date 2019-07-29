package com.taozen.quithabit.ProgressCard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import java.util.Objects;
import java.util.prefs.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingsActivity extends AppCompatActivity {

    @BindView(R.id.savingsTxt) TextView savingsTxt;
    @BindView(R.id.addSavingsBtnId) Button otherSavingsBtn;
    @BindView(R.id.editTxtSavingsId) EditText editTxtSavings;
    String savingsString = null;
    int otherIntSavings;
    //shared pref
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        ButterKnife.bind(SavingsActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

//        FromHerokuWithRetrofit.resp = FromHerokuWithRetrofit.returnStringFromServerWithRetrofit(2);
//        Log.d("RETROFIT", "response: " + FromHerokuWithRetrofit.resp);

        editTxtSavings.setInputType(InputType.TYPE_CLASS_NUMBER);

        otherSavingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherIntSavings = Integer.parseInt(String.valueOf(editTxtSavings.getText()));
                addOtherSavingsMethod();
            }
        });
        getValueOfSavings();
    }

    private void addOtherSavingsMethod() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                savingsString = String.valueOf(
                        intent.getIntExtra("savingsFinal", 0));
                int finalSum = Integer.parseInt(savingsString)+otherIntSavings;
                savingsTxt.setText("Your total savings: " + finalSum + " $");
                Log.d("LOGGTAO", "savings: " + finalSum);
                editor.putInt("savingsFinal", finalSum);
                editor.apply();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //fetching value from MainActivity
    private void getValueOfSavings() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                savingsString = String.valueOf(
                                intent.getIntExtra("savingsFinal", 0));
                savingsTxt.setText("Your total savings: " + savingsString + " $");
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
}
