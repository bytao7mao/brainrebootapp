package com.taozen.quithabit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity extends AppCompatActivity {

    @BindView(R.id.progressTextViewId) TextView progressPercentView;
    @BindView(R.id.percentImageId) ImageView percentImageView;
    String valueOfpercent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(ProgressActivity.this);
        //get value from MainActivity of percent
        getValueOfPercent();
        //change the coresponding image for percent
        conditionForImagePercent();
    }

    private void getValueOfPercent() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                valueOfpercent =
                        String.valueOf(
                                intent.getIntExtra
                                        ("pro", 0));
                Log.d("progress", "value of percent " + valueOfpercent);
                progressPercentView.setText(valueOfpercent+"%");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void conditionForImagePercent(){
        getValueOfPercent();
        if (Integer.parseInt(valueOfpercent) < 11) {
            percentImageView.setImageResource(R.drawable.ten);
        } else if (Integer.parseInt(valueOfpercent) < 21) {
            percentImageView.setImageResource(R.drawable.twenty);
        } else if (Integer.parseInt(valueOfpercent) < 36) {
            percentImageView.setImageResource(R.drawable.thirty);
        } else if (Integer.parseInt(valueOfpercent) < 51) {
            percentImageView.setImageResource(R.drawable.fifty);
        } else if (Integer.parseInt(valueOfpercent) < 66) {
            percentImageView.setImageResource(R.drawable.sixty);
        } else if (Integer.parseInt(valueOfpercent) < 76) {
            percentImageView.setImageResource(R.drawable.seventeen);
        } else if (Integer.parseInt(valueOfpercent) < 96) {
            percentImageView.setImageResource(R.drawable.eightfive);
        } else if (Integer.parseInt(valueOfpercent) == 100) {
            percentImageView.setImageResource(R.drawable.hundred);
        }
    }

}
