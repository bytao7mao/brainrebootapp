package com.taozen.quithabit.ProgressCard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import com.taozen.quithabit.Utils.MyHttpCoreAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressActivity_HerokuStyleFetching extends AppCompatActivity {

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
    @BindView(R.id.tvErrorId)
    TextView errorText;

    Button myBtn;
    List<MyAsyncTask> tasks;
    List<Books> booksList;

    public static final String HTTPS_PYFLASKTAO_HEROKUAPP_COM_BOOKS = "https://pyflasktao.herokuapp.com/books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress__heroku_style_fetching);

        final View parentLayout = findViewById(android.R.id.content);
        ButterKnife.bind(ProgressActivity_HerokuStyleFetching.this);

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
                    outputText.setText("ERROR fortyfour :(");
                    Snackbar.make(parentLayout, "NO INTERNET CONNECTION!", Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(ProgressActivity_HerokuStyleFetching.this, "Network isn't available", Toast.LENGTH_LONG).show();
                }
            }
        });

    }//onCreate[END]

    private void requestData(String url) {
        MyAsyncTask task = new MyAsyncTask();
        task.execute(url);

    }//requestData[END]

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

    protected void updateDisplay() {
        if (booksList != null) {
            for (Books books : booksList) {
                outputText.append(books.getName() + "\n");
            }
        }
    }//updateDisplay[END]

    protected void updateDisplayString(String message) {
        outputText.append(message + "\n");
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            updateDisplayString("Starting to fetch data from heroku ...");
            if (tasks.size() == 0) {
                progressBarLoading.setVisibility(View.VISIBLE);
                percentImageView.setVisibility(View.INVISIBLE);
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
                percentImageView.setVisibility(View.VISIBLE);
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

}//progressActivityClass[END]
