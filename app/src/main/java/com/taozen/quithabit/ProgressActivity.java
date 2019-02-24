package com.taozen.quithabit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity extends AppCompatActivity {
    public static final String TAG = ProgressActivity.class.getSimpleName();

    @BindView(R.id.progressTextViewId) TextView progressPercentView;
    @BindView(R.id.percentImageId) ImageView percentImageView;
    @BindView(R.id.apiText) TextView apiText;
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.tvErrorId) TextView errorText;
    boolean dataIsSet = false;
    String valueOfpercent = null;
    String tip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(ProgressActivity.this);
        Log.d("TAGG", "onCreate created ");
        //getUrl

        getFirebaseData();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!dataIsSet){
                            progressBarLoading.setVisibility(View.VISIBLE);
                            percentImageView.setVisibility(View.INVISIBLE);
                            errorText.setVisibility(View.INVISIBLE);
                            apiText.setVisibility(View.INVISIBLE);
                        } else {
                            apiText.setVisibility(View.VISIBLE);
                            progressBarLoading.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }, 100, 1000);

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

//    //network task to run in background
//    public class BooksQueryTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressBarLoading.setVisibility(View.INVISIBLE);
//            percentImageView.setVisibility(View.VISIBLE);
//            //if no internet connection or error retrieving data
////            if (!dataIsSet) {
////                apiText.setVisibility(View.INVISIBLE);
////                errorText.setVisibility(View.VISIBLE);
////                percentImageView.setVisibility(View.INVISIBLE);
////            } else {
////                apiText.setVisibility(View.VISIBLE);
////                errorText.setVisibility(View.INVISIBLE);
////            }
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            Log.d("TAGG", "working inside doinbrackground " + Thread.currentThread().getName());
//
//            if (dataIsSet)
//                apiText.setVisibility(View.VISIBLE);
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            if (!dataIsSet){
//                progressBarLoading.setVisibility(View.VISIBLE);
//                percentImageView.setVisibility(View.INVISIBLE);
//                errorText.setVisibility(View.INVISIBLE);
//                apiText.setVisibility(View.INVISIBLE);
//            }
//            getFirebaseData();
//        }
//    }

    private void getFirebaseData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("books").child("2");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String key = ds.getValue(String.class);
                            Log.d("TAGG", " retrieved some data " + key);
                        }
                        Log.d("TAGG", " retrieved some data " + dataSnapshot.getValue(String.class));
                        tip = dataSnapshot.getValue(String.class);
                        apiText.setText(tip);
                        dataIsSet = true;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                };
                myRef.addValueEventListener(valueEventListener);
            }
        });

    }

}
