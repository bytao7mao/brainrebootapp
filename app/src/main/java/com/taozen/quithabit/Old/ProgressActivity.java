package com.taozen.quithabit.Old;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity extends AppCompatActivity {
    public static final String TAG = ProgressActivity.class.getSimpleName();

    @BindView(R.id.progressTextViewId) TextView progressPercentView;
    @BindView(R.id.percentImgId) ImageView percentImageView;
    @BindView(R.id.errorImageId) ImageView errorImageId;
    @BindView(R.id.apiText) TextView apiText;
    @BindView(R.id.loadingProgressId) ProgressBar progressBarLoading;
    @BindView(R.id.tvErrorId) TextView errorText;

    String valueOfpercent = null;
    String[] valuesFromFirebase = new String[2];
    ArrayList<String> valuesArrayListfromFirebase;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<MyAsyncTask> myAsyncTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        View parentLayout = findViewById(android.R.id.content);
        ButterKnife.bind(ProgressActivity.this);
        progressBarLoading.setVisibility(View.INVISIBLE);//redundant
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(ProgressActivity.this);
        editor = preferences.edit();
        Log.d("TAGG", "onCreate created ");
        //executing task to retrieve data from firebase
        myAsyncTasks = new ArrayList<>();
        errorImageId.setVisibility(View.INVISIBLE);
        if (isOnline()){
            errorText.setVisibility(View.INVISIBLE);
            startAsyncTask();
        } else {
            errorText.setVisibility(View.VISIBLE);
            errorImageId.setVisibility(View.VISIBLE);
            percentImageView.setVisibility(View.INVISIBLE);
            apiText.setText("ERROR fortyfour :(");
            Snackbar.make(parentLayout, "NO INTERNET CONNECTION!", Snackbar.LENGTH_LONG).show();
        }

        //get value from MainActivity of percent
        getValueOfPercent();
        //change the coresponding image for percent
        conditionForImagePercent();
    }

    private void startAsyncTask(){
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
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

    private String[] getFirebaseDataAsynchronous2(){
        valuesArrayListfromFirebase = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("books");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getValue(String.class);
                    Log.d("TAGG", " retrieved some data " + key);
                    valuesArrayListfromFirebase.add(key);
                }
                for (int i = 0; i < 2; i++) {
                    valuesFromFirebase[i] = valuesArrayListfromFirebase.get(i);
                    Log.d("TAGG", "firebaseMethod val = " + valuesFromFirebase[i]);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(valueEventListener);

        return valuesFromFirebase;
    }

    protected void updateDisplay(String message) {
        apiText.setText(message + " ");
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            updateDisplay("Starting to fetch data from firebase ...");
            if (myAsyncTasks.size() == 0) {
                progressBarLoading.setVisibility(View.VISIBLE);
                percentImageView.setVisibility(View.INVISIBLE);
            }
            myAsyncTasks.add(this);
        }

        @Override
        protected String doInBackground(String... voids) {
            voids = getFirebaseDataAsynchronous2();
            for (int i = 0; i < voids.length; i++) {
                Log.d("TAGG", "itar " + voids[i]);
                publishProgress(voids[i]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return Arrays.toString(voids);
        }

        @Override
        protected void onPostExecute(String result) {
            updateDisplay(result);
            myAsyncTasks.remove(this);
            if (myAsyncTasks.size() == 0) {
                progressBarLoading.setVisibility(View.INVISIBLE);
                percentImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
            updateDisplay("Starting to fetch data from firebase ...");
        }

    }

}
