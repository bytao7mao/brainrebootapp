package com.taozen.quithabit.ProgressCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingsActivity extends AppCompatActivity {

    @BindView(R.id.savingsTxt)
    TextView savingsTxt;

    String savingsString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        ButterKnife.bind(SavingsActivity.this);

        getValueOfPercent();
    }

    private String setFormatForSavings(String thesave){
        String intSaving = String.valueOf(thesave);
        Log.d("progress", "intSaving " + intSaving);
        String formatTheSave = getString(R.string.total_savings, intSaving);
        Log.d("progress", "formatTheSave " + formatTheSave);
        return formatTheSave;

    }

    //fetching value from MainActivity
    private void getValueOfPercent() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                savingsString =
                        String.valueOf(
                                intent.getIntExtra
                                        ("savings", 0));
                Log.d("progress", "getValueOfPercent " + savingsString);
                String result = setFormatForSavings(savingsString);
                Log.d("progress", "sresult = " + result);
                savingsTxt.setText(result);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }//fetching value from main activity[END]
}
