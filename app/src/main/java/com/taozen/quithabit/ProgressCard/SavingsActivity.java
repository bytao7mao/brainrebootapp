package com.taozen.quithabit.ProgressCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.taozen.quithabit.Obj.Books;
import com.taozen.quithabit.R;
import com.taozen.quithabit.Retrofit.FromHerokuWithRetrofit;
import com.taozen.quithabit.Retrofit.MessageService;
import com.taozen.quithabit.Retrofit.ServiceBuilder;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.taozen.quithabit.Retrofit.FromHerokuWithRetrofit.getValueFromHerokuServer;

public class SavingsActivity extends AppCompatActivity {

    @BindView(R.id.savingsTxt)
    TextView savingsTxt;

    String savingsString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        ButterKnife.bind(SavingsActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getValueFromHerokuServer();

        getValueOfPercent();
    }

    private String setFormatForSavings(String thesave){
        String intSaving = String.valueOf(thesave);
        Log.d("progress", "intSaving " + intSaving);
        String formatTheSave = getString(R.string.total_savings, intSaving);
        Log.d("progress", "formatTheSave " + formatTheSave);
        return formatTheSave;

    }

//    private void getValueFromHerokuServer(){
//        //RETROFIT
//        MessageService taskService = ServiceBuilder.builderService(MessageService.class);
//        //we get id of book from heroku server
//        Call<Books> call = taskService.getFirstBook(1);
//        call.enqueue(new Callback<Books>() {
//            @Override
//            public void onResponse(Call<Books> call, Response<Books> response) {
//                if (response.isSuccessful()){
//                    String responseFromRetrofit = response.body().getName();
//                    Log.d("RETROFIT", "response = " + responseFromRetrofit);
//                    //if server is dead
//                } else if (response.code() == 404) {
//                    Log.d("RETROFIT", "response = " + response.code());
//                } else {
//                    Log.d("RETROFIT", "response = " + response.code());
//                }
//            }
//            @Override
//            public void onFailure(Call<Books> call, Throwable t) {
//            }
//        });
//    }//RETROFIT[END]


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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
