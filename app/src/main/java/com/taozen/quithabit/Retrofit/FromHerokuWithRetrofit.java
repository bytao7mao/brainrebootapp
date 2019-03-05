package com.taozen.quithabit.Retrofit;

import android.util.Log;

import com.taozen.quithabit.Obj.Books;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FromHerokuWithRetrofit {
    public static void getValueFromHerokuServer(){
        //RETROFIT
        MessageService taskService = ServiceBuilder.builderService(MessageService.class);
        //we get id of book from heroku server
        Call<Books> call = taskService.getFirstBook(1);
        call.enqueue(new Callback<Books>() {
            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                if (response.isSuccessful()){
                    //to see more info type OkHttp in searchbar of log
                    String responseFromRetrofit = response.body().getName();
                    Log.d("RETROFIT", "response = " + responseFromRetrofit);
                    //if server is dead
                } else if (response.code() == 404) {
                    Log.d("RETROFIT", "response = " + response.code());
                } else {
                    Log.d("RETROFIT", "response = " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Books> call, Throwable t) {
            }
        });
    }//RETROFIT[END]
}
