package com.taozen.quithabit.Old;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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
import com.taozen.quithabit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity_OLD extends AppCompatActivity {
    public static final String TAG = ProgressActivity.class.getSimpleName();

    @BindView(R.id.progressTextViewId) TextView progressPercentView;
    @BindView(R.id.percentImgId) ImageView percentImageView;
    @BindView(R.id.apiText) TextView apiText;
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.tvErrorId) TextView errorText;

    String valueOfpercent = null;
    String tip = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(ProgressActivity_OLD.this);
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(ProgressActivity_OLD.this);
        editor = preferences.edit();
        Log.d("TAGG", "onCreate created ");
        //executing task to retrieve data from firebase
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
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
    private void getFirebaseDataAsynchronous(){
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
                Log.d("Firebasetao", " tip = " + tip);
                editor.putString("tip", tip);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                editor.putString("tip", "canceled");
                editor.apply();
            }
        };
        myRef.addValueEventListener(valueEventListener);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            apiText.setText("Starting to fetch data from firebase ...");
            progressBarLoading.setVisibility(View.VISIBLE);
            percentImageView.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {Thread.sleep(1000);}catch (InterruptedException e){e.printStackTrace();}
            getFirebaseDataAsynchronous();
//            try {Thread.sleep(1000);}catch (InterruptedException e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBarLoading.setVisibility(View.INVISIBLE);
            if (isNetworkConnected()){
                tip = preferences.getString("tip", tip);
                Log.d("Firebasetao", "connected");
                apiText.setText(tip);
                percentImageView.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.INVISIBLE);
            } else {
                tip = "not connected ... sorry :(";
                Log.d("Firebasetao", "not connected");
                apiText.setText(tip);
                percentImageView.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
//            apiText.setText(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            apiText.setText("no wifi");
        }
    }

}
