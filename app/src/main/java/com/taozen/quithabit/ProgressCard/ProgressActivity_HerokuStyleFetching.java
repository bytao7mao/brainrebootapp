package com.taozen.quithabit.ProgressCard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taozen.quithabit.Obj.Books;
import com.taozen.quithabit.R;
import com.taozen.quithabit.Retrofit.FromHerokuWithRetrofit;
import com.taozen.quithabit.Retrofit.MessageService;
import com.taozen.quithabit.Retrofit.ServiceBuilder;
import com.taozen.quithabit.Utils.MyHttpCoreAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressActivity_HerokuStyleFetching extends AppCompatActivity {
    String res = "";

    @BindView(R.id.progressTextViewId)
    TextView progressPercentTextView;
    @BindView(R.id.percentImgId)
    ImageView percentImageView;
    @BindView(R.id.errorImageId)
    ImageView errorImageId;
    @BindView(R.id.outputId)
    TextView outputText;
    @BindView(R.id.loadingProgressId)
    ProgressBar progressBarLoading;
    @BindView(R.id.loadingProgressId2)
    ProgressBar progressBarLoading2;
    @BindView(R.id.loadingProgressId3)
    ProgressBar progressBarLoading3;
    @BindView(R.id.tvErrorId)
    TextView errorText;
//    @BindView(R.id.percentImgId2)
//    ImageView percentImageView2;

    Button myBtn;
    List<MyAsyncTask> tasks;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String valueOfpercent = null;

    public static final String HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS = "https://pyflasktao.herokuapp.com/books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress__heroku_style_fetching);

        final View parentLayout = findViewById(android.R.id.content);
        ButterKnife.bind(ProgressActivity_HerokuStyleFetching.this);

        progressBarLoading3.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(ProgressActivity_HerokuStyleFetching.this);
        editor = preferences.edit();

        myBtn = findViewById(R.id.startBtnId);
        outputText.setMovementMethod(new ScrollingMovementMethod());

        tasks = new ArrayList<>();
        final Random ran = new Random();

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    int i = ran.nextInt(4) + 1;
                    requestDataById(HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS, i);
                } else {
                    errorText.setVisibility(View.VISIBLE);
                    errorImageId.setVisibility(View.VISIBLE);
                    percentImageView.setVisibility(View.INVISIBLE);
//                    percentImageView2.setVisibility(View.INVISIBLE);
                    outputText.setText("ERROR fortyfour :(");
                    Snackbar.make(parentLayout, "NO INTERNET CONNECTION!", Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(ProgressActivity_HerokuStyleFetching.this, "Network isn't available", Toast.LENGTH_LONG).show();
                }
            }
        });

        //get value from MainActivity of percent
        getValueOfPercent();
        //change the coresponding image for percent
        condForImageLevelMode();

    }//onCreate[END]

    private void requestDataById(String url, int id) {
        MyAsyncTask task = new MyAsyncTask();
        task.execute(url + "/" + id);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }//isOnline[END]

    //condition to update percent image, we use levels.xml layout and change the image accordingly to the coresponding level
    private void condForImageLevelMode(){
        int level = 0;
        getValueOfPercent();
        if (Integer.parseInt(valueOfpercent) < 11) {
            level += 10;
        } else if (Integer.parseInt(valueOfpercent) < 21) {
            level += 20;
        } else if (Integer.parseInt(valueOfpercent) < 36) {
            level += 30;
        } else if (Integer.parseInt(valueOfpercent) < 51) {
            level += 50;
        } else if (Integer.parseInt(valueOfpercent) < 66) {
            level += 60;
        } else if (Integer.parseInt(valueOfpercent) < 76) {
            level += 70;
        } else if (Integer.parseInt(valueOfpercent) < 96) {
            level += 90;
        } else if (Integer.parseInt(valueOfpercent) == 100) {
            level += 100;
        }
        percentImageView.setImageLevel(level);
//        percentImageView2.setImageLevel(level);
    }//condForImageLevelMode[END]

    //fetching value from MainActivity
    private void getValueOfPercent() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                valueOfpercent =
                        String.valueOf(
                                intent.getIntExtra
                                        ("pro", 0));
                Log.d("progress", "value of percent " + valueOfpercent);
                progressPercentTextView.setText(valueOfpercent+"%");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }//fetching value from main activity[END]

    protected void updateDisplayString(String message) {
        outputText.append(message + "\n");
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            updateDisplayString("Starting to fetch data from heroku ...");
            res = FromHerokuWithRetrofit.returnStringFromServerWithRetrofit(2);
            if (tasks.size() == 0) {
                progressBarLoading.setVisibility(View.VISIBLE);
                progressBarLoading2.setVisibility(View.VISIBLE);
                progressBarLoading3.setVisibility(View.VISIBLE);
                percentImageView.setVisibility(View.INVISIBLE);
//                percentImageView2.setVisibility(View.INVISIBLE);
            }
            //if we click we add a task
            tasks.add(this);

        }//onPreExecute[END]

        @Override
        protected String doInBackground(String... params) {
            try {
                //using GSON
                JsonParser parser = new JsonParser();
                //using MyHttpManager getData static method
//              String content = MyHttpManager.getData(params[0]);

                //using MyHttpCoreAndroid
                String content = MyHttpCoreAndroid.getData(params[0]);
                JsonElement rootNode = parser.parse(content);
                JsonObject details = rootNode.getAsJsonObject();
                JsonElement nameNode = details.get("name");
                return nameNode.getAsString();


//                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }//using GSON[END]
        }//doInBackground[END]

        @Override
        protected void onPostExecute(String result) {
            //using raw JSON PARSER
            updateDisplayString(result);
            //we get rid of the task that we created
            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBarLoading.setVisibility(View.INVISIBLE);
                progressBarLoading2.setVisibility(View.INVISIBLE);
                progressBarLoading3.setVisibility(View.INVISIBLE);
                percentImageView.setVisibility(View.VISIBLE);
//                percentImageView2.setVisibility(View.VISIBLE);
            }
            if (result == null) {
                Toast.makeText(ProgressActivity_HerokuStyleFetching.this, "Can't connect to web service",
                        Toast.LENGTH_LONG).show();
            }
        }//onPostExecute[END]

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplayString(values[0]);
        }//onProgressUpdate[END]
    }//MyAsyncTask[END]

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}//progressActivityClass[END]













//condition for setting proper image
//    private void conditionForImagePercent(){
//        getValueOfPercent();
//        if (Integer.parseInt(valueOfpercent) < 11) {
//            percentImageView.setImageResource(R.drawable.ten);
//        } else if (Integer.parseInt(valueOfpercent) < 21) {
//            percentImageView.setImageResource(R.drawable.twenty);
//        } else if (Integer.parseInt(valueOfpercent) < 36) {
//            percentImageView.setImageResource(R.drawable.thirty);
//        } else if (Integer.parseInt(valueOfpercent) < 51) {
//            percentImageView.setImageResource(R.drawable.fifty);
//        } else if (Integer.parseInt(valueOfpercent) < 66) {
//            percentImageView.setImageResource(R.drawable.sixty);
//        } else if (Integer.parseInt(valueOfpercent) < 76) {
//            percentImageView.setImageResource(R.drawable.seventeen);
//        } else if (Integer.parseInt(valueOfpercent) < 96) {
//            percentImageView.setImageResource(R.drawable.eightfive);
//        } else if (Integer.parseInt(valueOfpercent) == 100) {
//            percentImageView.setImageResource(R.drawable.hundred);
//        }
//    }//condition for setting proper image[END]
